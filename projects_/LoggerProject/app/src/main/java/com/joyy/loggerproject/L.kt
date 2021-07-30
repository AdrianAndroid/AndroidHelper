package com.joyy.loggerproject

import android.util.Log

/**
 * Time:2021/7/29 10:51
 * Author: flannery
 * Description:
 */
object L {
    fun log(msg: String) {
        Log.e("[[[L]]]", msg)
    }

    fun t() {
        Log.e("[[[L]]]", "<>", Throwable())
    }
}