package com.flannery.multilanguage.conflict

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat
import com.flannery.multilanguage.BuildConfig
import kotlin.math.max

/**
 * Time:2021/7/2 09:22
 * Author:
 * Description:
 */
class NestedWebView(context: Context, attrs: AttributeSet?) : WebView(context, attrs),
    NestedScrollingChild {

    private var mLastMotionY: Float = 0F
    private var mNesteedYOffset = 0
    private var mScrollOffset = intArrayOf(0, 0)
    private var mScrollConsumed = intArrayOf(0, 0)
    private val mChildHelper: NestedScrollingChildHelper by lazy {
        NestedScrollingChildHelper(this)
    }

    var onScrollListener: ((NestedWebView, l: Int, t: Int, oldl: Int, oldt: Int) -> Unit)? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            isNestedScrollingEnabled = true
        }
    }

    @SuppressLint("Recycle", "ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("TAG", "onTouchEvent scrollY=$scrollY")
        var result = false
        ev?.let { event ->
            val trackedEvent = MotionEvent.obtain(event)
            if (event.action == MotionEvent.ACTION_DOWN) {
                mNesteedYOffset = 0  //没有偏移
            }
            event.offsetLocation(0F, mNesteedYOffset.toFloat())

            val y = event.y

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mLastMotionY = y
                    startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
                    result = super.onTouchEvent(event)
                }
                MotionEvent.ACTION_MOVE -> {
                    var deltaY = (mLastMotionY - y).toInt() // 距上次移动的距离
                    if (BuildConfig.DEBUG) Log.i("TAG", "deltaY = $deltaY")
                    if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
                        deltaY -= mScrollConsumed[1] // 其他View消费的距离
                        trackedEvent.offsetLocation(0F, mScrollOffset[1].toFloat())
                        mNesteedYOffset += mScrollOffset[1]
                    }

                    mLastMotionY = y - mScrollOffset[1]

                    val oldY = scrollY
                    val newScrollY = max(0, oldY + deltaY)
                    val dyConsumed = newScrollY - oldY
                    val dyUnconsumed = deltaY - dyConsumed

                    if (dispatchNestedScroll(0, dyConsumed, 0, dyUnconsumed, mScrollOffset)) {
                        mLastMotionY -= mScrollOffset[1]
                        trackedEvent.offsetLocation(0F, mScrollOffset[1].toFloat())
                        mNesteedYOffset += mScrollOffset[1]
                    }

                    result = super.onTouchEvent(trackedEvent)
                    trackedEvent.recycle()
                }
                MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    stopNestedScroll()
                    result = super.onTouchEvent(event)
                }
            }
        }

        return result
    }

    override fun onNestedFling(
        target: View?,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        if (BuildConfig.DEBUG) Log.e(
            "NestedWebView",
            "onNestedFling -> velocityX:$velocityX , velocityY:$velocityY"
        )
        return super.onNestedFling(target, velocityX, velocityY, consumed)
    }

    override fun flingScroll(vx: Int, vy: Int) {
        super.flingScroll(vx, vy)
        if (BuildConfig.DEBUG) Log.e(
            "NestedWebView",
            "flingScroll -> vx:$vx , vy:$vy"
        )
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) { // 设置回调监听
        super.onScrollChanged(l, t, oldl, oldt)
        onScrollListener?.invoke(this, l, t, oldl, oldt)
        if (BuildConfig.DEBUG) Log.e(
            "NestedWebView",
            "onScrollChanged -> l:$l , t:$t , oldl:$oldl , oldt:$oldt "
        )
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mChildHelper.isNestedScrollingEnabled = enabled
    }

    override fun isNestedScrollingEnabled(): Boolean {
        if (BuildConfig.DEBUG) Log.e(
            "NestedWebView",
            "isNestedScrollingEnabled() "
        )
        return mChildHelper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        if (BuildConfig.DEBUG) Log.e(
            "NestedWebView",
            "startNestedScroll ->  $axes"
        )
        return mChildHelper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        if (BuildConfig.DEBUG) Log.e(
            "NestedWebView",
            "stopNestedScroll"
        )
        mChildHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        if (BuildConfig.DEBUG) Log.e(
            "NestedWebView",
            "hasNestedScrollingParent"
        )
        return mChildHelper.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        return mChildHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        if (BuildConfig.DEBUG) Log.e(
            "NestedWebView",
            "dispatchNestedFling"
        )
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY)
    }

}