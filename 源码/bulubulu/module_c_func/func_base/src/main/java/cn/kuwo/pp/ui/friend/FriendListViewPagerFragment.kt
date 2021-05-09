package cn.kuwo.pp.ui.friend

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import cn.kuwo.common.app.App
import cn.kuwo.common.base.BaseFragment
import cn.kuwo.common.util.L
import cn.kuwo.common.view.NoScrollViewPager
import cn.kuwo.pp.BuildConfig
import cn.kuwo.pp.R
import cn.kuwo.pp.event.UnreadMessageEvent
import cn.kuwo.pp.manager.FriendList.FriendListManager
import cn.kuwo.pp.manager.FriendList.FriendListManager.*
import cn.kuwo.pp.util.CommonViewPageAdapter
import cn.kuwo.pp.util.magictabview.BadgePagerTitleView
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class FriendListViewPagerFragment : BaseFragment() {
    companion object {
        fun newInstance(): FriendListViewPagerFragment {
            val args = Bundle()
            val fragment = FriendListViewPagerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friend_list_viewpager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = ArrayList<Fragment>()
        val titles = listOf("我喜欢", "喜欢我", "心意相通", "邂逅")
//        val titles = listOf("心意相通", "心意相通", "心意相通", "心意相通")
        val types = listOf(
            FriendListManager.RELATION_FOLLOWED,
            FriendListManager.RELATION_BE_FOLLOWED,
            FriendListManager.RELATION_BOTH,
            FriendListManager.RELATION_NONE
        )
        // 创建4个Fragment
        for (i in 0..3) {
            fragments.add(createFragment(types[i], titles[i]))
        }
        val adapter = CommonViewPageAdapter(childFragmentManager, fragments, titles)

        val viewPager = view.findViewById(R.id.view_pager) as NoScrollViewPager
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 2 //懒加载两个

        val commonNavigator = CommonNavigator(context)
        commonNavigator.isAdjustMode = false
        commonNavigator.isSmoothScroll = false
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return fragments.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView: BadgePagerTitleView = BadgePagerTitleView(context)
                simplePagerTitleView.setText(titles[index])
                simplePagerTitleView.normalColor = Color.parseColor("#7fffffff")
                simplePagerTitleView.selectedColor = Color.parseColor("#ffffff")
                simplePagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            // 导航栏
            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineHeight = UIUtil.dip2px(context, 4.0).toFloat()
                indicator.lineWidth = UIUtil.dip2px(context, 15.0).toFloat()
                indicator.roundRadius = UIUtil.dip2px(context, 2.0).toFloat()
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(Color.parseColor("#FFDF1F"))
                //indicator.yOffset = 30f
                return indicator
            }
        }

        val magicIndicator = view.findViewById(R.id.tab_layout) as MagicIndicator
        magicIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator, viewPager)

        onUnreadMessageChange(UnreadMessageEvent())

        if (App.DEBUG) {
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    L.m(printClass, "printTopChildFragmentIndex currentPosition", position)
                    printTopChildFragmentIndex()
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })
        }
    }


    private fun printTopChildFragmentIndex() {
        super.getTopChildFragment()
        if (App.DEBUG) {
            val topChildFragment = topChildFragment
            val viewPager = view?.findViewById(R.id.view_pager) as NoScrollViewPager
            val commonNavigatorAdapter = viewPager.adapter as CommonViewPageAdapter
            for (index in 0 until commonNavigatorAdapter.count) {
                if (commonNavigatorAdapter.getItem(index) == topChildFragment) {
                    L.m(printClass, "printTopChildFragmentIndex index", index)
                }
            }
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (App.DEBUG) {
            printTopChildFragmentIndex()
        }
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        if (App.DEBUG) {
            printTopChildFragmentIndex()
        }
    }


    private fun createFragment(type: Int, title: String): Fragment {
        val fragment = FriendListFragment()
        val args = Bundle()

        args.putInt("type", type)
        args.putString("title", title)
        fragment.arguments = args
        return fragment
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUnreadMessageChange(event: UnreadMessageEvent?) {
        if (App.DEBUG) L.L(javaClass, "onUnreadMessageChange")
        val magicIndicator = view?.findViewById(R.id.tab_layout) as MagicIndicator
        val navigator = magicIndicator.navigator as CommonNavigator

        val titleViewFollowed = navigator.getPagerTitleView(0) as BadgePagerTitleView
        val listFollowed = FriendListManager.getInstance().getList(RELATION_FOLLOWED)
        titleViewFollowed.setBadgeNumber(listFollowed.unreadMessageCount)

        val titleViewBeFollow = navigator.getPagerTitleView(1) as BadgePagerTitleView
        val listBeFollow = FriendListManager.getInstance().getList(RELATION_BE_FOLLOWED)
        titleViewBeFollow.setBadgeNumber(listBeFollow.unreadMessageCount)

        val titleViewBoth = navigator.getPagerTitleView(2) as BadgePagerTitleView
        val listBoth = FriendListManager.getInstance().getList(RELATION_BOTH)
        titleViewBoth.setBadgeNumber(listBoth.unreadMessageCount)

        val titleViewNone = navigator.getPagerTitleView(3) as BadgePagerTitleView
        val listNone = FriendListManager.getInstance().getList(RELATION_NONE)
        titleViewNone.setBadgeNumber(listNone.unreadMessageCount)
    }


    override fun getPrintClass(): Class<*> {
        return javaClass;
    }
}