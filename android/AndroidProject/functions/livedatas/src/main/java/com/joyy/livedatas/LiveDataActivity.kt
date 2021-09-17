package com.joyy.livedatas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joyy.livedatas.ui.main.LiveDataFragment

class LiveDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.live_data_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LiveDataFragment.newInstance())
                .commitNow()
        }
    }
}