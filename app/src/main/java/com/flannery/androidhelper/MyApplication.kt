package com.flannery.androidhelper

import android.app.Application
import com.flannery.anrwatchdog.ANRActivity

class MyApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        ANRActivity.initANR()
    }

}