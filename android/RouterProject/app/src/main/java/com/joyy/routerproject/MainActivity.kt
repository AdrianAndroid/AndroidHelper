package com.joyy.routerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joyy.router.annotations.Destination

@Destination(url = "app/MainActivity", description = "description")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}