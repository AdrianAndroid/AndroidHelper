package com.example.routerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imooc.router.annotations.Destination

@Destination(
    url = "router://MainActivity2",
    description = "第二个"
)
class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }
}