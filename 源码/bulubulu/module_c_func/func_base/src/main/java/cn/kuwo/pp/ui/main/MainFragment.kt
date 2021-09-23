package cn.kuwo.pp.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.Nullable
import cn.kuwo.common.app.App
import cn.kuwo.common.base.BaseFragment
import cn.kuwo.common.privacy.PrivacyDialogUtil
import cn.kuwo.common.util.L
import cn.kuwo.func_base.utils.PlayBgMusic
import cn.kuwo.pp.R
import cn.kuwo.pp.event.EditProfileEvent
import cn.kuwo.pp.event.MainTabSelectChangeEvent
import cn.kuwo.pp.event.UnreadMessageEvent
import cn.kuwo.pp.manager.FriendList.FriendListManager
import cn.kuwo.pp.manager.UserInfoManager
import cn.kuwo.pp.ui.discover.DiscoverFragment
import cn.kuwo.pp.ui.friend.FriendListViewPagerFragment
import cn.kuwo.pp.ui.login.LoginFragment
import cn.kuwo.pp.ui.mine.UserInfoFragment
import cn.kuwo.pp.ui.web.WebFragment
import cn.kuwo.pp.ui.world.WorldEntryFragment
import cn.kuwo.pp.util.HeaderRoundImageHelper
import cn.kuwo.pp.util.UserActionLog
import com.elbbbird.android.analytics.AnalyticsUtils
import com.elbbbird.android.analytics.UmengEventId
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import q.rorbin.badgeview.QBadgeView


class MainFragment : BaseFragment() {
    companion object {
        fun newInstance(): MainFragment {
            val args = Bundle()
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val mTabItemName = arrayOf("聊天", "找朋友", "动态")
    private val mTabItemIcon =
        arrayOf(R.drawable.main_tab_chat, R.drawable.main_tab_search, R.drawable.main_tab_trend)
    private val mTabItemSelectedIcon = arrayOf(
        R.drawable.main_tab_chat_selected,
        R.drawable.main_tab_search_selected,
        R.drawable.main_tab_trend_select
    )

    private val mFragments = arrayOfNulls<ISupportFragment>(mTabItemName.size)

    private var mTabSelectedListener: TabLayout.OnTabSelectedListener? = null

    private var mLoginSuccessRunnable: Runnable? = null

    private var mMessageView: QBadgeView? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupView()
        addListener()
        onUnreadMessageChange(UnreadMessageEvent())
        UserActionLog.reportAction(UserActionLog.dailyOpenedTimes, "1")
    }

    private fun setupView() {
        val selectPosition = 1
        initFragments(selectPosition)

        mMessageView = QBadgeView(context);
        mMessageView?.badgeBackgroundColor = Color.parseColor("#FFDF1F")//0xffffdf1L.toInt()
        mMessageView?.badgeTextColor = Color.parseColor("#000000")//0xff000000.toInt()

        for (position in mTabItemName.indices) {
            val tab = tab_layout.newTab().setCustomView(R.layout.main_tab_item_custom)

            if (position == 0) {
                val icon = tab.customView?.findViewById<ImageView>(R.id.ivTabIcon)
                mMessageView?.bindTarget(icon)
            }

            if (selectPosition == position) {
                tab.customView?.findViewById<ImageView>(R.id.ivTabIcon)
                    ?.setImageResource(mTabItemSelectedIcon[position])
                tab_layout.addTab(tab, true)
            } else {
                tab.customView?.findViewById<ImageView>(R.id.ivTabIcon)
                    ?.setImageResource(mTabItemIcon[position])
                tab_layout.addTab(tab, false)
            }
            val parent = tab.customView?.parent as View
            parent.setTag(R.id.main_tab_position, position)
            parent.setOnClickListener { v ->
                //if (PrivacyDialogUtil.acceptPrivacy()) { // 接受了意思协议之后才会有所跳转
                val oldPosition = tab_layout.selectedTabPosition
                post { onTabItemClicked(v, oldPosition) }
                //}
            }

        }

        mainViewModel.onTabItemClicked.observe(
            viewLifecycleOwner
        ) { position -> // 要跳转的position
            onTabItemClicked(position, tab_layout.selectedTabPosition)
        }

        setUserHeaderImage()
    }

    private fun setUserHeaderImage() {
        HeaderRoundImageHelper.showCircleImg(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        if (!PrivacyDialogUtil.acceptPrivacy()) {
        //startFromMain(PrivacyFragment())
//        }
        // 显示隐私政策
        PrivacyDialogUtil.showPrivacy(view.findViewById(R.id.privacy),
            {
                startFromMain(
                    WebFragment.newInstance(
                        "https://h5app.kuwo.cn/m/3dab9c3a/server.html",
                        "用户协议",
                        ""
                    )
                )
            }
        ) {
            startFromMain(
                WebFragment.newInstance(
                    "https://h5app.kuwo.cn/m/3d724391/secret.html",
                    "隐私政策",
                    ""
                )
            )
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUserInfoChange(event: EditProfileEvent) {
        if (App.DEBUG) L.L(javaClass, "onUserInfoChange")
        setUserHeaderImage();
    }

    // 更新Tab
    private fun onTabItemClicked(view: View, oldPosition: Int) {
        val position = view.getTag(R.id.main_tab_position) as Int
        onTabItemClicked(position, oldPosition)
    }

    /**
     * 处理tab item点击事件，需要登录的界面切换之前判断登陆状态
     *
     * @param view        CustomView的父view，所属位置信息保存在view的tag中
     * @param oldPosition
     */
    private fun onTabItemClicked(position: Int, oldPosition: Int) { // 退出登陆可能要调用到
        //val position = view.getTag(R.id.main_tab_position) as Int
        if (oldPosition == position) {
            mTabSelectedListener?.onTabReselected(tab_layout.getTabAt(position))
        } else if (needLogin(position)) {
            mLoginSuccessRunnable = Runnable {
                tab_layout?.getTabAt(position)?.select()
                mTabSelectedListener?.onTabUnselected(tab_layout?.getTabAt(oldPosition))
                mTabSelectedListener?.onTabSelected(tab_layout?.getTabAt(position))
                mLoginSuccessRunnable = null
            }
            //没有登录的话，打开登录界面，同时还原Tablayout选中位置
            startLoginActivity()
            tab_layout.getTabAt(oldPosition)?.select()
        } else {
            mTabSelectedListener?.onTabUnselected(tab_layout.getTabAt(oldPosition))
            mTabSelectedListener?.onTabSelected(tab_layout.getTabAt(position))
        }
    }

    private fun startLoginActivity() {
        start(LoginFragment.newInstance(false))
    }

    private fun needLogin(position: Int): Boolean {
        return (position == 0 || position == 2) && UserInfoManager.isNotLogin
    }

    override fun onLoginSuccess() {
        super.onLoginSuccess()
        HeaderRoundImageHelper.showCircleImg(this) // 显示头像
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        PlayBgMusic.stopBgMusic()
        childFragmentManager.fragments.forEach { fragment ->
            if (fragment.isVisible) {
                (fragment as? BaseFragment)?.onSupportInvisible()
            }
        }
    }

    override fun isPageStatisticsEnable(): Boolean {
        return false
    }

    override fun onLogoutSuccess() {
        super.onLogoutSuccess()
        //popTo(MainFragment::class.java, false, {}, 0)
        //popToChild()
        mTabSelectedListener?.onTabUnselected(tab_layout.getTabAt(tab_layout.selectedTabPosition))
        tab_layout.getTabAt(1)?.select()
        mTabSelectedListener?.onTabSelected(tab_layout.getTabAt(1))
        //ivUserHeader.setImageResource(R.drawable.default_header)// 先设置默认头像
        HeaderRoundImageHelper.showCircleImg(this)
    }

    private fun initFragments(selectPosition: Int) {
        mFragments[0] = FriendListViewPagerFragment.newInstance() //
        mFragments[1] = DiscoverFragment.newInstance()
        mFragments[2] = WorldEntryFragment.newInstance()
        loadMultipleRootFragment(R.id.container_id, selectPosition, *mFragments)
    }

    private fun addListener() {
        mTabSelectedListener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment = mFragments[tab.position]
                (fragment as SupportFragment).exitTransition = null
                showHideFragment(fragment)
                tab.customView?.findViewById<ImageView>(R.id.ivTabIcon)
                    ?.setImageResource(mTabItemSelectedIcon[tab.position])
                EventBus.getDefault().post(MainTabSelectChangeEvent(tab.position))
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.customView?.findViewById<ImageView>(R.id.ivTabIcon)
                    ?.setImageResource(mTabItemIcon[tab.position])
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                val fragment = mFragments[tab.position]
                (fragment as SupportFragment).exitTransition = null
                showHideFragment(fragment)
                tab.customView?.findViewById<ImageView>(R.id.ivTabIcon)
                    ?.setImageResource(mTabItemSelectedIcon[tab.position])
                EventBus.getDefault().post(MainTabSelectChangeEvent(tab.position))
            }
        }

        HeaderRoundImageHelper.setCircleImgClickListener(this) {
            AnalyticsUtils.onEvent(
                activity,
                UmengEventId.CLICK_USER_HEADER,
                "MAIN"
            )

            if (UserInfoManager.isLogin) {
                startFromMain(
                    UserInfoFragment.newInstance(
                        UserInfoManager.voiceInfo,
                        UserInfoManager.userInfo
                    )
                )
            } else {
                startLoginActivity()
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUnreadMessageChange(event: UnreadMessageEvent?) {
        L.m3(FriendListManager.getInstance().unreadMessageCount)
        if (mMessageView?.badgeNumber != FriendListManager.getInstance().unreadMessageCount) {
            mMessageView?.badgeNumber = FriendListManager.getInstance().unreadMessageCount
        }
    }

    override fun getPrintClass(): Class<*> {
        return javaClass
    }
}
