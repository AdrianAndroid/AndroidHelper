package com.joyy.webviews.js

import com.joyy.webviews.js.handler.IJsApi
import com.joyy.webviews.js.handler.JSDataHandler
import com.joyy.webviews.js.handler.JSUIHandler
import com.yy.quesgo.web.js.handler.JSDeviceHandler

/**
 * JS执行 - 工厂
 *
 * @author chen wenwei
 * @Date 2019/01/30
 */
class JsApiHandler {
    private var mJSUIHandler: JSUIHandler? = null

    private var mJSDataHandler: JSDataHandler? = null

    private var mJSDeviceHandler: JSDeviceHandler? = null

    fun create(module: String, callback: IJsCallBack, mJsUICallBack: IJsUICallBack? = null): IJsApi? {
        return when (module.toLowerCase()) {
            "ui" -> {
                if (mJSUIHandler == null) {
                    mJSUIHandler = JSUIHandler(callback, mJsUICallBack)
                }
                mJSUIHandler!!
            }
            "data" -> {
                if (mJSDataHandler == null) {
                    mJSDataHandler = JSDataHandler(callback)
                }
                mJSDataHandler!!
            }
            "device" -> {
                if (mJSDeviceHandler == null) {
                    mJSDeviceHandler = JSDeviceHandler(callback)
                }
                mJSDeviceHandler!!
            }
            else -> null
        }
    }
}
