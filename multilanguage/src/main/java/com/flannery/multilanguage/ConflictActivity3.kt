package com.flannery.multilanguage

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flannery.multilanguage.conflict.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ConflictActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conflict3)
        // Viewpager
        val mViewPager: ViewPager = findViewById(R.id.mViewPager)
        //clearOnChildAttachStateChangeListeners
//        mViewPager.mRecyclerView.clearOnChildAttachStateChangeListeners()
        mViewPager.adapter = object : PagerAdapter() {
            override fun getCount(): Int = 2

            override fun isViewFromObject(view: View, obj: Any): Boolean {
                return view == obj
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                return super.instantiateItem(container, position)
            }

        }
//        mViewPager.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//            override fun onCreateViewHolder(
//                parent: ViewGroup,
//                viewType: Int
//            ): RecyclerView.ViewHolder {
//                return if (viewType == 0)
//                    WebViewHolder(layoutInflater.inflate(R.layout.view_webview, parent, false))
//                else
//                    RecyclerViewHodler(
//                        layoutInflater.inflate(
//                            R.layout.view_recyclerview,
//                            parent,
//                            false
//                        )
//                    )
//            }
//
//            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//                if (holder is WebViewHolder) {
//                    holder.loadUrl()
//                } else if (holder is RecyclerViewHodler) {
//                    holder.loadData()
//                }
//            }
//
//            override fun getItemCount(): Int {
//                return 2
//            }
//
//        }
//
//        TabLayoutMediator(findViewById(R.id.mTabLayout), mViewPager) { tab, position ->
//            tab.text = "TAB $position"
//        }.attach()
//        TabLayoutMediator(findViewById(R.id.mTabLayout2), mViewPager) { tab, position ->
//            tab.text = "TAB $position"
//        }.attach()
    }

    class WebViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val webView: VWebView? = itemView.findViewById(R.id.mWebView)
        fun loadUrl() {
            webView?.loadUrl("https://juejin.cn/post/6979575721693806629?utm_source=gold_browser_extension")
        }
    }

    class RecyclerViewHodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mRecyclerView = itemView.findViewById<RecyclerView>(R.id.mRecyclerView)
        fun loadData() {
            // 方数据等
            //val mRecyclerView = inflate.findViewById<RecyclerView>(R.id.mRecyclerView)
            mRecyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            mRecyclerView.adapter =
                object : RecyclerView.Adapter<RecyclerViewFragment.MyAdapter>() {
                    override fun getItemCount(): Int = 1000
                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): RecyclerViewFragment.MyAdapter =
                        RecyclerViewFragment.MyAdapter(
                            LayoutInflater.from(parent.context)
                                .inflate(android.R.layout.simple_list_item_1, parent, false)
                        )

                    override fun onBindViewHolder(
                        holder: RecyclerViewFragment.MyAdapter,
                        position: Int
                    ) {
                        val mTv = holder.itemView.findViewById<TextView>(android.R.id.text1)
                        mTv.setText("$position")
                        mTv.setTextColor(Color.BLACK)
                        mTv.setTextSize(10F)
                    }
                }
        }
    }
}