package com.joyy.routerproject

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.joyy.router.annotations.Destination
import com.joyy.router.runtime.Router

@Destination(url = "app/MainActivity", description = "description")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Router.init()

        findViewById<View>(R.id.mPrintMapping).setOnClickListener {
            Router.printMapping()
        }

        findViewById<View>(R.id.mMainActvity).setOnClickListener {
            Router.go(this, "app/MainActivity")
        }

        findViewById<View>(R.id.mSecondActvity).setOnClickListener {
            Router.go(this, "app/SecondActivity")
        }

        findViewById<View>(R.id.mModuleOneActivity).setOnClickListener {
            Router.go(this, "module_1/ModuleOneActivity")
        }

        findViewById<View>(R.id.mModuleTwoActivity).setOnClickListener {
            Router.go(this, "module_2/ModuleTwoActivity")
        }
    }
}