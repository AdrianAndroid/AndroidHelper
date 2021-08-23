package com.joyy.webviews.defaults

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.joyy.webviews.api.IWebCallBack
import java.net.URL

/**
 * Time:2021/8/23 14:57
 * Author: flannery
 * Description:
 */
open class WebCallBackImpl : IWebCallBack {
    override fun shouldOverrideUrlLoadingWithLoadUrl(
        view: WebView?,
        request: String?
    ): Boolean {
        return true
    }

    override fun onPageStarted(view: WebView?, url: String?, favIcon: Bitmap?) {
    }

    override fun onPageFinished(isRecError: Boolean) {
    }

    override fun onReceivedError() {
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
    }

    override fun onProgressChanged(newProgress: Int) {
    }

    override fun onHideCustomView() {
    }

    override fun doOpenFileChooser(intent: Intent) {
    }

    override fun onShowCustomView(view: View, callback: WebChromeClient.CustomViewCallback?) {
    }

    override fun ignoreSslError(url: String?): Boolean {
        val host = URL(url).host
        return host.endsWith(".huanjuyun.com")
                || host.endsWith(".duowan.com")
                || host.endsWith(".yy.com")
                || host.endsWith(".hiido.com")
                || host.endsWith("yystatic.com")
    }

    override fun userAgentString(): String {
        return ("Platform/Android" + Build.VERSION.RELEASE +
                //" APP/" + "APPID" +
                //" AppVersion/" + ver.versionNameWithoutSnapshot +
                " Model/" + Build.MODEL +
                " Browser/Default" +
                " "// + baseUserAgent
                )
    }
}