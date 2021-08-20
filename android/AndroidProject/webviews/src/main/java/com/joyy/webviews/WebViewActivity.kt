package com.joyy.webviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imooc.router.annotations.Destination
import kotlinx.android.synthetic.main.activity_web_view.*

@Destination(
    url = "module/webviews",
    description = "webviews"
)
class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

//        mWebView.loadUrl("https://www.jianshu.com/p/6c796944a19f")
        mWebView.loadUrl("file:///android_asset/web/test.html")
    }
}