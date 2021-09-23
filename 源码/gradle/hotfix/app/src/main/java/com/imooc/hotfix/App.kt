package com.imooc.hotfix

import android.app.Application
import android.content.Context

class App: Application(){

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        HotFixUtil.install(base!!)
    }
}