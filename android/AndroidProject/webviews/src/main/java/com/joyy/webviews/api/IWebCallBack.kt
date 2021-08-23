package com.joyy.webviews.api

import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView

/**
 * Time:2021/8/23 14:02
 * Author: flannery
 * Description:
 */
interface IWebCallBack {
    /**************** WebViewClient *************/

    fun shouldOverrideUrlLoadingWithLoadUrl(
        view: WebView?,
        request: String?
    ): Boolean // 本WebView进行跳转

    fun onPageStarted(view: WebView?, url: String?, favIcon: Bitmap?)

    fun onPageFinished(isRecError: Boolean)

    fun onReceivedError()

    /**************** WebChromeClient *************/

    fun onReceivedTitle(view: WebView?, title: String?)

    fun onProgressChanged(newProgress: Int)

    fun onHideCustomView()

    fun doOpenFileChooser(intent: Intent)

    fun onShowCustomView(view: View, callback: WebChromeClient.CustomViewCallback?)

    fun ignoreSslError(url: String?): Boolean

    /**************** 用户信息 *************/
    fun userAgentString(): String
}