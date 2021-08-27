package com.joyy.routerproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.com.joyy.routerproject.R
import com.joyy.router.annotations.Destination

@Destination(url = "app/MainActivity", description = "description")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}