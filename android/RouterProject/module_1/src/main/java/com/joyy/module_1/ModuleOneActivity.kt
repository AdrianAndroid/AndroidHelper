package com.joyy.module_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joyy.router.annotations.Destination

@Destination(
    url = "module_1/ModuleOneActivity",
    description = "MainActiity"
)
class ModuleOneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_one)
    }
}