package com.flannery.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * 放在中间位置的TextView
 */
class CenterTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 测量宽高
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        // 布局位置
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 绘制中心文字
    }
}