package com.joyy.webviews.utils

import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * Time:2021/8/23 10:25
 * Author: flannery
 * Description:
 */
object JSHelper {

    fun log(msg: String) {
        Log.e("JSHelper", msg)
    }

    const val ON_BRIDGE_EVENT = "onBridgeEvent"

    private const val INVOKE_WEB_METHOD_JSON =
        "javascript:try{window.YYApiCore.invokeWebMethod('%s',JSON.parse('%s'))}catch(e){if(console)console.log(e)}"

    private const val INVOKE_WEB_METHOD =
        "javascript:try{window.YYApiCore.invokeWebMethod('%s',%s)}catch(e){if(console)console.log(e)}"

    private const val INVOKR_TEACHEE_WEB =
        "javascript:try{window.%s}catch(e){if(console)console.log(e)}"

    /**
     * YY协议， 向H5发送请求
     */
    fun loadYyJs(webView: WebView, params: Any?, func: String? = ON_BRIDGE_EVENT) {
        val invokeStr = String.format(
            if (params is JSONObject) INVOKE_WEB_METHOD else INVOKE_WEB_METHOD_JSON,
            func,
            params
        )
        loadJavaScript(webView, invokeStr)
    }

    private fun loadJavaScript(
        webView: WebView,
        invokeStr: String,
        cb: ValueCallback<String>? = null
    ) {
        if (invokeStr.isBlank()) return //空的没啥意义
        //suspend CoroutineScope.() -> Unit
        val func: suspend CoroutineScope.() -> Unit = { // 通过协程处理
            webView.evaluateJavascript(invokeStr, cb)
        }
        if (webView.parent is LifecycleOwner) {
            (webView.parent as LifecycleOwner).lifecycleScope.launch(block = func) // 局部作用域
        } else {
            GlobalScope.launch(block = func) // 全局作用域// 不建议用
        }
    }

}