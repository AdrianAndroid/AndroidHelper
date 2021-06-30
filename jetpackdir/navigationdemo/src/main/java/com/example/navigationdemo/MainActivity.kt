package com.example.navigationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentController
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import com.flannery.utils.K
import com.flannery.utils.L

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        K.m(MainActivity::class.java, "otherTag", "tag1", "tag2")
//        test()
//        test2()
    }

    private fun test() {

        val btn = Button(baseContext)
        btn.findNavController()
        test2()
    }

    private fun test2() {
        test()
        supportFragmentManager.commit {
            addToBackStack(null)
            val args = Bundle().apply { putString("key", "value") }
            //replace<HomeFragment>(R.id.nav_controller_view_tag, args = args)
        }
    }

}