package com.flannery.multilanguage.confict4

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.flannery.multilanguage.BuildConfig
import com.flannery.utils.K

/**
 * Time:2021/7/2 19:36
 * Author:
 * Description:
 */
class WrapHeightViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private var position = 0

    init {
        if (BuildConfig.DEBUG) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setOnScrollChangeListener(object : OnScrollChangeListener {
                    override fun onScrollChange(
                        v: View?,
                        scrollX: Int,
                        scrollY: Int,
                        oldScrollX: Int,
                        oldScrollY: Int
                    ) {
                        K.m(
                            javaClass,
                            "WrapHeightViewPager setOnScrollChangeListener scrollX=$scrollX scrollY=$scrollY oldScrollX=$oldScrollX oldScrollY=$oldScrollY v=$v"
                        )
                    }
                })
            }
        }
    }

    fun referencePosition(position: Int) {
        this.position = position
    }

    // 创建的时候调用onMeasure一次
    // RecyclerView调用一次

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (BuildConfig.DEBUG) Log.e("onMeasure", "onMeasure")
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


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (BuildConfig.DEBUG) K.m(javaClass, "onScrollChanged l=$l t=$t oldl=$oldl oldt=$oldt")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (BuildConfig.DEBUG) K.m(javaClass, "onLayout changed:$changed l=$l t=$t r=$r b=$b")
    }


    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        if (BuildConfig.DEBUG) K.m(
            javaClass,
            "onOverScrolled scrollX=$scrollY scrollY=$scrollY clampedX=$clampedX clampedY=$clampedY"
        )
    }

}