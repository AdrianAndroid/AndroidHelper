package com.joyy.webviews

import com.joyy.webviews.js.handler.IModuleJS

const val ON_BRIDGE_EVENT = "onBridgeEvent"

/**
 * Time:2021/8/20 14:16
 * Author: flannery
 * Description:
 */
interface ILiteWeb {

    // 创建
    fun create()

    // 调用网址
    fun loadUrl(url: String?, force: Boolean? = false)

    // 刷新
    fun refresh()


    fun loadYyJs(params: Any?, name: String = ON_BRIDGE_EVENT)

    // 声明周期
    fun resume()

    // 声明周期
    fun destroy()

    /**
     * 添加模块，进行调用
     */
    fun addModuleJS(moduleJS: IModuleJS)

    /**
     * 删除相应的模块
     */
    fun removeModuleJS(moduleJS: IModuleJS)

}