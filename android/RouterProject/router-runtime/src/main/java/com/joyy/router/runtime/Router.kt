package com.joyy.router.runtime

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log

/**
 * Time:2021/9/9 14:33
 * Author: flannery
 * Description: 工具类
 */
object Router {

    private fun log(msg: String) {
        Log.d("[Router]", msg)
    }

    private fun log(e: Throwable) {
        e.printStackTrace()
    }

    // 编译期间生成的总映射表 com/joyy/router/mapping/generated/RouterMapping
    private const val GENERATED_MAPPING = "com.joyy.router.mapping.generated.RouterMapping"

    // 存储所有映射表信息
    private val mapping: HashMap<String, String> = HashMap()

    fun init() {
        try {
            val clazz = Class.forName(GENERATED_MAPPING)
            val method = clazz.getMethod("get")
            val allMapping = method.invoke(null) as Map<*, *>
            allMapping.onEach {
                log("[allMapping] ${it.key} = ${it.value}") //打印所有的map
                mapping[it.key as String] = it.value as String
            }
        } catch (e: Throwable) {
            log(e)
        }
    }

    fun printMapping() {
        mapping.forEach { (k, v) ->
            log(k)
            log("----->$v")

        }
    }

    fun go(context: Context, url: String, bundle: Bundle = Bundle()) {

        // 根据url找到全路径, 先直接找
        val activityTargetClass = mapping[url] // 类的全路径
        if (activityTargetClass.isNullOrBlank()) {
            log("$url 路径，没有找到全路径类名.")
            return
        }

        try {
            val activity = Class.forName(activityTargetClass)
            val intent = Intent(context, activity)
            intent.putExtras(bundle)
            context.startActivity(intent)
        } catch (e: Throwable) {
            log(e)
        }
    }

}