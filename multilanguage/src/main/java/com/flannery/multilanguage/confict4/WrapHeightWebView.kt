package com.flannery.multilanguage.confict4

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView
import androidx.viewpager.widget.ViewPager
import com.flannery.multilanguage.BuildConfig
import com.flannery.utils.K

/**
 * Time:2021/7/4 14:33
 * Author:
 * Description:
 */
class WrapHeightWebView(context: Context, attrs: AttributeSet?) : WebView(context, attrs) {

    init {
        if (BuildConfig.DEBUG) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setOnScrollChangeListener(object : OnScrollChangeListener {
                    override fun onScrollChange(
                        v: View?,
                        scrollX: Int,
                        scrollY: Int,
                        oldScrollX: Int,
                        oldScrollY: Int
                    ) {
                        K.m(
                            javaClass,
                            "WrapHeightWebView setOnScrollChangeListener scrollX=$scrollX scrollY=$scrollY oldScrollX=$oldScrollX oldScrollY=$oldScrollY v=$v"
                        )
                    }
                })
            }
        }
    }

    override fun requestLayout() {
        super.requestLayout()
    }
//    fun requestParentLayout() {
//        var p = parent
//        while (p != null) {
//            if (p is ViewPager) {
//                p.requestLayout() // 重新调用onMeasure
//                break
//            }
//            p = p.parent
//        }
//    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (BuildConfig.DEBUG) K.m(javaClass, "onScrollChanged l=$l t=$t oldl=$oldl oldt=$oldt")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (BuildConfig.DEBUG) K.m(javaClass, "onLayout changed:$changed l=$l t=$t r=$r b=$b")
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (BuildConfig.DEBUG) K.m(
            javaClass,
            "onMeasure widthSpec=$widthSpec heightSpec=$heightSpec"
        )
    }


    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        if (BuildConfig.DEBUG) K.m(
            javaClass,
            "onOverScrolled scrollX=$scrollY scrollY=$scrollY clampedX=$clampedX clampedY=$clampedY"
        )
    }

}