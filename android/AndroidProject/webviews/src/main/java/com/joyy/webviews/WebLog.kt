package com.joyy.webviews

import android.util.Log

/**
 * Time:2021/8/20 16:06
 * Author: flannery
 * Description:
 */
internal object WebLog { // 仅模块使用
    fun log(str: String) {
        Log.i("[WebView]", str)
    }
}