package com.yy.quesgo.web.js.api

import android.webkit.JavascriptInterface
import com.joyy.webviews.WebLog

/**
 * Author: chenwenwei
 * Date: 2020-01-06
 * Desc: 收到JS的接口
 * SinceVer: 1.4.0
 */
class JsInterface {

    @JavascriptInterface
    fun invoke(name: String, params: String, callback: String) {
        WebLog.log("JsInterface invoke name=$name params=$params callback=$callback")
    }

}
