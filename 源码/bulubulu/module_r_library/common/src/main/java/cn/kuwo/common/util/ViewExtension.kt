package cn.kuwo.common.util

import android.animation.Animator
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

/**
 * 带动画显示view
 *
 * @param immediately 是否跳过动画，立即执行
 */
fun View.animIn(immediately: Boolean) {
    if (this.visibility == View.VISIBLE) {
        return
    }
    if (immediately) {
        this.visibility = View.VISIBLE
        return
    }
    val anim = AlphaAnimation(0f, 1f)
    anim.duration = this.context.resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            this@animIn.visibility = View.VISIBLE
        }

        override fun onAnimationEnd(animation: Animation) {}

        override fun onAnimationRepeat(animation: Animation) {}
    })
    this.startAnimation(anim)
}

/**
 * 带动画隐藏view
 *
 * @param setGone     true：GONE，false：INVISIBLE
 * @param immediately 是否跳过动画，立即执行
 */
fun View.animOut(setGone: Boolean, immediately: Boolean) {
    if (this.visibility != View.VISIBLE || immediately) {
        this.visibility = if (setGone) View.GONE else View.INVISIBLE
        return
    }

    val anim = AlphaAnimation(1f, 0f)
    anim.duration = this.context.resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {}

        override fun onAnimationEnd(animation: Animation) {
            this@animOut.visibility = if (setGone) View.GONE else View.INVISIBLE
        }

        override fun onAnimationRepeat(animation: Animation) {}
    })
    this.startAnimation(anim)
}