package com.flannery.multilanguage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flannery.multilanguage.conflict.NestedWebView
import com.flannery.multilanguage.conflict.RecyclerViewFragment
import com.flannery.multilanguage.conflict.WebViewFragment
import com.google.android.material.tabs.TabLayoutMediator

class ConflictActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conflict)
        // Viewpager
        val mViewPager: ViewPager2 = findViewById(R.id.mViewPager)
        val list = listOf(
            WebViewFragment(),
            RecyclerViewFragment()
        )
        mViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = list.size
            override fun createFragment(position: Int): Fragment = list[position]
        }


        TabLayoutMediator(findViewById(R.id.mTabLayout), mViewPager) { tab, position ->
            tab.text = "TAB $position"
        }.attach()
        TabLayoutMediator(findViewById(R.id.mTabLayout2), mViewPager) { tab, position ->
            tab.text = "TAB $position"
        }.attach()

//        val webView = WebView(this)
//        webView.setOnScrollChangeListener
    }
}