package com.joyy.livedatas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.joyy.livedatas.ui.main.LiveDataFragment

//@Destination(
//    url = "livedatas/LiveDataActivity",
//    description = "LiveDataActivity"
//)
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