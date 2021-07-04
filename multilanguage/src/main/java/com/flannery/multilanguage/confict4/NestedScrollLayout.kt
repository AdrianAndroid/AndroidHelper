package com.flannery.multilanguage.confict4

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.core.widget.NestedScrollView.OnScrollChangeListener
import androidx.recyclerview.widget.RecyclerView

/**
 * 适配RecyclerView滑动
 * Time:2021/7/1 19:10
 * Author:
 * Description:
 */
class NestedScrollLayout(context: Context, attrs: AttributeSet?) :
    NestedScrollView(context, attrs) {

    private lateinit var topView: View // 只需要一个TopView

//    private val mFlingHelper: FlingHelper = FlingHelper(context) //滑动距离计算

    override fun onFinishInflate() {
        super.onFinishInflate()
        // 找不到，直接崩溃就好了
        topView = (getChildAt(0) as ViewGroup).getChildAt(0)
    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(target, dx, dy, consumed, type)
        val b = dy > 0 && scrollY < topView.measuredHeight
        Log.e("TAG", "onNestedPreScroll  $b  dy = $dy")
        // 向上滑动。若当前topview可见，需要将topview滑动至不见
        if (b) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
    }

}