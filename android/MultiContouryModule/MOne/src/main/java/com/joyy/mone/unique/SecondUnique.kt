package com.joyy.mone.unique

import android.widget.TextView
import com.joyy.base.Unique

/**
 * Time:2021/9/22 17:50
 * Author: flannery
 * Description:
 */
open class SecondUnique : Unique {
    open fun getText(): String {
        return "Tint:显示文字"
    }

    open fun setTextColor(tv: TextView) {
        //tv.setTextColor(Color.)
        // 下边设置文字
    }
}