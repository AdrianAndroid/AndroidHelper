package com.joyy.webviews

import android.webkit.JavascriptInterface
import com.joyy.webviews.api.IWebJS
import com.joyy.webviews.utils.JSHelper
import java.lang.IllegalArgumentException

/**
 * Time:2021/8/23 11:38
 * Author: flannery
 * Description: 跟WebView交互
 */
class JSInterface : IWebJS {


    private val hashMap = hashMapOf<String, Command>() // 存放与JS交互的方法

    // 唯一的跟WebView交互
    @JavascriptInterface
    override fun invoke(name: String?, params: String?, cbName: String?): String? {
        JSHelper.log(">>>${Thread.currentThread().name} $name $params $cbName <<<")
        return hashMap[name]?.invokeMethod?.invoke(name, params, cbName)
    }

    fun addCommand(command: Command) {
        if (command.name.isBlank()) throw IllegalArgumentException("name不能为空!!")
        hashMap[command.name] = command
    }

}