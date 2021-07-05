package com.flannery.multilanguage.confict4

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.flannery.multilanguage.BuildConfig

/**
 * Time:2021/7/2 19:36
 * Author:
 * Description:
 */
class WrapHeightViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private val POSITION_DEFAULT = -1
    private var position = POSITION_DEFAULT

    fun referencePosition(position: Int) {
        this.position = position
    }

    // 创建的时候调用onMeasure一次
    // RecyclerView调用一次

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if(BuildConfig.DEBUG) Log.e("onMeasure", "onMeasure")
        var height = 0
        for (i in 0 until childCount) {
            if (position == i) {
                val childAt = getChildAt(i)
                childAt.measure(
                    widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                )
                val measuredHeight = childAt.layoutParams.height

                // 找到所有循环中，高度最高的哪个
                if (measuredHeight > height) {
                    height = measuredHeight
                }
            }
        }
        if (height > 0) {
            super.onMeasure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            )
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }


//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        var height = 0
//        for (i in 0 until childCount) {
//            val childAt = getChildAt(i)
//            childAt.measure(
//                widthMeasureSpec,
//                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
//            )
//            val measuredHeight = childAt.measuredHeight
//
//            if (this.position == i) {
//                positionHeight = measuredHeight //记录下此位置的高度
//                if (positionHeight > 0) {
//                    height = positionHeight
//                    break //跳出循环
//                }
//            }
//            // 找到所有循环中，高度最高的哪个
//            if (measuredHeight > height) {
//                height = measuredHeight
//            }
//            if (BuildConfig.DEBUG) Log.e("TAG", "child----$i  currentHeight=$height")
//        }
//        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
//    }

}