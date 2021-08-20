package com.joyy.webviews.js

import com.google.gson.Gson

/**
 * JS回调接口
 * 如果H5返回的callback name为空，就不需要执行
 *
 * @author chen wenwei
 * @Date 2019/01/30
 */

interface IJsCallBack {
    fun callback(name: String?, value: String?)
}

/**
 * 1.YYApiCore.invokeClientMethod接收 retrun的值（同步）
 * 2.H5通过cbName接受 json值 （可以是异步）
 */
fun IJsCallBack.returnToH5(cbName: String?, obj: Any): String {
    val jsonString = Gson().toJson(obj)
    callback(cbName, jsonString)
    return jsonString
}