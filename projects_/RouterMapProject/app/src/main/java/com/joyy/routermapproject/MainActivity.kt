package com.joyy.routermapproject

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.imooc.gradle.router.runtime.Router
import com.imooc.router.annotations.Destination
import com.imooc.router.mapping.RouterPath_app

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


        RouterPath_app.ROUTERFIRSTFRAGMENT
//        val fragments = findViewById<FragmentContainerView>(R.id.fragments)

//        click(R.id.f1) { replace(RouterPath.ROUTERFIRSTFRAGMENT) }
//        click(R.id.f2) { replace(RouterPath.ROUTERSECONDFRAGMENT) }
//        click(R.id.f3) { replace(RouterPath.ROUTERTHIRDFRAGMENT) }
//        click(R.id.f4) { replace(RouterPath.ROUTERFOURFRAGMENT) }
////        click(R.id.f5) { replace(FiveFragment()) }
//        click(R.id.f6) { replace(RouterPath.ROUTERLOGINFRAGMENT) }
//        click(R.id.f7) { replace(RouterPath.ROUTERSCROLLINGFRAGMENT) }
    }

    fun replace(url: String) {
        val f = Router.instanceFragment(url)
        supportFragmentManager.beginTransaction().replace(R.id.fragments, f).commitAllowingStateLoss()
    }

    fun click(id: Int, call: (() -> Unit)) {
        findViewById<View>(id).setOnClickListener {
            call()
        }
    }
}
