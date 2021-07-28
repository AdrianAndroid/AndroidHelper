package com.joyy.loglibrary.writer

/**
 * Time:2021/7/27 16:28
 * Author:
 * Description:
 */
object FileLog {

    val MODE_ASYNC = 0
    val MODE_SYNC = 1

    external fun open(
        logDir: String?, mmapDir: String?, nameprefix: String?, logLevel: Int, mode: Int,
        publicKey: String?
    )

    external fun flush(sync: Boolean)

    external fun close()

    external fun level(logLevel: Int)

    external fun mode(mode: Int)

    external fun fileMaxSize(size: Int)

    external fun useConsoleLog(use: Boolean)

    external fun logWrite(
        level: Int, tag: String?, fileName: String?, funcName: String?, line: Int, pid: Int,
        tid: Long, maintid: Long, ms: String?
    )

}