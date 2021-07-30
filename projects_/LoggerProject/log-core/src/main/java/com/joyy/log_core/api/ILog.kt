package com.joyy.log_core.api

/**
 * Time:2021/7/29 11:03
 * Author: flannery
 * Description:
 */
interface ILog {

    fun v(tag: String?, msg: String?, tr: Throwable? = null)

    fun d(tag: String?, msg: String?, tr: Throwable? = null)

    fun i(tag: String?, msg: String?, tr: Throwable? = null)

    fun w(tag: String?, msg: String?, tr: Throwable? = null)

    fun e(tag: String?, msg: String?, tr: Throwable? = null)
}