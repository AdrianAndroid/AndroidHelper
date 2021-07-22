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

        Router.init()

        findViewById<View>(R.id.go1).setOnClickListener {
            Router.go(this, "router://SecondActivity")
        }
        findViewById<View>(R.id.go2).setOnClickListener {
            Router.go(this, "router://ThirdActivity")
        }
        findViewById<View>(R.id.go3).setOnClickListener {
            Router.go(this, "router://FourActivity")
        }
        findViewById<View>(R.id.go4).setOnClickListener {
            Router.go(this, "router://FiveActivity")
        }
    }
}