package com.zero.navigation1.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.zero.navigation1.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainFragment = MainFragment.newIntance()
        supportFragmentManager.commit {
            add(R.id.frameLayout,mainFragment)
        }

    }
}
