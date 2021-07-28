package com.joyy.loglibrary.init

import com.joyy.loglibrary.api.ILog
import com.joyy.loglibrary.utils.RuntimeInfo

/**
 * Time:2021/7/27 17:34
 * Author: flannery
 * Description:
 */
class LogImpl : ILog {
    private var mLogcatVisible = RuntimeInfo.sIsDebuggable

    private fun shouldLog(priority: Int) : Boolean


    override fun logcatVisible(visible: Boolean) {
    }

    override fun v(tag: String, message: () -> Any?) {
    }

    override fun v(tag: String, format: String, vararg args: Any?) {
    }

    override fun v(tag: String, message: String) {
    }

    override fun d(tag: String, message: () -> Any?) {
    }

    override fun d(tag: String, format: String, vararg args: Any?) {
    }

    override fun d(tag: String, message: String) {
    }

    override fun i(tag: String, message: () -> Any?) {
    }

    override fun i(tag: String, format: String, vararg args: Any?) {
    }

    override fun i(tag: String, message: String) {
    }

    override fun w(tag: String, message: () -> Any?) {
    }

    override fun w(tag: String, format: String, vararg args: Any?) {
    }

    override fun w(tag: String, message: String) {
    }

    override fun e(tag: String, message: () -> Any?, error: Throwable?) {
    }

    override fun e(tag: String, format: String, error: Throwable?, vararg args: Any?) {
    }

    override fun e(tag: String, message: String, error: Throwable?) {
    }
}