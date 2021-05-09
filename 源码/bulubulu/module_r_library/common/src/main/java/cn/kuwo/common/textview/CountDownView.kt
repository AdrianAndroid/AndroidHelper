package cn.kuwo.common.textview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import cn.kuwo.common.R
import cn.kuwo.daynight.TypefaceUtil

/**
 * 显示倒计时的TextView
 */
class CountDownView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private val tvOne: TextView?
    private val tvTwo: TextView?
    private val tvThree: TextView?
    private val tvFour: TextView?
    val animSet = AnimatorSet()

    init {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER

        inflate(context, R.layout.merge_countdownview, this) //  初始化完直接塞到这里
//        LayoutInflater.from(context).


        val SCALE = 1.5f
        tvOne = findViewById<TextView>(R.id.tvOne)
        tvTwo = findViewById<TextView>(R.id.tvTwo)
        tvThree = findViewById<TextView>(R.id.tvThree)
        tvFour = findViewById<TextView>(R.id.tvFour)

        TypefaceUtil.setTypeface(tvOne, TypefaceUtil.DIN_Condensed_Bold_2)
        TypefaceUtil.setTypeface(tvTwo, TypefaceUtil.DIN_Condensed_Bold_2)
        TypefaceUtil.setTypeface(tvThree, TypefaceUtil.DIN_Condensed_Bold_2)
        TypefaceUtil.setTypeface(tvFour, TypefaceUtil.DIN_Condensed_Bold_2)

        if (tvThree != null && tvFour != null) {
            val alpha3 = ObjectAnimator.ofFloat(tvThree, "alpha", 1f, 0f)
            val scaleX3 = ObjectAnimator.ofFloat(tvThree, "scaleX", 1f, SCALE)
            val scaleY3 = ObjectAnimator.ofFloat(tvThree, "scaleY", 1f, SCALE)

            val alpha4 = ObjectAnimator.ofFloat(tvFour, "alpha", 1f, 0f)
            val scaleX4 = ObjectAnimator.ofFloat(tvFour, "scaleX", 1f, SCALE)
            val scaleY4 = ObjectAnimator.ofFloat(tvFour, "scaleY", 1f, SCALE)
            animSet.duration = 400
            animSet.interpolator = AccelerateInterpolator()
            animSet.startDelay = 400
            animSet.playTogether(alpha3, scaleX3, scaleY3, alpha4, scaleX4, scaleY4)
        }
    }


    public fun setSecond(second: Int) {
        val m = second / 60
        val d = second % 60

        if (m > 60) {
            throw IllegalArgumentException("不要超过1个小时");
        }

        tvOne?.text = "${m / 10}"
        tvTwo?.text = "${m % 10}"
        tvThree?.text = "${d / 10}"
        tvFour?.text = "${d % 10}"
        // <10 #FF6565

        if (second < 10) {
            val parseColor = Color.parseColor("#FF6565")
            tvOne?.setTextColor(parseColor)
            tvTwo?.setTextColor(parseColor)
            tvThree?.setTextColor(parseColor)
            tvFour?.setTextColor(parseColor)
            // 设置动画
            animSet.cancel()
            if (second > 0) {
                animSet.start()
            } else {
                tvThree?.alpha = 1f
                tvThree?.scaleX = 1f
                tvThree?.scaleY = 1f
                tvFour?.alpha = 1f
                tvFour?.scaleX = 1f
                tvFour?.scaleY = 1f
            }
        }
    }


    fun stopAnim() {
        animSet.cancel()
    }
}