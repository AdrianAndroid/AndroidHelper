package com.joyy.mone.unique.china

import android.graphics.Color
import android.widget.TextView
import com.joyy.mone.unique.SecondUnique

/**
 * Time:2021/9/22 17:50
 * Author: flannery
 * Description:
 */
class ChinaSecondUnique : SecondUnique() {
    override fun getText(): String {
        return "China"
    }

    override fun setTextColor(tv: TextView) {
        //super.setTextColor(tv)
        tv.setTextColor(Color.RED)
    }
}