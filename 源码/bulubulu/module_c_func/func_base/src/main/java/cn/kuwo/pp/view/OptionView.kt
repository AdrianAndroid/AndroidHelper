package cn.kuwo.pp.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import cn.kuwo.common.animator.AnimatorListenerImpl
import cn.kuwo.common.util.ImageLoader
import cn.kuwo.pp.R

class OptionView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val optionTxt: TextView?
    private val optionImg: ImageView?
    private val optionResult: View? // 结果布局

    private val optionResultText: TextView?
    private val optionResultImage: ImageView?

    private var resultFinished = false

    init {
        val inflate = inflate(context, R.layout.item_friend_test_2_option, this)
        // 找到几个
//        setBackgroundColor(Color.GREEN)

        optionTxt = inflate.findViewById<TextView>(R.id.optionTxt)
        optionImg = inflate.findViewById<ImageView>(R.id.optionImage)
        optionResult = inflate.findViewById<View>(R.id.optionResult)

        // 要显示百分比的
        optionResultText = optionResult?.findViewById<TextView>(R.id.optionResultText)
        // 显示隐藏
        optionResultImage = optionResult?.findViewById<ImageView>(R.id.optionResultImage)
    }

    // 设置默认值
    fun setDefault() {
        optionTxt?.text = "";
        optionTxt?.setBackgroundColor(Color.TRANSPARENT);
        optionTxt?.visibility = View.INVISIBLE

        optionImg?.setImageResource(R.drawable.image_rounded_placeholder);
        optionImg?.visibility = View.INVISIBLE
    }

    // 设置图片问题
    fun setPicQuestion(optionOne: String) {
        // 将文字的设置隐藏
        // 图片的设置成显示
        optionImg?.visibility = View.VISIBLE
        ImageLoader.showImage(
            optionImg,
            optionOne,
            R.drawable.image_rounded_placeholder
        )
    }

    // 设置文字问题
    fun setTextQuestion(text: String, randomIndex: Int, optionOne: Boolean) {
        optionTxt?.visibility = View.VISIBLE
        optionTxt?.text = text

        setTextQuestionBgColor(randomIndex, optionOne)
    }


    fun setResultDisplay(display: Boolean) {
        optionResult?.visibility = if (display) View.VISIBLE else View.INVISIBLE
    }

    // 显示结果
    fun setResult(win: Boolean, percent: Int) {
        resultFinished = true // 说明已经有结果了


        if (optionResult == null) return

        // 设置文字
        optionResultText?.text = "${percent}%"

        // 显示动画
        // 1. 显示背景色
        optionResult.setBackgroundColor(
            if (win) Color.parseColor("#E5FFE43F")
            else Color.parseColor("#CCE0E0E0")
        )
        // 2。设置高度
        val layoutParams = optionResult.layoutParams
        layoutParams.height = getResultHeight(percent) //parent的高度
        optionResult.layoutParams = layoutParams


//        optionResult.let { view ->
//            val lp = view.layoutParams
//            lp.height = (lp.height * getMiniPercent(percent))
//            layoutParams = lp
//        }

        optionResultImage?.visibility = if (win) View.VISIBLE else View.GONE

    }

    fun getResultAnimator(): ObjectAnimator {
        optionResult?.visibility = View.VISIBLE
        // 3。播放动画
        // 缩放轴心点
        optionResult?.pivotY = optionResult?.height?.toFloat() ?: 1F // 设置轴心点
        // 先设置隐藏
        return ObjectAnimator.ofFloat(optionResult, "scaleY", 0F, 1F)
            .apply {
                duration = 200
                // listener 应该也就是调用一次
//                addListener(object : AnimatorListenerImpl() {
//                    override fun onAnimationStart(animation: Animator?) {
//                        optionResult?.visibility = View.VISIBLE
//                    }
//                })
            }
    }

    private fun getResultHeight(f: Int): Int {
        val i = f / 100F;
        return Math.max((height * i).toInt(), (height * 0.3).toInt())
    }

//    // 获取最小的百分比
//    private fun getMiniPercent(f: Float): Float {
//        val i = f / 100F
//        if (i < 30) {
//            return 30F
//        }
//        return i
//    }


    // 设置问题的背景颜色
    private fun setTextQuestionBgColor(index: Int, optionOne: Boolean) {
        if (optionOne) { // left
            optionTxt?.setTextColor(
                when (index) {
                    1 -> Color.parseColor("#4E3B1B")
                    2 -> Color.parseColor("#223056")
                    else -> Color.parseColor("#4A2657")
                }
            )
            optionTxt?.setBackgroundColor(
                when (index) {
                    1 -> Color.parseColor("#FFC058")
                    2 -> Color.parseColor("#6E96FF")
                    else -> Color.parseColor("#C273DE")
                }
            )
        } else { // right
            optionTxt?.setTextColor(
                when (index) {
                    1 -> Color.parseColor("#144647")
                    2 -> Color.parseColor("#56221C")
                    else -> Color.parseColor("#223056")
                }
            )
            optionTxt?.setBackgroundColor(
                when (index) {
                    1 -> Color.parseColor("#3DD6D9")
                    2 -> Color.parseColor("#DD5C4E")
                    else -> Color.parseColor("#6E96FF")
                }
            )
        }
    }

}