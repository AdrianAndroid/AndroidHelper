package com.joyy.mone.unique.china

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.joyy.mone.unique.Fragment1Unique

/**
 * Time:2021/9/23 10:24
 * Author: flannery
 * Description:
 */
class ChinaFragment1Unique : Fragment1Unique() {

    override fun getText(): String {
        return "China"
    }

    override fun setTextColor(tv: TextView) {
        //super.setTextColor(tv)
        tv.setTextColor(Color.RED)
    }

    override fun imagesDisplay(): Int {
        return View.VISIBLE
    }
}