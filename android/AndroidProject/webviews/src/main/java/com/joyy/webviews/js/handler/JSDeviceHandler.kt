package com.yy.quesgo.web.js.handler

import com.joyy.webviews.js.IJsCallBack
import com.joyy.webviews.js.handler.IJsApi

/**
 * JS的 module 分发类：Data
 *
 * @author chen wenwei
 * @Date 2019/01/30
 */
class JSDeviceHandler(private val callback: IJsCallBack) : IJsApi {


    override fun invoke(name: String?, params: String?, cbName: String?): String? {
        return when (name) {
            else -> null
        }
    }
}