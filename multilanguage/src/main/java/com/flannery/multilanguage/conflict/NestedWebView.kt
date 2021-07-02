package com.flannery.multilanguage.conflict

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.webkit.WebView
import androidx.core.view.NestedScrollingChild2
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.flannery.multilanguage.BuildConfig

/**
 * Time:2021/7/2 09:22
 * Author: 备份用
 * Description:
 */
class NestedWebView(context: Context, attrs: AttributeSet?) : WebView(context, attrs),
    NestedScrollingChild2, NestedScrollingChild3 {

    /**
     * The RecyclerView is not currently scrolling.
     * @see .getScrollState
     */
    val SCROLL_STATE_IDLE = 0

    /**
     * The RecyclerView is currently being dragged by outside input such as user touch input.
     * @see .getScrollState
     */
    val SCROLL_STATE_DRAGGING = 1

    /**
     * The RecyclerView is currently animating to a final position while not under
     * outside control.
     * @see .getScrollState
     */
    val SCROLL_STATE_SETTLING = 2

    /**
     * Indicates that the input type for the gesture is from a user touching the screen.
     */
    val TYPE_TOUCH = 0

    /**
     * Indicates that the input type for the gesture is caused by something which is not a user
     * touching a screen. This is usually from a fling which is settling.
     */
    val TYPE_NON_TOUCH = 1

    private var mScrollOffset = intArrayOf(0, 0)
    private var mNestedOffsets = intArrayOf(0, 0)
    private val mReusableIntPair = intArrayOf(0, 0)
    private var mInitialTouchX: Int = 0
    private var mInitialTouchY: Int = 0
    private var mLastTouchX: Int = 0
    private var mLastTouchY: Int = 0

    private val mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop

    // Touch/scrolling handling
    private var mScrollState = RecyclerView.SCROLL_STATE_IDLE //当前的状态
    private val mChildHelper: NestedScrollingChildHelper by lazy {
        NestedScrollingChildHelper(this)
    }

    var onScrollListener: ((NestedWebView, l: Int, t: Int, oldl: Int, oldt: Int) -> Unit)? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            isNestedScrollingEnabled = true
        }
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        //return super.onTouchEvent(event)
//        var result = false
//        result = super.onTouchEvent(event)
//        if (BuildConfig.DEBUG) Log.e("TAG", "return $result")
//        return result
//    }

    fun onTouchEvent2(event: MotionEvent?): Boolean {
        event?.let { e ->
            val action = e.actionMasked
            if (action == MotionEvent.ACTION_DOWN) {
                mNestedOffsets[0] = 0
                mNestedOffsets[1] = 0
            }
            val vtev = MotionEvent.obtain(e)
            vtev.offsetLocation(mNestedOffsets[0].toFloat(), mNestedOffsets[1].toFloat())
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    // 得到未知
                    mInitialTouchX = ((e.x + 0.5f).toInt())
                    mLastTouchX = mInitialTouchX
                    mInitialTouchY = ((e.y + 0.5f).toInt())
                    mLastTouchY = mInitialTouchY

                    startNestedScroll(
                        ViewCompat.SCROLL_AXIS_NONE or ViewCompat.SCROLL_AXIS_VERTICAL,
                        TYPE_TOUCH
                    )
                }
                MotionEvent.ACTION_MOVE -> {
                    val x: Int = (e.x + 0.5f).toInt()
                    val y: Int = (e.y + 0.5f).toInt()
                    var dx: Int = mLastTouchX - x
                    var dy: Int = mLastTouchY - y

                    if (mScrollState != SCROLL_STATE_DRAGGING) { // 还不是拖拽状态
                        var startScroll = false
                        if (dy > 0) {
                            dy = Math.max(0, dy - mTouchSlop)
                        } else {
                            dy = Math.min(0, dy + mTouchSlop)
                        }
                        if (dy != 0) {
                            startScroll = true
                        }
                        if (startScroll) {
                            setScrollState(SCROLL_STATE_DRAGGING)
                        }
                    }
                    if (mScrollState == SCROLL_STATE_DRAGGING) { // 已经是拖拽状态了
                        mReusableIntPair[0] = 0
                        mReusableIntPair[1] = 0
                        if (dispatchNestedPreScroll(
                                0,
                                dy,
                                mReusableIntPair,
                                mScrollOffset,
                                TYPE_TOUCH
                            )
                        ) {
                            dx -= mReusableIntPair[0]
                            dy -= mReusableIntPair[1]
                            // Updated the nested offsets
                            mNestedOffsets[0] += mScrollOffset[0]
                            mNestedOffsets[1] += mScrollOffset[1]
                            // Scroll has initiated, prevent parents from intercepting
                            parent.requestDisallowInterceptTouchEvent(true)
                        }
                        mLastTouchX = x - mScrollOffset[0]
                        mLastTouchY = y - mScrollOffset[1]

                    }
                }
                MotionEvent.ACTION_UP -> {
                    stopNestedScroll(TYPE_TOUCH)
                }
            }

            vtev.recycle()
        }
        return true
    }

    /**
     * Return the current scrolling state of the RecyclerView.
     *
     * @return [.SCROLL_STATE_IDLE], [.SCROLL_STATE_DRAGGING] or
     * [.SCROLL_STATE_SETTLING]
     */
    fun getScrollState(): Int = mScrollState

    fun setScrollState(state: Int) {
        if (state == mScrollState) {
            return
        }
//        if (RecyclerView.DEBUG) {
//            Log.d(
//                RecyclerView.TAG, "setting scroll state to $state from $mScrollState",
//                Exception()
//            )
//        }
        mScrollState = state
//        if (state != RecyclerView.SCROLL_STATE_SETTLING) {
//            stopScrollersInternal()
//        }
//        dispatchOnScrollStateChanged(state)
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

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return mChildHelper.startNestedScroll(axes, type)
    }

    override fun startNestedScroll(axes: Int): Boolean {
        if (BuildConfig.DEBUG) Log.e(
            "NestedWebView",
            "startNestedScroll ->  $axes"
        )
        return mChildHelper.startNestedScroll(axes)
    }

    override fun stopNestedScroll(type: Int) {
        mChildHelper.stopNestedScroll(type)
    }

    override fun stopNestedScroll() {
        if (BuildConfig.DEBUG) Log.e(
            "NestedWebView",
            "stopNestedScroll"
        )
        mChildHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return mChildHelper.hasNestedScrollingParent(type)
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
        offsetInWindow: IntArray?,
        type: Int,
        consumed: IntArray
    ) {
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return mChildHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type
        )
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
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return mChildHelper.dispatchNestedPreScroll(
            dx,
            dy,
            consumed,
            offsetInWindow,
            type
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