package com.flannery.multilanguage.confict4

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import com.flannery.multilanguage.BuildConfig

/**
 * 适配RecyclerView滑动
 * Time:2021/7/1 19:10
 * Author:
 * Description:
 */
class NestedScrollLayout(context: Context, attrs: AttributeSet?) :
    NestedScrollView(context, attrs) {

    private var topView: View? = null // 只需要一个TopView
    private var tabView: View? = null //TabLayout
    private var contentView: View? = null //ViewPager
    private var alreadyMeasure = false
//    private val mFlingHelper: FlingHelper = FlingHelper(context) //滑动距离计算

    override fun onFinishInflate() {
        super.onFinishInflate()
        // 找不到，直接崩溃就好了
        (getChildAt(0) as? ViewGroup)?.run {
            topView = getChildAt(0)
            tabView = getChildAt(1)
            contentView = getChildAt(2)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //val size = MeasureSpec.getSize(heightMeasureSpec)
        // 设定一个初始化高度
        if (!alreadyMeasure) {
            contentView?.run {
                val lp = layoutParams
                lp.height = MeasureSpec.getSize(heightMeasureSpec) - (tabView?.measuredHeight ?: 0)
                if (BuildConfig.DEBUG) Log.e("TAG", "lp.height=${lp.height}")
                layoutParams = lp
            }
            alreadyMeasure = true
        }
    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(target, dx, dy, consumed, type)
        val b = dy > 0 && scrollY < (topView?.measuredHeight ?: 0)
        Log.e("TAG", "onNestedPreScroll  $b  dy = $dy")
        // 向上滑动。若当前topview可见，需要将topview滑动至不见
        if (b) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
    }

}