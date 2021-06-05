package com.flannery.lottie

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_lottie.*

class LottieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //json_image_5
        mLottieAnimationView.imageAssetsFolder = "json_image_5"
        mLottieAnimationView2.imageAssetsFolder = "json_image_3"

//        val challangeLoadingView = ChallangeLoadingView(findViewById(R.id.challengeLayout))
//        challangeLoadingView.start()

    }
}