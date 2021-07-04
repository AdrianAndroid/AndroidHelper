package com.flannery.multilanguage.confict4

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import androidx.viewpager.widget.ViewPager

/**
 * Time:2021/7/4 14:33
 * Author:
 * Description:
 */
class WrapHeightWebView(context: Context, attrs: AttributeSet?) : WebView(context, attrs) {


    fun requestParentLayout() {
        var p = parent
        while (p != null) {
            if (p is ViewPager) {
                p.requestLayout() // 重新调用onMeasure
                break
            }
            p = p.parent
        }
    }

}