package com.joyy.webviews.defaults

import android.graphics.Bitmap
import android.net.http.SslError
import android.webkit.*
import com.joyy.webviews.api.IWebCallBack

/**
 * Time:2021/8/23 14:45
 * Author: flannery
 * Description:
 */
class DefaultWebViewClient(val callBack: IWebCallBack? = null) : WebViewClient() {

    private var mRecvError = false // 判断是否是因为错误
    private var mCurrentUrl: String? = null

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return callBack?.shouldOverrideUrlLoadingWithLoadUrl(view, request?.url?.host)
            ?: super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return callBack?.shouldOverrideUrlLoadingWithLoadUrl(view, url)
            ?: super.shouldOverrideUrlLoading(view, url)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        view?.settings?.blockNetworkImage = false
        mRecvError = false
        mCurrentUrl = url
        callBack?.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        view?.settings?.blockNetworkImage = false
        mCurrentUrl = url
        callBack?.onPageFinished(mRecvError)
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        if (callBack?.ignoreSslError(error?.url) == true) {
            handler?.proceed()
        } else {
            super.onReceivedSslError(view, handler, error)
        }
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        onError(request?.url?.host)
    }

    override fun onReceivedError(
        view: WebView?,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        onError(failingUrl)
    }

    private fun onError(failingUrl: String?) {
        mCurrentUrl = failingUrl
        mRecvError = true
        callBack?.onReceivedError()
    }

}