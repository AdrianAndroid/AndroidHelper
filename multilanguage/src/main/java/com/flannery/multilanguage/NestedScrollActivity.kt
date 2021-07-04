package com.flannery.multilanguage

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NestedScrollActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scroll)


        val mNestedWebView = findViewById<WebView>(R.id.mNestedWebView)
        mNestedWebView.loadUrl("https://juejin.cn/post/6979575721693806629?utm_source=gold_browser_extension")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mNestedWebView.setOnScrollChangeListener { v, _, _, _, _ ->
                Log.e("TAG", "${mNestedWebView.measuredHeight}")
            }
        }

        mNestedWebView.postDelayed({
            runOnUiThread {
                Toast.makeText(this, "${mNestedWebView.measuredHeight}", Toast.LENGTH_SHORT).show()
            }
        }, 2000)
    }
}