package com.flannery.androidhelper

import android.app.Application
import androidx.multidex.MultiDex
import com.flannery.anrwatchdog.ANRActivity

class MyApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        ANRActivity.initANR()
    }

}