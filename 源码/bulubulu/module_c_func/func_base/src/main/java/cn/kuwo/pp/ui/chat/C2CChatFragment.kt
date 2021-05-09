package cn.kuwo.pp.ui.chat

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import cn.kuwo.base.KwTimer
import cn.kuwo.common.app.App
import cn.kuwo.common.base.BaseFragment
import cn.kuwo.common.dialog.DialogUtils
import cn.kuwo.common.event.LikeAnimEvent
import cn.kuwo.common.util.ImageLoader
import cn.kuwo.common.util.L
import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.player.BuluPlayer
import cn.kuwo.player.PlayCallback
import cn.kuwo.player.PlayError
import cn.kuwo.pp.R
import cn.kuwo.pp.event.FriendChangeEvent
import cn.kuwo.pp.event.MessageReceiptEvent
import cn.kuwo.pp.http.bean.QuestionModel
import cn.kuwo.pp.http.bean.voice.VoiceInfo
import cn.kuwo.pp.manager.CustomizeMessage
import cn.kuwo.pp.manager.FriendList.FriendList
import cn.kuwo.pp.manager.FriendList.FriendListManager
import cn.kuwo.pp.manager.GiftManager
import cn.kuwo.pp.manager.UserInfoManager
import cn.kuwo.pp.ui.mine.UserInfoFragment
import cn.kuwo.pp.ui.report.ReportDialog
import cn.kuwo.pp.util.UserActionLog
import com.elbbbird.android.analytics.AnalyticsUtils
import com.elbbbird.android.analytics.UmengEventId
import com.gyf.barlibrary.ImmersionBar
import com.tencent.imsdk.TIMConversationType
import com.tencent.imsdk.TIMManager
import com.tencent.imsdk.TIMMessage
import com.tencent.qcloud.uikit.business.chat.c2c.model.C2CChatManager
import com.tencent.qcloud.uikit.business.chat.model.MessageInfo
import com.tencent.qcloud.uikit.business.chat.model.MessageInfoUtil
import com.tencent.qcloud.uikit.business.chat.view.widget.ChatListEvent
import com.tencent.qcloud.uikit.common.component.action.PopMenuAction
import com.tencent.qcloud.uikit.common.component.audio.UIKitAudioArmMachine
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_personal_chat.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap


/**
 * 聊天主页面
 */
class C2CChatFragment : BaseFragment() {

    companion object {
        fun newInstance(title: String, chatId: String, voiceInfo: VoiceInfo?): C2CChatFragment {
            val args = Bundle()
            args.putString("title", title)
            args.putString("chatId", chatId)
            args.putParcelable("voiceInfo", voiceInfo)
            val fragment = C2CChatFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val TAG: String = "CHAT"
    private lateinit var onlineTimer: KwTimer
    private var isFromMatch: Boolean = false
    private var pickSameCount = 0;
    private var isViewEnable: Boolean = false
    private val pkPickMap: HashMap<String, String> = HashMap<String, String>()
    private var voiceInfo: VoiceInfo = VoiceInfo()
    private val identifier: String
        get() {
            return arguments?.getString("chatId") ?: ""
        }

    private val playCallback = object : PlayCallback() {
        override fun onPlayStart() {
            tvVoicePlayState?.setIconText("&#xe6d2;")
        }

        override fun onPlayPause() {
            tvVoicePlayState?.setIconText("&#xe6d3;")
        }

        override fun onPlayStop() {
            tvVoicePlayState?.setIconText("&#xe6d3;")
        }

        override fun onError(code: PlayError?, errExtMsg: String?) {
            tvVoicePlayState?.setIconText("&#xe6d3;")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return attachToSwipeBack(
            inflater.inflate(
                R.layout.fragment_personal_chat,
                container,
                false
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (App.DEBUG) L.L(javaClass, "onViewCreated")
        isViewEnable = true
        setupView()
        addListeners()
        if (arguments?.get("voiceInfo") != null) {
            voiceInfo = arguments?.getParcelable("voiceInfo")!!
            showVoiceInfo(false)
        } else {
            voiceInfo.uid = identifier
            FriendListManager.getInstance()
                .getVoiceInfo(identifier, this, object : FriendListManager.IGetVoiceInfoResult {
                    override fun IGetVoiceInfoResult_success(voice: VoiceInfo) {
                        voiceInfo = voice
                        showVoiceInfo(true)
                    }

                    override fun IGetVoiceInfoResult_failed(msg: String?) {}
                })
        }
        checkFriends()

        if (arguments?.getString("title")?.isNotEmpty()!!) {
            tvLikeLabel.visibility = View.VISIBLE
            tvLikeLabel.text = arguments?.getString("title")
        } else {
            tvLikeLabel.visibility = View.GONE
        }

        AnalyticsUtils.onEvent(activity, UmengEventId.FRIEND_CHAT, "")
    }

//`    override fun onNewBundle(args: Bundle?) {
//        super.onNewBundle(args)
//        // 设置bundle过来
//    }`

    override fun onDestroyView() {
        isViewEnable = false
        onlineTimer.stop()
        C2CChatManager.getInstance().setDataListener(null);
        super.onDestroyView()
    }

    /**
     * 延时主线程执行主线程执行
     * @param delay 延时时间，单位ms
     */
    fun runOnUIThread(runnable: Runnable?, delay: Long): Disposable {
        // BackgroundTasks.getInstance().runOnUiThread(runnable, delay)
        return Observable.just("")
            .delay(delay, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())//执行结果在主线程运行
            .subscribe { _ ->
                runnable?.run()
            }
    }


    private lateinit var timChatAdapter: TIMChatAdapter
    private fun setupView() { // 初始化所有的东西
        enableToolbar(R.id.toolbar, "", true) // Toolbar
        mToolbar.toolbar.setNavigationIcon(R.drawable.icon_back)
        ImmersionBar.with(this).keyboardEnable(true).statusBarDarkFont(false).init()//沉浸式相关
        chatPanel.initDefault()
        timChatAdapter = TIMChatAdapter() // 显示列表的Adapter
        timChatAdapter.setOwnerView(this) // 自己属于谁, 用于回调
        timChatAdapter.setUserIcons(voiceInfo.headImg) // 用户的Image
        timChatAdapter.setDataSourceListener {
            checkLastMessageLocation() // 获取最新的消息, 回调获取
        }
        //val nightResource = NightModeResourceUtils.getResources()
        chatPanel.setChatAdapter(timChatAdapter) // 设置adapter
        chatPanel.mChatList.selfContentColor = getColor(R.color.colorTitleColor) //背景颜色
        chatPanel.mChatList.selfBubbleId = R.drawable.tim_self_message_bg //每条背景
        chatPanel.mChatList.oppositeBubbleId = R.drawable.tim_other_message_bg // 对方每条背景
        chatPanel.mChatList.tipsMessageBubbleId = R.drawable.round_5_shape_bg_alpha_12 //提示背景
        chatPanel.mChatList.tipsMessageColor = getColor(R.color.colorSubTitleColor) //提示提示文字颜色
        chatPanel.mChatList.tipsMessageSize = 13 //提示文字大小
        chatPanel.mChatList.oppositeContentColor = getColor(R.color.colorTitleColor) // 对面提示内容颜色

        chatPanel.setMessagePopActions(arrayListOf(PopMenuAction("复制")), true) //长按按钮弹出框
        chatPanel.setBaseChatId(identifier) // 聊天的ID, 里面会请求最新的几个消息
        chatPanel.titleBar.visibility = View.GONE // TITLEBar
        chatPanel.setGiftNum(voiceInfo.integral) // 显示的积分
        val inputGroup = chatPanel.inputGroup // 底部聊天框
        inputGroup.showVoiceInputView()
        chatPanel.setChatListEvent(object : ChatListEvent {
            override fun onMessageLongClick(view: View?, position: Int, messageInfo: MessageInfo?) {
                if (App.DEBUG) L.m(
                    printClass,
                    "ChatPanel",
                    "onMessageLongClick"
                )

                // 长按消息
                chatPanel.showItemPopMenu(position - 1, timChatAdapter.getItem(position), view)
            }

            override fun onUserIconClick(view: View?, position: Int, messageInfo: MessageInfo?) {
                // 点击用户头像
                if (App.DEBUG) L.m(
                    printClass,
                    "ChatPanel",
                    "onUserIconClick"
                )
                chatPanel.inputGroup.hideSoftInput()
                if (messageInfo!!.isSelf) {
                    startFromMain(
                        UserInfoFragment.newInstance(
                            UserInfoManager.voiceInfo,
                            UserInfoManager.userInfo
                        )
                    )
                } else {
                    startFromMain(UserInfoFragment.newInstance(voiceInfo, null))
                }

                // 统计点击用户头像
                AnalyticsUtils.onEvent(
                    activity,
                    UmengEventId.CLICK_USER_HEADER,
                    TAG
                )
            }
        })
        chatPanel.setSendMessageListener {
            // 点击发送消息
            if (App.DEBUG) L.m(
                printClass,
                "ChatPanel",
                "setSendMessageListener",
                "用户行为数据上报"
            )
            UserActionLog.reportAction(UserActionLog.sendMessageTimes, "1") // 发送消息到后台
        }

        // 数据监听
        C2CChatManager.getInstance().setDataListener(
            object : C2CChatManager.IC2CChatManagerDataListener {
                override fun IC2CChatManagerDataListener_loadMsg(messages: MutableList<TIMMessage>) {
                    if (App.DEBUG) L.m(
                        printClass,
                        "ChatPanel",
                        "IC2CChatManagerDataListener_loadMsg",
                        messages
                    )
                    // load message
                    messages.forEach {
                        if (!it.isSelf && !it.isRead
                            && CustomizeMessage.getType(it) == CustomizeMessage.MESSAGE_GIFT
                        ) {
                            val gift = CustomizeMessage.getContent(it).get("id")!!
                            runOnUIThread(Runnable { addShowGift(gift.toInt(), false) }, 1000)
                        }
                    }

                    for (index in messages.size - 1 downTo 0) {
                        val timMsg = messages[index]
                        if (!timMsg.isSelf && CustomizeMessage.getType(timMsg) == CustomizeMessage.MESSAGE_PK_PICK) {
                            val msgContent = CustomizeMessage.getContent(timMsg)
                            pkPickMap.putAll(msgContent);
                        }

                        if (CustomizeMessage.getType(timMsg) == CustomizeMessage.MESSAGE_PK_INVITE) {
                            updatePickStatus(timMsg) // 更新pick之后的状态
                        }
                    }
                }

                override fun IC2CChatManagerDataListener_newMsg(message: TIMMessage) {
                    //新消息
                    if (App.DEBUG) L.m(
                        printClass,
                        "ChatPanel",
                        "IC2CChatManagerDataListener_newMsg",
                        message
                    )
                    if (!message.isSelf
                        && CustomizeMessage.getType(message) == CustomizeMessage.MESSAGE_GIFT
                    ) {
                        updateGiftNum()
                        val gift = CustomizeMessage.getContent(message).get("id")!!
                        runOnUIThread(Runnable { addShowGift(gift.toInt(), false) }, 1000)
                    }

                    if (!message.isSelf && CustomizeMessage.getType(message) == CustomizeMessage.MESSAGE_PK_PICK) {
                        runOnUIThread(Runnable { updatePickMessage(message) }, 200)
                    }
                }

                override fun IC2CChatManagerDataListener_newMsgAll(message: TIMMessage?) {
                    if (App.DEBUG) L.m(
                        printClass,
                        "ChatPanel",
                        "IC2CChatManagerDataListener_newMsgAll",
                        message
                    )
                    if (message?.isSelf == false) {
                        runOnUIThread(Runnable {
                            UserActionLog.reportAction(
                                UserActionLog.getMessageTimes,
                                "1"
                            )
                        }, 1000)
                    }
                }
            }
        )

        chatPanel.inputGroup.setItemClickListener {
            // 获取题目, 点击"发起投票功能"
            if (App.DEBUG) L.m(printClass, "ChatPanel", "chatPanel.inputGroup")
            C2CChatFragmentPresenter.requestQuestionList(this, identifier);
        }
    }

    private fun updatePickMessage(message: TIMMessage) {
        val msgContent = CustomizeMessage.getContent(message)
        pkPickMap.putAll(msgContent);

        for (index in timChatAdapter.itemCount - 1 downTo 1) {
            val messageInfo = timChatAdapter.getItem(index)
            if (updatePickStatus(messageInfo.timMessage)) {
                timChatAdapter.notifyItemChanged(index)
            }
        }
    }

    private fun updatePickStatus(inviteMessage: TIMMessage): Boolean {
        if (pkPickMap.keys.contains(inviteMessage.msgId)) {
            if (pkPickMap[inviteMessage.msgId].equals("1", true)) {
                when (inviteMessage.customInt) {
                    CustomizeMessage.PKPick.PICK_NULL.ordinal -> {
                        inviteMessage.customInt = CustomizeMessage.PKPick.USER_PICK_ONE.ordinal
                    }

                    CustomizeMessage.PKPick.MY_PICK_ONE.ordinal -> {
                        inviteMessage.customInt = CustomizeMessage.PKPick.PICK_SAME_ONE.ordinal
                    }

                    CustomizeMessage.PKPick.MY_PICK_TWO.ordinal -> {
                        inviteMessage.customInt = CustomizeMessage.PKPick.PICK_DIFF_TWO.ordinal
                    }
                }
            } else if (pkPickMap[inviteMessage.msgId].equals("2", true)) {
                when (inviteMessage.customInt) {
                    CustomizeMessage.PKPick.PICK_NULL.ordinal -> {
                        inviteMessage.customInt = CustomizeMessage.PKPick.USER_PICK_TWO.ordinal
                    }

                    CustomizeMessage.PKPick.MY_PICK_ONE.ordinal -> {
                        inviteMessage.customInt = CustomizeMessage.PKPick.PICK_DIFF_ONE.ordinal
                    }

                    CustomizeMessage.PKPick.MY_PICK_TWO.ordinal -> {
                        inviteMessage.customInt = CustomizeMessage.PKPick.PICK_SAME_TWO.ordinal
                    }
                }
            }

            return true
        }

        return false
    }

    private fun addListeners() {
        tvAddFriend.setOnClickListener {
            addFriend()
        }
        tvVoicePlayState.setOnClickListener {
            voiceInfo.url?.let {
                if (BuluPlayer.getInstance().isPlaying) {
                    BuluPlayer.getInstance().pause()
                } else {
                    BuluPlayer.getInstance().play(it)
                }
            }
        }
        user_info_layout.setOnClickListener {
            chatPanel.inputGroup.hideSoftInput()
            startFromMain(UserInfoFragment.newInstance(voiceInfo, null))
        }
        img_more.setOnClickListener {
            ReportDialog.newInstance(ReportDialog.TYPE_REPORT_USER, identifier)
                .setChatList("聊天内容")
                .show(childFragmentManager)
        }

        chatPanel.setRequestDelegate { id ->
            addShowGift(id, true)
            GiftManager.getInstance()
                .sendGift(identifier, id, object : GiftManager.IGiftRequestResult {
                    override fun IGiftRequestResult_success(arg: Int) {
                        updateGiftNum()
                    }

                    override fun IGiftRequestResult_failed(msg: String) {
                        UtilsCode.showShort("请求失败：$msg")
                    }
                })
        }

        ivChatUserHeader.setOnClickListener {
            chatPanel.inputGroup.hideSoftInput()
            startFromMain(UserInfoFragment.newInstance(voiceInfo, null))
        }
        ivMyHeader.setOnClickListener {
            chatPanel.inputGroup.hideSoftInput()
            startFromMain(UserInfoFragment.newInstance(null, UserInfoManager.userInfo))
        }

        onlineTimer = KwTimer(object : KwTimer.Listener {
            override fun onTimer(timer: KwTimer?) {
                checkUserOnline()
            }
        })
        onlineTimer.start(3 * 60 * 1000, Int.MAX_VALUE)
    }

    private fun showVoiceInfo(fromService: Boolean) {
        if (App.DEBUG) L.L(javaClass, "showVoiceInfo")
        timChatAdapter?.setUserIcons(voiceInfo.headImg)
        ImageLoader.showCircleImage(ivUserHeader, voiceInfo.headImg, voiceInfo.newDefaultHeadImage)

        // tvVoicePlayState.visibility = if (voiceInfo.url.isNullOrEmpty()) View.GONE else View.VISIBLE
        tvVoicePlayState?.visibility = View.GONE
        tvUserName?.text = voiceInfo.name
        tvAgeLabel?.setSexAndAge(voiceInfo.sex, voiceInfo.age)
        tvLocationLabel?.text = if (voiceInfo.city.isNullOrEmpty()) "bulu星球" else voiceInfo.city

//        if(!fromService && (voiceInfo.card == null || voiceInfo.card.isEmpty())) {
//            FriendListManager.getInstance().getVoiceInfo(identifier, this, object: FriendListManager.IGetVoiceInfoResult{
//                override fun IGetVoiceInfoResult_success(voice: VoiceInfo) {
//                    voiceInfo = voice
//                    showVoiceInfo(true)
//                }
//                override fun IGetVoiceInfoResult_failed(msg: String?) {}
//            })
//        }

        ImageLoader.showCircleImage(
            ivChatUserHeader,
            voiceInfo.headImg,
            voiceInfo.newDefaultHeadImage
        )
        ImageLoader.showCircleImage(
            ivMyHeader,
            UserInfoManager.userInfo?.headImg,
            UserInfoManager.userInfo!!.getNewDefaultHeadImage()
        )
        FriendListManager.getInstance()
            .getMatchValue(voiceInfo.uid, this, object : FriendListManager.IGetMatchValueResult {
                override fun IGetMatchValueResult_success(matchValue: Int) {
                    if (App.DEBUG) L.L(
                        javaClass,
                        "showVoiceInfo",
                        "IGetMatchValueResult_success"
                    )
                    tvMatchValue.text = "匹配度 $matchValue%";

                    val layoutParams =
                        view_match_progress.layoutParams as ConstraintLayout.LayoutParams
                    layoutParams.width =
                        (view_match_bg.layoutParams.width.toFloat() * matchValue.toFloat() / 100.0).toInt()
                    view_match_progress.layoutParams = layoutParams
                }
            })

        FriendListManager.getInstance()
            .getSameCount(voiceInfo.uid, this, object : FriendListManager.IGetSameCountResult {
                override fun IGetSameCountResult_success(sameCount: Int) {
                    if (App.DEBUG) L.L(
                        javaClass,
                        "showVoiceInfo",
                        "IGetSameCountResult_success"
                    )
                    pickSameCount = sameCount;
                    timChatAdapter.notifyItemChanged(0)
                    timChatAdapter.notifyItemChanged(1)
                }
            })

        checkUserOnline()
        updateGiftNum()
    }

    public fun setHelloText(textView: TextView, isSelf: Boolean) {
        if (isSelf) {
            if (pickSameCount > 0) {
                textView.text = "哇哦(๑•̀ㅂ•́)و✧～ TA在"
                val countText = SpannableString(pickSameCount.toString());
                countText.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                        ds.color = Color.parseColor("#FFE43F")
                    }
                }, 0, countText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                textView.append(countText)
                textView.append("件事上都和你态度相同喔～")
                textView.append(" TA已经注意到你了喔，不如和TA打个招呼吧？")
            } else {
                textView.text = FriendList.TEXT_SAY_HELLO;
                if (App.DEBUG) L.m(
                    javaClass,
                    "TEXT_HELLO",
                    "setHelloText"
                )
            }
        } else {
            if (pickSameCount > 0) {
                textView.text = "哇哦(๑•̀ㅂ•́)و✧～ TA在"
                val countText = SpannableString(pickSameCount.toString());
                countText.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                        ds.color = Color.parseColor("#FFE43F")
                    }
                }, 0, countText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                textView.append(countText)
                textView.append("件事上都和你态度相同喔～")
                textView.append(" 对方申请做您心尖尖上的宝贝，接受请求吗？")
            } else {
                textView.text = FriendList.TEXT_HELLO_TOME;
            }
        }
    }

    // 检查用户是否在线
    private fun checkUserOnline() {
        if (App.DEBUG) L.L(javaClass, "checkUserOnline")
        FriendListManager.getInstance().getUserStatus(voiceInfo.uid, this) { onlineBean ->
            if (onlineBean?.to_Account.equals(voiceInfo.uid, true)) {
                if (onlineBean!!.isOnline) {
                    tvUserOnline.visibility = View.VISIBLE;
                } else {
                    tvUserOnline.visibility = View.INVISIBLE;
                }
            }
        }
    }

    // 定位消息最后的位置
    private fun checkLastMessageLocation() {
        chatPanel?.mChatList?.postDelayed({
            chatPanel?.let {
                val mChatList = chatPanel.mChatList ?: return@let
                val child = mChatList.getChildAt(mChatList.adapter!!.itemCount - 1)
                val intArrayOf = intArrayOf(0, 0)
                child?.getLocationOnScreen(intArrayOf)
            }
        }, 200)
    }

    // 显示之后
    override fun onSupportVisible() {
        super.onSupportVisible()
        BuluPlayer.getInstance().stop()
        BuluPlayer.getInstance().addPlayCallback(playCallback)
    }

    // 消失之后
    override fun onSupportInvisible() {
        super.onSupportInvisible()
        BuluPlayer.getInstance().removePlayCallback(playCallback)
        BuluPlayer.getInstance().stop()
        tvVoicePlayState?.setIconText("&#xe6d3;")
        UIKitAudioArmMachine.getInstance().stopPlayRecord()
    }

    private fun isFollowUser(): Boolean {
        if (App.DEBUG) L.L(javaClass, "isFollowUser")
        // 是否在喜欢列表中
        val isFollowed = FriendListManager.getInstance()
            .hasUser(voiceInfo.uid, FriendListManager.RELATION_FOLLOWED)
        // 是否在相互夏欢列表中
        val isBoth =
            FriendListManager.getInstance().hasUser(voiceInfo.uid, FriendListManager.RELATION_BOTH)

        return (isFollowed || isBoth)
    }

    override fun onBackPressedSupport(): Boolean {
        if (isFromMatch && !isFollowUser()) {
            DialogUtils.showAttensionDialog({ agree ->
                if (agree) {
                    EventBus.getDefault().post(LikeAnimEvent.getCancelAnimEvent())
                    pop()
                }
            }, this)
//            AlertDialog.getInstance(
//                _mActivity,
//                "",
//                "确认退出嘛？没有关注TA的话，以后可能不会再碰到TA喔"
//            ) { dialog, which ->
//                EventBus.getDefault().post(LikeAnimEvent.getCancelAnimEvent())
//                pop()
//            }.show()
            return true
        }

        EventBus.getDefault().post(LikeAnimEvent.getCancelAnimEvent())
        return super.onBackPressedSupport()
    }

    override fun onBackBtnClicked(view: View?) {
        if (isFromMatch && !isFollowUser()) {
//            AlertDialog.getInstance(
//                _mActivity,
//                "",
//                "确认退出嘛？没有关注TA的话，以后可能不会再碰到TA喔"
//            ) { dialog, which ->
//                pop()
//            }.show()
            DialogUtils.showAttensionDialog({ agree ->
                if (agree) {
                    pop()
                }
            }, this)

        } else {
            try {
                if (preFragment == null) {
                    _mActivity.finish()
                } else {
                    pop()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 添加/删除好友
    private fun addFriend() {
        if (App.DEBUG) L.L(javaClass, "addFriend")
        if (tvAddFriend.tag == TAG_OTHER) { // 我们还没有关系
            FriendListManager.getInstance()
                .follow(voiceInfo, this, object : FriendListManager.IFriendListManagerResult {
                    override fun IFriendListManagerResult_success(result: Int) { // 添加好友成功
                        UtilsCode.showShort("已添加为好友")
                        EventBus.getDefault().post(FriendChangeEvent())
                    }

                    override fun IFriendListManagerResult_failed(msg: String) {
                        UtilsCode.showShort(msg)
                    }
                })
        } else {
            FriendListManager.getInstance()
                .delete(voiceInfo.uid, this, object : FriendListManager.IFriendListManagerResult {
                    override fun IFriendListManagerResult_success(result: Int) { // 删除好友成功
                        UtilsCode.showShort("已删除好友")
                        EventBus.getDefault().post(FriendChangeEvent())
                    }

                    override fun IFriendListManagerResult_failed(msg: String) {
                        UtilsCode.showShort(msg)
                    }
                })
        }
    }

    private fun checkFriends() {
        if (App.DEBUG) L.L(javaClass, "checkFriends")
        // 是否是自己喜欢
        val isFollowed = FriendListManager.getInstance()
            .hasUser(voiceInfo.uid, FriendListManager.RELATION_FOLLOWED)
        // 是否是邂逅
        val isBoth =
            FriendListManager.getInstance().hasUser(voiceInfo.uid, FriendListManager.RELATION_BOTH)

        if (isFollowed || isBoth) { // 我喜欢 ｜｜ 心意相通
            updateAddBtnStat(voiceInfo.uid, FriendListManager.RELATION_FOLLOWED)
        } else {
            updateAddBtnStat(voiceInfo.uid, FriendListManager.RELATION_NONE)
        }

        val conv = TIMManager.getInstance().getConversation(TIMConversationType.C2C, voiceInfo.uid)
        if (conv.lastMsg == null) { // 说明一条消息都没有
            FriendListManager.getInstance().sayHello(voiceInfo.uid) //打招呼
            C2CChatFragmentPresenter.requestQuestionList(this, identifier)
        } else if (CustomizeMessage.getType(conv.lastMsg) == CustomizeMessage.MESSAGE_HELLO) {
            C2CChatFragmentPresenter.requestQuestionList(this, identifier)
        }
    }

    // 用户关系发生了变化
    @Subscribe
    public fun onFriendChangeEvent(event: FriendChangeEvent) {
        if (App.DEBUG) L.L(javaClass, "onFriendChangeEvent")
        checkFriends()
    }

    @Subscribe
    public fun onLikeAnimEndEvent(event: LikeAnimEvent) {
        if (App.DEBUG) L.L(javaClass, "onLikeAnimEndEvent")
        if (event.animEvent == LikeAnimEvent.ANIM_TXT_END) {
            user_info_layout.visibility = View.VISIBLE
        }
    }

    // 新的用户关系产生了
    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onFollow(event: FriendListManager.OnFollow_event) {
        if (App.DEBUG) L.L(javaClass, "onFollow")
        updateAddBtnStat(event.item.user.uid, event.status)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDeleteFriend(event: FriendListManager.OnDeleteFriend_event) {
        if (App.DEBUG) L.L(javaClass, "onDeleteFriend")
        updateAddBtnStat(event.item.user.uid, event.status)
    }

    private val TAG_OTHER: Int = 0
    private val TAG_ME = 1

    private fun updateAddBtnStat(uid: String, status: Int) {
        if (App.DEBUG) L.L(javaClass, "updateAddBtnStat")
        if (uid == identifier) {
            if (status == FriendListManager.RELATION_NONE
                || status == FriendListManager.RELATION_BE_FOLLOWED
            ) {
                tvAddFriend.tag = TAG_OTHER
                tvAddFriend.setImageResource(R.drawable.personal_chat_like)
            } else {
                tvAddFriend.tag = TAG_ME
                tvAddFriend.setImageResource(R.drawable.personal_chat_already_like)
            }
        }
    }

    private fun updateGiftNum() {
        GiftManager.getInstance().getMyPoints(this, object : GiftManager.IGiftRequestResult {
            override fun IGiftRequestResult_success(arg: Int) {
                if (chatPanel != null) {
                    chatPanel.setGiftNum(arg)
                }
            }

            override fun IGiftRequestResult_failed(msg: String?) {}
        })
    }

    private var giftWaitingList: LinkedList<Pair<Int, Boolean>>? = null
    private fun addShowGift(gift: Int, isSelf: Boolean) {
        if (giftWaitingList != null) {
            giftWaitingList!!.addLast(Pair<Int, Boolean>(gift, isSelf))
            return
        }

        giftWaitingList = LinkedList<Pair<Int, Boolean>>()
        giftWaitingList!!.add(Pair<Int, Boolean>(gift, isSelf))

        try {
            realShowGift()
        } catch (e: Exception) {
        }
    }

    // 显示信息的动画
    private fun realShowGift() {
        val gift = giftWaitingList!!.first
        giftWaitingList!!.removeLast()

        var num = gift.first
        if (gift.first == 84) {
            num = 1
            ivGiftNotifyImg.setImageResource(R.drawable.bottom_action_send_gift_1)
        } else if (gift.first == 85) {
            num = 3
            ivGiftNotifyImg.setImageResource(R.drawable.bottom_action_send_gift_2)
        } else if (gift.first == 86) {
            num = 5
            ivGiftNotifyImg.setImageResource(R.drawable.bottom_action_send_gift_3)
        }

        layoutGiftNotify.visibility = View.VISIBLE
        val anim = AnimationUtils.loadAnimation(this.activity, R.anim.gift_notify)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                if (gift.second) {
                    tvGiftNotifyText.text = "biubiubiu~爱意已发送\nTA一定感受到了吧！"
                } else {
                    tvGiftNotifyText.text = "TA向你发送了爱意*$num\n他一定超级喜欢你～"
                }
            }

            override fun onAnimationEnd(p0: Animation?) {
                if (giftWaitingList!!.isNotEmpty()) {
                    realShowGift()
                } else {
                    giftWaitingList = null
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }
        })
        layoutGiftNotify.startAnimation(anim);
    }

    public fun setFromMatch() {
        isFromMatch = true
    }

    // 从MainActivity发送过来
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageReceiptEvent(event: MessageReceiptEvent) {
        if (App.DEBUG) L.L(javaClass, "onMessageReceiptEvent")
        event.list.forEach {
            if (it.conversation.peer.equals(voiceInfo.uid, true)) {
                timChatAdapter.notifyDataSetChanged()
            }
        };
    }

    // 发出PK邀请
    public fun sendPKMessage(model: QuestionModel) {
        val messageInfo = MessageInfoUtil.buildPKInviteMessage(
            String.format(
                "{\"type\"=\"103\", \"pk_tid\"=\"%s\"}",
                model.id
            ), "发出PK邀请"
        );
        messageInfo.localTag = model;
        chatPanel.sendMessage(messageInfo) // 发送给整个聊天区域
    }

    // 这个消息的详细信息
    public fun getQuestionDetail(position: Int, message: MessageInfo) {
        val msgMap = CustomizeMessage.getContent(message.timMessage);
        if (msgMap.containsKey("pk_tid")) {
            C2CChatFragmentPresenter.queryQuestionDetail(msgMap["pk_tid"], position, message, this);
        }
    }

    public fun onGetQuestionDetailResult(
        position: Int,
        message: MessageInfo,
        model: QuestionModel
    ) {
        message.localTag = model
        timChatAdapter.notifyItemChanged(position)
    }

    override fun getPrintClass(): Class<*> {
        return javaClass
    }
}