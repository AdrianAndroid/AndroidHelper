package com.flannery.app_test.handler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import com.flannery.app_test.R

class UseHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_use_handler)
    }

//    val handler = Handler();

    val myhandler = MyHandler();
    val handlerCallBack = object : Handler(object : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            if (msg.what == 5) {
                Log.i(
                    UseHandlerActivity::class.java.name,
                    "Handler(object : Handler.Callback-->${msg.what}   return true"
                )
                return true
            } else if (msg.what == 6) {
                Log.i(
                    UseHandlerActivity::class.java.name,
                    "Handler(object : Handler.Callback-->${msg.what}   return false"
                )
                return false
            }
            return true
        }

    }) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            printLog(msg)
        }
    }


    fun clickHandler(view: View) {
        when (view.id) {
            R.id.useNormalHandler -> {
                methodNormalClick()
            }
            R.id.interviewHandler -> {
                methodInterview();
            }
            R.id.threadHandler -> {
                threadHandler()
            }
            R.id.threadHandlerSend -> {
                Message.obtain(handlerT, 777).sendToTarget()
            }
        }
    }

    private fun methodInterview() {
        val handlerOne = NameHandler("handle one ")
        val handlerTwo = NameHandler("handle two ")
        val handlerThree = NameHandler("handle three ")

        Message.obtain(handlerOne, 1).sendToTarget()
        Message.obtain(handlerTwo, 2).sendToTarget()
        Message.obtain(handlerThree, 3).sendToTarget()
    }

    var handlerT: Handler? = null

    private fun threadHandler() {
        Thread(Runnable {
            Looper.prepare() // 创建一个新的Looper
            val handler = Handler { msg ->
                msg?.run {
                    Log.i(UseHandlerActivity::class.java.name, "$what , $arg1")
                }
                true
            } // 创建handler
            handlerT = handler
            Message.obtain(handler, 666).sendToTarget()
            Log.i(
                UseHandlerActivity::class.java.name,
                "主线程&子线程的looper => ${Looper.getMainLooper() == Looper.myLooper()}"
            )
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                Log.i(
//                    UseHandlerActivity::class.java.name,
//                    "主线程&子线程的queue => ${Looper.getMainLooper().queue == Looper.myLooper().queue}"
//                )
            }
            Looper.loop() // 将Looper运行起来, for循环，跳不出来
            // 每个Thread都有单独的Looper， 和单独的Queue
        }, "ThreadHandler").start()
    }

    class NameHandler(val name: String) : Handler() {
        override fun handleMessage(msg: Message?) {
//            super.handleMessage(msg)
            msg?.run {
                Log.i(UseHandlerActivity::class.java.name, "$name, $what , $arg1")
            }
        }
    }

    private fun methodNormalClick() {
        val obtain = Message.obtain()
        obtain.what = 1
        obtain.arg1 = 1
        myhandler.sendMessage(obtain);


        val obtain1 = Message.obtain(myhandler)
        obtain1.what = 2
        obtain1.arg1 = 2
        obtain1.sendToTarget()


        val obtain2 =
            Message.obtain(myhandler) { Log.i(UseHandlerActivity::class.java.name, "-->3") }
        obtain2.what = 3
        obtain2.arg1 = 3
        obtain2.sendToTarget()

        Message.obtain(myhandler, 4, 4, 4).sendToTarget()


        // Handler(CallBack)
        val obtain3 = Message.obtain(handlerCallBack)
        obtain3.what = 5
        obtain3.arg1 = 5
        obtain3.sendToTarget()


        val obtain4 = Message.obtain(handlerCallBack)
        obtain4.what = 6
        obtain4.arg1 = 6
        obtain4.sendToTarget()
    }


    fun printLog(msg: Message) {
//        L.m(javaClass, msg.what, msg.arg1)
        Log.i(UseHandlerActivity::class.java.name, "${msg.what} , ${msg.arg1}")
    }


    inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            printLog(msg)
        }
    }


    fun testAllNewHandler() {
        val h1 = Handler();
        val h2 = Handler();
        val h3 = Handler();
    }

    fun testSyncBarrier() {


        val obtain = Message.obtain()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            obtain.isAsynchronous = true // 发送异步消息
        }
    }
}


