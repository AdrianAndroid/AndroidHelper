package com.joyy.routerproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.joyy.router.annotations.Destination

@Destination(
    url = "app/SecondActivity",
    description = "SecondActivity"
)
class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
}