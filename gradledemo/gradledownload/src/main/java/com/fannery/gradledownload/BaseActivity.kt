package com.fannery.gradledownload

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.StringBuilder

/**
 * Time:2021/7/26 10:48
 * Author:
 * Description:
 */
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        printStackTrace()
    }


    fun printStackTrace() {
        // StackTraceElement[] trace = getOurStackTrace();
        // for (StackTraceElement traceElement : trace)
        // s.println("\tat " + traceElement);
        val sb = StringBuilder()
        for (element in Thread.currentThread().stackTrace) {
            sb.append("\tat").append(element).appendLine()
        }
        Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show()
        Log.e("MainActivity", sb.toString())
    }


}