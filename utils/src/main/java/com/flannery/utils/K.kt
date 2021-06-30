package com.flannery.utils

import android.text.TextUtils
import android.util.Log

/**
 * Time:2021/6/30 11:34
 * Author: 工具类
 * Description:
 */
object K {

    private const val DEBUG = true;

    private fun findClassStackTraceElement(aClass: Class<*>?): StackTraceElement? {
        if (DEBUG)
        //Thread.currentThread().getStackTrace()[4].getClassName()
        //cn.kuwo.pp.ui.discover.FriendMatchingDialog
        //aClass.getCanonicalName()
        //cn.kuwo.pp.ui.discover.FriendMatchingDialog
            for (stackTraceElement in Thread.currentThread().stackTrace) {
                if (stackTraceElement != null && aClass != null) {
                    val className = stackTraceElement.className
                    val canonicalName = aClass.canonicalName
                    if (!TextUtils.isEmpty(className)
                        && !TextUtils.isEmpty(canonicalName)
                        && className.startsWith(canonicalName)
                    ) {
                        return stackTraceElement
                    }
                }
            }
        return null
    }

    open fun m(aClass: Class<*>?, vararg o: String?) {
        if (DEBUG) {
            if (aClass == null) {
                printLogString(null, *o)
            } else {
                val classStackTraceElement = findClassStackTraceElement(aClass)
//                if (classStackTraceElement == null && TextUtils.isEmpty(otherTag)) {
//                    otherTag = aClass.name
//                }
                printLogString(classStackTraceElement, *o)
            }
        }
    }


    // 打印log
    private fun printLogString(stackTraceElement: StackTraceElement?, vararg o: String?) {
        val sb = java.lang.StringBuilder()
        var fileName: String? = "L" //防止为空
        if (stackTraceElement != null) {
            fileName = stackTraceElement.fileName
            //val className = stackTraceElement.className
            val methodName = stackTraceElement.methodName
            val lineNumber = stackTraceElement.lineNumber
            sb.append(methodName)
                .append("(").append(fileName).append(":").append(lineNumber).append(")")
        }
        sb.append(Thread.currentThread().name).append(",")
        o.forEach { i -> if (i != null) sb.append(i).append(",") }
        //Log.i(fileName, methodName+"("+fileName+":"+lineNumber+")");
        Log.i("[KLog]", sb.toString())
    }


}