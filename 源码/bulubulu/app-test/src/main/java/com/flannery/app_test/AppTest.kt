package com.flannery.app_test

import android.content.Context
import androidx.multidex.MultiDex
import cn.kuwo.common.app.App

class AppTest : App() {


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}