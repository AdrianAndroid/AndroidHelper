package com.elbbbird.android.analytics

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import cn.kuwo.common.BuildConfig
import cn.kuwo.common.utilscode.UtilsCode
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import java.lang.NullPointerException

/*https://developer.umeng.com/docs/66632/detail/66946?um_channel=sdk*/
object AnalyticsUtils {

    //1400461918
    fun initUMeng(context: Context, channel: String) {
        try {
            UMConfigure.init(
                context,
                "5ea7a3270cafb2f27d000034",
                channel,
                UMConfigure.DEVICE_TYPE_PHONE,
                ""
            )

            UMConfigure.setLogEnabled(BuildConfig.DEBUG)
            setCatchUncaughtExceptions(false)
            openActivityDurationTrack(false) //不再自动统计activity的页面时长

            //MobclickAgent.se
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onPageStart(str: String) {
        MobclickAgent.onPageStart(str)
    }

    fun onPageEnd(str: String) {
        MobclickAgent.onPageEnd(str)
    }

    fun onEvent(context: Context?, key: String, value: String) {
        if (TextUtils.isEmpty(key)) {
            if (BuildConfig.DEBUG) {
                throw NullPointerException(key)
            }
        } else {
            MobclickAgent.onEvent(context, key /*UmengEventId.FRIEND_CHAT*/, value)
        }
    }

    fun onEvent(context: Activity?, key: String, value: String) {
        if (TextUtils.isEmpty(key)) {
            if (BuildConfig.DEBUG) {
                throw NullPointerException(key)
            } else {
                return
            }
        } else {
            MobclickAgent.onEvent(context, key /*UmengEventId.FRIEND_CHAT*/, value)
        }
    }

    // 使用账号密码登陆
    fun loginIn(userId: String?, authType: Int?) {
        // // 1 短信验证码登陆 3 第三方登陆 6token登陆 6 小程序登陆 7 苹果登陆
        if (TextUtils.isEmpty(userId)) {
            if (BuildConfig.DEBUG) {
                throw NullPointerException("$userId <-> $authType");
            } else {
                return
            }
        }
        if (authType == 1) {
            MobclickAgent.onProfileSignIn(UtilsCode.null2Length0(userId), "DX")
        } else if (authType == 3) {
            MobclickAgent.onProfileSignIn(UtilsCode.null2Length0(userId), "THIRD")
        } else if (authType == 6) {
            MobclickAgent.onProfileSignIn(UtilsCode.null2Length0(userId), "TOKEN")
        } else if (authType == 6) {
            MobclickAgent.onProfileSignIn(UtilsCode.null2Length0(userId), "XCX")
        } else if (authType == 7) {
            MobclickAgent.onProfileSignIn(UtilsCode.null2Length0(userId), "APPLE")
        } else {
            MobclickAgent.onProfileSignIn(UtilsCode.null2Length0(userId), "OTHER")
        }
    }

//    // 从微信登陆
//    fun loginInWeixin(userId: String) {
//        MobclickAgent.onProfileSignIn("WX", userId)
//    }
//
//    // 从QQ登陆过来
//    fun loginInQQ(userId: String) {
//        MobclickAgent.onProfileSignIn("QQ", userId)
//    }

    fun loginOff() {
        MobclickAgent.onProfileSignOff() // 登陆退出统计
    }


    fun onResume(context: Context?) {
        MobclickAgent.onResume(context)
    }

    fun onPause(context: Context?) {
        MobclickAgent.onPause(context)
    }

    private fun setCatchUncaughtExceptions(b: Boolean) {
        MobclickAgent.setCatchUncaughtExceptions(b)
    }

    private fun openActivityDurationTrack(b: Boolean)//不再自动统计activity的页面时长
    {
        //MobclickAgent.openActivityDurationTrack(b)
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO) // 自动统计页面
    }


}