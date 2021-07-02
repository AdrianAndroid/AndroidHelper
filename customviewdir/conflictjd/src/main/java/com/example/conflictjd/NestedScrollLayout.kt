package com.example.conflictjd

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView

/**
 * Time:2021/7/1 19:10
 * Author:
 * Description:
 */
class NestedScrollLayout(context: Context, attrs: AttributeSet?) :
    NestedScrollView(context, attrs) {

    private lateinit var topView: View
    private lateinit var contentView: ViewGroup
    private var totalDy = 0
    private var isStartFling = false //用于判断RecyclerView是否在fling
    private var velocityY = 0 //记录当前滑动的y轴加速度

    private val mFlingHelper: FlingHelper = FlingHelper(context) //滑动距离计算

    private fun log(msg: String) {
        Log.e("TAG", msg)
    }

    init {
        setOnScrollChangeListener { v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            v?.let {
                if (isStartFling) {
                    totalDy = 0;
                    isStartFling = false
                }
                if (scrollY == 0) {
                    //Top SCROLL
                }
                if (scrollY == (getChildAt(0).measuredHeight - it.measuredHeight)) {
                    // BOTTOM SCROLL
                    dispatchChildFling();
                }
                // 记录RecyclerView fling情况下， 记录当前RecyclerView在y轴的偏移
                totalDy += scrollY - oldScrollY
            }
        }
    }

    private fun dispatchChildFling() {
        if (velocityY != 0) {
            val splineFlingDistance = mFlingHelper.getSplineFlingDistance(velocityY)
            if (splineFlingDistance > totalDy) {
                childFLing(mFlingHelper.getVelocityByDistance(splineFlingDistance - totalDy.toDouble()))
            }
        }
    }

    private fun childFLing(velY: Int) {

    }




}