//package com.joyy.log_core.conf
//
//import android.os.Environment
//import java.io.File
//import java.util.concurrent.atomic.AtomicBoolean
//
///**
// * Time:2021/7/29 12:00
// * Author: flannery
// * Description:
// */
//object LogOption {
//
////    var useLocalLog: Boolean = true //是否使用本地log
////    var useFileLog: Boolean = true //是否使用文件Log
//
//
//    private var level: Int = 0
//    private var maxSize: Int = 0
//    private var logCacheMaxSize: Long = 1024 * 1024 * 100L
//
//    private var logPath: String? = null
//    private var processTag: String = ""
//    private var publicKey: String = ""
//
//    var logDir: String = "/sdcard/logs" // 需要在初始化的时候替换
//    var logName: String = "KLog_"
//
//    /**
//     * 是否已经apply
//     * apply后只能设置logLevel singleLogMaxSize，而logPath，processTag, publicKey则不能再进行重复设置了
//     */
//    private val consume: AtomicBoolean = AtomicBoolean(false)
//
//    /**
//     * 指定log进程标识, 最终的日志文件名以此为前缀,如传入ylog,则文件名为ylogpid_2018_05_26_16_53.txt
//     */
////        override fun processTag(processTag: String): ILogConfig = apply {
////            if (!consume.get()) {
////                val replace = ProcessorUtils.getMyProcessName()?.replace(".", "-")?.replace(":", "-")
////                    ?: ""
////                LogConfig.processTag = "${processTag}__$replace"
////            }
////        }
//
//    /**
//     * 设置log等级，超过指定等级的log将会被输出
//     */
////        override fun logLevel(level: Int): ILogConfig = apply {
////            LogConfig.level = level
////            if (consume.get()) FileWriter.setLogLevel(level)
////        }
////
////        override fun logcat(visible: Boolean): ILogConfig = apply {
////            LogConfig.logcat = visible
////            if (consume.get())
////                KLog.mLogImpl?.logcatVisible(logcat)
////        }
//
//    /**
//     * 设置单个log文件的大小，当达到该大小时，自动执行文件切分
//     */
////        override fun singleLogMaxSize(maxSize: Int): ILogConfig = apply {
////            LogConfig.maxSize = maxSize
////            if (consume.get()) FileWriter.setFileMaxSize(maxSize)
////        }
//
//    /**
//     * 获取log输出的等级
//     */
//    fun getLogLevel(): Int = level
//
//    /**
//     * 设置写log的文件目录
//     */
////        override fun logPath(path: String?): ILogConfig = apply { if (!consume.get()) logPath = path }
//
//    /**
//     * 用于加密的client public key
//     */
////        override fun publicKey(key: String): ILogConfig = apply { if (!consume.get()) publicKey = key }
//
//    /**
//     * 获取log的文件目录
//     */
//    fun getLogPath(): String? {
//        return logPath
//    }
//
//    /**
//     * 设置log缓存目录最大值
//     * */
////        override fun logCacheMaxSiz(maxSize: Long): ILogConfig = apply { logCacheMaxSize = maxSize }
//
//    /**
//     *
//     * 获取缓存日志总之最大值
//     * */
//    fun getLogCacheMaxSize(): Long = logCacheMaxSize
//
//    /**
//     * 应用log配置，只需调用一次
//     */
////    override fun apply() {
////        Log.i(TAG, "apply")
////        if (!consume.getAndSet(true)) {
////            if (logPath == null) {
////                logPath = File(StorageUtils.getCacheDir(RuntimeInfo.sAppContext), "logs").path
////            }
////            if (!YYFileUtils.isFileCanWrite(logPath!!)) {
////                logPath = File(RuntimeInfo.sAppContext.filesDir, "logs")
////                    .apply {
////                        if (!exists()) mkdirs()
////                    }.absolutePath
////            }
////
////            Log.e("LogService", "path = $logPath")
////            FileWriter.run {
////                init()
////                setLogLevel(level)
////                setFileMaxSize(maxSize)
////                useConsoleLog(false)
////                open(
////                    logPath!!, File(RuntimeInfo.sAppContext.filesDir, "log").path, processTag,
////                    level, publicKey
////                )
////            }
////            KLog.mLogImpl?.logcatVisible(logcat)
////        }
////    }
//}