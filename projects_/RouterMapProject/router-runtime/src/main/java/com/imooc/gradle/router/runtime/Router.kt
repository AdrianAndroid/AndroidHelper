package com.imooc.gradle.router.runtime

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import java.lang.IllegalArgumentException
import java.lang.StringBuilder

object Router {
    val TAG = "[Router]"

    // 编译期间生成的总映射表
    private const val GENERATED_MAPPING =
            "com.imooc.router.mapping.generated.RouterMapping"

    // 存储所有映射表信息
    private val mapping: HashMap<String, String> = HashMap()

    fun init() {

        try {
            val clazz = Class.forName(GENERATED_MAPPING)
            val method = clazz.getMethod("get")
            val allMapping = method.invoke(null) as Map<String, String>

            if (allMapping?.size > 0) {
                Log.i(TAG, "init: get all mapping:")
                allMapping.onEach {
                    Log.i(TAG, "    ${it.key} -> ${it.value}")
                }
                mapping.putAll(allMapping)
            }
            Log.e(TAG, "init: success!!!")
        } catch (e: Throwable) {
            Log.e(TAG, "init: error while init router : $e")
        }
    }

    // url Destination上的路由（完整字符串）
    fun fullPathFromMapping(url: String): String? {
        return mapping[url]
    }

    fun instanceFragment(url: String): Fragment {
        val fullPath = fullPathFromMapping(url)
        val fragment = Class.forName(fullPath!!) // Fragment应该在开发阶段就不能出错
        val newInstance = fragment.getDeclaredConstructor().newInstance()
        return if (newInstance is Fragment) newInstance else throw IllegalArgumentException("url=$url 不能转化成Fragment哟")
    }

    fun go(context: Context, url: String) {

        // 匹配URL，找到目标页面
        // router://imooc/profile?name=imooc&message=hello

        val uri = Uri.parse(url)
        val scheme = uri.scheme
        val host = uri.host
        val path = uri.path

        var targetActivityClass = ""

        mapping.onEach {
            val ruri = Uri.parse(it.key)
            val rscheme = ruri.scheme
            val rhost = ruri.host
            val rpath = ruri.path

            if (rscheme == scheme && rhost == host && rpath == path) {
                targetActivityClass = it.value
            }
        }

        if (targetActivityClass == "") {
            Log.e(TAG, "go:     no destination found.")
            return
        }

        // 解析URL里的参数，封装成为一个 Bundle

        val bundle = Bundle()
        val query = uri.query
        query?.let {
            if (it.length >= 3) {
                val args = it.split("&")
                args.onEach { arg ->
                    val splits = arg.split("=")
                    bundle.putString(splits[0], splits[1])
                }
            }
        }


        // 打开对应的Activity，并传入参数

        try {
            val activity = Class.forName(targetActivityClass)
            val intent = Intent(context, activity)
            intent.putExtras(bundle)
            context.startActivity(intent)
        } catch (e: Throwable) {
            Log.e(
                    TAG, "go: error while start activity: $targetActivityClass, " +
                    "e = $e"
            )
        }
    }

    override fun toString(): String {
        if (mapping.isEmpty()) return "[:]"
        val sb = StringBuilder()
        mapping.forEach { (t, u) ->
            val line = "$t : $u \n"
            println(line)
            sb.append(line)
        }
        return sb.toString()
    }
}









