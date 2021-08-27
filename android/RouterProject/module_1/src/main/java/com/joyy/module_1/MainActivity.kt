package com.joyy.module_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.com.joyy.module_1.R
import com.joyy.router.annotations.Destination

@Destination(
    url = "module_1/MainActivity",
    description = "MainActiity"
)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}