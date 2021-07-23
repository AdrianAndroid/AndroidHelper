package com.joyy.biz_reading2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imooc.router.annotations.Destination
import com.imooc.router.mapping.RouterPath_bizreading2

@Destination(
        url = "router://ReadingActivity",
        description = "ReadingActivity"
)
class ReadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

//        RouterPath_bizreading2.ROUTERREADINGACTIVITY

    }
}