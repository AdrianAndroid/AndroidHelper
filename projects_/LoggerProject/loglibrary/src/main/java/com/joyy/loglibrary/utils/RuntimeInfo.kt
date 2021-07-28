package com.joyy.loglibrary.utils

import android.content.Context

/**
 * Time:2021/7/27 15:34
 * Author:
 * Description:
 */
object RuntimeInfo {

    @JvmField
    var sProcessName: String? = ""

    @JvmField
    var sPackageName = ""

    lateinit var sAppContext: Context

    @JvmField
    var sIsDebuggable = false

    @JvmField
    var sIsMainProcess = true

    @JvmField
    var sVersion: String? = ""

    /**
     * 保存进程名
     *
     * @param processName
     **/
    fun processName(processName: String): RuntimeInfo = apply { sProcessName = processName }

    /**
     * 保存包名
     *
     * @param packageName
     * */
    fun packageName(packageName: String): RuntimeInfo = apply { sPackageName = packageName }

    /**
     * 保存application对象
     *
     * @param context
     * */
    fun appContext(context: Context): RuntimeInfo = apply { sAppContext = context }

    /**
     * 保存当前是否调试模式
     * @param debug
     * **/
    fun isDebuggable(debug: Boolean): RuntimeInfo = apply { sIsDebuggable = debug }

    /**
     * 保存当前进程是否UI进程
     * @param mainProcess
     * **/
    fun isMainProcess(mainProcess: Boolean): RuntimeInfo = apply { sIsMainProcess = mainProcess }

    /**
     * 保存当前进程版本号
     */
    fun version(ver: String): RuntimeInfo = apply { sVersion = ver }
}