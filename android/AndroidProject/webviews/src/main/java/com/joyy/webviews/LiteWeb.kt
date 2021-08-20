package com.joyy.webviews

import android.webkit.WebView
import com.joyy.webviews.init.DefaultSetting
import com.joyy.webviews.init.DefaultWebChromeClient
import com.joyy.webviews.init.DefaultWebViewClient
import com.joyy.webviews.js.IJsUICallBack
import com.joyy.webviews.js.IWebCallBack
import com.joyy.webviews.js.handler.IModuleJS

/**
 * Time:2021/8/20 14:16
 * Author: flannery
 * Description:
 */
class LiteWeb : ILiteWeb {
    var webView: WebView? = null
    var callBack: IWebCallBack? = null
    var jsUICallBack: IJsUICallBack? = null
    var mIMouleJs = mutableSetOf<IModuleJS>()

    private var mWebChromeClient: DefaultWebChromeClient? = null
    private var mWebViewClient: DefaultWebViewClient? = null
    private var mSetting: DefaultSetting? = null

    override fun create() {
        mWebChromeClient = DefaultWebChromeClient()
        mWebViewClient = DefaultWebViewClient()
        mSetting = DefaultSetting()
        // 设置回调
        mWebViewClient?.setClientCallack(callBack)
        mWebChromeClient?.setClientCallack(callBack)
        initWebView()
        WebLog.log("LiteWeb create()")
    }

    private fun initWebView() {
        webView?.webChromeClient = mWebChromeClient
        webView?.webViewClient = mWebViewClient!!
//        webView?.addJavascriptInterface()
    }


    override fun loadUrl(url: String?, force: Boolean?) {
    }

    override fun refresh() {
    }

    override fun loadYyJs(params: Any?, name: String) {
    }

    override fun resume() {
    }

    override fun destroy() {
    }

    override fun addModuleJS(moduleJS: IModuleJS) {
    }

    override fun removeModuleJS(moduleJS: IModuleJS) {
    }

}