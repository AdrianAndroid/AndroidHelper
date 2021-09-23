package com.joyy.themedemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * https://blog.csdn.net/yingtian648/article/details/106528885
 */
class MainActivity : AppCompatActivity() {
    companion object {
        var currentStyle: Int = R.style.Theme_ThemeDemo_BLACK

        fun setMyTheme(act: Activity, style: Int) {
            currentStyle = style
            act.setTheme(currentStyle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Theme.ThemeDemo.BLACK
        setTheme(R.style.Theme_ThemeDemo_BLACK)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val findViewById = findViewById<TextView>(R.id.txt)
        findViewById.text = "MainActivity"
        findViewById.setTextColor(ContextCompat.getColor(this, R.color.aaaa))


        findViewById<View>(R.id.btn).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        findViewById<View>(R.id.btnBlack).setOnClickListener {
            currentStyle = R.style.Theme_ThemeDemo_BLACK
            setTheme(currentStyle)
        }
        findViewById<View>(R.id.btnRed).setOnClickListener {
            currentStyle = R.style.Theme_ThemeDemo_RED
            setTheme(currentStyle)

        }
        findViewById<View>(R.id.btnGreen).setOnClickListener {
            currentStyle = R.style.Theme_ThemeDemo_RED
            setTheme(currentStyle)
        }
    }

}