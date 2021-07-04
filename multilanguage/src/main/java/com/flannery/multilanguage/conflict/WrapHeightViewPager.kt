package com.flannery.multilanguage.conflict

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

/**
 * Time:2021/7/2 19:36
 * Author:
 * Description:
 */
class WrapHeightViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var height = 0
        for (i in 0 until childCount) {
            val childAt = getChildAt(i)
            childAt.measure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            val measuredHeight = childAt.measuredHeight
            if (measuredHeight > height) {
                height = measuredHeight
            }
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
    }

}