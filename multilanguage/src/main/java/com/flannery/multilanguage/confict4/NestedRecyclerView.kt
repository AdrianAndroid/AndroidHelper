package com.flannery.multilanguage.confict4

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.flannery.multilanguage.BuildConfig
import com.flannery.utils.K

/**
 * Time:2021/7/1 19:51
 * Author:
 * Description:
 */
class NestedRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

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
                            "NestedRecyclerView setOnScrollChangeListener scrollX=$scrollX scrollY=$scrollY oldScrollX=$oldScrollX oldScrollY=$oldScrollY v=$v"
                        )
                    }
                })
            }
        }
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        Log.e("RecyclerViewNestedLog", "setNestedScrollingEnabled $enabled")
        super.setNestedScrollingEnabled(enabled)
    }

    override fun isNestedScrollingEnabled(): Boolean {
        Log.e("RecyclerViewNestedLog", "isNestedScrollingEnabled")
        return super.isNestedScrollingEnabled()
    }

    override fun startNestedScroll(axes: Int): Boolean {
        Log.e("RecyclerViewNestedLog", "startNestedScroll")
        return super.startNestedScroll(axes)
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        Log.e("RecyclerViewNestedLog", "startNestedScroll")
        return super.startNestedScroll(axes, type)
    }

    override fun stopNestedScroll() {
        Log.e("RecyclerViewNestedLog", "stopNestedScroll")
        super.stopNestedScroll()
    }

    override fun stopNestedScroll(type: Int) {
        Log.e("RecyclerViewNestedLog", "stopNestedScroll")
        super.stopNestedScroll(type)
    }

    override fun hasNestedScrollingParent(): Boolean {
        Log.e("RecyclerViewNestedLog", "hasNestedScrollingParent")
        return super.hasNestedScrollingParent()
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        Log.e("RecyclerViewNestedLog", "hasNestedScrollingParent")
        return super.hasNestedScrollingParent(type)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
        dyUnconsumed: Int, offsetInWindow: IntArray?
    ): Boolean {
        Log.e("RecyclerViewNestedLog", "dispatchNestedScroll")
        return super.dispatchNestedScroll(
            dxConsumed, dyConsumed,
            dxUnconsumed, dyUnconsumed, offsetInWindow
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
        dyUnconsumed: Int, offsetInWindow: IntArray?, type: Int
    ): Boolean {
        Log.e("RecyclerViewNestedLog", "dispatchNestedScroll")
        return super.dispatchNestedScroll(
            dxConsumed, dyConsumed,
            dxUnconsumed, dyUnconsumed, offsetInWindow, type
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        Log.e("RecyclerViewNestedLog", "dispatchNestedPreScroll")
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedPreScroll(
        dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        Log.e("RecyclerViewNestedLog", "dispatchNestedPreScroll")
        return super.dispatchNestedPreScroll(
            dx, dy, consumed, offsetInWindow,
            type
        )
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        Log.e("RecyclerViewNestedLog", "dispatchNestedFling")
        return super.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        Log.e("RecyclerViewNestedLog", "dispatchNestedPreFling")
        return super.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun onNestedPreScroll(target: View?, dx: Int, dy: Int, consumed: IntArray?) {
        super.onNestedPreScroll(target, dx, dy, consumed)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (BuildConfig.DEBUG) K.m(javaClass, "onScrollChanged l=$l t=$t oldl=$oldl oldt=$oldt")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (BuildConfig.DEBUG) K.m(javaClass, "onLayout changed:$changed l=$l t=$t r=$r b=$b")
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (BuildConfig.DEBUG) K.m(
            javaClass,
            "onMeasure widthSpec=$widthSpec heightSpec=$heightSpec"
        )
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (BuildConfig.DEBUG) K.m(javaClass, "onScrollStateChanged state=$state")
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        if (BuildConfig.DEBUG) K.m(javaClass, "onScrolled dx=$dx dy=$dy")
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        if (BuildConfig.DEBUG) K.m(
            javaClass,
            "onOverScrolled scrollX=$scrollY scrollY=$scrollY clampedX=$clampedX clampedY=$clampedY"
        )
    }


}