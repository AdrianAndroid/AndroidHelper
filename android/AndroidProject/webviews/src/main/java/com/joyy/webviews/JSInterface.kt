package com.joyy.webviews

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.joyy.webviews.api.IWeb
import com.joyy.webviews.api.IWebCallBack
import com.joyy.webviews.api.IWebJS
import com.joyy.webviews.default.DefaultWebChromeClient
import com.joyy.webviews.default.DefaultWebViewClient
import com.joyy.webviews.default.WebCallBackImpl
import com.joyy.webviews.utils.JSHelper
import java.lang.IllegalArgumentException

/**
 * Time:2021/8/23 11:38
 * Author: flannery
 * Description: 跟WebView交互
 */
@SuppressLint("SetJavaScriptEnabled")
class JSInterface(val webView: WebView) : WebCallBackImpl(),
    IWebJS,
    IWeb {

    //IWebJS////////////////////////
    // 与JS交互的方法
    private val hashMap = hashMapOf<String, Command>() // 存放与JS交互的方法

    init {
        create()
    }

    private fun initWebSsettings() {
        val s = webView.settings
        s.javaScriptEnabled = true
        s.setRenderPriority(WebSettings.RenderPriority.HIGH) // 提高渲染的优先级
        // 隐藏缩放
        s.builtInZoomControls = false
        // 打开缩放
        //        s.setSupportZoom(true)
        //        s.builtInZoomControls = true
        //        s.displayZoomControls = false
        // 支持ivePorys设置
        s.useWideViewPort = false
        // 把图片加载放在最后来加载渲染
        s.blockNetworkImage = false
        // Http和Https混合，允许从任何来源来加载内容，即使起源是不安全的
        s.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        // 缓存相关
        s.cacheMode = WebSettings.LOAD_DEFAULT
        s.domStorageEnabled = true
        s.setAppCacheEnabled(true)
    }


    // 唯一的跟WebView交互
    @JavascriptInterface
    override fun invoke(name: String?, params: String?, cbName: String?): String? {
        JSHelper.log(">>>${Thread.currentThread().name} $name $params $cbName <<<")
        return hashMap[name]?.invokeMethod?.invoke(name, params, cbName)
    }


    //IWeb/////////////////////////
    override fun loadUrl(url: String, force: Boolean) {
        if (force || webView.url != url) {
            if (url.startsWith("http") || url.startsWith("file")) {
                webView.settings.blockNetworkImage = true
            }
            webView.loadUrl(url)
        }
    }

    override fun create() {
        webView.webViewClient = DefaultWebViewClient(this)
        webView.webChromeClient = DefaultWebChromeClient(this)

        // 可以设置DEBUG
        if (BuildConfig.DEBUG) WebView.setWebContentsDebuggingEnabled(true)
        initWebSsettings()

        // 注入JS
        webView.addJavascriptInterface(this, "app")
    }

    override fun resume() {
    }

    override fun destroy() {
    }

    override fun refresh() {
    }

    override fun addCommand(command: Command) {
        if (command.name.isBlank()) throw IllegalArgumentException("name不能为空!!")
        hashMap[command.name] = command
    }

    override fun removeCommand(command: Command) {
        hashMap.remove(command.name)
    }


}