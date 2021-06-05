package com.flannery.leakcanary22

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        synchronized(this){
//            Thread.sleep(10000)
//        }
    }

    fun test2() {
        //IBookManager.Stub
        val m = Messenger(Handler(Looper.getMainLooper()));
        //m.binder

    }

    fun test() {
//        EventBus.builder().build().post("");
//        EventBus.getDefault().post("")
        EventBus.getDefault().register(this)
        EventBus.getDefault().unregister(this)
        EventBus.getDefault().postSticky("")

    }

    // https://www.jianshu.com/p/239a866cf85d
    // startService
    // onCreate - onStartCommand - onDestroy
    // bindService
    // onCreate - onBind - onUnbind - onDestroy

    fun _startService() {
        startService(Intent(this, MService::class.java))
//        I/System.out: MService()
//        I/System.out: attachBaseContext
//        I/System.out: onCreate
//        I/System.out: onStartCommand
//        I/System.out: onStart

    }

    fun _bindService() {
        bindService(Intent(this, MService::class.java), object :ServiceConnection{
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                println("onServiceConnected")
                var asInterface = IBookManager.Stub.asInterface(service)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                println("onServiceDisconnected")
            }
        }, 0)
    }

    fun clickTest(view: View) {
        //testObserver()
        //test()
        //testObserver()
        _bindService()
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    private fun testObserver() {
        Observable
            .just("lfkdsk", "just-we")
            .map {
                println("map1 ${Thread.currentThread().name}")
            }
            .map {
                println("map2 ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                println("map3 ${Thread.currentThread().name}")
            }
            .map {
                println("map4 ${Thread.currentThread().name}")
            }
            .subscribe()
    }
}