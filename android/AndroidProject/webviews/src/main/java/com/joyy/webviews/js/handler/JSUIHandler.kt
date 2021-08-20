package com.joyy.webviews.js.handler

import com.joyy.webviews.js.IJsCallBack
import com.joyy.webviews.js.IJsUICallBack

/**
 * JS的 module 分发类：UI
 *
 * @author chen wenwei
 * @Date 2019/01/30
 */
class JSUIHandler(private val callback: IJsCallBack, private val mJsUICallBack: IJsUICallBack?) :
    IJsApi {

    override fun invoke(name: String?, params: String?, cbName: String?): String? {
        return when (name) {
            "exit" -> exit(params, cbName)
            "popViewController" -> exit(params, cbName)
            "goto" -> _goto(params, cbName)
            else -> null
        }
    }

    private fun exit(params: String?, cbName: String?): String {
        mJsUICallBack?.exit()
//        return callback.returnToH5(cbName, ResultData(0))
        return ""
    }

    private fun _goto(params: String?, cbName: String?): String {
//        if (params == null) {
//            return "null"
//        }
//        try {
//            val jsonObject = JSONObject(params)
//            val uriString = jsonObject.optString("uri")
//            if (uriString.startsWith("yymobile://Web/Features/5/Url/")) {
//                //todo 用pathSegments写法才优雅
//                val str = uriString.replace("yymobile://Web/Features/5/Url/", "")
//                val dec = URLDecoder.decode(str, "UTF-8")
//                QuesGoManager.mNav.startWeb(Uri.decode(dec))
//            }
//        } catch (e: Exception) {
//            KLog.e(LiteWebDef.TAG_EXT, e) { "parse goto fail" }
//        }
//        return callback.returnToH5(cbName, ResultData(0))
        return ""
    }
}