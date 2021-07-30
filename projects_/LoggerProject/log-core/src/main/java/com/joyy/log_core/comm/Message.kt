package com.joyy.log_core.comm

/**
 * Time:2021/7/29 13:56
 * Author: flannery
 * Description: 只在这个模块可以使用
 */
data class Message(
    var what: Int = NONE, // 什么类型
    var level: Int = 0, // log级别
    var tag: String = "[Message]", // TAG
    var msg: String = "", //message 内容

    var pid: Int = 0,
    var tid: Long = 0,
    var mid: Long = 0,
    var callback: ((Boolean) -> Unit)? = null, // 是否成功

    internal var use: Boolean = false, //是否正在使用
    internal var next: Message? = null// 不允许外部修改
) {

    companion object {
        val obj: Any = Any()

        // 日志等级
        const val VERBOSE = 2
        const val DEBUG = 3
        const val INFO = 4
        const val WARN = 5
        const val ERROR = 6

        const val NONE = 0
        const val OPEN = 1
        const val FLUSH = 2
        const val CLOSE = 3
        const val SET_LOG_LEVEL = 4
        const val SET_FILE_MAX_SIZE = 5
        const val USE_CONSOLE_LOG = 6
        const val WRITE = 7

        private val MAX_POOL_SIZE = 50
        private var sPool: Message? = null
        private var sPoolSize: Int = 0

        fun obtain(): Message {
            synchronized(obj) {
                if (sPool != null) {
                    val m = sPool
                    sPool = m!!.next
                    m.next = null
                    sPoolSize--
                    return m
                }
            }
            return Message() // 创建一个新的
        }

        fun obtainOpen(): Message = obtain().apply {
            what = OPEN
        }

        fun obtainClose(): Message = obtain().apply {
            what = CLOSE
        }

        fun obtainWrite(): Message = obtain().apply {
            what = WRITE
        }
    }

    fun levelName(): String = when (level) {
        VERBOSE -> "[VERBOSE]"
        DEBUG -> "[DEBUG]"
        INFO -> "[INFO]"
        WARN -> "[WARN]"
        ERROR -> "[ERROR]"
        else -> "[]"
    }

    fun recycle() {
        if (use) {
            throw IllegalStateException("This message cannot be recycled because it is still in use")
        }
        recycleUnchecked()
    }

    private fun recycleUnchecked() {
        what = NONE // 什么类型
        level = 0 // log级别
        tag = "[Message]" // TAG
        msg = "" //message 内容

        pid = 0
        tid = 0
        mid = 0
        callback = null // 是否成功

        use = false //是否正在使用
        next = null// 不允许外部修改

        synchronized(obj) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool
                sPool = this
                sPoolSize++
            }
        }
    }
}