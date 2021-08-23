package com.joyy.webviews.api

import com.joyy.webviews.Command

/**
 * Time:2021/8/23 13:56
 * Author: flannery
 * Description:
 */
interface IWeb {
    fun loadUrl(url: String, force: Boolean = false)

    fun create() // 创建
    fun resume() // 调用onResume的时候
    fun destroy() // destroy
    fun refresh() //刷新

    fun addCommand(command: Command)
    fun removeCommand(command: Command)
}