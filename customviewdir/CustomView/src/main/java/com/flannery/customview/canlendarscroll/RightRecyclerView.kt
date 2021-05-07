package com.flannery.customview.canlendarscroll

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.flannery.customview.touchevent.L

class RightRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    public var editMode = false


    override fun onInterceptHoverEvent(event: MotionEvent?): Boolean {
        var onInterceptHoverEvent = super.onInterceptHoverEvent(event)
        L.m(javaClass, CanlenderScrollActivity::class.simpleName, onInterceptHoverEvent)

        return editMode
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {

        // 这里搞吗？
        event?.let { ev ->
            val findChildViewUnder = findChildViewUnder(ev.x, ev.y)
            findChildViewUnder?.setBackgroundColor(Color.GREEN)
        }

        if (editMode) {
            return editMode
        }
        val dispatchTouchEvent = super.dispatchTouchEvent(event)
        L.m(javaClass, CanlenderScrollActivity::class.simpleName, dispatchTouchEvent)
        return dispatchTouchEvent
    }


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        val onTouchEvent = super.onTouchEvent(ev)
        L.m(javaClass, CanlenderScrollActivity::class.simpleName, onTouchEvent)
        return onTouchEvent
    }

}