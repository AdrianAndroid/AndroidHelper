package com.joyy.routermapproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imooc.gradle.router.runtime.Router
import com.imooc.router.annotations.Destination

@Destination(
    url = "router://ThirdActivity",
    description = "ThirdActivity"
)
class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
    }
}