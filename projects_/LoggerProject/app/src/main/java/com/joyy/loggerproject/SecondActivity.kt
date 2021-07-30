package com.joyy.loggerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joyy.log_core.KLog

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        KLog.et()
    }
}