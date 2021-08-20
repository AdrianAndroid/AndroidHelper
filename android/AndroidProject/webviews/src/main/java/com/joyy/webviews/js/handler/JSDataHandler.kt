package com.joyy.webviews.js.handler

import android.os.Build
import com.joyy.webviews.js.IJsCallBack
import com.joyy.webviews.js.returnToH5
import org.json.JSONObject

/**
 * JS的 module 分发类：Data
 *
 * @author chen wenwei
 * @Date 2019/01/30
 */
class JSDataHandler(private val callback: IJsCallBack) : IJsApi {

    override fun invoke(name: String?, params: String?, cbName: String?): String? {
        return when (name) {
            "hdid" -> hdid(params, cbName)
            "uid" -> uid(params, cbName)
            "webToken" -> webToken(params, cbName)
            "instituteId" -> instituteId(params, cbName)
            "userId" -> siteUserId(params, cbName)
            "deviceInfo" -> deviceInfo(params, cbName)
            else -> null
        }
    }

    private fun hdid(params: String?, cbName: String?): String {
        return callback.returnToH5(cbName, "QuesGoManager.mUserInfoService.getHdid()")
    }

    private fun uid(params: String?, cbName: String?): String {
        return callback.returnToH5(cbName, "QuesGoManager.mUserInfoService.getUid()")
    }

    private fun webToken(params: String?, cbName: String?): String {
        return callback.returnToH5(cbName, "QuesGoManager.mUserInfoService.userV2Token")
    }

    private fun instituteId(params: String?, cbName: String?): String {
        return callback.returnToH5(cbName, "QuesGoManager.mUserInfoService.getInstituteId()")
    }

    private fun siteUserId(params: String?, cbName: String?): String {
        return callback.returnToH5(cbName, "QuesGoManager.mUserInfoService.getSiteUserId()")
    }

    private fun deviceInfo(params: String?, cbName: String?): String {
        return callback.returnToH5(cbName, JSONObject().apply {
            //val ver = VersionUtil.getLocalVer(RuntimeContext.sApplicationContext)
            put("appVersion", "ver.versionNameWithoutSnapshot")
            put("appVersionNumber", "ver.versionNameWithoutSnapshot.replace()")
            put("systemVersion", "Android${Build.VERSION.RELEASE}")
            put("systemName", "android")
            put("deviceName", Build.MODEL)
            put("bundleId", "RuntimeContext.sPackageName")
            put("appName", "URLEncoder.encode(RuntimeContext.sAppName)")
        })
    }
}