package com.joyy.webviews.init

import android.annotation.TargetApi
import android.net.Uri
import android.os.Build
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.joyy.webviews.WebLog
import com.joyy.webviews.js.IWebCallBack
import com.joyy.webviews.js.UploadMessage

private const val TAG = "DefaultWebChromeClient"

/**
 * 默认的 WebChromeClient
 *
 * @author chen wenwei
 * @Date 2019/2/14
 */
class DefaultWebChromeClient : WebChromeClient() {
    private var mClientCallack: IWebCallBack? = null

    private var uploadMessage = UploadMessage()

    fun setClientCallack(callback: IWebCallBack?) {
        this.mClientCallack = callback
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
        val message = consoleMessage.message()
        val lineNumber = consoleMessage.lineNumber()
        val level = consoleMessage.messageLevel()
        WebLog.log("$TAG onConsoleMessage::msg = $message \t lineNumber = $lineNumber \t level=$level")
        return super.onConsoleMessage(consoleMessage)
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        WebLog.log("$TAG onReceivedTitle :: title = $title")
        mClientCallack?.onReceivedTitle(view, title)
        super.onReceivedTitle(view, title)
    }

    override fun onProgressChanged(view: WebView, newProgress: Int) {
        WebLog.log("$TAG onProgressChanged ::  progress = $newProgress")
        mClientCallack?.onProgressChanged(newProgress)
        super.onProgressChanged(view, newProgress)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onShowFileChooser(
        webView: WebView,
        filePathCallback: ValueCallback<Array<Uri>>,
        fileChooserParams: WebChromeClient.FileChooserParams
    ): Boolean {
        mClientCallack?.apply {
            uploadMessage.setUploadMessageAboveL(filePathCallback)
            doOpenFileChooser(
                uploadMessage.openImageChooserActivity(fileChooserParams.acceptTypes),
                uploadMessage
            )
        }
        return true
    }

    fun openFileChooser(valueCallback: ValueCallback<Uri>) {
        // uploadMessage = valueCallback;
        mClientCallack?.apply {
            uploadMessage.setUploadMessage(valueCallback)
            doOpenFileChooser(uploadMessage.openImageChooserActivity("image/*"), uploadMessage)
        }
    }

    // For Android  >= 3.0
    fun openFileChooser(valueCallback: ValueCallback<Uri>, acceptType: String) {
        mClientCallack?.apply {
            uploadMessage.setUploadMessage(valueCallback)
            doOpenFileChooser(uploadMessage.openImageChooserActivity(acceptType), uploadMessage)
        }
    }

    // For Android  >= 4.1
    fun openFileChooser(valueCallback: ValueCallback<Uri>, acceptType: String, capture: String) {
        mClientCallack?.apply {
            uploadMessage.setUploadMessage(valueCallback)
            doOpenFileChooser(uploadMessage.openImageChooserActivity(acceptType), uploadMessage)
        }
    }

    override fun onShowCustomView(view: View, callback: WebChromeClient.CustomViewCallback) {
        WebLog.log("$TAG onShowCustomView1 ::  view = $view")
        mClientCallack?.onShowCustomView(view, callback)
        super.onShowCustomView(view, callback)
    }

    override fun onShowCustomView(
        view: View,
        requestedOrientation: Int,
        callback: WebChromeClient.CustomViewCallback
    ) {
        mClientCallack?.onShowCustomView(view, callback)
        WebLog.log("$TAG onShowCustomView2 ::  view = $view")
        super.onShowCustomView(view, requestedOrientation, callback)
    }

    override fun onHideCustomView() {
        WebLog.log("$TAG onHideCustomView")
        mClientCallack?.onHideCustomView()
        super.onHideCustomView()
    }
}
