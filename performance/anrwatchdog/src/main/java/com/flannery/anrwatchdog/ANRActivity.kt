package com.flannery.anrwatchdog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.flannery.anrwatchdog.ui.main.ANRFragment
import com.github.anrwatchdog.ANRWatchDog

class ANRActivity : AppCompatActivity() {

    companion object{
        fun initANR() {
            ANRWatchDog().start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.anr_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ANRFragment.newInstance())
                .commitNow()
        }
    }
}