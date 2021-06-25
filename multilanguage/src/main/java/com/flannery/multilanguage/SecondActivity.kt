package com.flannery.multilanguage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<View>(R.id.jump).setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }

        findViewById<View>(R.id.lanZH).setOnClickListener {
            MultiLanguageHelper.switchEN(this)
        }

        findViewById<View>(R.id.lanHan).setOnClickListener {
            MultiLanguageHelper.switchKR(this)
        }

        val txt = findViewById<TextView>(R.id.txt)
        findViewById<View>(R.id.swicthTxt).setOnClickListener {
            txt.setText(getString(R.string.test_string))
        }
    }


}