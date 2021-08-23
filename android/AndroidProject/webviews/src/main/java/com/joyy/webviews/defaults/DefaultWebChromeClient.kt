package com.joyy.webviews.default

import android.net.Uri
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.joyy.webviews.api.IWebCallBack
import com.joyy.webviews.utils.JSHelper

/**
 * Time:2021/8/23 14:03
 * Author: flannery
 * Description:
 */
class DefaultWebChromeClient(val callBack: IWebCallBack? = null) : WebChromeClient() {
    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        JSHelper.log(
            """
                onConsoleMessage::msg=${consoleMessage?.message()}
                    lineNumber=${consoleMessage?.lineNumber()}
                    level=${consoleMessage?.messageLevel()}
                """
        )
        return super.onConsoleMessage(consoleMessage)
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        callBack?.onReceivedTitle(view, title)
        super.onReceivedTitle(view, title)
    }


    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        callBack?.onProgressChanged(newProgress)
        super.onProgressChanged(view, newProgress)
    }

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        val acceptTypes = fileChooserParams?.acceptTypes
        if (acceptTypes != null && callBack != null) {
            callBack.doOpenFileChooser(UploadMessage.openImageChooserActivity(acceptTypes))
        }
        return true
    }


    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        view?.let { this.callBack?.onShowCustomView(it, callback) }
        super.onShowCustomView(view, callback)
    }

    override fun onShowCustomView(
        view: View?,
        requestedOrientation: Int,
        callback: CustomViewCallback?
    ) {
        view?.let { this.callBack?.onShowCustomView(it, callback) }
        super.onShowCustomView(view, requestedOrientation, callback)
    }

    override fun onHideCustomView() {
        callBack?.onHideCustomView()
        super.onHideCustomView()
    }

}