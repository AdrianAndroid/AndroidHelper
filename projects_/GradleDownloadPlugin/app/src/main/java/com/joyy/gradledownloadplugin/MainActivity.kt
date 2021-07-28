package com.joyy.gradledownloadplugin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var switch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var textStr = findViewById<TextView>(R.id.textStr)

        textStr.setOnClickListener {
            switch = !switch
            // 切换语言
            if (switch)
                MultiLanguageHelper.switchEN(this)
            else
                MultiLanguageHelper.switchKR(this)

            textStr.text = ""
//            arrayListOf(
//
//            ).forEach { id ->
//                textStr.append(getString(id))
//                textStr.append("\n")
//            }
        }
    }
}