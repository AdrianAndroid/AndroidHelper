package com.fannery.gradledownload

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/**
 * Time:2021/7/27 10:30
 * Author:
 * Description:
 */
class MYTextView(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {


//    fun test() {
//        context.getString()
//    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
    }
}