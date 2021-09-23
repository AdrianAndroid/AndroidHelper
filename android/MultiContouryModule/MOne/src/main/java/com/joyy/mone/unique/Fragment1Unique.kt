package com.joyy.mone.unique

import android.view.View
import android.widget.TextView
import com.joyy.base.Unique

/**
 * Time:2021/9/23 10:24
 * Author: flannery
 * Description:
 */
open class Fragment1Unique : Unique {
    open fun getText(): String {
        return "Tint:显示文字"
    }

    open fun setTextColor(tv: TextView) {
        //tv.setTextColor(Color.)
        // 下边设置文字
    }

    // 是否显示这些图片
    open fun imagesDisplay(): Int {
        return View.VISIBLE
    }
}