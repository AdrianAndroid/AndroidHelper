package com.joyy.themedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.joyy.themedemo.R

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.setMyTheme(this, MainActivity.currentStyle)
        setContentView(R.layout.activity_main)


        val tv = findViewById<TextView>(R.id.txt)
        tv.text = "SecondActivity"

        findViewById<View>(R.id.btn).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }


        findViewById<View>(R.id.btnBlack).setOnClickListener {
            MainActivity.setMyTheme(this, R.style.Theme_ThemeDemo_BLACK)
        }
        findViewById<View>(R.id.btnRed).setOnClickListener {
            MainActivity.setMyTheme(this, R.style.Theme_ThemeDemo_RED)

        }
        findViewById<View>(R.id.btnGreen).setOnClickListener {
            MainActivity.setMyTheme(this, R.style.Theme_ThemeDemo_GREEN)
        }
    }
}