package com.flannery.customview.touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout

class MyLayout(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return when (ev?.action) {
            MotionEvent.ACTION_DOWN -> false
            MotionEvent.ACTION_MOVE -> false //表示父类需要
            MotionEvent.ACTION_UP -> false
            else -> {
                Log.e("TestView", "父容器拦截")
                false // 如果设置拦截，除了down，其他都是父类处理
            }
        }
        //return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> Log.d("TAG", "You down layout")
            MotionEvent.ACTION_UP -> Log.d("TAG", "You up layout")
            MotionEvent.ACTION_MOVE -> Log.d("TAG", "You move layout")
        }
        return true
    }


}