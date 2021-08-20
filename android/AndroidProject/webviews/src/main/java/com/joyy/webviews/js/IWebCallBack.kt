package com.joyy.webviews.js

import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView

/**
 * WebViewClient 和 WebChromeClient 回调
 *
 * @author chen wenwei
 * @Date 2019/3/2
 */
interface IWebCallBack {
    /**************** WebViewClient *************/

//    fun onInterceptShouldOverrideUrlLoading(view: WebView, url: String): Boolean //是否拦截处理

    fun shouldOverrideUrlLoadingWithLoadUrl(view: WebView, url: String): Boolean // 本WebView进行跳转

    fun onPageStarted(view: WebView?, url: String?, favIcon: Bitmap?)

    fun onPageFinished(isRecError: Boolean)

    fun onReceivedError()

    /**************** WebChromeClient *************/

    fun onReceivedTitle(view: WebView?, title: String?)

    fun onProgressChanged(newProgress: Int)

    fun onHideCustomView()

    fun doOpenFileChooser(intent: Intent, message: UploadMessage)

    fun onShowCustomView(view: View, callback: WebChromeClient.CustomViewCallback)
}
