package com.joyy.log_core.comm

import android.os.Looper
import android.os.Process
import android.util.Log
import com.joyy.log_core.api.IFile
import com.joyy.log_core.api.ILog

/**
 * Time:2021/7/29 12:29
 * Author: flannery
 * Description:
 */
object FileWriter : ILog {

    val pid: Int = Process.myPid()
    val mid: Long = Looper.getMainLooper().thread.id

    var iFile: IFile? = null

    private val vThread: WorkLogThread = WorkLogThread { msg ->
        Log.e("[FileWriter]", "$msg")
        // 写入本地
        try {
            msg.use = true
            when (msg.what) {
                Message.WRITE -> {
                    //iFile?.logWrite(msg.level, msg.tag, msg.pid, msg.tid, msg.mid)
                    iFile?.logWrite(msg)
                }
                Message.OPEN -> {
                    iFile?.open(msg)
                }
                Message.FLUSH -> {
                    iFile?.flush(true)
                    msg.callback?.invoke(true)
                }
                Message.CLOSE -> iFile?.close()
                Message.SET_LOG_LEVEL -> iFile?.level(msg.level)
                Message.SET_FILE_MAX_SIZE -> iFile?.fileMaxSize(0/*msg.size*/)
                Message.USE_CONSOLE_LOG -> iFile?.useConsoleLog(msg.use)
            }
            msg.use = false
            msg.recycle()
        } catch (e: Exception) {
            Log.i("[FileWriter]", e.message ?: "")
        }
    }

    fun startThreadWithLog(iFile: IFile) {
        FileWriter.iFile = iFile
        if (!vThread.isAlive) {
            vThread.start()
        }
        vThread.postMessage(
            Message.obtainOpen()
        )
    }

    fun endThread() {
        if (vThread.isAlive) {
            vThread.stopThread()
        }
        FileWriter.iFile?.close()
    }

    private fun extraMessageParams(msg: Message) {
        msg.pid = pid
        msg.tid = Thread.currentThread().id
        msg.mid = mid
    }

    override fun v(tag: String?, msg: String?, tr: Throwable?) {
        val message = Message.obtainWrite()
        message.level = Message.VERBOSE
        message.msg = "$msg \n ${Log.getStackTraceString(tr)}"
        extraMessageParams(message)
        vThread.postMessage(message)
    }

    override fun d(tag: String?, msg: String?, tr: Throwable?) {
        val message = Message.obtainWrite()
        message.level = Message.DEBUG
        message.msg = "$msg \n ${Log.getStackTraceString(tr)}"
        extraMessageParams(message)
        vThread.postMessage(message)
    }

    override fun i(tag: String?, msg: String?, tr: Throwable?) {
        val message = Message.obtainWrite()
        message.level = Message.INFO
        message.msg = "$msg \n ${Log.getStackTraceString(tr)}"
        extraMessageParams(message)
        vThread.postMessage(message)
    }

    override fun w(tag: String?, msg: String?, tr: Throwable?) {
        val message = Message.obtainWrite()
        message.level = Message.WARN
        message.msg = "$msg \n ${Log.getStackTraceString(tr)}"
        extraMessageParams(message)
        vThread.postMessage(message)
    }

    override fun e(tag: String?, msg: String?, tr: Throwable?) {
        val message = Message.obtainWrite()
        message.level = Message.ERROR
        message.msg = "$msg \n ${Log.getStackTraceString(tr)}"
        extraMessageParams(message)
        vThread.postMessage(message)
    }
}