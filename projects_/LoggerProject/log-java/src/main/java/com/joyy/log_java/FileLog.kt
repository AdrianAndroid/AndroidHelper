package com.joyy.log_java

import android.util.Log
import com.joyy.log_core.LogManager
import com.joyy.log_core.api.IFile
import com.joyy.log_core.comm.Message
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.lang.IllegalStateException
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Time:2021/7/29 17:51
 * Author: flannery
 * Description:
 */
class FileLog : IFile {

    val MODE_ASYNC = 0
    val MODE_SYNC = 1


    var writer: BufferedWriter? = null
    var isActive = AtomicBoolean(false)

    override fun open(msg: Message) {
//        if (isActive) {
//            close()
//        }
//        val logFile = LogManager.getLogFile()
//        writer = BufferedWriter(OutputStreamWriter(FileOutputStream(logFile, true)))
//        isActive = true
    }

    override fun flush(sync: Boolean) {
        if (isActive.get()) writer?.flush()
    }

    override fun close() {
    }

    override fun level(logLevel: Int) {
    }

    override fun mode(mode: Int) {
    }

    override fun fileMaxSize(size: Int) {
    }

    override fun useConsoleLog(use: Boolean) {
    }

    override fun logWrite(msg: Message) {
        Log.e("[FileLog]", "logWrite $msg")
        val logFile = LogManager.getLogFile()
        if (!LogManager.isCurrentLogFile(logFile)) { // 不是当前的log
            close2() // 先close掉
        }
        if (!isActive.get()) { //不在激活状态
            open2(logFile)
        }
        if (isActive.get()) {
            writer?.write("${msg.levelName()}  ${msg.tag}  pid = ${msg.pid}  tid =${msg.tid}  mid = ${msg.mid}")
            writer?.write(msg.msg)
        }
    }

    private fun open2(logFile: File) {
        LogManager.currentLogFile = logFile.absolutePath
        writer = BufferedWriter(OutputStreamWriter(FileOutputStream(logFile, true)))
        isActive.set(true)
    }

    private fun close2() {
        if (isActive.get()) writer?.close()
    }
}