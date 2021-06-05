package com.flannery.customview.touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.Button

class MyButton(context: Context?, attrs: AttributeSet?) : Button(context, attrs) {

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> Log.d("TAG", "You down button")
            MotionEvent.ACTION_UP -> Log.d("TAG", "You up button")
            MotionEvent.ACTION_MOVE -> Log.d("TAG", "You move button")
        }
        return false;
    }

}