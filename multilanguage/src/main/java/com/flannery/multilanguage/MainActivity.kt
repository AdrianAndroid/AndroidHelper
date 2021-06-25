package com.flannery.multilanguage

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txt: TextView = findViewById(R.id.txt)

        findViewById<View>(R.id.lanZH).setOnClickListener {
            MultiLanguageHelper.switchEN(this)
        }

        findViewById<View>(R.id.lanHan).setOnClickListener {
            MultiLanguageHelper.switchKR(this)
        }

        findViewById<View>(R.id.swicthTxt).setOnClickListener {
            txt.setText(getString(R.string.test_string))
        }

        findViewById<View>(R.id.jump).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    fun test(txt: TextView) {
        txt.setText(0)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

}