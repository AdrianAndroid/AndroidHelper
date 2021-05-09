package cn.kuwo.pp.ui.friend

//import cn.kuwo.common.dialog.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import cn.kuwo.common.app.App
import cn.kuwo.common.base.BaseFragment
import cn.kuwo.common.dialog.DialogUtils
import cn.kuwo.common.event.LoginEvent
import cn.kuwo.common.util.CustomLoadMoreView
import cn.kuwo.common.util.L
import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.common.view.MultipleStatusView
import cn.kuwo.pp.R
import cn.kuwo.pp.event.UnreadMessageEvent
import cn.kuwo.pp.http.bean.voice.VoiceInfo
import cn.kuwo.pp.manager.FriendList.FriendList
import cn.kuwo.pp.manager.FriendList.FriendListItem
import cn.kuwo.pp.manager.FriendList.FriendListManager
import cn.kuwo.pp.manager.FriendList.FriendListManager.*
import cn.kuwo.pp.ui.chat.C2CChatFragment
import cn.kuwo.pp.ui.friend.adapter.FriendListAdapter
import cn.kuwo.pp.ui.mine.UserInfoFragment
import cn.kuwo.pp.ui.system.SystemMessageFragment
import com.tencent.imsdk.TIMManager
import com.tencent.imsdk.TIMMessage
import com.tencent.imsdk.TIMMessageListener
import kotlinx.android.synthetic.main.fragment_friend_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


/**
 * 交友列表, 通过type区分是那个列表
 */
class FriendListFragment : BaseFragment() {
    private lateinit var mMultipleStatusView: MultipleStatusView
    private lateinit var mAdapter: FriendListAdapter
    private lateinit var mPresenter: FriendListPresenter
    private var type: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friend_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        type = this.arguments!!.getInt("type", 0)

        mAdapter = FriendListAdapter(null)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mAdapter
        mAdapter.setEmptyView(R.layout.multiple_status_view, recyclerView)
        mMultipleStatusView = mAdapter.emptyView.findViewById(R.id.multiple_status_view)
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mAdapter.setPreLoadNumber(10)
        setPageFooterView()

        mPresenter = FriendListPresenter(this)

        addListeners()

        var list = FriendListManager.getInstance().getList(type)
        if (!list.isFinished) {
            mMultipleStatusView.showLoading()
        } else if (!list.isSuccess) {
            mMultipleStatusView.showError()
        } else {
            mAdapter.addData(list.data)
            mAdapter.loadMoreEnd()
            if (list.data.isEmpty()) {
                showEmptyTip()
            } else {
                checkExpireTime()
            }
        }
    }

    private fun initRefreshLayout() {
//        mSwipeRefreshLayout.setOnRefreshListener {
//            // 请求网络
//        }
    }

    private fun setPageFooterView() {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_friend_list_footer,
            recyclerView,
            false
        ) as TextView

        view.text = "tips:48小时内没有收到TA的回复你们就会擦肩而过"
        if (type == RELATION_BE_FOLLOWED) {
            view.text = "tips:48小时内没有收到你的回复他会转身离开"
        } else if (type == RELATION_BOTH) {
            view.text = "tips:这是你们的世界，尽情玩耍吧～"
        }
        mAdapter.setFooterView(view)
    }

    private fun addListeners() {
        // 每个条目的点击事件
        mAdapter.setOnItemClickListener { _, _, position ->
            val title = this.arguments?.getString("title", "")

            val getItem = mAdapter.getItem(position)
            getItem?.let { item ->
                if (item.systemMessage) {
                    startFromMain(SystemMessageFragment.newInstance()) // 系统消息永远在第一位的
                } else {
                    item.lastMessageTime = System.currentTimeMillis() // 点击了事件时候时间
                    startSingleTaskFromMain(
                        C2CChatFragment.newInstance(title!!, item.user.uid, item.user)
                    )
                    // 先显示正确的顺序，然后才更新列表，不要取反了
                    // FriendListManager.getInstance().getList(type).reDistinctAndSort()

                    // 应该给列表提前，而不是排序
                    onUpdateList(FriendListUpdated_event(type, true)) // 设置新数据

                    advanceAdapterPosition(position, item)
                    recyclerView.scrollToPosition(0) //滚动到第一个
                }
            }
        }
        mAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.ivUserImage -> startFromMain(
                    UserInfoFragment.newInstance(
                        mAdapter.getItem(
                            position
                        )!!.user, null
                    )
                )
                R.id.voice_play_btn -> mPresenter.onPlayButtonClicked(
                    mAdapter.getItem(position),
                    view as ImageView
                )
            }
        }
        // 重新加载
        mMultipleStatusView.setOnRetryClickListener {
            mMultipleStatusView.showLoading()
            if (App.DEBUG) L.L(javaClass, "mMultipleStatusView setOnRetryClickListener")
            FriendListManager.getInstance().getList(type).requestData()
        }
        mAdapter.setOnItemLongClickListener { _, _, position ->
            if (type == FriendListManager.RELATION_BE_FOLLOWED) { // 喜欢我的不能有删除操作
                return@setOnItemLongClickListener false
            } else {
                val item = mAdapter.getItem(position)
                if (item!!.systemMessage) {
                    return@setOnItemLongClickListener false
                }

                showDeleteDialog(mAdapter.getItem(position)?.user, position)
                return@setOnItemLongClickListener true
            }
        }

        TIMManager.getInstance().addMessageListener(object : TIMMessageListener {
            override fun onNewMessages(p0: MutableList<TIMMessage>?): Boolean {
                if (App.DEBUG) L.L(javaClass, "TIMManager", "onNewMessages")
                p0?.forEach {
                    val uid = it.conversation.peer
                    run loop@{
                        mAdapter.data.forEachIndexed { index, item ->
                            if (item.user.uid == uid) {
                                if (item.unReadMsgNum != it.conversation.unreadMessageNum) {
                                    item.unReadMsgNum = it.conversation.unreadMessageNum;
                                    FriendList.updateMessageInfo(item)
                                    mAdapter.data.removeAt(index)
                                    mAdapter.data.add(0, item)
                                    mAdapter.notifyDataSetChanged()
                                } else {
                                    FriendList.updateMessageInfo(item)
                                    mAdapter.notifyItemChanged(index)
                                }
                                return@loop
                            }
                        }
                    }
                }

                EventBus.getDefault().post(UnreadMessageEvent())
                return false
            }
        })
    }


    private fun advanceAdapterPosition(position: Int, item: FriendListItem) {
        // 先删掉原先列表的
        mAdapter.remove(position)
        //mAdapter.notifyItemRemoved(position) // 更新删除操作
        // 再将其加到第一位
        mAdapter.addData(0, item)
        //mAdapter.notifyItemInserted(0)
    }

    private fun showDeleteDialog(user: VoiceInfo?, position: Int) {
        DialogUtils.showDeleteFriends({ agree ->
            if (agree) {
                deleteFriend(user, position)
            }
        }, this)
//        AlertDialog.getInstance(_mActivity, "", "确定要删除好友吗？") { _, _ ->
//            deleteFriend(user, position)
//        }.show()
    }

    private fun deleteFriend(user: VoiceInfo?, position: Int) {
        if (App.DEBUG) L.L(javaClass, "deleteFriend")
        FriendListManager.getInstance()
            .delete(user?.uid, this, object : FriendListManager.IFriendListManagerResult {
                override fun IFriendListManagerResult_success(result: Int) {
                    UtilsCode.showShort("已删除好友")
                    mAdapter.remove(position)
                    if (mAdapter.data.isEmpty()) {
                        showEmptyTip()
                    }
                }

                override fun IFriendListManagerResult_failed(msg: String) {
                    UtilsCode.showShort("删除好友失败：$msg")
                }
            })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFollow(event: FriendListManager.OnFollow_event) {
        if (App.DEBUG) L.L(javaClass, "onFollow")
        if (event.status == type) {
            addOne(event.item)
        } else {
            deleteOne(event.item.user)
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDeleteFriend(event: FriendListManager.OnDeleteFriend_event) {
        if (App.DEBUG) L.L(javaClass, "onDeleteFriend")
        if (type == FriendListManager.RELATION_FOLLOWED
            || type == FriendListManager.RELATION_BOTH
        ) {
            deleteOne(event.item.user)
        } else if (event.status == type) {
            addOne(event.item)
        }
    }

    // 列表更新的时候
    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onUpdateList(event: FriendListManager.FriendListUpdated_event) {
        if (App.DEBUG) L.L(javaClass, "onUpdateList")
        if (event.type != type) { // 不是本条消息的话，就不操作
            return
        }
        if (!event.success) {
            mAdapter.setNewData(ArrayList<FriendListItem>())
            mMultipleStatusView.showError()
            return
        } else {
            mMultipleStatusView.showContent()
        }

        mAdapter.setNewData(FriendListManager.getInstance().getList(type).data)
        mAdapter.loadMoreEnd()

        if (mAdapter.data.isEmpty()) {
            showEmptyTip()
        }

        checkExpireTime()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onConversationRefresh(event: FriendListManager.ConversationRefresh_event) {
        L.m3("event.convs", event.convs.size)
        event.convs.forEach {
            run inside@{
                // 对应的id
                mAdapter.data.forEachIndexed { index, item ->
                    if (it.peer == item.user.uid) {
                        item.unReadMsgNum = it.unreadMessageNum;
                        FriendList.updateMessageInfo(item) // 刷新一下列表
                        mAdapter.data.removeAt(index)
                        mAdapter.data.add(0, item)
                        return@inside
                    }
                }
                if (FriendListManager.getInstance().getList(type).hasUser(it.peer)) {
                    val item = FriendListManager.getInstance().getList(type).getUser(it.peer)
                    mAdapter.data.add(0, item)
                }
            }
        }

        mAdapter.notifyDataSetChanged()
        EventBus.getDefault().post(UnreadMessageEvent())
    }

    public override fun onLoginEvent(event: LoginEvent?) {
        super.onLoginEvent(event)
        mAdapter.loadMoreEnd()
        mAdapter.setNewData(listOf())
    }

    public override fun onLoginSuccess() {
        if (App.DEBUG) L.L(javaClass, "onLoginSuccess")
        var list = FriendListManager.getInstance().getList(type)
        if (!list.isFinished) {
            mMultipleStatusView.showLoading()
        } else if (!list.isSuccess) {
            mMultipleStatusView.showError()
        } else {
            mAdapter.addData(list.data)
            mAdapter.loadMoreEnd()
            if (list.data.isEmpty()) {
                showEmptyTip()
            } else {
                checkExpireTime()
            }
        }
    }


    override fun onConnectChanged() {
        super.onConnectChanged()
        if ((mAdapter.data.isEmpty()) && UtilsCode.isConnected()) {
            // 重新请求网络
            FriendListManager.getInstance().getList(type).requestData()
        }
    }

    private fun addOne(newItem: FriendListItem) {
        run outside@{
            mAdapter.data.forEachIndexed { index, item ->
                if (item.user.uid == newItem.user.uid) {
                    recyclerView.scrollToPosition(index)
                    return@outside
                }
            }

            mAdapter.addData(0, newItem)
            recyclerView.scrollToPosition(0)
        }
    }

    private fun deleteOne(user: VoiceInfo) {
        run outside@{
            mAdapter.data.forEachIndexed { index, item ->
                if (item.user.uid == user.uid) {
                    mAdapter.remove(index)
                    return@outside
                }
            }
        }
        if (mAdapter.data.isEmpty()) {
            showEmptyTip()
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        checkExpireTime()
        if (mMultipleStatusView.isViewStatusError) {
            FriendListManager.getInstance().getList(type).requestData()
        }
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
    }

    override fun onChildSupportVisible() {
        super.onChildSupportVisible()
    }

    override fun onChildSupportInvisible() {
        super.onChildSupportInvisible()
    }

    var isExpireTimeChecked: Boolean = false // 本次是否已经检查完, 就检查一次???
    private fun checkExpireTime() {
        if (isExpireTimeChecked) {
            return
        }
        if (type == FriendListManager.RELATION_BOTH
            || type == FriendListManager.RELATION_BE_FOLLOWED
        ) {
            return
        }
        val l = mutableListOf<FriendListItem>()
        mAdapter.data.forEach {
            if (it.expireType == 2) {  // 0 未过期，1 超过24小时， 2 超过48小时
                l.add(it)
            }
        }
        if (l.isNotEmpty()) { // 有超过24小时的
            showExpireDlg(l)
        }
        isExpireTimeChecked = true
    }

    private fun showExpireDlg(l: List<FriendListItem>) {
        if (App.DEBUG) L.L(javaClass, "showExpireDlg")
        DialogUtils.showMissFriends({ agree ->
            if (agree) {
                l.forEach {
                    // 删掉所有的已经超过48小时的好友
                    FriendListManager.getInstance().delete(it.user.uid, this, null)
                }
            }
        }, this)


//        val dlgContent = "错过你是TA的损失，更有趣的灵魂在等你发现哦～"
//        var dlg: MaterialDialog? = null
//        dlg = AlertDialog.getSingleBtnInstance(activity, dlgContent, "知道了") {
//            dlg?.dismiss()
//            dlg = null
//            l.forEach {
//                // 删掉所有的已经超过48小时的好友
//                FriendListManager.getInstance().delete(it.user.uid, this, null)
//            }
//        }
//        dlg?.show();
    }

    private fun showEmptyTip() {
        if (type == RELATION_FOLLOWED) {
            mMultipleStatusView.showEmpty(
                R.drawable.empty_chat, "你喜欢的小朋友会在这里等你喔～\n" +
                        "但是╮(╯▽╰)╭\n" +
                        "48小时内没有收到TA的回复\n" +
                        "你们就会擦肩而过喔，\n" +
                        "努力说点什么，\n" +
                        "让TA看到你的爱意吧～"
            )
        } else if (type == RELATION_BE_FOLLOWED) {
            mMultipleStatusView.showEmpty(
                R.drawable.empty_chat, "TA们都喜欢你诶～请陛下翻牌子吧～\n" +
                        "但是╮(╯▽╰)╭\n" +
                        "48小时内没有收到你的回复\n" +
                        "TA就会难过的转身离开哦\n" +
                        "把握机会吧，珍惜TA的爱意～"
            )
        } else if (type == RELATION_BOTH) {
            mMultipleStatusView.showEmpty(
                R.drawable.empty_chat, "相爱容易，因为五官(*/ω＼*)\n" +
                        "相处不易，因为三观(◡ᴗ◡✿)"
            )
        } else if (type == RELATION_NONE) {
            mMultipleStatusView.showEmpty(
                R.drawable.empty_chat, "转角遇见TA, 也许会有奇妙的化学反应～\n" +
                        "但是╮(╯▽╰)╭\n" + "48小时内没有收到回复，你们就会擦肩而过喔，把握机会吧，享受这次相遇～"
            )
        }
    }


    ///////////////////////////////
    ///////////////////////////////
    //调试bug使用
    ///////////////////////////////
    ///////////////////////////////
    override fun getPrintClass(): Class<*> {
        return javaClass;
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }


}