package com.yy.quesgo.web.js.api

import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.yy.quesgo.component.logger.KLog
import com.yy.quesgo.utils.DontProguardClass
import com.yy.quesgo.web.js.IJsUICallBack
import com.yy.quesgo.web.js.handler.IModuleJS

/**
 * Time:2021/7/14 6:24 下午
 * Author:fei
 * Description:前端觉得yyjs太重 在有些页面不适用所以单独搞了个js回调方法
 * Version:
 */
@DontProguardClass
class LiteJsInterface(private val mWebView: WebView?, val mJsUICallBack: IJsUICallBack?,
                      val mIModuleJSs: MutableSet<IModuleJS>?) : JsYYInterface(mWebView, mJsUICallBack, mIModuleJSs) {
    companion object {
        const val TAG = "LiteJsInterface"
    }

    @JavascriptInterface
    fun callAppResetPassword(): String {
        KLog.i(TAG) {
            "callAppResetPassword"
        }
        return jsInvokeApp("ui", RESET_PWD, "", "")
    }
}