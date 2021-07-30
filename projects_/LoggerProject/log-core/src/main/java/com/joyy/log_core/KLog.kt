package com.joyy.log_core

import android.util.Log
import com.joyy.log_core.api.IFile
import com.joyy.log_core.api.ILog
import com.joyy.log_core.comm.FileWriter
import java.io.File

/**
 * Time:2021/7/29 10:59
 * Author: flannery
 * Description:
 */
object KLog : ILog {
    private const val TAG = "[KLog]"

    /**
     * 写入的目录
     * IFile 写入本地文件的类
     */
    fun initLog(logDir: File, iFile: IFile) {
        logDir.mkdirs() //没有的话需要重新创建
        LogManager.logDir = logDir.absolutePath
        LogManager.start(iFile)
    }

    fun end() {
        LogManager.end()
    }

    // 全部的状况

    override fun v(tag: String?, msg: String?, tr: Throwable?) {
        if (LogManager.useLocalLog) Log.v(tag, msg, tr)
        if (LogManager.useFileLog) FileWriter.v(tag, msg, tr)
    }

    override fun d(tag: String?, msg: String?, tr: Throwable?) {
        if (LogManager.useLocalLog) Log.v(tag, msg, tr)
        if (LogManager.useFileLog) FileWriter.d(tag, msg, tr)
    }

    override fun i(tag: String?, msg: String?, tr: Throwable?) {
        if (LogManager.useLocalLog) Log.i(tag, msg, tr)
        if (LogManager.useFileLog) FileWriter.i(tag, msg, tr)
    }

    override fun w(tag: String?, msg: String?, tr: Throwable?) {
        if (LogManager.useLocalLog) Log.w(tag, msg, tr)
        if (LogManager.useFileLog) FileWriter.w(tag, msg, tr)
    }

    override fun e(tag: String?, msg: String?, tr: Throwable?) {
        if (LogManager.useLocalLog) Log.e(tag, msg, tr)
        if (LogManager.useFileLog) FileWriter.e(tag, msg, tr)
    }

    // 只有Throwable

    fun vt(tr: Throwable? = null) = v(TAG, null, tr ?: Throwable())

    fun dt(tr: Throwable? = null) = d(TAG, null, tr ?: Throwable())

    fun it(tr: Throwable? = null) = i(TAG, null, tr ?: Throwable())

    fun wt(tr: Throwable? = null) = w(TAG, null, tr ?: Throwable())

    fun et(tr: Throwable? = null) = e(TAG, null, tr ?: Throwable())

    // 只有String

    fun v(st: String) = v(TAG, st, null)

    fun d(st: String) = d(TAG, st, null)

    fun i(st: String) = d(TAG, st, null)

    fun w(st: String) = d(TAG, st, null)

    fun e(st: String) = d(TAG, st, null)

    // 使用lamda回调

    fun v(tag: String = TAG, c: () -> String) = v(tag, c(), null)

    fun d(tag: String = TAG, c: () -> String) = d(tag, c(), null)

    fun i(tag: String = TAG, c: () -> String) = d(tag, c(), null)

    fun w(tag: String = TAG, c: () -> String) = d(tag, c(), null)

    fun e(tag: String = TAG, c: () -> String) = d(tag, c(), null)
}