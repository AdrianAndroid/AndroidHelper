package com.flannery.customview.canlendarscroll

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.flannery.customview.touchevent.L

class LeftRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {


    override fun onInterceptHoverEvent(event: MotionEvent?): Boolean {
        val onInterceptHoverEvent = super.onInterceptHoverEvent(event)
        L.m(javaClass, CanlenderScrollActivity::class.simpleName, onInterceptHoverEvent)
        return onInterceptHoverEvent
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val dispatchTouchEvent = super.dispatchTouchEvent(ev)
        L.m(javaClass, CanlenderScrollActivity::class.simpleName, dispatchTouchEvent)
        return dispatchTouchEvent
    }


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        val onTouchEvent = super.onTouchEvent(ev)
        L.m(javaClass, CanlenderScrollActivity::class.simpleName, onTouchEvent)
        return onTouchEvent
    }


}