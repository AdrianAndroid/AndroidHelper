package com.joyy.mone.unique.india

import android.graphics.Color
import android.widget.TextView
import com.joyy.mone.unique.SecondUnique

/**
 * Time:2021/9/22 17:59
 * Author: flannery
 * Description:
 */
class IndiaSecondUnique : SecondUnique() {

    override fun getText(): String {
        return "India"
    }

    override fun setTextColor(tv: TextView) {
        //super.setTextColor(tv)
        tv.setTextColor(Color.GREEN)
    }
}