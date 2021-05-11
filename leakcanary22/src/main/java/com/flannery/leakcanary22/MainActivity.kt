package com.flannery.leakcanary22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import leakcanary.LeakCanary

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun test() {

    }
}