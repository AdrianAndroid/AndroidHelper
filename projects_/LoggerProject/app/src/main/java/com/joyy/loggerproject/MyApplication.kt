package com.joyy.loggerproject

import android.app.Application
import com.joyy.log_core.KLog
import com.joyy.log_java.FileLog
import java.io.File

/**
 * Time:2021/7/29 12:27
 * Author: flannery
 * Description:
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //KLog.initLog(LogOption(), FileLog())
        KLog.initLog(
            File(externalCacheDir, "logs"),
            FileLog()
        )
    }

    override fun onTerminate() {
        super.onTerminate()
        KLog.end()
    }
}