package com.joyy.androidproject

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        findViewById<View>(android.R.id.content).postDelayed(Runnable {
            Toast.makeText(this, BuildConfig.APPLICATION_ID, Toast.LENGTH_LONG).show()
        }, 2000)
    }
}