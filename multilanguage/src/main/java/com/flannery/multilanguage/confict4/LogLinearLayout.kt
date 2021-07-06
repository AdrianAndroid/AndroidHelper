package com.flannery.multilanguage.confict4

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.flannery.multilanguage.BuildConfig
import com.flannery.utils.K

/**
 * Time:2021/7/6 10:17
 * Author:
 * Description:
 */
class LogLinearLayout(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

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
                            "LogLinearLayout setOnScrollChangeListener scrollX=$scrollX scrollY=$scrollY oldScrollX=$oldScrollX oldScrollY=$oldScrollY v=$v"
                        )
                    }
                })
            }
        }
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (BuildConfig.DEBUG) K.m(javaClass, "onScrollChange l=$l t=$t oldl=$oldl oldt=$oldt")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (BuildConfig.DEBUG) K.m(javaClass, "onSizeChanged w=$w h=$h oldw=$oldw oldh=$oldh")
    }



}