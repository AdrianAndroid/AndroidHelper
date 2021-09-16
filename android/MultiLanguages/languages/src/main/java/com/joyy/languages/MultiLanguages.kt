package com.joyy.languages

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.LocaleList
import android.text.TextUtils
import java.util.*

/**
 * Time:2021/9/16 14:09
 * Author: flannery
 * Description:
 */
object MultiLanguages {

    private const val KEY_LANGUAGE = "key_language"
    private const val KEY_COUNTRY = "key_country"
    private const val PREFER = "language_setting"

    private lateinit var sApplication: Application // 不能为空的
    private lateinit var sSystemLanguage: Locale // 保留了系统语言信息，也不会为空

    /**
     * 不为空，就是设置了语言
     * 为空，就是使用默认语言
     */
    private var sCurrentLanguage: Locale? = null

    var onSystemLocaleChange: ((oldLocale: Locale, newLocale: Locale) -> Unit)? = null
    var onAppLocaleChange: ((oldLocale: Locale, newLocale: Locale) -> Unit)? = null

    // 从本地获取Local
    var onStorageCallback: Storage = object : Storage {

        // 保存到本地
        override fun save(ctx: Context, local: Locale) {
            ctx.getSharedPreferences(PREFER, Context.MODE_PRIVATE).edit()
                .putString(KEY_LANGUAGE, local.language)
                .putString(KEY_COUNTRY, local.country)
                .apply()
        }

        // 从本地取出
        override fun get(ctx: Context): Locale {
            val s = ctx.getSharedPreferences(PREFER, Context.MODE_PRIVATE)
            val language = s.getString(KEY_LANGUAGE, "") ?: ""
            val country = s.getString(KEY_COUNTRY, "") ?: ""
            if (TextUtils.isEmpty(language)) {
                return getLocale(ctx)
            }
            return Locale(language, country)
        }

        // 从本地清除
        override fun clear(ctx: Context) {
            ctx.getSharedPreferences(PREFER, Context.MODE_PRIVATE).edit()
                .remove(KEY_LANGUAGE)
                .remove(KEY_COUNTRY)
                .apply()
        }

    }

    private val mComponentCallbacks: ComponentCallbacks = object : ComponentCallbacks {

        /**
         * 手机的配置发生了变化
         */
        override fun onConfigurationChanged(newConfig: Configuration) {
            val newLocale = getLocale(newConfig)
            val oldLocal = sSystemLanguage
            // 更新Application的配置， 否则会出现横竖屏切换之后Application的orientation没有随之变化的问题
            updateConfigurationChanged(sApplication, newConfig)

            if (isEquals(oldLocal, newLocale)) { // 说明没有更换语言
                return
            }

            sSystemLanguage = newLocale //记录新的语言

            // 如果当前的语种是跟随系统变化的，那么就需要重置一下当前App的语种
            if (isSystemLanguage()) clearLanguage(sApplication)

            // 回调信息
            onSystemLocaleChange?.invoke(oldLocal, newLocale)
        }

        override fun onLowMemory() {
        }
    }

    private val mActivityLifecycleCallbacks: Application.ActivityLifecycleCallbacks =
        object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                updateAppLanguage(activity)
                updateAppLanguage(activity.application)
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                updateAppLanguage(activity)
                updateAppLanguage(activity.application)
            }

            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityDestroyed(p0: Activity) {
            }

        }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ///////对外使用//////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    fun inits(application: Application) {
        sApplication = application
        sSystemLanguage = getLocale(application)
        application.registerComponentCallbacks(mComponentCallbacks)
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
    }

    /**
     * 在上下文的子类中重写attachBaseContext方法（用于更新context的语种）
     */
    fun attachContext(context: Context): Context {
        if (isEquals(getLocale(context), getAppLanguage(context))) {
            return context
        }
        return attachLanguages(context, getAppLanguage(context))
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////工具类//////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    private fun getLocale(context: Context): Locale = getLocale(context.resources.configuration)

    private fun getLocale(config: Configuration): Locale {
        return if (VERSION.SDK_INT >= VERSION_CODES.N) config.locales[0] else config.locale
    }

    /**
     * 设置语种对象
     */
    private fun setLocale(config: Configuration, locale: Locale) {
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            config.setLocales(LocaleList(locale))
        } else {
            config.setLocale(locale)
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                val localeList = LocaleList(locale)
//                config.setLocales(localeList)
//            } else {
//                config.setLocale(locale)
//            }
//        } else {
//            config.locale = locale
//        }
    }

    /**
     * 设置默认的语种环境(日期格式化会用到)
     */
    private fun setDefaultLocale(context: Context) {
        val configuration = context.resources.configuration
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            LocaleList.setDefault(configuration.locales)
        } else {
            Locale.setDefault(configuration.locale)
        }
    }

    /**
     * 绑定当前的语种
     */
    private fun attachLanguages(context: Context, locale: Locale): Context {
        var ctx = context
        val resources = ctx.resources
        val config = Configuration(resources.configuration) // 新创建一个
        setLocale(config, locale)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        ctx = ctx.createConfigurationContext(config)
//        }
        resources.updateConfiguration(config, resources.displayMetrics)
        return ctx
    }

    fun updateAppLanguage(context: Context) {
        updateAppLanguage(context.resources)
    }

    private fun updateAppLanguage(resources: Resources) {
        val appLanguage = getAppLanguage(sApplication)
        if (getLocale(resources.configuration) != appLanguage) {
            updateLanguages(resources, appLanguage)
        }
    }

    /**
     * 更新Resources语种
     */
    private fun updateLanguages(resources: Resources, locale: Locale) {
        val config = resources.configuration
        setLocale(config, locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    /**
     * 更新手机配置信息变化
     */
    private fun updateConfigurationChanged(context: Context, newConfig: Configuration) {
        val config = Configuration()
        // 绑定当前语种到这个新的配置对象中
        setLocale(config, getAppLanguage(context))
        // 更新上下文的配置信息
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    /**
     * 获取某个语种下的Resources对象
     */
    private fun getLanguageResources(context: Context, locale: Locale): Resources {
        val config = Configuration()
        setLocale(config, locale)
        return if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            context.createConfigurationContext(config).resources
        } else {
            Resources(context.assets, context.resources.displayMetrics, config)
        }
    }

    fun setSystemLanguage(context: Context): Boolean {
        clearLanguage(context) // 清空本地保存的
        if (!isEquals(getLocale(context), sSystemLanguage)) {
            updateLanguages(context.resources, sSystemLanguage)
            setDefaultLocale(context)
            sCurrentLanguage = null // 约定为空， 是系统语言
            if (context != sApplication) updateLanguages(sApplication.resources, sSystemLanguage)
            return true
        } else {
            return false
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////存储操作////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    // 存储操作
    fun getAppLanguage(context: Context = sApplication): Locale {
        if (sCurrentLanguage == null) {
            // 从SharedPreference获取Local
            sCurrentLanguage = onStorageCallback.get(context)
        }
        return sCurrentLanguage ?: sSystemLanguage
    }

    // 设置APP的语言
    fun setAppLanguage(context: Context, newLocale: Locale): Boolean {
        val oldLocale = getLocale(context)
        if (!isEquals(oldLocale, newLocale)) { // 语言没变化
            // 保存起来
            onStorageCallback.save(context, newLocale)
            // 更新Application的语言
            if (context != sApplication) updateLanguages(sApplication.resources, newLocale)
            setDefaultLocale(context)
            sCurrentLanguage = newLocale
            onAppLocaleChange?.invoke(oldLocale, newLocale)
            return true
        } else {
            return false
        }
    }

    private fun clearLanguage(context: Context) {
        onStorageCallback.clear(context) //清空
    }

    fun isSystemLanguage(): Boolean {
        return sCurrentLanguage == null
    }

    fun isEquals(oldLocale: Locale, newLocale: Locale): Boolean = newLocale == oldLocale
}