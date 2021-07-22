package com.joyy.routermapproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.imooc.gradle.router.runtime.Router
import com.imooc.router.annotations.Destination

@Destination(
    url = "router://MainActivity",
    description = "MainActivity"
)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.go).setOnClickListener {
            Router.go(this, "router://SecondActivity")
        }
    }
}