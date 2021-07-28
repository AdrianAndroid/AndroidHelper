package com.joyy.loglibrary.utils

/**
 * Created by 张世竹 on 2019/1/3
 */
class BundleMessage {
    var what: Int = NONE
    var level: Int = 0
    var tag: String = ""
    var fileName: String = ""
    var funcName: String = ""
    var msg: String = ""
    var line: Int = 0
    var pid: Int = 0
    var tid: Long = 0
    var mid: Long = 0
    var use: Boolean = false
    var size: Int = 0
    var namePrefix: String = ""
    var logDir: String = ""
    var publicKey: String = ""
    var mmapDir: String = ""
    var flushCallback: ((Boolean) -> Unit)? = null
    var format: String = ""
    var args: Array<out Any?> = arrayOf("")
    private val FLAG_IN_USE = 1 shl 0

    private val MAX_POOL_SIZE = 50
    var next: BundleMessage? = null
//    private var flags: Int = 0

    companion object {
        //        private val sPoolSync = Any()
        private var sPool: BundleMessage? = null
        private var sPoolSize = 0

        // 消息复用， 模仿Handle
        fun obtain(): BundleMessage {
            synchronized(BundleMessage::class.java) {
                if (sPool != null) {
                    val m = sPool
                    sPool = m!!.next
                    m.next = null
//                    m.flags = 0
                    sPoolSize--
                    return m
                }
            }
            return BundleMessage()
        }

        const val NONE = 0
        const val OPEN = 1
        const val FLUSH = 2
        const val CLOSE = 3
        const val SET_LOG_LEVEL = 4
        const val SET_FILE_MAX_SIZE = 5
        const val USE_CONSOLE_LOG = 6
        const val WRITE = 7
    }

    fun recycleUnchecked() {

        level = 0
        tag = ""
        fileName = ""
        funcName = ""
        msg = ""
        line = 0
        pid = 0
        tid = 0
        mid = 0
        use = false
        size = 0
        namePrefix = ""
        logDir = ""
        publicKey = ""
        mmapDir = ""
        flushCallback = null
        format = ""
        args = arrayOf("")

        synchronized(BundleMessage::class.java) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool
                sPool = this
                sPoolSize++
            }
        }
    }
}