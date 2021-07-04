package com.flannery.multilanguage.conflict

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.core.widget.NestedScrollView.OnScrollChangeListener
import androidx.recyclerview.widget.RecyclerView

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

    init {
        if (false)
            setOnScrollChangeListener(OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
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
                    Log.e(
                        "TAG",
                        "setOnScrollChangeListener isStartFling=$isStartFling  totalDy=$totalDy"
                    )
                }
            })
    }

    private fun dispatchChildFling() {
        if (velocityY != 0) {
            val splineFlingDistance = mFlingHelper.getSplineFlingDistance(velocityY)
            if (splineFlingDistance > totalDy) {
                childFLing(mFlingHelper.getVelocityByDistance(splineFlingDistance - totalDy.toDouble()))
            }
        }
    }

    // 开始滑动
    private fun childFLing(velY: Int) {
//        getChildRecyclerView(contentView)?.let {
//            it.fling(0, velY)
//        }
    }

    override fun fling(velocityY: Int) {
        super.fling(velocityY)
        if (velocityY <= 0) {
            this.velocityY = 0
        } else {
            isStartFling = true
            this.velocityY = velocityY
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // 找不到，直接崩溃就好了
        topView = (getChildAt(0) as ViewGroup).getChildAt(0)
        contentView = (getChildAt(0) as ViewGroup).getChildAt(1) as ViewGroup
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 调整contentView的高度为父容器高度， 使之填充布局，避免父容器滚动后出现空白
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val lp = contentView.layoutParams
        lp.height = measuredHeight
        contentView.layoutParams = lp
    }

//    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
//        super.onNestedPreScroll(target, dx, dy, consumed)
//        val b = dy > 0 && scaleY < topView.measuredHeight
//        Log.e("TAG", "onNestedPreScroll  $b")
//        // 向上滑动。若当前topview可见，需要将topview滑动至不见
//        if (b) {
//            scrollBy(0, dy)
//            consumed[1] = dy
//        }
//    }

    private fun getChildRecyclerView(viewGroup: ViewGroup): RecyclerView? {
        for (i in 0 until viewGroup.childCount) {
            val view = getChildAt(i)
            if (view is RecyclerView) {
                return view
            } else if (view is ViewGroup) {
                val childRecyclerView = getChildRecyclerView(view as ViewGroup)
                if (childRecyclerView is RecyclerView) {
                    return childRecyclerView
                }
            }
        }
        return null
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