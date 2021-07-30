package com.joyy.log_core.comm

import android.os.Process
import android.util.Log
import java.util.Queue
import java.util.concurrent.LinkedBlockingQueue

/**
 * Time:2021/7/29 14:43
 * Author: flannery
 * Description: handleMessage 每次从这里回调
 */
class WorkLogThread(private val handleMessage: (msg: Message) -> Unit) : Thread("[WorkLogThread]") {

    var isRunning = true

    init {
        priority = Thread.NORM_PRIORITY
    }

    //    private var mMessages: Message? = null // 模仿MessageQueue的单链表
    private var mQueue: LinkedBlockingQueue<Message> = LinkedBlockingQueue<Message>(100)

    // 将message加入到链表中
    fun postMessage(msg: Message) {
        Log.e("[WorkLogThread]", "postMessage")
        mQueue.put(msg)
    }

    override fun run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_FOREGROUND)
        while (isRunning) { // 这个就不考虑多线程了，停止的情况很少
            Log.e("[WorkLogThread]", "run-poll-handleMessage")
            mQueue.take()?.let(handleMessage)
        }
    }

    fun stopThread() {
        isRunning = false
    }
}