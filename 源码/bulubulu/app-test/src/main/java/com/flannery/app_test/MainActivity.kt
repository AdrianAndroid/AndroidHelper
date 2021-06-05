package com.flannery.app_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserHandle
import android.view.View
import com.flannery.app_test.bitmap.BitmapActivity
import com.flannery.app_test.handler.LeakHandlerActivity
import com.flannery.app_test.handler.UseHandlerActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //startActivity(Intent(this, LoginA
        // ctivity::class.java))
    }

    fun onClickMain(view: View) {
        when (view.id) {
            R.id.useHandler -> {
                startActivity(Intent(this, UseHandlerActivity::class.java))
            }
            R.id.leakHandler -> {
                startActivity(Intent(this, LeakHandlerActivity::class.java))
            }
            R.id.bitmap -> {
                startActivity(Intent(this, BitmapActivity::class.java))
            }
        }
    }
}