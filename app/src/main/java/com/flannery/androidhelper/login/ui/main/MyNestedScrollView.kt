package cn.kuwo.pp.ui.login

import android.animation.LayoutTransition
import android.animation.StateListAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityNodeProvider
import android.view.animation.Animation
import android.view.animation.LayoutAnimationController
import android.view.animation.Transformation
import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.core.widget.NestedScrollView
import com.flannery.customview.touchevent.L
import java.util.ArrayList

class MyNestedScrollView(context: Context, attrs: AttributeSet?) :
    NestedScrollView(context, attrs) {
    val IDLE_DEBUG = false
    override fun invalidateDrawable(drawable: Drawable) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.invalidateDrawable(drawable)
    }

    override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.scheduleDrawable(who, what, `when`)
    }

    override fun unscheduleDrawable(who: Drawable, what: Runnable) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.unscheduleDrawable(who, what)
    }

    override fun unscheduleDrawable(who: Drawable?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.unscheduleDrawable(who)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onKeyLongPress(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onKeyUp(keyCode, event)
    }

    override fun onKeyMultiple(keyCode: Int, repeatCount: Int, event: KeyEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onKeyMultiple(keyCode, repeatCount, event)
    }

    override fun sendAccessibilityEvent(eventType: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.sendAccessibilityEvent(eventType)
    }

    override fun sendAccessibilityEventUnchecked(event: AccessibilityEvent?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.sendAccessibilityEventUnchecked(event)
    }

    override fun getVerticalFadingEdgeLength(): Int {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.getVerticalFadingEdgeLength()
    }

    override fun setFadingEdgeLength(length: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setFadingEdgeLength(length)
    }

    override fun getHorizontalFadingEdgeLength(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getHorizontalFadingEdgeLength()
    }

    override fun getVerticalScrollbarWidth(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getVerticalScrollbarWidth()
    }

    override fun getHorizontalScrollbarHeight(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getHorizontalScrollbarHeight()
    }

    override fun setVerticalScrollbarPosition(position: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setVerticalScrollbarPosition(position)
    }

    override fun getVerticalScrollbarPosition(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getVerticalScrollbarPosition()
    }

    override fun setScrollIndicators(indicators: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setScrollIndicators(indicators)
    }

    override fun setScrollIndicators(indicators: Int, mask: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setScrollIndicators(indicators, mask)
    }

    override fun getScrollIndicators(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getScrollIndicators()
    }

    override fun setOnScrollChangeListener(l: OnScrollChangeListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnScrollChangeListener(l)
    }

    override fun setOnScrollChangeListener(l: View.OnScrollChangeListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnScrollChangeListener(l)
    }

    override fun setOnFocusChangeListener(l: OnFocusChangeListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnFocusChangeListener(l)
    }

    override fun addOnLayoutChangeListener(listener: OnLayoutChangeListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addOnLayoutChangeListener(listener)
    }

    override fun removeOnLayoutChangeListener(listener: OnLayoutChangeListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.removeOnLayoutChangeListener(listener)
    }

    override fun addOnAttachStateChangeListener(listener: OnAttachStateChangeListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addOnAttachStateChangeListener(listener)
    }

    override fun removeOnAttachStateChangeListener(listener: OnAttachStateChangeListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.removeOnAttachStateChangeListener(listener)
    }

    override fun getOnFocusChangeListener(): OnFocusChangeListener {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getOnFocusChangeListener()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnClickListener(l)
    }

    override fun hasOnClickListeners(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.hasOnClickListeners()
    }

    override fun setOnLongClickListener(l: OnLongClickListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnLongClickListener(l)
    }

    override fun setOnContextClickListener(l: OnContextClickListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnContextClickListener(l)
    }

    override fun setOnCreateContextMenuListener(l: OnCreateContextMenuListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnCreateContextMenuListener(l)
    }

    override fun performClick(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.performClick()
    }

    override fun callOnClick(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.callOnClick()
    }

    override fun performLongClick(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.performLongClick()
    }

    override fun performLongClick(x: Float, y: Float): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.performLongClick(x, y)
    }

    override fun performContextClick(x: Float, y: Float): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.performContextClick(x, y)
    }

    override fun performContextClick(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.performContextClick()
    }

    override fun showContextMenu(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.showContextMenu()
    }

    override fun showContextMenu(x: Float, y: Float): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.showContextMenu(x, y)
    }

    override fun startActionMode(callback: ActionMode.Callback?): ActionMode {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.startActionMode(callback)
    }

    override fun startActionMode(callback: ActionMode.Callback?, type: Int): ActionMode {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.startActionMode(callback, type)
    }

    override fun setOnKeyListener(l: OnKeyListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnKeyListener(l)
    }

    override fun setOnTouchListener(l: OnTouchListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnTouchListener(l)
    }

    override fun setOnGenericMotionListener(l: OnGenericMotionListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnGenericMotionListener(l)
    }

    override fun setOnHoverListener(l: OnHoverListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnHoverListener(l)
    }

    override fun setOnDragListener(l: OnDragListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnDragListener(l)
    }

    override fun requestRectangleOnScreen(rectangle: Rect?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.requestRectangleOnScreen(rectangle)
    }

    override fun requestRectangleOnScreen(rectangle: Rect?, immediate: Boolean): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.requestRectangleOnScreen(rectangle, immediate)
    }

    override fun clearFocus() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.clearFocus()
    }

    override fun hasFocus(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.hasFocus()
    }

    override fun hasFocusable(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.hasFocusable()
    }

    override fun hasExplicitFocusable(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.hasExplicitFocusable()
    }

    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
    }

    override fun setAccessibilityPaneTitle(accessibilityPaneTitle: CharSequence?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setAccessibilityPaneTitle(accessibilityPaneTitle)
    }

    override fun getAccessibilityPaneTitle(): CharSequence? {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getAccessibilityPaneTitle()
    }

    override fun announceForAccessibility(text: CharSequence?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.announceForAccessibility(text)
    }

    override fun dispatchPopulateAccessibilityEvent(event: AccessibilityEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchPopulateAccessibilityEvent(event)
    }

    override fun onPopulateAccessibilityEvent(event: AccessibilityEvent?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onPopulateAccessibilityEvent(event)
    }

    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onInitializeAccessibilityEvent(event)
    }

    override fun createAccessibilityNodeInfo(): AccessibilityNodeInfo {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.createAccessibilityNodeInfo()
    }

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onInitializeAccessibilityNodeInfo(info)
    }

    override fun getAccessibilityClassName(): CharSequence {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getAccessibilityClassName()
    }

    override fun onProvideStructure(structure: ViewStructure?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onProvideStructure(structure)
    }

    override fun onProvideAutofillStructure(structure: ViewStructure?, flags: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onProvideAutofillStructure(structure, flags)
    }

    override fun onProvideVirtualStructure(structure: ViewStructure?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onProvideVirtualStructure(structure)
    }

    override fun onProvideAutofillVirtualStructure(structure: ViewStructure?, flags: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onProvideAutofillVirtualStructure(structure, flags)
    }

    override fun autofill(value: AutofillValue?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.autofill(value)
    }

    override fun autofill(values: SparseArray<AutofillValue>) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.autofill(values)
    }

    override fun setAutofillId(id: AutofillId?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setAutofillId(id)
    }

    override fun getAutofillType(): Int {
        val autofillType = super.getAutofillType()
        if (true) L.m(javaClass, "S=MyScroll", autofillType)
        return autofillType
    }

    override fun getAutofillHints(): Array<String>? {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getAutofillHints()
    }

    override fun getAutofillValue(): AutofillValue? {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getAutofillValue()
    }

    override fun getImportantForAutofill(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getImportantForAutofill()
    }

    override fun setImportantForAutofill(mode: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setImportantForAutofill(mode)
    }


    override fun dispatchProvideStructure(structure: ViewStructure?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchProvideStructure(structure)
    }

    override fun dispatchProvideAutofillStructure(structure: ViewStructure, flags: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchProvideAutofillStructure(structure, flags)
    }

    override fun addExtraDataToAccessibilityNodeInfo(
        info: AccessibilityNodeInfo,
        extraDataKey: String,
        arguments: Bundle?
    ) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addExtraDataToAccessibilityNodeInfo(info, extraDataKey, arguments)
    }

    override fun isVisibleToUserForAutofill(virtualId: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isVisibleToUserForAutofill(virtualId)
    }


    override fun setAccessibilityDelegate(delegate: View.AccessibilityDelegate?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setAccessibilityDelegate(delegate)
    }

    override fun getAccessibilityNodeProvider(): AccessibilityNodeProvider {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getAccessibilityNodeProvider()
    }

    override fun setContentDescription(contentDescription: CharSequence?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setContentDescription(contentDescription)
    }

    override fun setAccessibilityTraversalBefore(beforeId: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setAccessibilityTraversalBefore(beforeId)
    }

    override fun getAccessibilityTraversalBefore(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getAccessibilityTraversalBefore()
    }

    override fun setAccessibilityTraversalAfter(afterId: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setAccessibilityTraversalAfter(afterId)
    }

    override fun getAccessibilityTraversalAfter(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getAccessibilityTraversalAfter()
    }

    override fun getLabelFor(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getLabelFor()
    }

    override fun setLabelFor(id: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setLabelFor(id)
    }

    override fun isFocused(): Boolean {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.isFocused()
    }

//    override fun findFocus(): View {
//        if(true) L.m(javaClass, "S=MyScroll")
//        return super.findFocus()
//    }

    override fun isScrollContainer(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isScrollContainer()
    }

    override fun setScrollContainer(isScrollContainer: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setScrollContainer(isScrollContainer)
    }

    override fun getDrawingCacheQuality(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getDrawingCacheQuality()
    }

    override fun setDrawingCacheQuality(quality: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setDrawingCacheQuality(quality)
    }

    override fun getKeepScreenOn(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getKeepScreenOn()
    }

    override fun setKeepScreenOn(keepScreenOn: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setKeepScreenOn(keepScreenOn)
    }

    override fun getNextFocusLeftId(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getNextFocusLeftId()
    }

    override fun setNextFocusLeftId(nextFocusLeftId: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setNextFocusLeftId(nextFocusLeftId)
    }

    override fun getNextFocusRightId(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getNextFocusRightId()
    }

    override fun setNextFocusRightId(nextFocusRightId: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setNextFocusRightId(nextFocusRightId)
    }

    override fun getNextFocusUpId(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getNextFocusUpId()
    }

    override fun setNextFocusUpId(nextFocusUpId: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setNextFocusUpId(nextFocusUpId)
    }

    override fun getNextFocusDownId(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getNextFocusDownId()
    }

    override fun setNextFocusDownId(nextFocusDownId: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setNextFocusDownId(nextFocusDownId)
    }

    override fun getNextFocusForwardId(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getNextFocusForwardId()
    }

    override fun setNextFocusForwardId(nextFocusForwardId: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setNextFocusForwardId(nextFocusForwardId)
    }

    override fun getNextClusterForwardId(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getNextClusterForwardId()
    }

    override fun setNextClusterForwardId(nextClusterForwardId: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setNextClusterForwardId(nextClusterForwardId)
    }

    override fun isShown(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isShown()
    }

    override fun fitSystemWindows(insets: Rect?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.fitSystemWindows(insets)
    }

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onApplyWindowInsets(insets)
    }

    override fun setOnApplyWindowInsetsListener(listener: OnApplyWindowInsetsListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnApplyWindowInsetsListener(listener)
    }

    override fun dispatchApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchApplyWindowInsets(insets)
    }

    override fun getRootWindowInsets(): WindowInsets {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getRootWindowInsets()
    }

    override fun computeSystemWindowInsets(
        `in`: WindowInsets?,
        outLocalInsets: Rect?
    ): WindowInsets {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.computeSystemWindowInsets(`in`, outLocalInsets)
    }

    override fun setFitsSystemWindows(fitSystemWindows: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setFitsSystemWindows(fitSystemWindows)
    }

    override fun getFitsSystemWindows(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getFitsSystemWindows()
    }

    override fun requestFitSystemWindows() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.requestFitSystemWindows()
    }

    override fun requestApplyInsets() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.requestApplyInsets()
    }

    override fun getVisibility(): Int {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.getVisibility()
    }

    override fun setVisibility(visibility: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setVisibility(visibility)
    }

    override fun isEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isEnabled()
    }

    override fun setEnabled(enabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setEnabled(enabled)
    }

    override fun setFocusable(focusable: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setFocusable(focusable)
    }

    override fun setFocusable(focusable: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setFocusable(focusable)
    }

    override fun setFocusableInTouchMode(focusableInTouchMode: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setFocusableInTouchMode(focusableInTouchMode)
    }

    override fun setAutofillHints(vararg autofillHints: String?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setAutofillHints(*autofillHints)
    }

    override fun setSoundEffectsEnabled(soundEffectsEnabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setSoundEffectsEnabled(soundEffectsEnabled)
    }

    override fun isSoundEffectsEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isSoundEffectsEnabled()
    }

    override fun setHapticFeedbackEnabled(hapticFeedbackEnabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setHapticFeedbackEnabled(hapticFeedbackEnabled)
    }

    override fun isHapticFeedbackEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isHapticFeedbackEnabled()
    }

    override fun setLayoutDirection(layoutDirection: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setLayoutDirection(layoutDirection)
    }

    override fun getLayoutDirection(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getLayoutDirection()
    }

    override fun hasTransientState(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.hasTransientState()
    }

    override fun setHasTransientState(hasTransientState: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setHasTransientState(hasTransientState)
    }

    override fun isAttachedToWindow(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isAttachedToWindow()
    }

    override fun isLaidOut(): Boolean {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.isLaidOut()
    }

    override fun setWillNotDraw(willNotDraw: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setWillNotDraw(willNotDraw)
    }

    override fun willNotDraw(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.willNotDraw()
    }

    override fun setWillNotCacheDrawing(willNotCacheDrawing: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setWillNotCacheDrawing(willNotCacheDrawing)
    }

    override fun willNotCacheDrawing(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.willNotCacheDrawing()
    }

    override fun isClickable(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isClickable()
    }

    override fun setClickable(clickable: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setClickable(clickable)
    }

    override fun isLongClickable(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isLongClickable()
    }

    override fun setLongClickable(longClickable: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setLongClickable(longClickable)
    }

    override fun isContextClickable(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isContextClickable()
    }

    override fun setContextClickable(contextClickable: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setContextClickable(contextClickable)
    }

    override fun setPressed(pressed: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setPressed(pressed)
    }

    override fun dispatchSetPressed(pressed: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchSetPressed(pressed)
    }

    override fun isPressed(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isPressed()
    }

    override fun isSaveEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isSaveEnabled()
    }

    override fun setSaveEnabled(enabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setSaveEnabled(enabled)
    }

    override fun getFilterTouchesWhenObscured(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getFilterTouchesWhenObscured()
    }

    override fun setFilterTouchesWhenObscured(enabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setFilterTouchesWhenObscured(enabled)
    }

    override fun isSaveFromParentEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isSaveFromParentEnabled()
    }

    override fun setSaveFromParentEnabled(enabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setSaveFromParentEnabled(enabled)
    }

    override fun getFocusable(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getFocusable()
    }

    override fun isScreenReaderFocusable(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isScreenReaderFocusable()
    }

    override fun setScreenReaderFocusable(screenReaderFocusable: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setScreenReaderFocusable(screenReaderFocusable)
    }

    override fun isAccessibilityHeading(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isAccessibilityHeading()
    }

    override fun setAccessibilityHeading(isHeading: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setAccessibilityHeading(isHeading)
    }

//    override fun focusSearch(focused: View?, direction: Int): View {
//        if (true) L.m(javaClass, "S=MyScroll")
//        return super.focusSearch(focused, direction)
//    }

    override fun focusSearch(direction: Int): View {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.focusSearch(direction)
    }

    override fun setKeyboardNavigationCluster(isCluster: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setKeyboardNavigationCluster(isCluster)
    }

    override fun setFocusedByDefault(isFocusedByDefault: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setFocusedByDefault(isFocusedByDefault)
    }

    override fun keyboardNavigationClusterSearch(currentCluster: View?, direction: Int): View {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.keyboardNavigationClusterSearch(currentCluster, direction)
    }

    override fun dispatchUnhandledMove(focused: View?, direction: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchUnhandledMove(focused, direction)
    }

    override fun setDefaultFocusHighlightEnabled(defaultFocusHighlightEnabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setDefaultFocusHighlightEnabled(defaultFocusHighlightEnabled)
    }

    override fun getFocusables(direction: Int): ArrayList<View> {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getFocusables(direction)
    }

    override fun addFocusables(views: ArrayList<View>?, direction: Int, focusableMode: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addFocusables(views, direction, focusableMode)
    }

    override fun addFocusables(views: ArrayList<View>?, direction: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addFocusables(views, direction)
    }

    override fun addKeyboardNavigationClusters(views: MutableCollection<View>, direction: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addKeyboardNavigationClusters(views, direction)
    }

    override fun findViewsWithText(outViews: ArrayList<View>?, text: CharSequence?, flags: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.findViewsWithText(outViews, text, flags)
    }

    override fun getTouchables(): ArrayList<View> {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTouchables()
    }

    override fun addTouchables(views: ArrayList<View>?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addTouchables(views)
    }

    override fun isAccessibilityFocused(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isAccessibilityFocused()
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.requestFocus(direction, previouslyFocusedRect)
    }

    override fun restoreDefaultFocus(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.restoreDefaultFocus()
    }

    override fun getImportantForAccessibility(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getImportantForAccessibility()
    }

    override fun setAccessibilityLiveRegion(mode: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setAccessibilityLiveRegion(mode)
    }

    override fun getAccessibilityLiveRegion(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getAccessibilityLiveRegion()
    }

    override fun setImportantForAccessibility(mode: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setImportantForAccessibility(mode)
    }

    override fun isImportantForAccessibility(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isImportantForAccessibility()
    }

    override fun getParentForAccessibility(): ViewParent {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getParentForAccessibility()
    }

    override fun addChildrenForAccessibility(outChildren: ArrayList<View>?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addChildrenForAccessibility(outChildren)
    }

    override fun dispatchNestedPrePerformAccessibilityAction(
        action: Int,
        arguments: Bundle?
    ): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchNestedPrePerformAccessibilityAction(action, arguments)
    }

    override fun performAccessibilityAction(action: Int, arguments: Bundle?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.performAccessibilityAction(action, arguments)
    }

    override fun dispatchStartTemporaryDetach() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchStartTemporaryDetach()
    }

    override fun onStartTemporaryDetach() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onStartTemporaryDetach()
    }

    override fun dispatchFinishTemporaryDetach() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchFinishTemporaryDetach()
    }

    override fun onFinishTemporaryDetach() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onFinishTemporaryDetach()
    }

    override fun getKeyDispatcherState(): KeyEvent.DispatcherState {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getKeyDispatcherState()
    }

    override fun dispatchKeyEventPreIme(event: KeyEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchKeyEventPreIme(event)
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchKeyEvent(event)
    }

    override fun dispatchKeyShortcutEvent(event: KeyEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchKeyShortcutEvent(event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.dispatchTouchEvent(ev)
    }

    override fun onFilterTouchEventForSecurity(event: MotionEvent?): Boolean {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.onFilterTouchEventForSecurity(event)
    }

    override fun dispatchTrackballEvent(event: MotionEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchTrackballEvent(event)
    }

    override fun dispatchCapturedPointerEvent(event: MotionEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchCapturedPointerEvent(event)
    }

    override fun dispatchGenericMotionEvent(event: MotionEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchGenericMotionEvent(event)
    }

    override fun dispatchHoverEvent(event: MotionEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchHoverEvent(event)
    }

    override fun dispatchGenericPointerEvent(event: MotionEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchGenericPointerEvent(event)
    }

    override fun dispatchGenericFocusedEvent(event: MotionEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchGenericFocusedEvent(event)
    }

    override fun dispatchWindowFocusChanged(hasFocus: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchWindowFocusChanged(hasFocus)
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onWindowFocusChanged(hasWindowFocus)
    }

    override fun hasWindowFocus(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.hasWindowFocus()
    }

    override fun dispatchVisibilityChanged(changedView: View, visibility: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchVisibilityChanged(changedView, visibility)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onVisibilityChanged(changedView, visibility)
    }

    override fun dispatchDisplayHint(hint: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchDisplayHint(hint)
    }

    override fun onDisplayHint(hint: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onDisplayHint(hint)
    }

    override fun dispatchWindowVisibilityChanged(visibility: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchWindowVisibilityChanged(visibility)
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onWindowVisibilityChanged(visibility)
    }

    override fun onVisibilityAggregated(isVisible: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onVisibilityAggregated(isVisible)
    }

    override fun getWindowVisibility(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getWindowVisibility()
    }

    override fun getWindowVisibleDisplayFrame(outRect: Rect?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.getWindowVisibleDisplayFrame(outRect)
    }

    override fun dispatchConfigurationChanged(newConfig: Configuration?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchConfigurationChanged(newConfig)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onConfigurationChanged(newConfig)
    }

    override fun isInTouchMode(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isInTouchMode()
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onKeyPreIme(keyCode, event)
    }

    override fun onKeyShortcut(keyCode: Int, event: KeyEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onKeyShortcut(keyCode, event)
    }

    override fun onCheckIsTextEditor(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onCheckIsTextEditor()
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onCreateInputConnection(outAttrs)
    }

    override fun checkInputConnectionProxy(view: View?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.checkInputConnectionProxy(view)
    }

    override fun createContextMenu(menu: ContextMenu?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.createContextMenu(menu)
    }

    override fun getContextMenuInfo(): ContextMenu.ContextMenuInfo {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getContextMenuInfo()
    }

    override fun onCreateContextMenu(menu: ContextMenu?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onCreateContextMenu(menu)
    }

    override fun onTrackballEvent(event: MotionEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onTrackballEvent(event)
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onGenericMotionEvent(event)
    }

    override fun onHoverEvent(event: MotionEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onHoverEvent(event)
    }

    override fun isHovered(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isHovered()
    }

    override fun setHovered(hovered: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setHovered(hovered)
    }

    override fun onHoverChanged(hovered: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onHoverChanged(hovered)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onTouchEvent(ev)
    }

    override fun cancelLongPress() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.cancelLongPress()
    }

    override fun setTouchDelegate(delegate: TouchDelegate?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setTouchDelegate(delegate)
    }

    override fun getTouchDelegate(): TouchDelegate {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTouchDelegate()
    }

    override fun bringToFront() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.bringToFront()
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onScrollChanged(l, t, oldl, oldt)
    }

//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        if(true) L.m(javaClass, "S=MyScroll")
//        super.onSizeChanged(w, h, oldw, oldh)
//    }

    override fun dispatchDraw(canvas: Canvas?) {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        super.dispatchDraw(canvas)
    }

    override fun setScrollX(value: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setScrollX(value)
    }

    override fun setScrollY(value: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setScrollY(value)
    }

    override fun getDrawingRect(outRect: Rect?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.getDrawingRect(outRect)
    }

    override fun getMatrix(): Matrix {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.getMatrix()
    }

    override fun getCameraDistance(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getCameraDistance()
    }

    override fun setCameraDistance(distance: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setCameraDistance(distance)
    }

    override fun getRotation(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getRotation()
    }

    override fun setRotation(rotation: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setRotation(rotation)
    }

    override fun getRotationY(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getRotationY()
    }

    override fun setRotationY(rotationY: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setRotationY(rotationY)
    }

    override fun getRotationX(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getRotationX()
    }

    override fun setRotationX(rotationX: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setRotationX(rotationX)
    }

    override fun getScaleX(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getScaleX()
    }

    override fun setScaleX(scaleX: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setScaleX(scaleX)
    }

    override fun getScaleY(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getScaleY()
    }

    override fun setScaleY(scaleY: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setScaleY(scaleY)
    }

    override fun getPivotX(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getPivotX()
    }

    override fun setPivotX(pivotX: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setPivotX(pivotX)
    }

    override fun getPivotY(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getPivotY()
    }

    override fun setPivotY(pivotY: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setPivotY(pivotY)
    }

    override fun isPivotSet(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isPivotSet()
    }

    override fun resetPivot() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.resetPivot()
    }

    override fun getAlpha(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getAlpha()
    }

    override fun forceHasOverlappingRendering(hasOverlappingRendering: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.forceHasOverlappingRendering(hasOverlappingRendering)
    }

    override fun hasOverlappingRendering(): Boolean {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.hasOverlappingRendering()
    }

    override fun setAlpha(alpha: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setAlpha(alpha)
    }

    override fun isDirty(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isDirty()
    }

    override fun getX(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getX()
    }

    override fun setX(x: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setX(x)
    }

    override fun getY(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getY()
    }

    override fun setY(y: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setY(y)
    }

    override fun getZ(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getZ()
    }

    override fun setZ(z: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setZ(z)
    }

    override fun getElevation(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getElevation()
    }

    override fun setElevation(elevation: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setElevation(elevation)
    }

    override fun getTranslationX(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTranslationX()
    }

    override fun setTranslationX(translationX: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setTranslationX(translationX)
    }

    override fun getTranslationY(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTranslationY()
    }

    override fun setTranslationY(translationY: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setTranslationY(translationY)
    }

    override fun getTranslationZ(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTranslationZ()
    }

    override fun setTranslationZ(translationZ: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setTranslationZ(translationZ)
    }


    override fun getStateListAnimator(): StateListAnimator {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getStateListAnimator()
    }

    override fun setStateListAnimator(stateListAnimator: StateListAnimator?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setStateListAnimator(stateListAnimator)
    }

    override fun setClipToOutline(clipToOutline: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setClipToOutline(clipToOutline)
    }

    override fun setOutlineProvider(provider: ViewOutlineProvider?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOutlineProvider(provider)
    }

    override fun getOutlineProvider(): ViewOutlineProvider {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getOutlineProvider()
    }

    override fun invalidateOutline() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.invalidateOutline()
    }

    override fun setOutlineSpotShadowColor(color: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOutlineSpotShadowColor(color)
    }

    override fun getOutlineSpotShadowColor(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getOutlineSpotShadowColor()
    }

    override fun setOutlineAmbientShadowColor(color: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOutlineAmbientShadowColor(color)
    }

    override fun getOutlineAmbientShadowColor(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getOutlineAmbientShadowColor()
    }

    override fun getHitRect(outRect: Rect?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.getHitRect(outRect)
    }

    override fun getFocusedRect(r: Rect?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.getFocusedRect(r)
    }

    override fun getGlobalVisibleRect(r: Rect?, globalOffset: Point?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getGlobalVisibleRect(r, globalOffset)
    }

    override fun offsetTopAndBottom(offset: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.offsetTopAndBottom(offset)
    }

    override fun offsetLeftAndRight(offset: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.offsetLeftAndRight(offset)
    }

    override fun getLayoutParams(): ViewGroup.LayoutParams {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.getLayoutParams()
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setLayoutParams(params)
    }

    override fun scrollTo(x: Int, y: Int) {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        super.scrollTo(x, y)
    }

    override fun scrollBy(x: Int, y: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.scrollBy(x, y)
    }

    override fun awakenScrollBars(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.awakenScrollBars()
    }

    override fun awakenScrollBars(startDelay: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.awakenScrollBars(startDelay)
    }

    override fun awakenScrollBars(startDelay: Int, invalidate: Boolean): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.awakenScrollBars(startDelay, invalidate)
    }

    override fun invalidate(dirty: Rect?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.invalidate(dirty)
    }

    override fun invalidate(l: Int, t: Int, r: Int, b: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.invalidate(l, t, r, b)
    }

    override fun invalidate() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.invalidate()
    }

    override fun isOpaque(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isOpaque()
    }

    override fun getHandler(): Handler {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getHandler()
    }

    override fun post(action: Runnable?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.post(action)
    }

    override fun postDelayed(action: Runnable?, delayMillis: Long): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.postDelayed(action, delayMillis)
    }

    override fun postOnAnimation(action: Runnable?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.postOnAnimation(action)
    }

    override fun postOnAnimationDelayed(action: Runnable?, delayMillis: Long) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.postOnAnimationDelayed(action, delayMillis)
    }

    override fun removeCallbacks(action: Runnable?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.removeCallbacks(action)
    }

    override fun postInvalidate() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.postInvalidate()
    }

    override fun postInvalidate(left: Int, top: Int, right: Int, bottom: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.postInvalidate(left, top, right, bottom)
    }

    override fun postInvalidateDelayed(delayMilliseconds: Long) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.postInvalidateDelayed(delayMilliseconds)
    }

    override fun postInvalidateDelayed(
        delayMilliseconds: Long,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.postInvalidateDelayed(delayMilliseconds, left, top, right, bottom)
    }

    override fun postInvalidateOnAnimation() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.postInvalidateOnAnimation()
    }

    override fun postInvalidateOnAnimation(left: Int, top: Int, right: Int, bottom: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.postInvalidateOnAnimation(left, top, right, bottom)
    }

    override fun computeScroll() {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        super.computeScroll()
    }

    override fun isHorizontalFadingEdgeEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isHorizontalFadingEdgeEnabled()
    }

    override fun setHorizontalFadingEdgeEnabled(horizontalFadingEdgeEnabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setHorizontalFadingEdgeEnabled(horizontalFadingEdgeEnabled)
    }

    override fun isVerticalFadingEdgeEnabled(): Boolean {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.isVerticalFadingEdgeEnabled()
    }

    override fun setVerticalFadingEdgeEnabled(verticalFadingEdgeEnabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setVerticalFadingEdgeEnabled(verticalFadingEdgeEnabled)
    }

    override fun getTopFadingEdgeStrength(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTopFadingEdgeStrength()
    }

    override fun getBottomFadingEdgeStrength(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getBottomFadingEdgeStrength()
    }

    override fun getLeftFadingEdgeStrength(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getLeftFadingEdgeStrength()
    }

    override fun getRightFadingEdgeStrength(): Float {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getRightFadingEdgeStrength()
    }

    override fun isHorizontalScrollBarEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isHorizontalScrollBarEnabled()
    }

    override fun setHorizontalScrollBarEnabled(horizontalScrollBarEnabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled)
    }

    override fun isVerticalScrollBarEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isVerticalScrollBarEnabled()
    }

    override fun setVerticalScrollBarEnabled(verticalScrollBarEnabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setVerticalScrollBarEnabled(verticalScrollBarEnabled)
    }

    override fun setScrollbarFadingEnabled(fadeScrollbars: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setScrollbarFadingEnabled(fadeScrollbars)
    }

    override fun isScrollbarFadingEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isScrollbarFadingEnabled()
    }

    override fun getScrollBarDefaultDelayBeforeFade(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getScrollBarDefaultDelayBeforeFade()
    }

    override fun setScrollBarDefaultDelayBeforeFade(scrollBarDefaultDelayBeforeFade: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setScrollBarDefaultDelayBeforeFade(scrollBarDefaultDelayBeforeFade)
    }

    override fun getScrollBarFadeDuration(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getScrollBarFadeDuration()
    }

    override fun setScrollBarFadeDuration(scrollBarFadeDuration: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setScrollBarFadeDuration(scrollBarFadeDuration)
    }

    override fun getScrollBarSize(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getScrollBarSize()
    }

    override fun setScrollBarSize(scrollBarSize: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setScrollBarSize(scrollBarSize)
    }

    override fun setScrollBarStyle(style: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setScrollBarStyle(style)
    }

    override fun getScrollBarStyle(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getScrollBarStyle()
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.canScrollHorizontally(direction)
    }

    override fun canScrollVertically(direction: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.canScrollVertically(direction)
    }

    override fun onDraw(canvas: Canvas?) {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        super.onDraw(canvas)
    }

    override fun onAttachedToWindow() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onAttachedToWindow()
    }

    override fun onScreenStateChanged(screenState: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onScreenStateChanged(screenState)
    }

    override fun onRtlPropertiesChanged(layoutDirection: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onRtlPropertiesChanged(layoutDirection)
    }

    override fun canResolveLayoutDirection(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.canResolveLayoutDirection()
    }

    override fun isLayoutDirectionResolved(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isLayoutDirectionResolved()
    }

    override fun onDetachedFromWindow() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onDetachedFromWindow()
    }

    override fun getWindowAttachCount(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getWindowAttachCount()
    }

    override fun getWindowToken(): IBinder {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getWindowToken()
    }

    override fun getWindowId(): WindowId {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getWindowId()
    }

    override fun getApplicationWindowToken(): IBinder {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getApplicationWindowToken()
    }

    override fun getDisplay(): Display {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getDisplay()
    }

    override fun onCancelPendingInputEvents() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onCancelPendingInputEvents()
    }

    override fun saveHierarchyState(container: SparseArray<Parcelable>?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.saveHierarchyState(container)
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchSaveInstanceState(container)
    }

    override fun onSaveInstanceState(): Parcelable? {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onSaveInstanceState()
    }

    override fun restoreHierarchyState(container: SparseArray<Parcelable>?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.restoreHierarchyState(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchRestoreInstanceState(container)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onRestoreInstanceState(state)
    }

    override fun getDrawingTime(): Long {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.getDrawingTime()
    }

    override fun setDuplicateParentStateEnabled(enabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setDuplicateParentStateEnabled(enabled)
    }

    override fun isDuplicateParentStateEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isDuplicateParentStateEnabled()
    }

    override fun setLayerType(layerType: Int, paint: Paint?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setLayerType(layerType, paint)
    }

    override fun setLayerPaint(paint: Paint?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setLayerPaint(paint)
    }

    override fun getLayerType(): Int {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.getLayerType()
    }

    override fun buildLayer() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.buildLayer()
    }

    override fun setDrawingCacheEnabled(enabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setDrawingCacheEnabled(enabled)
    }

    override fun isDrawingCacheEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isDrawingCacheEnabled()
    }

    override fun getDrawingCache(): Bitmap {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getDrawingCache()
    }

    override fun getDrawingCache(autoScale: Boolean): Bitmap {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getDrawingCache(autoScale)
    }

    override fun destroyDrawingCache() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.destroyDrawingCache()
    }

    override fun setDrawingCacheBackgroundColor(color: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setDrawingCacheBackgroundColor(color)
    }

    override fun getDrawingCacheBackgroundColor(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getDrawingCacheBackgroundColor()
    }

    override fun buildDrawingCache() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.buildDrawingCache()
    }

    override fun buildDrawingCache(autoScale: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.buildDrawingCache(autoScale)
    }

    override fun isInEditMode(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isInEditMode()
    }

    override fun isPaddingOffsetRequired(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isPaddingOffsetRequired()
    }

    override fun getLeftPaddingOffset(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getLeftPaddingOffset()
    }

    override fun getRightPaddingOffset(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getRightPaddingOffset()
    }

    override fun getTopPaddingOffset(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTopPaddingOffset()
    }

    override fun getBottomPaddingOffset(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getBottomPaddingOffset()
    }

    override fun isHardwareAccelerated(): Boolean {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.isHardwareAccelerated()
    }

    override fun setClipBounds(clipBounds: Rect?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setClipBounds(clipBounds)
    }

    override fun getClipBounds(): Rect {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getClipBounds()
    }

    override fun getClipBounds(outRect: Rect?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getClipBounds(outRect)
    }

    override fun draw(canvas: Canvas?) {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        super.draw(canvas)
    }

    override fun getOverlay(): ViewGroupOverlay {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getOverlay()
    }

    override fun getSolidColor(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getSolidColor()
    }

    override fun isLayoutRequested(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isLayoutRequested()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        super.onLayout(changed, l, t, r, b)
    }

    override fun onFinishInflate() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onFinishInflate()
    }

    override fun getResources(): Resources {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getResources()
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.verifyDrawable(who)
    }

    override fun drawableStateChanged() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.drawableStateChanged()
    }

    override fun drawableHotspotChanged(x: Float, y: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.drawableHotspotChanged(x, y)
    }

    override fun dispatchDrawableHotspotChanged(x: Float, y: Float) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchDrawableHotspotChanged(x, y)
    }

    override fun refreshDrawableState() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.refreshDrawableState()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onCreateDrawableState(extraSpace)
    }

    override fun jumpDrawablesToCurrentState() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.jumpDrawablesToCurrentState()
    }

    override fun setBackgroundColor(color: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setBackgroundColor(color)
    }

    override fun setBackgroundResource(resid: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setBackgroundResource(resid)
    }

    override fun setBackground(background: Drawable?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setBackground(background)
    }

    override fun setBackgroundDrawable(background: Drawable?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setBackgroundDrawable(background)
    }

//    override fun getBackground(): Drawable {
//        if(true) L.m(javaClass, "S=MyScroll")
//        return super.getBackground()
//    }

    override fun setBackgroundTintList(tint: ColorStateList?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setBackgroundTintList(tint)
    }

    override fun getBackgroundTintList(): ColorStateList? {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getBackgroundTintList()
    }

    override fun setBackgroundTintMode(tintMode: PorterDuff.Mode?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setBackgroundTintMode(tintMode)
    }


    override fun getBackgroundTintMode(): PorterDuff.Mode? {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getBackgroundTintMode()
    }


//    override fun getForeground(): Drawable {
//        if(true) L.m(javaClass, "S=MyScroll")
//        return super.getForeground()
//    }

    override fun setForeground(foreground: Drawable?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setForeground(foreground)
    }

    override fun getForegroundGravity(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getForegroundGravity()
    }

    override fun setForegroundGravity(foregroundGravity: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setForegroundGravity(foregroundGravity)
    }

    override fun setForegroundTintList(tint: ColorStateList?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setForegroundTintList(tint)
    }

    override fun getForegroundTintList(): ColorStateList? {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getForegroundTintList()
    }

    override fun setForegroundTintMode(tintMode: PorterDuff.Mode?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setForegroundTintMode(tintMode)
    }

    override fun getForegroundTintMode(): PorterDuff.Mode? {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getForegroundTintMode()
    }


    override fun onDrawForeground(canvas: Canvas?) {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        super.onDrawForeground(canvas)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setPadding(left, top, right, bottom)
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setPaddingRelative(start, top, end, bottom)
    }

    override fun getPaddingTop(): Int {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.getPaddingTop()
    }

    override fun getPaddingBottom(): Int {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.getPaddingBottom()
    }

    override fun getPaddingLeft(): Int {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.getPaddingLeft()
    }

    override fun getPaddingStart(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getPaddingStart()
    }

    override fun getPaddingRight(): Int {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.getPaddingRight()
    }

    override fun getPaddingEnd(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getPaddingEnd()
    }

    override fun isPaddingRelative(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isPaddingRelative()
    }

    override fun setSelected(selected: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setSelected(selected)
    }

    override fun dispatchSetSelected(selected: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchSetSelected(selected)
    }

    override fun isSelected(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isSelected()
    }

    override fun setActivated(activated: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setActivated(activated)
    }

    override fun dispatchSetActivated(activated: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchSetActivated(activated)
    }

    override fun isActivated(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isActivated()
    }

    override fun getViewTreeObserver(): ViewTreeObserver {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getViewTreeObserver()
    }

    override fun getRootView(): View {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getRootView()
    }

    override fun getLocationOnScreen(outLocation: IntArray?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.getLocationOnScreen(outLocation)
    }

    override fun getLocationInWindow(outLocation: IntArray?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.getLocationInWindow(outLocation)
    }

    override fun setId(id: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setId(id)
    }

    override fun getId(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getId()
    }


    override fun getTag(): Any {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTag()
    }

    override fun getTag(key: Int): Any {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTag(key)
    }

    override fun setTag(tag: Any?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setTag(tag)
    }

    override fun setTag(key: Int, tag: Any?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setTag(key, tag)
    }

    override fun getBaseline(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getBaseline()
    }

    override fun isInLayout(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isInLayout()
    }

    override fun requestLayout() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.requestLayout()
    }

    override fun forceLayout() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.forceLayout()
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        if(true) L.m(javaClass, "S=MyScroll")
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//    }

    override fun getSuggestedMinimumHeight(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getSuggestedMinimumHeight()
    }

    override fun getSuggestedMinimumWidth(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getSuggestedMinimumWidth()
    }

    override fun getMinimumHeight(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getMinimumHeight()
    }

    override fun setMinimumHeight(minHeight: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setMinimumHeight(minHeight)
    }

    override fun getMinimumWidth(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getMinimumWidth()
    }

    override fun setMinimumWidth(minWidth: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setMinimumWidth(minWidth)
    }


    override fun startAnimation(animation: Animation?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.startAnimation(animation)
    }

    override fun clearAnimation() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.clearAnimation()
    }

    override fun setAnimation(animation: Animation?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setAnimation(animation)
    }

    override fun onAnimationStart() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onAnimationStart()
    }

    override fun onAnimationEnd() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onAnimationEnd()
    }

    override fun onSetAlpha(alpha: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onSetAlpha(alpha)
    }

    override fun playSoundEffect(soundConstant: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.playSoundEffect(soundConstant)
    }

    override fun performHapticFeedback(feedbackConstant: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.performHapticFeedback(feedbackConstant)
    }

    override fun performHapticFeedback(feedbackConstant: Int, flags: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.performHapticFeedback(feedbackConstant, flags)
    }

    override fun setSystemUiVisibility(visibility: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setSystemUiVisibility(visibility)
    }

    override fun getSystemUiVisibility(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getSystemUiVisibility()
    }

    override fun getWindowSystemUiVisibility(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getWindowSystemUiVisibility()
    }

    override fun onWindowSystemUiVisibilityChanged(visible: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onWindowSystemUiVisibilityChanged(visible)
    }

    override fun dispatchWindowSystemUiVisiblityChanged(visible: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchWindowSystemUiVisiblityChanged(visible)
    }

    override fun setOnSystemUiVisibilityChangeListener(l: OnSystemUiVisibilityChangeListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnSystemUiVisibilityChangeListener(l)
    }

    override fun dispatchSystemUiVisibilityChanged(visible: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchSystemUiVisibilityChanged(visible)
    }

    override fun onDragEvent(event: DragEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onDragEvent(event)
    }

    override fun dispatchDragEvent(event: DragEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchDragEvent(event)
    }

    override fun overScrollBy(
        deltaX: Int,
        deltaY: Int,
        scrollX: Int,
        scrollY: Int,
        scrollRangeX: Int,
        scrollRangeY: Int,
        maxOverScrollX: Int,
        maxOverScrollY: Int,
        isTouchEvent: Boolean
    ): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.overScrollBy(
            deltaX,
            deltaY,
            scrollX,
            scrollY,
            scrollRangeX,
            scrollRangeY,
            maxOverScrollX,
            maxOverScrollY,
            isTouchEvent
        )
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
    }

    override fun getOverScrollMode(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getOverScrollMode()
    }

    override fun setOverScrollMode(overScrollMode: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOverScrollMode(overScrollMode)
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setNestedScrollingEnabled(enabled)
    }

    override fun isNestedScrollingEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isNestedScrollingEnabled()
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        val startNestedScroll = super.startNestedScroll(axes, type)
        if (true) L.m(
            javaClass,
            "S=MyScroll",
            "axes",
            axes,
            "type",
            type,
            "startNestedScroll",
            startNestedScroll
        )
        return startNestedScroll
    }

    override fun startNestedScroll(axes: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.startNestedScroll(axes)
    }

    override fun stopNestedScroll(type: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.stopNestedScroll(type)
    }

    override fun stopNestedScroll() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.hasNestedScrollingParent(type)
    }

    override fun hasNestedScrollingParent(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int,
        consumed: IntArray
    ) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type,
            consumed
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
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun setTextDirection(textDirection: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setTextDirection(textDirection)
    }

    override fun getTextDirection(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTextDirection()
    }

    override fun canResolveTextDirection(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.canResolveTextDirection()
    }

    override fun isTextDirectionResolved(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isTextDirectionResolved()
    }

    override fun setTextAlignment(textAlignment: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setTextAlignment(textAlignment)
    }

    override fun getTextAlignment(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTextAlignment()
    }

    override fun canResolveTextAlignment(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.canResolveTextAlignment()
    }

    override fun isTextAlignmentResolved(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isTextAlignmentResolved()
    }

    override fun onResolvePointerIcon(event: MotionEvent?, pointerIndex: Int): PointerIcon {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onResolvePointerIcon(event, pointerIndex)
    }

    override fun setPointerIcon(pointerIcon: PointerIcon?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setPointerIcon(pointerIcon)
    }

    override fun getPointerIcon(): PointerIcon {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getPointerIcon()
    }

    override fun hasPointerCapture(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.hasPointerCapture()
    }

    override fun requestPointerCapture() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.requestPointerCapture()
    }

    override fun releasePointerCapture() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.releasePointerCapture()
    }

    override fun onPointerCaptureChange(hasCapture: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onPointerCaptureChange(hasCapture)
    }

    override fun dispatchPointerCaptureChanged(hasCapture: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchPointerCaptureChanged(hasCapture)
    }

    override fun onCapturedPointerEvent(event: MotionEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onCapturedPointerEvent(event)
    }

    override fun setOnCapturedPointerListener(l: OnCapturedPointerListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnCapturedPointerListener(l)
    }

    override fun animate(): ViewPropertyAnimator {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.animate()
    }

    override fun getTransitionName(): String {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTransitionName()
    }

    override fun setTooltipText(tooltipText: CharSequence?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setTooltipText(tooltipText)
    }

    override fun getTooltipText(): CharSequence? {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTooltipText()
    }

    override fun addOnUnhandledKeyEventListener(listener: OnUnhandledKeyEventListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addOnUnhandledKeyEventListener(listener)
    }

    override fun removeOnUnhandledKeyEventListener(listener: OnUnhandledKeyEventListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.removeOnUnhandledKeyEventListener(listener)
    }

    override fun requestTransparentRegion(child: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.requestTransparentRegion(child)
    }

    override fun onDescendantInvalidated(child: View, target: View) {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        super.onDescendantInvalidated(child, target)
    }

    override fun invalidateChildInParent(location: IntArray?, dirty: Rect?): ViewParent {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.invalidateChildInParent(location, dirty)
    }

    override fun requestChildFocus(child: View?, focused: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.requestChildFocus(child, focused)
    }

    override fun recomputeViewAttributes(child: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.recomputeViewAttributes(child)
    }

    override fun clearChildFocus(child: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.clearChildFocus(child)
    }

    override fun getChildVisibleRect(child: View?, r: Rect?, offset: Point?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getChildVisibleRect(child, r, offset)
    }

    override fun bringChildToFront(child: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.bringChildToFront(child)
    }

    override fun focusableViewAvailable(v: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.focusableViewAvailable(v)
    }

    override fun showContextMenuForChild(originalView: View?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.showContextMenuForChild(originalView)
    }

    override fun showContextMenuForChild(originalView: View?, x: Float, y: Float): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.showContextMenuForChild(originalView, x, y)
    }

    override fun startActionModeForChild(
        originalView: View?,
        callback: ActionMode.Callback?
    ): ActionMode {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.startActionModeForChild(originalView, callback)
    }

    override fun startActionModeForChild(
        originalView: View?,
        callback: ActionMode.Callback?,
        type: Int
    ): ActionMode {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.startActionModeForChild(originalView, callback, type)
    }

    override fun childDrawableStateChanged(child: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.childDrawableStateChanged(child)
    }

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
    }

    override fun requestChildRectangleOnScreen(
        child: View?,
        rectangle: Rect?,
        immediate: Boolean
    ): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.requestChildRectangleOnScreen(child, rectangle, immediate)
    }

    override fun requestSendAccessibilityEvent(child: View?, event: AccessibilityEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.requestSendAccessibilityEvent(child, event)
    }

    override fun childHasTransientStateChanged(child: View?, childHasTransientState: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.childHasTransientStateChanged(child, childHasTransientState)
    }

    override fun notifySubtreeAccessibilityStateChanged(
        child: View?,
        source: View,
        changeType: Int
    ) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.notifySubtreeAccessibilityStateChanged(child, source, changeType)
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onStartNestedScroll(child, target, axes, type)
    }

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onStartNestedScroll(child, target, nestedScrollAxes)
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onNestedScrollAccepted(child: View, target: View, nestedScrollAxes: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onNestedScrollAccepted(child, target, nestedScrollAxes)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onStopNestedScroll(target, type)
    }

    override fun onStopNestedScroll(target: View) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onStopNestedScroll(target)
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onNestedScroll(
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed
        )
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onNestedPreScroll(target, dx, dy, consumed, type)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onNestedPreScroll(target, dx, dy, consumed)
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onNestedFling(target, velocityX, velocityY, consumed)
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onNestedPreFling(target, velocityX, velocityY)
    }

    override fun onNestedPrePerformAccessibilityAction(
        target: View?,
        action: Int,
        args: Bundle?
    ): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onNestedPrePerformAccessibilityAction(target, action, args)
    }

    override fun addView(child: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addView(child)
    }

    override fun addView(child: View?, index: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addView(child, index)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addView(child, params)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addView(child, index, params)
    }

    override fun addView(child: View?, width: Int, height: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.addView(child, width, height)
    }

    override fun updateViewLayout(view: View?, params: ViewGroup.LayoutParams?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.updateViewLayout(view, params)
    }

    override fun removeView(view: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.removeView(view)
    }

    override fun getDescendantFocusability(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getDescendantFocusability()
    }

    override fun setDescendantFocusability(focusability: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setDescendantFocusability(focusability)
    }

    override fun onRequestSendAccessibilityEvent(
        child: View?,
        event: AccessibilityEvent?
    ): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onRequestSendAccessibilityEvent(child, event)
    }

    override fun getFocusedChild(): View {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getFocusedChild()
    }

    override fun setTouchscreenBlocksFocus(touchscreenBlocksFocus: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setTouchscreenBlocksFocus(touchscreenBlocksFocus)
    }

    override fun getTouchscreenBlocksFocus(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getTouchscreenBlocksFocus()
    }

    override fun onInterceptHoverEvent(event: MotionEvent?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onInterceptHoverEvent(event)
    }

    override fun setMotionEventSplittingEnabled(split: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setMotionEventSplittingEnabled(split)
    }

    override fun isMotionEventSplittingEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isMotionEventSplittingEnabled()
    }

    override fun isTransitionGroup(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isTransitionGroup()
    }

    override fun setTransitionGroup(isTransitionGroup: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setTransitionGroup(isTransitionGroup)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.onInterceptTouchEvent(ev)
    }

    override fun onRequestFocusInDescendants(
        direction: Int,
        previouslyFocusedRect: Rect?
    ): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect)
    }

    override fun dispatchFreezeSelfOnly(container: SparseArray<Parcelable>?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchFreezeSelfOnly(container)
    }

    override fun dispatchThawSelfOnly(container: SparseArray<Parcelable>?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.dispatchThawSelfOnly(container)
    }

    override fun setChildrenDrawingCacheEnabled(enabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setChildrenDrawingCacheEnabled(enabled)
    }

    override fun getChildDrawingOrder(childCount: Int, drawingPosition: Int): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getChildDrawingOrder(childCount, drawingPosition)
    }

    override fun drawChild(canvas: Canvas?, child: View?, drawingTime: Long): Boolean {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.drawChild(canvas, child, drawingTime)
    }

    override fun getClipChildren(): Boolean {
        val clipChildren = super.getClipChildren()
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll", clipChildren)
        return clipChildren
    }

    override fun setClipChildren(clipChildren: Boolean) {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        super.setClipChildren(clipChildren)
    }

    override fun setClipToPadding(clipToPadding: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setClipToPadding(clipToPadding)
    }

    override fun getClipToPadding(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getClipToPadding()
    }

    override fun setStaticTransformationsEnabled(enabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setStaticTransformationsEnabled(enabled)
    }

    override fun getChildStaticTransformation(child: View?, t: Transformation?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getChildStaticTransformation(child, t)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.checkLayoutParams(p)
    }

    override fun setOnHierarchyChangeListener(listener: OnHierarchyChangeListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setOnHierarchyChangeListener(listener)
    }

    override fun onViewAdded(child: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onViewAdded(child)
    }

    override fun onViewRemoved(child: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.onViewRemoved(child)
    }

    override fun addViewInLayout(
        child: View?,
        index: Int,
        params: ViewGroup.LayoutParams?
    ): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.addViewInLayout(child, index, params)
    }

    override fun addViewInLayout(
        child: View?,
        index: Int,
        params: ViewGroup.LayoutParams?,
        preventRequestLayout: Boolean
    ): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.addViewInLayout(child, index, params, preventRequestLayout)
    }

    override fun cleanupLayoutState(child: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.cleanupLayoutState(child)
    }

    override fun attachLayoutAnimationParameters(
        child: View?,
        params: ViewGroup.LayoutParams?,
        index: Int,
        count: Int
    ) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.attachLayoutAnimationParameters(child, params, index, count)
    }

    override fun removeViewInLayout(view: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.removeViewInLayout(view)
    }

    override fun removeViewsInLayout(start: Int, count: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.removeViewsInLayout(start, count)
    }

    override fun removeViewAt(index: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.removeViewAt(index)
    }

    override fun removeViews(start: Int, count: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.removeViews(start, count)
    }

    override fun setLayoutTransition(transition: LayoutTransition?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setLayoutTransition(transition)
    }

    override fun getLayoutTransition(): LayoutTransition {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getLayoutTransition()
    }

    override fun removeAllViews() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.removeAllViews()
    }

    override fun removeAllViewsInLayout() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.removeAllViewsInLayout()
    }

    override fun removeDetachedView(child: View?, animate: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.removeDetachedView(child, animate)
    }

    override fun attachViewToParent(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.attachViewToParent(child, index, params)
    }

    override fun detachViewFromParent(child: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.detachViewFromParent(child)
    }

    override fun detachViewFromParent(index: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.detachViewFromParent(index)
    }

    override fun detachViewsFromParent(start: Int, count: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.detachViewsFromParent(start, count)
    }

    override fun detachAllViewsFromParent() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.detachAllViewsFromParent()
    }

    override fun canAnimate(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.canAnimate()
    }

    override fun startLayoutAnimation() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.startLayoutAnimation()
    }

    override fun scheduleLayoutAnimation() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.scheduleLayoutAnimation()
    }

    override fun setLayoutAnimation(controller: LayoutAnimationController?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setLayoutAnimation(controller)
    }

    override fun getLayoutAnimation(): LayoutAnimationController {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getLayoutAnimation()
    }

    override fun isChildrenDrawingOrderEnabled(): Boolean {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.isChildrenDrawingOrderEnabled()
    }

    override fun setChildrenDrawingOrderEnabled(enabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setChildrenDrawingOrderEnabled(enabled)
    }


    override fun getLayoutMode(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getLayoutMode()
    }

    override fun setLayoutMode(layoutMode: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setLayoutMode(layoutMode)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.generateLayoutParams(attrs)
    }

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?): ViewGroup.LayoutParams {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.generateLayoutParams(lp)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.generateDefaultLayoutParams()
    }

    override fun debug(depth: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.debug(depth)
    }

    override fun indexOfChild(child: View?): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.indexOfChild(child)
    }

    override fun getChildCount(): Int {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.getChildCount()
    }

    override fun getChildAt(index: Int): View {
        if (IDLE_DEBUG) L.m(javaClass, "S=MyScroll")
        return super.getChildAt(index)
    }

    override fun measureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.measureChildren(widthMeasureSpec, heightMeasureSpec)
    }

    override fun measureChild(
        child: View?,
        parentWidthMeasureSpec: Int,
        parentHeightMeasureSpec: Int
    ) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec)
    }

    override fun measureChildWithMargins(
        child: View?,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int
    ) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.measureChildWithMargins(
            child,
            parentWidthMeasureSpec,
            widthUsed,
            parentHeightMeasureSpec,
            heightUsed
        )
    }

    override fun clearDisappearingChildren() {
        if (true) L.m(javaClass, "S=MyScroll")
        super.clearDisappearingChildren()
    }

    override fun startViewTransition(view: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.startViewTransition(view)
    }

    override fun endViewTransition(view: View?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.endViewTransition(view)
    }

    override fun gatherTransparentRegion(region: Region?): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.gatherTransparentRegion(region)
    }

    override fun getLayoutAnimationListener(): Animation.AnimationListener {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getLayoutAnimationListener()
    }

    override fun setAddStatesFromChildren(addsStates: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setAddStatesFromChildren(addsStates)
    }

    override fun addStatesFromChildren(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.addStatesFromChildren()
    }

    override fun setLayoutAnimationListener(animationListener: Animation.AnimationListener?) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setLayoutAnimationListener(animationListener)
    }

    override fun shouldDelayChildPressedState(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.shouldDelayChildPressedState()
    }

    override fun getNestedScrollAxes(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getNestedScrollAxes()
    }

    override fun setMeasureAllChildren(measureAll: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setMeasureAllChildren(measureAll)
    }

    override fun getMeasureAllChildren(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getMeasureAllChildren()
    }

    override fun getMaxScrollAmount(): Int {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.getMaxScrollAmount()
    }

    override fun isFillViewport(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isFillViewport()
    }

    override fun setFillViewport(fillViewport: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setFillViewport(fillViewport)
    }

    override fun isSmoothScrollingEnabled(): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.isSmoothScrollingEnabled()
    }

    override fun setSmoothScrollingEnabled(smoothScrollingEnabled: Boolean) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.setSmoothScrollingEnabled(smoothScrollingEnabled)
    }

    override fun executeKeyEvent(event: KeyEvent): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.executeKeyEvent(event)
    }


    override fun pageScroll(direction: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.pageScroll(direction)
    }

    override fun fullScroll(direction: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.fullScroll(direction)
    }

    override fun arrowScroll(direction: Int): Boolean {
        if (true) L.m(javaClass, "S=MyScroll")
        return super.arrowScroll(direction)
    }


    override fun computeScrollDeltaToGetChildRectOnScreen(rect: Rect?): Int {
        if (true) L.m(javaClass, "S=MyScroll", rect)
        return super.computeScrollDeltaToGetChildRectOnScreen(rect)
    }

    override fun fling(velocityY: Int) {
        if (true) L.m(javaClass, "S=MyScroll")
        super.fling(velocityY)
    }
}