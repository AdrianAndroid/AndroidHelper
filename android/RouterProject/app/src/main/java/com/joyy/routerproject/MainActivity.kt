package com.joyy.routerproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.joyy.module_1.ModuleOneActivity
import com.joyy.module_2.ModuleTwoActivity
import com.joyy.router.annotations.Destination

@Destination(url = "app/MainActivity", description = "description")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<View>(R.id.mSecondActvity).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        findViewById<View>(R.id.mModuleOneActivity).setOnClickListener {
            startActivity(Intent(this, ModuleOneActivity::class.java))
        }

        findViewById<View>(R.id.mModuleTwoActivity).setOnClickListener {
            startActivity(Intent(this, ModuleTwoActivity::class.java))
        }

    }
}