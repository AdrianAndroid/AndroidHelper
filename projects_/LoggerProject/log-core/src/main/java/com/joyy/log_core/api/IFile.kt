package com.joyy.log_core.api

import com.joyy.log_core.comm.Message

/**
 * Time:2021/7/29 12:01
 * Author: flannery
 * Description:
 */
interface IFile {

    //    val MODE_ASYNC = 0
    //    val MODE_SYNC = 1

    fun open(msg: Message)

    fun flush(sync: Boolean)

    fun close()

    fun level(logLevel: Int)

    fun mode(mode: Int)

    fun fileMaxSize(size: Int)

    fun useConsoleLog(use: Boolean)

    fun logWrite(msg: Message)
}