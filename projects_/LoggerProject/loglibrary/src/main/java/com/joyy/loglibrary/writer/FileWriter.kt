package com.joyy.loglibrary.writer

import android.os.Looper
import android.os.Process
import android.util.Log
import com.joyy.loglibrary.api.LogLevel
import com.joyy.loglibrary.utils.BundleMessage
import java.util.Queue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * Time:2021/7/27 16:39
 * Author: flannery
 * Description:
 */
object FileWriter {
    private var mInited = AtomicBoolean(false)

    private var mProcessId: Int = 0
    private var mMainTid: Long = 0
    private var mQueue: Queue<BundleMessage> = LinkedBlockingQueue<BundleMessage>() // 消息队列
    private var wThread: WriteLogThread? = null // 工作线程

    val lock: Lock = ReentrantLock() // 用于生产者消费者
    val condition: Condition = lock.newCondition()

    init {
        if (!mInited.get()) {
            try {
                wThread = WriteLogThread(mQueue).apply {
                    start() //开启线程
                    priority = Thread.MAX_PRIORITY
                    name = "[WriteLogThread]"
                }
                System.loadLibrary("yylog")
                mInited.set(true)
            } catch (e: Exception) {
                Log.w("[FileWriter]", Log.getStackTraceString(e))
                mInited.set(false)
            }
        }
    }

    /**
     * mmapDir一般定义在/data/data/xxx/files/log/里面
     */
    fun open(
        logDir: String, mmapDir: String, namePrefix: String, logLevel: Int, publicKey: String
    ) {
        if (!mInited.get()) {
            return
        }
        mProcessId = Process.myPid()
        mMainTid = Looper.getMainLooper().thread.id

        postMessage(BundleMessage.obtain().apply {
            what = BundleMessage.OPEN
            this.logDir = logDir
            this.mmapDir = mmapDir
            this.namePrefix = namePrefix
            this.level = logLevel
            this.publicKey = publicKey
        })
    }

    fun flush(flushCallback: ((Boolean) -> Unit)) {
        if (!mInited.get()) {
            return
        }
        postMessage(BundleMessage.obtain().apply {
            what = BundleMessage.FLUSH
            this.flushCallback = flushCallback
        })
    }

    fun flush() {
        if (!mInited.get()) {
            return
        }
        postMessage(BundleMessage.obtain().apply {
            what = BundleMessage.FLUSH
        })
    }

    fun close() {
        if (!mInited.get()) {
            return
        }

        postMessage(BundleMessage.obtain().apply {
            what = BundleMessage.CLOSE
        })
    }

    fun setLogLevel(logLevel: Int) {
        if (!mInited.get()) {
            return
        }

        postMessage(BundleMessage.obtain().apply {
            what = BundleMessage.SET_LOG_LEVEL
            this.level = logLevel
        })
    }

    fun setFileMaxSize(size: Int) {
        if (!mInited.get()) {
            return
        }

        postMessage(BundleMessage.obtain().apply {
            what = BundleMessage.SET_FILE_MAX_SIZE
            this.size = size
        }
        )
    }

    fun useConsoleLog(use: Boolean) {
        if (!mInited.get()) {
            return
        }
        postMessage(BundleMessage.obtain().apply {
            this.use = use
            this.what = BundleMessage.USE_CONSOLE_LOG
        })
    }

    fun v(
        tag: String,
        fileName: String,
        funcName: String,
        line: Int,
        tid: Long,
        msg: String
    ) {
        if (!mInited.get()) {
            return
        }
        logWrite(
            LogLevel.VERBOSE, tag, fileName,
            funcName, line, mProcessId, tid, mMainTid, msg
        )
    }

    fun v(
        tag: String,
        fileName: String,
        funcName: String,
        line: Int,
        tid: Long,
        format: String,
        vararg args: Any?
    ) {
        if (!mInited.get()) {
            return
        }
        logWrite(
            LogLevel.VERBOSE, tag, fileName,
            funcName, line, mProcessId, tid, mMainTid, format, *args
        )
    }

    fun i(
        tag: String,
        fileName: String,
        funcName: String,
        line: Int,
        tid: Long,
        msg: String
    ) {
        if (!mInited.get()) {
            return
        }
        logWrite(
            LogLevel.VERBOSE, tag, fileName,
            funcName, line, mProcessId, tid, mMainTid, msg
        )
    }

    fun i(
        tag: String,
        fileName: String,
        funcName: String,
        line: Int,
        tid: Long,
        format: String,
        vararg args: Any?
    ) {
        if (!mInited.get()) {
            return
        }
        logWrite(
            LogLevel.INFO, tag, fileName,
            funcName, line, mProcessId, tid, mMainTid, format, *args
        )
    }

    fun d(
        tag: String,
        fileName: String,
        funcName: String,
        line: Int,
        tid: Long,
        msg: String
    ) {
        if (!mInited.get()) {
            return
        }
        logWrite(
            LogLevel.DEBUG, tag, fileName,
            funcName, line, mProcessId, tid, mMainTid, msg
        )
    }

    fun d(
        tag: String,
        fileName: String,
        funcName: String,
        line: Int,
        tid: Long,
        format: String,
        vararg args: Any?
    ) {
        if (!mInited.get()) {
            return
        }
        logWrite(
            LogLevel.DEBUG, tag, fileName,
            funcName, line, mProcessId, tid, mMainTid, format, *args
        )
    }

    fun w(
        tag: String,
        fileName: String,
        funcName: String,
        line: Int,
        tid: Long,
        msg: String
    ) {
        if (!mInited.get()) {
            return
        }
        logWrite(
            LogLevel.WARN, tag, fileName, funcName,
            line, mProcessId, tid, mMainTid, msg
        )
    }

    fun w(
        tag: String,
        fileName: String,
        funcName: String,
        line: Int,
        tid: Long,
        format: String,
        vararg args: Any?
    ) {
        if (!mInited.get()) {
            return
        }
        logWrite(
            LogLevel.WARN, tag, fileName, funcName,
            line, mProcessId, tid, mMainTid, format, *args
        )
    }

    fun e(
        tag: String,
        fileName: String,
        funcName: String,
        line: Int,
        tid: Long,
        msg: String
    ) {
        if (!mInited.get()) {
            return
        }
        logWrite(
            LogLevel.ERROR, tag, fileName,
            funcName, line, mProcessId, tid, mMainTid, msg
        )
    }

    fun e(
        tag: String,
        fileName: String,
        funcName: String,
        line: Int,
        tid: Long,
        format: String,
        vararg args: Any?
    ) {
        if (!mInited.get()) {
            return
        }
        logWrite(
            LogLevel.ERROR, tag, fileName,
            funcName, line, mProcessId, tid, mMainTid, format, *args
        )
    }

    private fun logWrite(
        level: Int,
        tag: String,
        fileName: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        msg: String
    ) {
        postMessage(BundleMessage.obtain().apply {
            what = BundleMessage.WRITE
            this.level = level
            this.tag = tag
            this.fileName = fileName
            this.funcName = funcName
            this.msg = msg
            this.line = line
            this.pid = pid
            this.tid = tid
            this.mid = maintid
        })
    }

    private fun logWrite(
        level: Int,
        tag: String,
        fileName: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        format: String,
        vararg args: Any?
    ) {
        postMessage(BundleMessage.obtain().apply {
            what = BundleMessage.WRITE
            this.level = level
            this.tag = tag
            this.fileName = fileName
            this.funcName = funcName
            this.format = format
            this.args = args
            this.line = line
            this.pid = pid
            this.tid = tid
            this.mid = maintid
        })
    }

    private fun postMessage(msg: BundleMessage) {
        mQueue.add(msg)
        try {
            if (lock.tryLock()) {
                try {
                    condition.signal()
                } finally {
                    lock.unlock()
                }
            }
        } catch (e: Exception) {
            Log.w("postMessage", e.message ?: "")
        }
    }

    class WriteLogThread(private val mQueue: Queue<BundleMessage>) : Thread() {

        override fun run() {
            //super.run()
            Process.setThreadPriority(Process.THREAD_PRIORITY_FOREGROUND) // 设置线程优先级

            while (true) {
                try {
                    if (mQueue.isEmpty()) {
                        if (lock.tryLock()) {
                            try {
                                condition.await()
                            } finally {
                                lock.unlock()
                            }
                        }
                    } else {
                        handleMessage(mQueue.poll())
                    }
                } catch (e: Exception) {
                    Log.w("[WriteLogThread]", Log.getStackTraceString(e));
                }
            }
        }

        private fun handleMessage(message: BundleMessage?) {
            when (message?.what) {
                BundleMessage.WRITE -> {
                    if (message.format.isBlank())
                        FileLog.logWrite(
                            message.level,
                            message.tag,
                            message.fileName,
                            message.funcName,
                            message.line,
                            message.pid,
                            message.tid,
                            message.mid,
                            message.msg
                        )
                    else
                        FileLog.logWrite(
                            message.level,
                            message.tag,
                            message.fileName,
                            message.funcName,
                            message.line,
                            message.pid,
                            message.tid,
                            message.mid,
                            String.format(message.format, message.args)
                        )
                }
                BundleMessage.OPEN -> FileLog.open(
                    message.logDir,
                    message.mmapDir,
                    message.namePrefix,
                    message.level,
                    FileLog.MODE_ASYNC,
                    message.publicKey
                )
                BundleMessage.FLUSH -> {
                    FileLog.flush(true)
                    message.flushCallback?.invoke(true)
                }
                BundleMessage.CLOSE -> FileLog.close()
                BundleMessage.SET_LOG_LEVEL -> FileLog.level(message.level)
                BundleMessage.SET_FILE_MAX_SIZE -> FileLog.fileMaxSize(message.size)
                BundleMessage.USE_CONSOLE_LOG -> FileLog.useConsoleLog(message.use)
            }
            message?.recycleUnchecked() //回收
        }
    }
}