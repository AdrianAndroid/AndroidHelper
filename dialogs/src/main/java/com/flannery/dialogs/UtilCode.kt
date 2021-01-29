package com.flannery.dialogs

import android.app.Activity
import android.app.Application
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils

object UtilCode {

    fun initApplication(context: Application) {
        Utils.init(context) // 初始化工具类
    }

    fun getTopActivity(): Activity? {
        return ActivityUtils.getTopActivity()
    }


    fun showd() {
    }

}