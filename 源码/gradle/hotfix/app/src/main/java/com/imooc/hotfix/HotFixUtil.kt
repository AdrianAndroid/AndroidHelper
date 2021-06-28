package com.imooc.hotfix

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.lang.RuntimeException
import java.lang.reflect.Array

/**
 * 负责运行时注入补丁包
 */
object HotFixUtil {

    private val FIXED_DEX_PATH =
        "${Environment.getExternalStorageDirectory().absolutePath}/fixed.dex"

    /**
     * 注入补丁包
     */
    fun install(context: Context) {
        try {
            // 获取补丁包的路径
            val fixedDexFile = File(FIXED_DEX_PATH)
            // 若补丁包不存在，说明不需要修复，直接返回
            if(!fixedDexFile.exists()) {
                return
            }

            // 尝试获取 PathClassLoader 的 pathList
            val pathListField = ReflectUtil.findField(
                context.classLoader,
                "pathList"
            )
            val dexPathList = pathListField.get(context.classLoader)

            // DexPathList 类里面  makeDexElements 方法
            val makeDexElements = ReflectUtil.findMethod(
                dexPathList,
                "makeDexElements",
                List::class.java,
                File::class.java,
                List::class.java,
                ClassLoader::class.java
            )

            // 把待加载的补丁文件，添加到一个列表中
            val filesToBeInstalled = ArrayList<File>()
            filesToBeInstalled.add(fixedDexFile)

            // 准备 调用makeDexElements 方法所需的其他参数
            val optimizedDirectory = File(context.filesDir, "fixed_dex")
            val suppressedExceptions = ArrayList<IOException>()

            // 得到 待修复Dex 对应的 Element[]
            val extraElements = makeDexElements.invoke(
                dexPathList,
                filesToBeInstalled,
                optimizedDirectory,
                suppressedExceptions,
                context.classLoader
            ) as kotlin.Array<Any>

            // 获取 原始的 Element[]
            val dexElementsField = ReflectUtil.findField(
                dexPathList, "dexElements"
            )
            val originalElements = dexElementsField.get(
                dexPathList
            ) as kotlin.Array<Any>

            // 创建一个新的数组，用于合并
            val combinedElements = Array.newInstance(
                originalElements.javaClass.componentType,
                originalElements.size + extraElements.size
            ) as kotlin.Array<Any>

            // 合并新旧数组
            System.arraycopy(
                extraElements,
                0,
                combinedElements,
                0,
                extraElements.size)
            System.arraycopy(
                originalElements,
                0,
                combinedElements,
                extraElements.size,
                originalElements.size
            )

            // 替换系统原来的 dexElements 数组
            dexElementsField.set(dexPathList, combinedElements)

        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }
}