package com.joyy.multilanguages

import android.app.Application
import android.content.Context
import com.joyy.languages.MultiLanguages

/**
 * Time:2021/9/16 15:14
 * Author: flannery
 * Description:
 */
class MyApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(MultiLanguages.attachContext(base))
    }

    override fun onCreate() {
        super.onCreate()
        // 多语言注册回调信息等
        MultiLanguages.inits(this)
    }

}