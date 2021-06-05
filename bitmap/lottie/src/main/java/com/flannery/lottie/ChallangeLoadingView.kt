package com.flannery.lottie

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd

class ChallangeLoadingView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {


    val challengeLayout: View?
    val challengeImage: View?
    val challengeText: View?
    val challengeShading: View?

    init {
        inflate(context, R.layout.layout_challenge_loading, this)

        challengeLayout = findViewById(R.id.challengeLayout)
        challengeImage = findViewById(R.id.challengeImage)
        challengeText = findViewById(R.id.challengeText)
        challengeShading = findViewById(R.id.challengeShading)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }


    fun start() {
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(challengeImage, "translationY", -150F, -50F)
                .apply {
                    startDelay = 0
                    interpolator = BounceInterpolator()
                    duration = 1500
                    repeatCount = 1000
                    repeatMode = ObjectAnimator.REVERSE
                },
            ObjectAnimator.ofFloat(challengeShading, "translationX", 0f, 600F)
                .apply {
                    startDelay = 0
                    interpolator = LinearInterpolator()
                    duration = 3500
                    doOnEnd { challengeShading?.visibility = View.INVISIBLE }
                },
            ObjectAnimator.ofFloat(challengeText, "alpha", 1.0f, 0.8f, 0.6f, 0.4f, 0.2f, 0.0f)
                .apply {
                    startDelay = 3500
                    duration = 1000
                    repeatCount = 10
                    repeatMode = ObjectAnimator.RESTART
                }
        )
        animatorSet.start()
        animatorSet.doOnEnd {
            visibility = View.GONE
            animatorSet.cancel()
        }
    }
}