package com.joyy.gradledownloadplugin

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import java.util.*


/**
 * Author: chenwenwei
 * Date: 2021/3/29
 * Desc: 多语言帮助类
 * SinceVer: v2.0.0
 */
object MultiLanguageHelper {


    // 切换语言
    private fun switchLanguage(context: Context, locale: Locale?) {
        val config: Configuration = context.getResources().getConfiguration() // 获得设置对象
        val resources: Resources = context.getResources() // 获得res资源对象
        val dm: DisplayMetrics = resources.getDisplayMetrics() // 获得屏幕参数：主要是分辨率，像素等。
        config.locale = locale //语言
        resources.updateConfiguration(config, dm)
    }

//    fun initConfig(country: String) {
//        MultiLanguageConfig.sDefaultAppCountry = country
//        try {
//            WebView(RuntimeContext.sApplicationContext).destroy()
//        } catch (e: Exception) {
//            KLog.e("Webview", e) { "gold hole" }
//        }
//    }

//    /**
//     * 国家选择列表，点击选择
//     */
//    fun updateCountry() {
//        val newLocale = getLocale(RegionConfig.sAppRegion ?: "")
//        changeAppLanguage(RuntimeContext.sActivityContext, newLocale, true)
//        changeAppLanguage(RuntimeContext.sApplicationContext, newLocale, false)
//        GlobalScope.launch {
//            delay(300L)
//            RuntimeContext.sActivityContext.apply {
//                val intent = QuesGoManager.mInfo.getMainIntent(this)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                startActivity(intent)
//                Process.killProcess(Process.myPid())
//            }
//        }
//    }

    /**
     * Application的Lifecycle监听
     * 如zoom的activity
     */
//    fun onActivityCreated(activity: Context) {
//        if (!isSameWithSetting(activity)) {
//            changeAppLanguage(activity, getLocale(localCountrySp), true)
//        }
//    }

//    /**
//     * Application attachBaseContext
//     */
//    fun attachBaseContext(context: Context?, instituteId: String? = null, country: String? = null): Context? {
//        if (context == null) {
//            return null
//        }
//        MultiLanguageConfig.init(context, instituteId, country)
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            createConfigurationResources(context)
//        } else {
//            setConfiguration(context)
//            context
//        }
//    }

    /**
     * 设置语言 7.0以下
     */
//    private fun setConfiguration(context: Context) {
//        val locale = getLocale(localCountrySp)
//        val configuration = context.resources.configuration
//        configuration.setLocale(locale)
//        val resources = context.resources
//        val dm = context.resources.displayMetrics
//        resources.updateConfiguration(configuration, dm)
//    }

    /**
     * 设置语言 7.0以上
     */
//    @TargetApi(Build.VERSION_CODES.N)
//    private fun createConfigurationResources(context: Context): Context {
//        val configuration = context.resources.configuration
//        val locale = getLocale(localCountrySp)
//        configuration.setLocale(locale)
//        configuration.setLocales(LocaleList(locale))
//        return context.createConfigurationContext(configuration)
//    }

//    /**
//     * 更改应用语言
//     *
//     * @param
//     * @param locale      语言地区
//     * @param persistence 是否持久化
//     */
//    fun changeAppLanguage(context: Context, locale: Locale, persistence: Boolean) {
//        val resources = context.resources
//        val metrics = resources.displayMetrics
//        val configuration = resources.configuration
//        when {
//            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
//                configuration.setLocale(locale)
//                configuration.setLocales(LocaleList(locale))
//                context.createConfigurationContext(configuration)
//            }
//            else -> {
//                configuration.setLocale(locale)
//            }
//        }
//        resources.updateConfiguration(configuration, metrics)
//        if (persistence) {
//            saveLanguageSetting(locale)
//        }
//    }
//
//    /**
//     * 保存语言设置到SP中
//     */
//    private fun saveLanguageSetting(locale: Locale) {
//        localCountrySp = locale.country
//        localLanguageSp = locale.language
//    }
//
//    /**
//     * 获取本地应用的实际的多语言信息
//     */
//    private fun getAppLocale(context: Context): Locale? {
//        val configuration = context.resources.configuration
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            configuration.locales[0]
//        } else {
//            configuration.locale
//        }
//    }
//
//    /**
//     * 判断sp中和app中的多语言信息是否相同
//     */
//    private fun isSameWithSetting(context: Context): Boolean {
//        val locale = getAppLocale(context)
//        return locale?.language == localLanguageSp && locale.country == localCountrySp
//    }

    fun switchEN(context: Context) = switchLanguage(context, getEN())

    fun switchKR(context: Context) = switchLanguage(context, getKR())

    private fun getEN() = getLocale("IN")

    private fun getKR() = getLocale("KR")

    private fun getLocale(country: String): Locale {
        return when (country) {
            "IN" -> Locale("en", "")
            "KR" -> Locale("ko", "KR")
            else -> Locale("en", "")
        }
    }
}