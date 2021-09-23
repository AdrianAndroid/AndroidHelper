package com.joyy.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.lang.IllegalArgumentException

/**
 * Time:2021/9/23 16:02
 * Author: flannery
 * Description:
 */
object ActivityUtils {

    fun startActivity(context: Context, absolutePath: String, block: ((Intent) -> Unit)? = null) {
        try {
            if (absolutePath.isNotBlank()) {
                val intent = Intent()
                block?.invoke(intent) // 添加额外参数
                intent.setClassName(context, absolutePath)
                context.startActivity(intent)
            } else {
                throw IllegalArgumentException("请传入正确的全类名")
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }


}