package com.joyy.mone.unique.india

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.joyy.mone.unique.Fragment1Unique
import com.joyy.mone.unique.SecondUnique

/**
 * Time:2021/9/22 17:59
 * Author: flannery
 * Description:
 */
class IndiaFragment1Unique : Fragment1Unique() {

    override fun getText(): String {
        return "India"
    }

    override fun setTextColor(tv: TextView) {
        //super.setTextColor(tv)
        tv.setTextColor(Color.GREEN)
    }

    override fun imagesDisplay(): Int {
        return View.INVISIBLE
    }
}