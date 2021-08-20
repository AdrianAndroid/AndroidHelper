package com.yy.quesgo.web.js.api

import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.joyy.webviews.WebLog
import com.joyy.webviews.js.IJsCallBack
import com.joyy.webviews.js.IJsUICallBack
import com.joyy.webviews.js.JsApiHandler
import com.joyy.webviews.js.JsHelper
import com.joyy.webviews.js.handler.IModuleJS

open class JsYYInterface(
    private val mWebView: WebView?, private val mJsUICallBack: IJsUICallBack?,
    private val mIModuleJSs: MutableSet<IModuleJS>?
) {
    companion object {
        //前端没有使用yyjs这里的name可以自定义
        const val RESET_PWD = "callAppResetPassword"
    }

    private val TAG = "JsYYInterface"
    protected var mJSApi: JsApiHandler? = null
    protected var mJsCallBack: IJsCallBack = object : IJsCallBack {
        override fun callback(name: String?, value: String?) {
            JsHelper.loadYyJs(mWebView, value, name)
        }
    }

    @JavascriptInterface
    operator fun invoke(
        module: String,
        name: String,
        parameters: String,
        callback: String
    ): String {
        WebLog.log("$TAG invoke :: module = $module name = $name params = $parameters callback = $callback")
        return jsInvokeApp(module, name, parameters, callback)
    }

    protected fun jsInvokeApp(
        module: String,
        name: String,
        parameters: String,
        callback: String
    ): String {
        if (mJSApi == null) {
            mJSApi = JsApiHandler()
        }
        mJSApi?.create(module, mJsCallBack, mJsUICallBack)

//        try {
//            if (mJSApi == null) {
//                mJSApi = JsApiHandler()
//            }
//            var result = mJSApi!!.create(module, mJsCallBack, mJsUICallBack)
//                ?.invoke(name, parameters, callback)
//            if (result == null && mIModuleJSs != null) {
//                for (moduleJS in mIModuleJSs) {
//                    if (module.equals(moduleJS.moduleName(), true)) {
//                        moduleJS.setCallBack(mJsCallBack)
//                        result = moduleJS.invoke(name, parameters, callback)
//                        if (!result.isNullOrEmpty()) {
//                            break
//                        }
//                    }
//                }
//            }
//            KLog.i(TAG_REQ) {
//                "$TAG invoke :: result = $result module = $module " +
//                        "name = $name params = $parameters callback = $callback"
//            }
//            return result ?: "-1"
//        } catch (e: Exception) {
//            KLog.e(
//                TAG_REQ,
//                e
//            ) { "$TAG invoke :: module = $module name = $name params = $parameters Exception" }
//        }
//        return Gson().toJson(ResultData(-1))

        return ""
    }
}
