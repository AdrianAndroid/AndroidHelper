package com.joyy.routermapproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.imooc.gradle.router.runtime.Router
import com.imooc.router.annotations.Destination
import com.joyy.routermapproject.fragment.*
import com.joyy.routermapproject.fragment.ui.login.LoginFragment

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

        val fragments = findViewById<FragmentContainerView>(R.id.fragments)
        click(R.id.f1) { replace(FirstFragment()) }
        click(R.id.f2) { replace(SecondFragment()) }
        click(R.id.f3) { replace(ThirdFragment()) }
        click(R.id.f4) { replace(FourFragment()) }
//        click(R.id.f5) { replace(FiveFragment()) }
        click(R.id.f6) { replace(LoginFragment()) }
        click(R.id.f7) { replace(ScrollingFragment()) }


    }

    fun replace(f: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragments, f).commitAllowingStateLoss()
    }

    fun click(id: Int, call: (() -> Unit)) {
        findViewById<View>(id).setOnClickListener {
            call()
        }
    }
}