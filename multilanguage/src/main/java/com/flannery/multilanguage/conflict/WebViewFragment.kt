package com.flannery.multilanguage.conflict

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.flannery.multilanguage.R

class WebViewFragment : Fragment() {

    val handler = Handler(Looper.myLooper()!!)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mNestedWebView: NestedWebView = view.findViewById<NestedWebView>(R.id.mNestedWebView)
        mNestedWebView.loadUrl("https://juejin.cn/post/6979575721693806629?utm_source=gold_browser_extension")
//        mNestedWebView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzI4MTY5NTk4Ng==&mid=2247497882&idx=4&sn=317b710b951f6a436f3f0ba1a40dcb13&chksm=eba7fb07dcd07211cf8baa1a822afa17e648a683908c58a4edc75155f52d980ea734ba857256&mpshare=1&scene=1&srcid=07025lapcEgVEafaZRH1KuPC&sharer_sharetime=1625192203023&sharer_shareid=df3106ba40f707d0a9d9a4f27ffc2ada&version=3.1.8.90238&platform=mac#rd")
//        mNestedWebView.loadUrl("http://m.jd.com/")
        mNestedWebView.isVerticalScrollBarEnabled = false // 上下弹动


//        handler.postDelayed({
//            //mNestedWebView.scrollTo(0, -100)
//            mNestedWebView.scrollBy(0, 100)
//            Toast.makeText(context, "${mNestedWebView.contentHeight}", Toast.LENGTH_SHORT).show()
//        }, 2000)
    }

}