package com.joyy.webviews.init

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.webkit.*
import com.joyy.webviews.LiteWebDef
import com.joyy.webviews.WebLog
import com.joyy.webviews.js.IWebCallBack
import java.net.URL

private const val TAG = "DefaultWebViewClient"

/**
 * 默认的 WebViewClient
 *
 * @author chen wenwei
 * @Date 2019/2/14
 */
class DefaultWebViewClient : WebViewClient() {
    private val clientLoadUrl = "objc://clientLoadUrl/"
    private var ignoreSslError = false
    private var mClientCallBack: IWebCallBack? = null
    private var mRecvError = false
    var mCurrentUrl: String? = null
    private var mIgnoreOverrideUrlFragment =
        listOf<String>("OrderManagerWebFragment", "BatchWebFragment")

    fun setClientCallack(callback: IWebCallBack?) {
        this.mClientCallBack = callback
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        //if (mClientCallBack?.onInterceptShouldOverrideUrlLoading(view, url) == true) return true //可以后续处理

        WebLog.log("$TAG shouldOverrideUrlLoading :: url = $url")
        //开启google play
        if (url.startsWith(LiteWebDef.JS_START_MARKET)) {
            val marketUri = Uri.parse(url)
            go2GooglePlay(marketUri.getQueryParameter("id"))
            return true
        }


        //启动浏览器
        if (url.startsWith(LiteWebDef.JS_START_APP)) {
            try {
                Intent.parseUri(url, Intent.URI_INTENT_SCHEME).apply {
                    addCategory("android.intent.category.BROWSABLE")
                    component = null
                    selector = null
                    view.context.startActivity(this)
                }
            } catch (e: Exception) {
                WebLog.log("$TAG shouldOverrideUrlLoading startapp Exception")
            }
            return true
        }

        //启动app 和 启动浏览器不要对调位置  不然会拦截
        if (url.startsWith(LiteWebDef.JS_START_APP_COMPATIIBILITY)) {
            var intent: Intent? = null
            kotlin.runCatching {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME).apply {
                    action = "android.intent.action.VIEW"
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    component = null
                    selector = null
                }
                view.context.startActivity(intent)
            }.onFailure {
                WebLog.log("startLink fail :$url")
                if (it is ActivityNotFoundException) {
                    go2GooglePlay(intent?.`package`)
                }
            }
            return true
        }

        //规范： document.location = "js://quesgo?arg1=cww&arg2=eve"
        val uri = Uri.parse(url)
        if (uri.scheme == "js") {
            if (uri.authority == "Constants.APP_CONFIG_ID") {
                val args = uri.queryParameterNames
                WebLog.log("$TAG shouldOverrideUrlLoading document.location $args")
            }
            return true
        }
        if (mClientCallBack?.shouldOverrideUrlLoadingWithLoadUrl(view, url) == false) { //是否拦截正常跳转
            if (checkIgnoreUrlFragment(mClientCallBack?.javaClass?.simpleName ?: "")) {
                return false
            } else {
                view.loadUrl(url)
            }
        }

        return true
    }

    //检查是否是不拦截url的Fragment
    private fun checkIgnoreUrlFragment(className: String): Boolean {
        return mIgnoreOverrideUrlFragment.contains(className)
    }

    private fun go2GooglePlay(packageName: String?) {
        throw IllegalArgumentException(packageName)
//        try {
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse("market://details?id=$packageName")
//            intent.setPackage("com.android.vending") //这里对应的是谷歌商店，跳转别的商店改成对应的即可
//            if (intent.resolveActivity(RuntimeContext.sActivityContext.packageManager) != null) {
//                RuntimeContext.sActivityContext.startActivity(intent)
//            } else {//没有应用市场，通过浏览器跳转到Google Play
//                val intent2 = Intent(Intent.ACTION_VIEW)
//                intent2.data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
//                if (intent2.resolveActivity(RuntimeContext.sActivityContext.packageManager) != null) {
//                    RuntimeContext.sActivityContext.startActivity(intent2)
//                } else {
//                    //没有Google Play 也没有浏览器
//                    KLog.e(TAG) { "can not handle google play" }
//                }
//            }
//        } catch (activityNotFoundException1: ActivityNotFoundException) {
//            KLog.e(TAG) { "${activityNotFoundException1.message}" }
//        }
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        WebLog.log("$TAG onPageStarted :: url = $url")
        super.onPageStarted(view, url, favicon)
        mRecvError = false
        mCurrentUrl = url
        mClientCallBack?.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView, url: String) {
        WebLog.log("$TAG onPageFinished :: url = $url, recvError: $mRecvError")
        super.onPageFinished(view, url)
        view.settings.blockNetworkImage = false
        mCurrentUrl = url
        mClientCallBack?.onPageFinished(mRecvError)
    }

    override fun onLoadResource(view: WebView, url: String) {
        WebLog.log("$TAG onLoadResource :: url = $url")
        super.onLoadResource(view, url)
    }

    override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
        WebLog.log("$TAG onReceivedSslError")
        if (ignoreSslError) {
            handler.proceed()
            return
        }

        val url = error.url
        try {
            val uri = URL(url)
            val host = uri.host
            WebLog.log("$TAG onReceivedSslError :: host = $host")
            if (host.endsWith(".huanjuyun.com")
                || host.endsWith(".duowan.com")
                || host.endsWith(".yy.com")
                || host.endsWith(".hiido.com")
                || host.endsWith("yystatic.com")
            ) {
                handler.proceed()
                return
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onReceivedError(
        view: WebView,
        e: Int,
        des: String,
        url: String
    ) {
        super.onReceivedError(view, e, des, url)
        WebLog.log("$TAG onReceivedError :: errorCode = $e , description = $des , failingUrl = $url")
        mCurrentUrl = url
        onError()
    }

    override fun onReceivedHttpError(
        view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        if (Build.VERSION.SDK_INT >= 21) {
            val statusCode = errorResponse?.statusCode
            WebLog.log("$TAG onReceivedHttpError :: errorCode = $statusCode, description = ${errorResponse?.reasonPhrase}, failingUrl = ${request?.url}")
//            request?.url?.toString()?.run {
//                if (endsWith(".css", true) || endsWith(".js", true)) {
//                    onError(view)
//                }
//            }
        }
    }

    private fun onError() {
        WebLog.log("onError")
        mRecvError = true
        mClientCallBack?.onReceivedError()
    }
}