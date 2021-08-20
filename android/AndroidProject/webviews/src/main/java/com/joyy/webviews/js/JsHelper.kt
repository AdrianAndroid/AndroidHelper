package com.joyy.webviews.js

import android.os.Looper
import android.webkit.WebView
import com.joyy.webviews.ON_BRIDGE_EVENT
import com.joyy.webviews.WebLog
import org.json.JSONObject

/**
 * Author: chenwenwei
 * Date: 2020-01-06
 * Desc: Js帮助类 load js统一入口
 * 请通过 LiteWeb 或者是 QuesGoManager.mWeb使用
 * SinceVer: 1.4.0
 */
object JsHelper {

    private const val INVOKE_WEB_METHOD_JSON =
        "javascript:try{window.YYApiCore.invokeWebMethod('%s',JSON.parse('%s'))}catch(e){if(console)console.log(e)}"

    private const val INVOKE_WEB_METHOD =
        "javascript:try{window.YYApiCore.invokeWebMethod('%s',%s)}catch(e){if(console)console.log(e)}"

    private const val INVOKR_TEACHEE_WEB =
        "javascript:try{window.%s}catch(e){if(console)console.log(e)}"

    /**
     * YY协议，向H5发送请求
     * 命令为 {@see INVOKE_WEB_METHOD_JSON}
     * @param params 参数，可以为String，或者是jsonObject  如{"name":"eve","age":"18"}
     * @param function H5暴露的方法名，默认为onBridgeEvent
     */
    fun loadYyJs(webView: WebView?, params: Any?, function: String? = ON_BRIDGE_EVENT) {
        val invokeStr = String.format(
            if (params is JSONObject) INVOKE_WEB_METHOD else INVOKE_WEB_METHOD_JSON,
            function,
            params
        )
        loadJavaScript(webView, invokeStr)
    }

    // 执行JS,所有入口
    fun loadJavaScript(webView: WebView?, js: String?) {
        WebLog.log("loadJavaScript : $js")

        if (!js.isNullOrBlank() && Looper.getMainLooper() == Looper.myLooper()) {
            webView?.evaluateJavascript(js, null)
        }

    }

    /**
     *  teachee题目和H5页面混编js协议
     *  塞数据：window.setQ
     *  拿结果：window.getA
     */
    fun loadTeacheeJs(webView: WebView?, params: String, returnStr: ITeacheeJsCallBack?) {
        WebLog.log("loadTeacheeJs : $params")
        if (Looper.getMainLooper() == Looper.myLooper()) {
            webView?.evaluateJavascript(String.format(INVOKR_TEACHEE_WEB, params)) { value ->
                WebLog.log("onReceiveValue : $value")
                returnStr?.onReceiveValue(value)
            }
        }
    }
}

interface ITeacheeJsCallBack {
    fun onReceiveValue(value: String?)
}