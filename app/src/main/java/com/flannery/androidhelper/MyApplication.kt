package com.flannery.androidhelper

import android.app.Application
import androidx.multidex.MultiDex
import com.flannery.anrwatchdog.ANRActivity
import me.yokeyword.fragmentation.Fragmentation
import me.yokeyword.fragmentation.helper.ExceptionHandler

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        ANRActivity.initANR()

        Fragmentation.builder()
            .stackViewMode(Fragmentation.BUBBLE)
            .handleException {
                it.printStackTrace()
            }
            .debug(true)
            .install()
    }

}