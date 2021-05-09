package cn.kuwo.pp.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import cn.kuwo.pp.R
import cn.kuwo.common.view.UseHeadImageLayout

class ParallelPKView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val ivPKIivPKIconcon: View? // Icon

    private val layoutUsers: View?

    private var finished = false

    // 左边的
    private val layoutAnswerOneUser: UseHeadImageLayout? //左边的
    private val tvGroupOne: TextView?

    // 右边的
    private val layoutAnswerTwoUser: UseHeadImageLayout?
    private val tvGroupTwo: TextView?

    init {
        val inflate = inflate(context, R.layout.item_friend_test_2_pk, this)

        ivPKIivPKIconcon = inflate.findViewById(R.id.ivPKIivPKIconcon)
        layoutUsers = inflate.findViewById(R.id.layoutUsers)

        // 左边
        layoutAnswerOneUser = inflate.findViewById(R.id.layoutAnswerOneUser)
        tvGroupOne = inflate.findViewById(R.id.tvGroupOne)

        // 右边
        layoutAnswerTwoUser = inflate.findViewById(R.id.layoutAnswerTwoUser)
        tvGroupTwo = inflate.findViewById(R.id.tvGroupTwo)

    }

    // 设置结果页
    @SuppressLint("SetTextI18n")
    fun setResult(
        leftText: String,
        leftUrls: ArrayList<String>,
        leftListener: UseHeadImageLayout.ImageLayoutClickListener,
        rightText: String,
        rightUrls: ArrayList<String>,
        rightListener: UseHeadImageLayout.ImageLayoutClickListener
    ) {
        // 左边
        tvGroupOne?.text = leftText
        layoutAnswerOneUser?.removeAllUrls()
        layoutAnswerOneUser?.setMoreFirst(true)
        layoutAnswerOneUser?.setUrls(leftUrls)
        layoutAnswerOneUser?.clickUserHeadListener = leftListener

        // 右边
        tvGroupTwo?.text = rightText
        layoutAnswerTwoUser?.removeAllUrls()
        layoutAnswerTwoUser?.setMoreFirst(true)
        layoutAnswerTwoUser?.setUrls(rightUrls)
        layoutAnswerTwoUser?.clickUserHeadListener = rightListener

//        layoutAnswerOneUser?.bringToFront()
//        layoutAnswerTwoUser?.bringToFront()
    }

    // PK头像
    fun getPKImageAnimator(): Animator {
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(ivPKIivPKIconcon, "scaleX", 0F, 1F),
            ObjectAnimator.ofFloat(ivPKIivPKIconcon, "scaleY", 0F, 1F)
        )
        ivPKIivPKIconcon?.scaleX = 0F
        ivPKIivPKIconcon?.scaleY = 0F
        animatorSet.duration = 300
        return animatorSet
    }

    // 除了PK头像之外的
    fun getUserAnimator(): Animator {
        val animatorSet = AnimatorSet();
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(layoutUsers, "scaleX", 0F, 1F),
            ObjectAnimator.ofFloat(layoutUsers, "scaleY", 0F, 1F)
        )
        layoutUsers?.scaleX = 0F
        layoutUsers?.scaleY = 0F
        animatorSet.duration = 300
        return animatorSet
    }

}