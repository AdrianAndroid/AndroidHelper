package com.flannery.multilanguage

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.flannery.multilanguage.conflict.NestedWebView

class WebViewActivity : AppCompatActivity() {

    val handler = Handler(Looper.myLooper()!!);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val mNestedWebView: NestedWebView = findViewById<NestedWebView>(R.id.mNestedWebView)
        //mNestedWebView.loadUrl("http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0716/3192.html")
        mNestedWebView.loadUrl("https://www.baidu.com/s?ie=UTF-8&wd=%E6%B2%A1%E6%9C%89%E8%B0%83%E7%94%A8onNestedPreScroll")
//        mNestedWebView.loadUrl("https://juejin.cn/post/6979575721693806629?utm_source=gold_browser_extension")


        handler.postDelayed({
            mNestedWebView.scrollTo(0, -100)
            Toast.makeText(this, "${mNestedWebView.contentHeight}", Toast.LENGTH_SHORT).show()
        }, 2000)

    }
}