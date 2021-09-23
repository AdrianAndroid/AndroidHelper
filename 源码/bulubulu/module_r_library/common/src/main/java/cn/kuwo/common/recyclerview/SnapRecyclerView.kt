package cn.kuwo.common.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.animation.Interpolator
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.*
import cn.kuwo.common.BuildConfig

/**
 * 左右滑动的页面
 */
class SnapRecyclerView : RecyclerView {
    private val pageSnapHelper = MPageSnapHelper();
    private lateinit var scrollListener: OnScrollListener;

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    private var startScroll: Boolean = false

    private fun init(context: Context) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        initPageSnapHelper()

//        setOnTouchListener(object :OnTouchListener{
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//
//            }
//        })

        addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (BuildConfig.DEBUG) {
                    Log.i(
                        "SnapRecyclerView",
                        "addOnItemTouchListener onInterceptTouchEvent isSmoothScrolling = ${(layoutManager as LinearLayoutManager).isSmoothScrolling}"
                    )
                }

                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                if (BuildConfig.DEBUG) {
                    Log.i("SnapRecyclerView", "addOnItemTouchListener onTouchEvent")
                }
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                if (BuildConfig.DEBUG) {
                    Log.i(
                        "SnapRecyclerView",
                        "addOnItemTouchListener onRequestDisallowInterceptTouchEvent disallowIntercept -> $disallowIntercept  isSmoothScrolling = ${(layoutManager as LinearLayoutManager).isSmoothScrolling}"
                    )
                }
            }

        })

    }


    override fun onTouchEvent(e: MotionEvent?): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.onTouchEvent(e)
    }


    override fun isNestedScrollingEnabled(): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return false;//super.isNestedScrollingEnabled()
    }


    override fun setNestedScrollingEnabled(enabled: Boolean) {
        super.setNestedScrollingEnabled(enabled)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

//    private var startScroll: Boolean = false

    override fun startNestedScroll(axes: Int): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(
                javaClass.simpleName,
                "${Thread.currentThread().stackTrace[2].methodName} axes=$axes"
            )
        }
        startScroll = true
        return super.startNestedScroll(axes)
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(
                javaClass.simpleName,
                "${Thread.currentThread().stackTrace[2].methodName} axes=$axes type=$type"
            )
        }
//        startScroll = true
        return super.startNestedScroll(axes, type)
    }

    override fun stopNestedScroll() {
        super.stopNestedScroll()
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun stopNestedScroll(type: Int) {
        super.stopNestedScroll(type)
        if (BuildConfig.DEBUG) {
            Log.i(
                javaClass.simpleName,
                "${Thread.currentThread().stackTrace[2].methodName} -> type:$type"
            )
            //ViewCompat.TYPE_TOUCH 0
            //ViewCompat.TYPE_NON_TOUCH 1
        }
        if (type == ViewCompat.TYPE_NON_TOUCH) {
            startScroll = false
        }
    }

    private fun initPageSnapHelper() {
        pageSnapHelper.attachToRecyclerView(this)
        scrollListener = object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                var currentPage = -1

                super.onScrollStateChanged(recyclerView, newState)
                if (BuildConfig.DEBUG) {

                    Log.i(
                        javaClass.simpleName,
                        if (newState == SCROLL_STATE_IDLE) {
                            "onScrollStateChanged -> SCROLL_STATE_IDLE"
                        } else if (newState == SCROLL_STATE_DRAGGING) {
                            "onScrollStateChanged -> SCROLL_STATE_DRAGGING"
                        } else if (newState == SCROLL_STATE_SETTLING) {
                            "onScrollStateChanged -> SCROLL_STATE_SETTLING"
                        } else {
                            "onScrollStateChanged -> none"
                        }
                    )
                }


                if (newState == SCROLL_STATE_IDLE) {
                    // 如果滚动结束
                    val snapView = pageSnapHelper.findSnapView(layoutManager)
                    snapView?.let {
                        val currentPageIndex = layoutManager?.getPosition(it)
                        if (currentPage != currentPageIndex && currentPageIndex != null) { //防止重复提示
                            currentPage = currentPageIndex
                            //Toast.makeText(context, "当前是第 $currentPageIndex 页", Toast.LENGTH_SHORT)
                            //    .show()
                        }
                        if (BuildConfig.DEBUG) {
                            Log.i(javaClass.simpleName, "当前是第 $currentPageIndex 页")
                        }
                    }
                }
            }
        }
        addOnScrollListener(scrollListener)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeOnScrollListener(scrollListener)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    /*
        private void skipToNext() {
        if (recyclerView.canScrollHorizontally(1)) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.smoothScrollToPosition(linearLayoutManager.findFirstVisibleItemPosition() + 1); //跳转到下一个
        }
    }
     */

    /**
     * 跳转到下一页
     */
    public fun skipToNext() {
        if (canScrollHorizontally(1) && layoutManager is LinearLayoutManager) {
            val position = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1
            if (BuildConfig.DEBUG) {
                Log.i(
                    javaClass.simpleName,
                    "${Thread.currentThread().stackTrace[2].methodName} position $position"
                )
            }
            smoothScrollToPosition(position) //跳转到下一个
        }
    }


    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    private class MPageSnapHelper : PagerSnapHelper() {

        override fun onFling(velocityX: Int, velocityY: Int): Boolean {
            if (BuildConfig.DEBUG) {
                Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
            }
            return super.onFling(velocityX, velocityY)
        }

        override fun calculateScrollDistance(velocityX: Int, velocityY: Int): IntArray {
            if (BuildConfig.DEBUG) {
                Log.i(
                    javaClass.simpleName,
                    "${Thread.currentThread().stackTrace[2].methodName} velocityX=$velocityX"
                )
            }
            return super.calculateScrollDistance(velocityX, velocityY)
        }

        override fun createScroller(layoutManager: LayoutManager?): SmoothScroller? {
            if (BuildConfig.DEBUG) {
                Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
            }
            return super.createScroller(layoutManager)
        }

        override fun createSnapScroller(layoutManager: LayoutManager?): LinearSmoothScroller? {
            if (BuildConfig.DEBUG) {
                Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
            }
            return super.createSnapScroller(layoutManager)
        }

        override fun calculateDistanceToFinalSnap(
            layoutManager: LayoutManager,
            targetView: View
        ): IntArray? {
            val calculateDistanceToFinalSnap =
                super.calculateDistanceToFinalSnap(layoutManager, targetView)
            if (BuildConfig.DEBUG) {
                Log.i(
                    javaClass.simpleName,
                    "${Thread.currentThread().stackTrace[2].methodName} ${
                        calculateDistanceToFinalSnap?.joinToString(",")
                    }"
                )
            }
            return calculateDistanceToFinalSnap
        }

        override fun findSnapView(layoutManager: LayoutManager?): View? {
            return super.findSnapView(layoutManager)
        }

        override fun findTargetSnapPosition(
            layoutManager: LayoutManager?,
            velocityX: Int,
            velocityY: Int
        ): Int {
            return super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
        }
    }


    override fun sendAccessibilityEventUnchecked(event: AccessibilityEvent?) {
        super.sendAccessibilityEventUnchecked(event)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun isAttachedToWindow(): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.isAttachedToWindow()
    }

    override fun focusSearch(focused: View?, direction: Int): View {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.focusSearch(focused, direction)
    }

    override fun addFocusables(views: ArrayList<View>?, direction: Int, focusableMode: Int) {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        super.addFocusables(views, direction, focusableMode)
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.onGenericMotionEvent(event)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun scrollBy(x: Int, y: Int) {
        super.scrollBy(x, y)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun computeHorizontalScrollRange(): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.computeHorizontalScrollRange()
    }

    override fun computeHorizontalScrollOffset(): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.computeHorizontalScrollOffset()
    }

    override fun computeHorizontalScrollExtent(): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.computeHorizontalScrollExtent()
    }

    override fun computeVerticalScrollRange(): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.computeVerticalScrollRange()
    }

    override fun computeVerticalScrollOffset(): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.computeVerticalScrollOffset()
    }

    override fun computeVerticalScrollExtent(): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.computeVerticalScrollExtent()
    }

    override fun onDraw(c: Canvas?) {
        super.onDraw(c)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>?) {
        super.dispatchSaveInstanceState(container)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.onSaveInstanceState()
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>?) {
        super.dispatchRestoreInstanceState(container)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun draw(c: Canvas?) {
        super.draw(c)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun getBaseline(): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getBaseline()
    }

    override fun requestLayout() {
        super.requestLayout()
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }


    override fun hasNestedScrollingParent(): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.hasNestedScrollingParent()
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.hasNestedScrollingParent(type)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun requestChildFocus(child: View?, focused: View?) {
        super.requestChildFocus(child, focused)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun requestChildRectangleOnScreen(
        child: View?,
        rect: Rect?,
        immediate: Boolean
    ): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.requestChildRectangleOnScreen(child, rect, immediate)
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        val onInterceptTouchEvent = super.onInterceptTouchEvent(e)
        if (BuildConfig.DEBUG) {
            Log.i(
                javaClass.simpleName,
                "${Thread.currentThread().stackTrace[2].methodName} $onInterceptTouchEvent"
            )
        }
        Log.i(
            javaClass.simpleName,
            "onInterceptTouchEvent isSmoothScrolling = ${(layoutManager as LinearLayoutManager).isSmoothScrolling}"
        )
        return onInterceptTouchEvent && !startScroll//onInterceptTouchEvent
    }

    override fun onRequestFocusInDescendants(
        direction: Int,
        previouslyFocusedRect: Rect?
    ): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect)
    }

    override fun getChildDrawingOrder(childCount: Int, i: Int): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getChildDrawingOrder(childCount, i)
    }

    override fun drawChild(canvas: Canvas?, child: View?, drawingTime: Long): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.drawChild(canvas, child, drawingTime)
    }

    override fun setClipToPadding(clipToPadding: Boolean) {
        super.setClipToPadding(clipToPadding)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun getClipToPadding(): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getClipToPadding()
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.checkLayoutParams(p)
    }

    override fun removeDetachedView(child: View?, animate: Boolean) {
        super.removeDetachedView(child, animate)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.LayoutParams {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.generateLayoutParams(attrs)
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams?): ViewGroup.LayoutParams {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.generateLayoutParams(p)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.generateDefaultLayoutParams()
    }

    override fun getCompatAccessibilityDelegate(): RecyclerViewAccessibilityDelegate? {
        return super.getCompatAccessibilityDelegate()
    }

    override fun setAccessibilityDelegateCompat(accessibilityDelegate: RecyclerViewAccessibilityDelegate?) {
        super.setAccessibilityDelegateCompat(accessibilityDelegate)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun setHasFixedSize(hasFixedSize: Boolean) {
        super.setHasFixedSize(hasFixedSize)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun hasFixedSize(): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.hasFixedSize()
    }

    override fun setScrollingTouchSlop(slopConstant: Int) {
        super.setScrollingTouchSlop(slopConstant)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun swapAdapter(adapter: Adapter<*>?, removeAndRecycleExistingViews: Boolean) {
        super.swapAdapter(adapter, removeAndRecycleExistingViews)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun setRecyclerListener(listener: RecyclerListener?) {
        super.setRecyclerListener(listener)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun addOnChildAttachStateChangeListener(listener: OnChildAttachStateChangeListener) {
        super.addOnChildAttachStateChangeListener(listener)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun removeOnChildAttachStateChangeListener(listener: OnChildAttachStateChangeListener) {
        super.removeOnChildAttachStateChangeListener(listener)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun clearOnChildAttachStateChangeListeners() {
        super.clearOnChildAttachStateChangeListeners()
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun setOnFlingListener(onFlingListener: OnFlingListener?) {
        super.setOnFlingListener(onFlingListener)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun getOnFlingListener(): OnFlingListener? {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getOnFlingListener()
    }

    override fun getLayoutManager(): LayoutManager? {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getLayoutManager()
    }

    override fun getRecycledViewPool(): RecycledViewPool {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getRecycledViewPool()
    }

    override fun setRecycledViewPool(pool: RecycledViewPool?) {
        super.setRecycledViewPool(pool)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun setViewCacheExtension(extension: ViewCacheExtension?) {
        super.setViewCacheExtension(extension)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun setItemViewCacheSize(size: Int) {
        super.setItemViewCacheSize(size)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun getScrollState(): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getScrollState()
    }

    override fun addItemDecoration(decor: ItemDecoration, index: Int) {
        super.addItemDecoration(decor, index)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun addItemDecoration(decor: ItemDecoration) {
        super.addItemDecoration(decor)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun getItemDecorationAt(index: Int): ItemDecoration {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getItemDecorationAt(index)
    }

    override fun getItemDecorationCount(): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getItemDecorationCount()
    }

    override fun removeItemDecorationAt(index: Int) {
        super.removeItemDecorationAt(index)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun removeItemDecoration(decor: ItemDecoration) {
        super.removeItemDecoration(decor)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun setChildDrawingOrderCallback(childDrawingOrderCallback: ChildDrawingOrderCallback?) {
        super.setChildDrawingOrderCallback(childDrawingOrderCallback)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun setOnScrollListener(listener: OnScrollListener?) {
        super.setOnScrollListener(listener)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun addOnScrollListener(listener: OnScrollListener) {
        super.addOnScrollListener(listener)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun removeOnScrollListener(listener: OnScrollListener) {
        super.removeOnScrollListener(listener)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun clearOnScrollListeners() {
        super.clearOnScrollListeners()
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun scrollToPosition(position: Int) {
        super.scrollToPosition(position)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun smoothScrollToPosition(position: Int) {
        super.smoothScrollToPosition(position)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun setLayoutFrozen(frozen: Boolean) {
        super.setLayoutFrozen(frozen)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun isLayoutFrozen(): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.isLayoutFrozen()
    }

    override fun smoothScrollBy(dx: Int, dy: Int) {
        super.smoothScrollBy(dx, dy)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun smoothScrollBy(dx: Int, dy: Int, interpolator: Interpolator?) {
        super.smoothScrollBy(dx, dy, interpolator)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.fling(velocityX, velocityY)
    }

    override fun stopScroll() {
        super.stopScroll()
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun getMinFlingVelocity(): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getMinFlingVelocity()
    }

    override fun getMaxFlingVelocity(): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getMaxFlingVelocity()
    }

    override fun setEdgeEffectFactory(edgeEffectFactory: EdgeEffectFactory) {
        super.setEdgeEffectFactory(edgeEffectFactory)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun getEdgeEffectFactory(): EdgeEffectFactory {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getEdgeEffectFactory()
    }

    override fun addOnItemTouchListener(listener: OnItemTouchListener) {
        super.addOnItemTouchListener(listener)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun removeOnItemTouchListener(listener: OnItemTouchListener) {
        super.removeOnItemTouchListener(listener)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun setItemAnimator(animator: ItemAnimator?) {
        super.setItemAnimator(animator)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }


    override fun isComputingLayout(): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.isComputingLayout()
    }

    override fun getItemAnimator(): ItemAnimator? {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getItemAnimator()
    }

    override fun isAnimating(): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.isAnimating()
    }


    override fun invalidateItemDecorations() {
        super.invalidateItemDecorations()
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun getPreserveFocusAfterLayout(): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getPreserveFocusAfterLayout()
    }

    override fun setPreserveFocusAfterLayout(preserveFocusAfterLayout: Boolean) {
        super.setPreserveFocusAfterLayout(preserveFocusAfterLayout)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun getChildViewHolder(child: View): ViewHolder {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getChildViewHolder(child)
    }

    override fun findContainingItemView(view: View): View? {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.findContainingItemView(view)
    }

    override fun findContainingViewHolder(view: View): ViewHolder? {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.findContainingViewHolder(view)
    }

    override fun getChildPosition(child: View): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getChildPosition(child)
    }

    override fun getChildAdapterPosition(child: View): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getChildAdapterPosition(child)
    }

    override fun getChildLayoutPosition(child: View): Int {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getChildLayoutPosition(child)
    }

    override fun getChildItemId(child: View): Long {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.getChildItemId(child)
    }

    override fun findViewHolderForPosition(position: Int): ViewHolder? {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.findViewHolderForPosition(position)
    }

    override fun findViewHolderForLayoutPosition(position: Int): ViewHolder? {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.findViewHolderForLayoutPosition(position)
    }

    override fun findViewHolderForAdapterPosition(position: Int): ViewHolder? {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.findViewHolderForAdapterPosition(position)
    }

    override fun findViewHolderForItemId(id: Long): ViewHolder {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.findViewHolderForItemId(id)
    }

    override fun findChildViewUnder(x: Float, y: Float): View? {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.findChildViewUnder(x, y)
    }

    override fun offsetChildrenVertical(dy: Int) {
        super.offsetChildrenVertical(dy)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun onChildAttachedToWindow(child: View) {
        super.onChildAttachedToWindow(child)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun onChildDetachedFromWindow(child: View) {
        super.onChildDetachedFromWindow(child)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun offsetChildrenHorizontal(dx: Int) {
        super.offsetChildrenHorizontal(dx)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun getDecoratedBoundsWithMargins(view: View, outBounds: Rect) {
        super.getDecoratedBoundsWithMargins(view, outBounds)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }


    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
    }

    override fun hasPendingAdapterUpdates(): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i(javaClass.simpleName, Thread.currentThread().stackTrace[2].methodName)
        }
        return super.hasPendingAdapterUpdates()
    }


}