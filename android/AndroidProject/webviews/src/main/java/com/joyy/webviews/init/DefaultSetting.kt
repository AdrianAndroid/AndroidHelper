package com.joyy.webviews.init

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import com.joyy.webviews.BuildConfig

/**
 * WebView Setting
 *
 * @author chen wenwei
 * @Date 2019/02/18
 */
class DefaultSetting {
    private var mWebSettings: WebSettings? = null

    @SuppressLint("SetJavaScriptEnabled")
    fun init(mWebView: WebView?) {
        mWebSettings = mWebView?.settings
        // 调试chrome://inspect/#devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
        }
        mWebSettings?.apply {
            // JS
            javaScriptEnabled = true
            // 提高渲染的优先级
            setRenderPriority(WebSettings.RenderPriority.HIGH)
            // UserAgent
            userAgentString += yyDefaultUaInner

            if (false) {
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
            } else {
                // 隐藏缩放控件
                builtInZoomControls = false
            }

            // 支持viewPort设置
            useWideViewPort = false
            // 把图片加载放在最后来加载渲染
            blockNetworkImage = false
            // Http和Https混合:允许从任何来源加载内容，即使起源是不安全的
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
            // 缓存相关
            cacheMode = WebSettings.LOAD_DEFAULT
            domStorageEnabled = true
            setAppCacheEnabled(true)
        }
    }

    companion object {
        // 强行设置User-Agent,和YYMobileApp对应
        val yyDefaultUaInner: String
            get() {
                val baseUserAgent = sharedVersionString() + addOnePieceUserAgent()
                //val ver = VersionUtil.getLocalVer(RuntimeContext.sApplicationContext)

                return ("Platform/Android" + Build.VERSION.RELEASE +
                        " APP/" + "APPID" +
//                        " AppVersion/" + ver.versionNameWithoutSnapshot +
                        " Model/" + Build.MODEL +
                        " Browser/Default" +
                        " " + baseUserAgent)
            }

        private fun sharedVersionString(): String {
            val edition = "未处理"//Constants.APPID
            // 仿照手Y版本号；
            // TODO:zhangdeheng 6.5.0先不要升，会影响到后台及前端的相关功能
            return " YY(ClientVersion:6.5.0,ClientVerCode:6.5.0，ClientEdition:$edition)"
        }

        private fun addOnePieceUserAgent(): String {
            return "addOnePieceUserAgent 未添加 "
//            val envStr = if (DomainProvider.isProductUrl()) {
//                "Online"
//            } else {
//                "Preview"
//            }
//            return " Environment/" + envStr +
//                    " NetType/" + NetworkUtils.getNetworkName(RuntimeContext.sApplicationContext) +
//                    " UserMode/" + if (QuesGoManager.mUserInfoService.getUid() > 0) "Registered" else "Guest"
        }
    }
}
