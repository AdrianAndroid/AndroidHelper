package com.joyy.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

/**
 * Time:2021/9/3 10:55
 * Author: flannery
 * Description:
 */
class JoyyToolBar(context: Context, attrs: AttributeSet?) : Toolbar(context, attrs) {

    init {
//       val lp = layoutParams
//        lp.setG

        for (i in 0..childCount) {
            val childAt = getChildAt(i)
            if (childAt is TextView) {
                val lp = Toolbar.LayoutParams(childAt.layoutParams)
                lp.gravity = Gravity.CENTER
                childAt.layoutParams = lp
                break
            }
        }
    }


}