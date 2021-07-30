package com.joyy.log_core

import android.annotation.SuppressLint
import com.joyy.log_core.api.IFile
import com.joyy.log_core.comm.FileWriter
import com.joyy.log_core.utils.LogCompress
import com.joyy.log_core.utils.TimeComparator
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Time:2021/7/29 16:55
 * Author: flannery
 * Description:
 */
object LogManager {
    private const val LOG_DATE_WITH_MINUTE_FORMAT_STR = "yyyy_MM_dd_HH"//_mm // 每小时一个文件

    /** 7 days.  */
    private const val DAYS_DELETE = 604800000// 7L * 24 * 60 * 60 * 1000

    /** 1days.**/
    private const val DAYS_COMPRESSED = 86400000 //1L * 24 * 60 * 60 * 1000

    val LOG_DATA_REGEX = Regex("_.*_(19|20)\\d{2}_[0-2]{2}_[0-9]{2}_[0-9]{2}_[0-9]{2}")

    private const val LOG_EXT = ".txt"
    private const val TAG = "LogManager"
    private var logCacheMaxSize: Long = 104857600 //1024 * 1024 * 100L

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat(LOG_DATE_WITH_MINUTE_FORMAT_STR)

    private var isStart = false;


    var useLocalLog: Boolean = true //是否使用本地log
    var useFileLog: Boolean = true //是否使用文件Log
    var logDir: String = "/sdcard/logs" // 需要在初始化的时候替换
    var currentLogFile: String? = null

    fun getLogFile(): File {
        val logDirFile = File(logDir)
        if (!logDirFile.exists()) logDirFile.mkdir()
        return File(logDirFile, "KLog_${sdf.format(Date())}$LOG_EXT")
    }

    fun isCurrentLogFile(logFile: File): Boolean = logFile.absolutePath == currentLogFile


    /**
     * 用来定时删除冗余日志文件
     */
    private var clearService: ScheduledExecutorService =
        Executors.newScheduledThreadPool(1) { r -> Thread(r, "logkit") }

    /**
     * LoginService开始初始化
     */
    fun start(iFile: IFile) {
        FileWriter.startThreadWithLog(iFile) // 开启线程
        /**
         * 启动2分钟后删一次，后面每隔30分钟进行次文件删除，压缩
         */
        clearService.scheduleAtFixedRate(
            { rollCacheLogs(logDir) }, //Runnable
            2, 30, TimeUnit.MINUTES
        )
    }

    /**
     * LoginService结束并清除资源
     */
    fun end() {
        FileWriter.endThread()
        clearService.shutdown()
    }

    /**
     * 修改缓存log
     * 删除逾期以及压缩当天以外的log
     * 删除无效文件(超过7天过期文件，不是zip或者text结尾的异常文件)
     * 一天之内的log不压缩
     * @param logDir log缓存目录
     * */
    private fun rollCacheLogs(logDir: String) {
        val logFile = File(logDir)
        if (!logFile.exists()) return
        val listNames = logFile.list() ?: arrayOf()
        val files = ArrayList<File>()
        for (fn in listNames) {
            val f = File(logFile, fn)
            if (!isInvalidFile(f)) { // 不合格的删掉
                f.delete()
                KLog.i(TAG, "delete more 7Day and invalid file!!!!")
            } else if (System.currentTimeMillis() - f.lastModified() > DAYS_COMPRESSED) { // 超过1太天的压缩
                files.add(compressLog(f)) // 合格的加进去
            } else {
                files.add(f)
            }
        }

        if (files.isEmpty()) return
        //log总大小溢出复查处理(删除时间最早的)
        // 超过总数据100M的删掉
        var size = 0L
        files.sortWith(TimeComparator()) // 按时间排序
        for (index in files) {
            if (size + index.length() > logCacheMaxSize) {
                index.delete()
                KLog.i(TAG, "delete more 100M zip")
            } else {
                size += index.length()
            }
        }
    }

    /**
     * 是否有效文件
     * 无效文件(超过7天过期文件，不是zip或者text结尾的异常文件, size<200b的zip文件)
     */
    private fun isInvalidFile(file: File): Boolean {
        return (System.currentTimeMillis() - file.lastModified()) > DAYS_DELETE ||
            !(file.name.endsWith(LogCompress.ZIP) || file.name.endsWith(LOG_EXT)) ||
            (file.name.endsWith(LogCompress.ZIP) && file.length() < 200)
    }

    /**
     * 压缩文件
     */
    @SuppressLint("SimpleDateFormat")
    private fun compressLog(file: File): File {
        // 符合要求
        if (file.name.endsWith(LOG_EXT) && !file.name.contains(sdf.format(Date()))) {
            var zipFile: File
            try {
                zipFile = LogCompress.compress(file)
                file.delete()
            } catch (e: Exception) {
                KLog.w(TAG, "LogCompress", e)
                zipFile = file
            }
            return zipFile
        } else {
            return file
        }
    }
}