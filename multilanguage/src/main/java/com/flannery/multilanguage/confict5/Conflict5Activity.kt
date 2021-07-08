package com.flannery.multilanguage.confict5

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flannery.multilanguage.R
import com.flannery.multilanguage.confict4.RecyclerViewFragment
import com.flannery.multilanguage.confict4.WebViewFragment
import com.flannery.multilanguage.confict4.WrapHeightViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Conflict5Activity : AppCompatActivity() {

    private val arrayListOf = arrayListOf("OverView", "Content")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conflict5)

        val topView: View = findViewById(R.id.topView);

        val viewPager: ViewPager2 = findViewById(R.id.mViewPager)
        val list = arrayListOf(
            WebViewFragment(),
            RecyclerViewFragment() // 点击事件，需要判断是否需要增加宽度
        )
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return list.size
            }

            override fun createFragment(position: Int): Fragment {
                return list[position]
            }

        }

        // initTab
        // https://blog.csdn.net/u010712703/article/details/77884399
        val mSecondTabLayout: TabLayout = findViewById(R.id.mSecondTabLayout)

        // indicator
        TabLayoutMediator(findViewById(R.id.mFirstTabLayout), viewPager) { tab, position ->
            tab.text = arrayListOf[position]
        }.attach()
        TabLayoutMediator(mSecondTabLayout, viewPager) { tab, position ->
            tab.text = arrayListOf[position]
        }.attach()


        // 设置ScrollView的滚动
        val mNestedScrollView: NestedScrollView = findViewById(R.id.mNestedScrollView)
        mNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener
        { _, _, scrollY, _, _ ->
            val b = scrollY >= topView.height
            if (b && mSecondTabLayout.visibility != View.VISIBLE) {
                Log.e("TAG", "scrollY=$scrollY  visiable true")
                mSecondTabLayout.visibility = View.VISIBLE
            } else if (!b && mSecondTabLayout.visibility == View.VISIBLE) {
                mSecondTabLayout.visibility = View.INVISIBLE
                Log.e("TAG", "scrollY=$scrollY  visiable false")
            }
        })
    }

}