package com.flannery.multilanguage.confict4

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import com.flannery.multilanguage.BuildConfig
import com.flannery.utils.K

/**
 * 适配RecyclerView滑动
 * Time:2021/7/1 19:10
 * Author:
 * Description:
 */
class NestedScrollLayout(context: Context, attrs: AttributeSet?) :
    NestedScrollView(context, attrs) {

    private var topView: View? = null // 只需要一个TopView
    private var tabView: View? = null //TabLayout
    private var contentView: View? = null //ViewPager
    private var alreadyMeasure = false
//    private val mFlingHelper: FlingHelper = FlingHelper(context) //滑动距离计算

    init {
        if (BuildConfig.DEBUG) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setOnScrollChangeListener(object : View.OnScrollChangeListener {
                    override fun onScrollChange(
                        v: View?,
                        scrollX: Int,
                        scrollY: Int,
                        oldScrollX: Int,
                        oldScrollY: Int
                    ) {
                        K.m(
                            javaClass,
                            "NestedScrollLayout setOnScrollChangeListener scrollX=$scrollX scrollY=$scrollY oldScrollX=$oldScrollX oldScrollY=$oldScrollY v=$v"
                        )
                    }
                })
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // 找不到，直接崩溃就好了
        (getChildAt(0) as? ViewGroup)?.run {
            topView = getChildAt(0)
            tabView = getChildAt(1)
            contentView = getChildAt(2)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //val size = MeasureSpec.getSize(heightMeasureSpec)
        // 设定一个初始化高度
        if (!alreadyMeasure) {
            contentView?.run {
                val lp = layoutParams
                lp.height = MeasureSpec.getSize(heightMeasureSpec) - (tabView?.measuredHeight ?: 0)
                if (BuildConfig.DEBUG) Log.e("TAG", "lp.height=${lp.height}")
                layoutParams = lp
            }
            alreadyMeasure = true
        }
    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(target, dx, dy, consumed, type)
        val b = dy > 0 && scrollY < (topView?.measuredHeight ?: 0)
        Log.e("TAG", "onNestedPreScroll  $b  dy = $dy")
        // 向上滑动。若当前topview可见，需要将topview滑动至不见
        if (b) {
            scrollBy(0, dy) // NestedScrollLayout滑动
            consumed[1] = dy //消费了多少
        }
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (BuildConfig.DEBUG) K.m(javaClass, "onScrollChanged l=$l t=$t oldl=$oldl oldt=$oldt")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (BuildConfig.DEBUG) K.m(javaClass, "onLayout changed:$changed l=$l t=$t r=$r b=$b")
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        if (BuildConfig.DEBUG) K.m(
            javaClass,
            "onOverScrolled scrollX=$scrollY scrollY=$scrollY clampedX=$clampedX clampedY=$clampedY"
        )
    }

}