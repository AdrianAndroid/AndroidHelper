package com.flannery.multilanguage.confict4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.TableLayout
import androidx.core.view.get
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.flannery.multilanguage.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Conflict4Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conflict4)

        val topView: View = findViewById(R.id.topView);

        val viewPager: ViewPager = findViewById(R.id.mViewPager)
        val list = arrayListOf(
            WebViewFragment(),
            RecyclerViewFragment() // 点击事件，需要判断是否需要增加宽度
        )

        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return list.size
            }

            override fun getItem(position: Int): Fragment {
                return list.get(position)
            }
        }

        // initTab
        // https://blog.csdn.net/u010712703/article/details/77884399
        val mFirstTabLayout: TabLayout = findViewById(R.id.mFirstTabLayout)
        arrayListOf("OverView", "Content").forEachIndexed { index: Int, str: String ->
            mFirstTabLayout.addTab(mFirstTabLayout.newTab())
            mFirstTabLayout.getTabAt(index)?.setText(str)
        }

        val mSecondTabLayout: TabLayout = findViewById(R.id.mSecondTabLayout)
        arrayListOf("OverView", "Content").forEachIndexed { index: Int, str: String ->
            mSecondTabLayout.addTab(mSecondTabLayout.newTab())
            mSecondTabLayout.getTabAt(index)?.setText(str)
        }

        mFirstTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    mSecondTabLayout.getTabAt(it)?.select()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        mSecondTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    mFirstTabLayout.getTabAt(it)?.select()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        mFirstTabLayout.setupWithViewPager(viewPager)
        mSecondTabLayout.setupWithViewPager(viewPager)


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

    fun test() {
        val vp2: ViewPager2? = null
    }
}