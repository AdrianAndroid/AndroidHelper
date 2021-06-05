package com.flannery.func_test.view

import android.animation.StateListAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.*
import android.text.*
import android.text.method.KeyListener
import android.text.method.MovementMethod
import android.text.style.URLSpan
import android.util.AttributeSet
import android.util.SparseArray
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityNodeProvider
import android.view.animation.Animation
import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import android.view.inputmethod.*
import android.view.textclassifier.TextClassifier
import android.widget.Scroller
import cn.kuwo.common.app.App
import cn.kuwo.common.util.L
import java.util.*

class MyEditText(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatEditText(context, attrs) {

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.requestFocus(direction, previouslyFocusedRect)
    }

    override fun clearFocus() {
        super.clearFocus()
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setFocusable(focusable: Boolean) {
        super.setFocusable(focusable)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setFocusable(focusable: Int) {
        super.setFocusable(focusable)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setFocusableInTouchMode(focusableInTouchMode: Boolean) {
        super.setFocusableInTouchMode(focusableInTouchMode)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setFocusedByDefault(isFocusedByDefault: Boolean) {
        super.setFocusedByDefault(isFocusedByDefault)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }


    override fun equals(other: Any?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.equals(other)
    }

    override fun hashCode(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.hashCode()
    }


    override fun invalidateDrawable(drawable: Drawable) {
        super.invalidateDrawable(drawable)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
        super.scheduleDrawable(who, what, `when`)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun unscheduleDrawable(who: Drawable, what: Runnable) {
        super.unscheduleDrawable(who, what)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun unscheduleDrawable(who: Drawable?) {
        super.unscheduleDrawable(who)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onKeyLongPress(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onKeyUp(keyCode, event)
    }

    override fun onKeyMultiple(keyCode: Int, repeatCount: Int, event: KeyEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onKeyMultiple(keyCode, repeatCount, event)
    }

    override fun sendAccessibilityEvent(eventType: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.sendAccessibilityEvent(eventType)
    }

    override fun sendAccessibilityEventUnchecked(event: AccessibilityEvent?) {
        super.sendAccessibilityEventUnchecked(event)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }


    override fun getVerticalFadingEdgeLength(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getVerticalFadingEdgeLength()
    }

    override fun setFadingEdgeLength(length: Int) {
        super.setFadingEdgeLength(length)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getHorizontalFadingEdgeLength(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getHorizontalFadingEdgeLength()
    }

    override fun getVerticalScrollbarWidth(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getVerticalScrollbarWidth()
    }

    override fun getHorizontalScrollbarHeight(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getHorizontalScrollbarHeight()
    }

    override fun setVerticalScrollbarPosition(position: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setVerticalScrollbarPosition(position)
    }

    override fun getVerticalScrollbarPosition(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getVerticalScrollbarPosition()
    }

    override fun setScrollIndicators(indicators: Int) {
        super.setScrollIndicators(indicators)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setScrollIndicators(indicators: Int, mask: Int) {
        super.setScrollIndicators(indicators, mask)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getScrollIndicators(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getScrollIndicators()
    }

    override fun setOnScrollChangeListener(l: OnScrollChangeListener?) {
        super.setOnScrollChangeListener(l)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setOnFocusChangeListener(l: OnFocusChangeListener?) {
        super.setOnFocusChangeListener(l)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun addOnLayoutChangeListener(listener: OnLayoutChangeListener?) {
        super.addOnLayoutChangeListener(listener)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun removeOnLayoutChangeListener(listener: OnLayoutChangeListener?) {
        super.removeOnLayoutChangeListener(listener)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun addOnAttachStateChangeListener(listener: OnAttachStateChangeListener?) {
        super.addOnAttachStateChangeListener(listener)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun removeOnAttachStateChangeListener(listener: OnAttachStateChangeListener?) {
        super.removeOnAttachStateChangeListener(listener)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getOnFocusChangeListener(): OnFocusChangeListener {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getOnFocusChangeListener()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun hasOnClickListeners(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.hasOnClickListeners()
    }

    override fun setOnLongClickListener(l: OnLongClickListener?) {
        super.setOnLongClickListener(l)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setOnContextClickListener(l: OnContextClickListener?) {
        super.setOnContextClickListener(l)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setOnCreateContextMenuListener(l: OnCreateContextMenuListener?) {
        super.setOnCreateContextMenuListener(l)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun performClick(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.performClick()
    }

    override fun callOnClick(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.callOnClick()
    }

    override fun performLongClick(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.performLongClick()
    }

    override fun performLongClick(x: Float, y: Float): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.performLongClick(x, y)
    }

    override fun performContextClick(x: Float, y: Float): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.performContextClick(x, y)
    }

    override fun performContextClick(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.performContextClick()
    }

    override fun showContextMenu(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.showContextMenu()
    }

    override fun showContextMenu(x: Float, y: Float): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.showContextMenu(x, y)
    }

    override fun startActionMode(callback: ActionMode.Callback?): ActionMode {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.startActionMode(callback)
    }

    override fun startActionMode(callback: ActionMode.Callback?, type: Int): ActionMode {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.startActionMode(callback, type)
    }

    override fun setOnKeyListener(l: OnKeyListener?) {
        super.setOnKeyListener(l)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setOnGenericMotionListener(l: OnGenericMotionListener?) {
        super.setOnGenericMotionListener(l)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setOnHoverListener(l: OnHoverListener?) {
        super.setOnHoverListener(l)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setOnDragListener(l: OnDragListener?) {
        super.setOnDragListener(l)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun requestRectangleOnScreen(rectangle: Rect?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.requestRectangleOnScreen(rectangle)
    }

    override fun requestRectangleOnScreen(rectangle: Rect?, immediate: Boolean): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.requestRectangleOnScreen(rectangle, immediate)
    }

    override fun hasFocus(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.hasFocus()
    }

    override fun hasFocusable(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.hasFocusable()
    }

    override fun hasExplicitFocusable(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.hasExplicitFocusable()
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setAccessibilityPaneTitle(accessibilityPaneTitle: CharSequence?) {
        super.setAccessibilityPaneTitle(accessibilityPaneTitle)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getAccessibilityPaneTitle(): CharSequence? {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAccessibilityPaneTitle()
    }

    override fun announceForAccessibility(text: CharSequence?) {
        super.announceForAccessibility(text)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun dispatchPopulateAccessibilityEvent(event: AccessibilityEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchPopulateAccessibilityEvent(event)
    }

    override fun onPopulateAccessibilityEvent(event: AccessibilityEvent?) {
        super.onPopulateAccessibilityEvent(event)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent?) {
        super.onInitializeAccessibilityEvent(event)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun createAccessibilityNodeInfo(): AccessibilityNodeInfo {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.createAccessibilityNodeInfo()
    }

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(info)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getAccessibilityClassName(): CharSequence {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAccessibilityClassName()
    }

    override fun onProvideStructure(structure: ViewStructure?) {
        super.onProvideStructure(structure)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun onProvideAutofillStructure(structure: ViewStructure?, flags: Int) {
        super.onProvideAutofillStructure(structure, flags)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun onProvideVirtualStructure(structure: ViewStructure?) {
        super.onProvideVirtualStructure(structure)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun onProvideAutofillVirtualStructure(structure: ViewStructure?, flags: Int) {
        super.onProvideAutofillVirtualStructure(structure, flags)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun autofill(value: AutofillValue?) {
        super.autofill(value)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun autofill(values: SparseArray<AutofillValue>) {
        super.autofill(values)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setAutofillId(id: AutofillId?) {
        super.setAutofillId(id)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getAutofillType(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAutofillType()
    }

    override fun getAutofillHints(): Array<String>? {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAutofillHints()
    }

    override fun getAutofillValue(): AutofillValue? {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAutofillValue()
    }

    override fun getImportantForAutofill(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getImportantForAutofill()
    }

    override fun setImportantForAutofill(mode: Int) {
        super.setImportantForAutofill(mode)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun dispatchProvideStructure(structure: ViewStructure?) {
        super.dispatchProvideStructure(structure)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun dispatchProvideAutofillStructure(structure: ViewStructure, flags: Int) {
        super.dispatchProvideAutofillStructure(structure, flags)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun addExtraDataToAccessibilityNodeInfo(
        info: AccessibilityNodeInfo,
        extraDataKey: String,
        arguments: Bundle?
    ) {
        super.addExtraDataToAccessibilityNodeInfo(info, extraDataKey, arguments)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun isVisibleToUserForAutofill(virtualId: Int): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isVisibleToUserForAutofill(virtualId)
    }

//    override fun getAccessibilityDelegate(): AccessibilityDelegate {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.getAccessibilityDelegate()
//    }

    override fun setAccessibilityDelegate(delegate: AccessibilityDelegate?) {
        super.setAccessibilityDelegate(delegate)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getAccessibilityNodeProvider(): AccessibilityNodeProvider {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAccessibilityNodeProvider()
    }

    override fun setContentDescription(contentDescription: CharSequence?) {
        super.setContentDescription(contentDescription)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setAccessibilityTraversalBefore(beforeId: Int) {
        super.setAccessibilityTraversalBefore(beforeId)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getAccessibilityTraversalBefore(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAccessibilityTraversalBefore()
    }

    override fun setAccessibilityTraversalAfter(afterId: Int) {
        super.setAccessibilityTraversalAfter(afterId)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getAccessibilityTraversalAfter(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAccessibilityTraversalAfter()
    }

    override fun getLabelFor(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getLabelFor()
    }

    override fun setLabelFor(id: Int) {
        super.setLabelFor(id)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun isFocused(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isFocused()
    }

    override fun findFocus(): View {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.findFocus()
    }

    override fun isScrollContainer(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isScrollContainer()
    }

    override fun setScrollContainer(isScrollContainer: Boolean) {
        super.setScrollContainer(isScrollContainer)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getDrawingCacheQuality(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getDrawingCacheQuality()
    }

    override fun setDrawingCacheQuality(quality: Int) {
        super.setDrawingCacheQuality(quality)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getKeepScreenOn(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getKeepScreenOn()
    }

    override fun setKeepScreenOn(keepScreenOn: Boolean) {
        super.setKeepScreenOn(keepScreenOn)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getNextFocusLeftId(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getNextFocusLeftId()
    }

    override fun setNextFocusLeftId(nextFocusLeftId: Int) {
        super.setNextFocusLeftId(nextFocusLeftId)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getNextFocusRightId(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getNextFocusRightId()
    }

    override fun setNextFocusRightId(nextFocusRightId: Int) {
        super.setNextFocusRightId(nextFocusRightId)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getNextFocusUpId(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getNextFocusUpId()
    }

    override fun setNextFocusUpId(nextFocusUpId: Int) {
        super.setNextFocusUpId(nextFocusUpId)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getNextFocusDownId(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getNextFocusDownId()
    }

    override fun setNextFocusDownId(nextFocusDownId: Int) {
        super.setNextFocusDownId(nextFocusDownId)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getNextFocusForwardId(): Int {
        return super.getNextFocusForwardId()
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setNextFocusForwardId(nextFocusForwardId: Int) {
        super.setNextFocusForwardId(nextFocusForwardId)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getNextClusterForwardId(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getNextClusterForwardId()
    }

    override fun setNextClusterForwardId(nextClusterForwardId: Int) {
        super.setNextClusterForwardId(nextClusterForwardId)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun isShown(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isShown()
    }

    override fun fitSystemWindows(insets: Rect?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.fitSystemWindows(insets)
    }

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onApplyWindowInsets(insets)
    }

    override fun setOnApplyWindowInsetsListener(listener: OnApplyWindowInsetsListener?) {
        super.setOnApplyWindowInsetsListener(listener)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun dispatchApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchApplyWindowInsets(insets)
    }

//    override fun setSystemGestureExclusionRects(rects: MutableList<Rect>) {
//        super.setSystemGestureExclusionRects(rects)
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//    }

//    override fun getSystemGestureExclusionRects(): MutableList<Rect> {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.getSystemGestureExclusionRects()
//    }

//    override fun getLocationInSurface(location: IntArray) {
//        super.getLocationInSurface(location)
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//    }

    override fun getRootWindowInsets(): WindowInsets {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getRootWindowInsets()
    }

    override fun computeSystemWindowInsets(
        `in`: WindowInsets?,
        outLocalInsets: Rect?
    ): WindowInsets {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.computeSystemWindowInsets(`in`, outLocalInsets)
    }

    override fun setFitsSystemWindows(fitSystemWindows: Boolean) {
        super.setFitsSystemWindows(fitSystemWindows)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getFitsSystemWindows(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getFitsSystemWindows()
    }

    override fun requestFitSystemWindows() {
        super.requestFitSystemWindows()
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun requestApplyInsets() {
        super.requestApplyInsets()
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getVisibility(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getVisibility()
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun isEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isEnabled()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setAutofillHints(vararg autofillHints: String?) {
        super.setAutofillHints(*autofillHints)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setSoundEffectsEnabled(soundEffectsEnabled: Boolean) {
        super.setSoundEffectsEnabled(soundEffectsEnabled)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun isSoundEffectsEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isSoundEffectsEnabled()
    }

    override fun setHapticFeedbackEnabled(hapticFeedbackEnabled: Boolean) {
        super.setHapticFeedbackEnabled(hapticFeedbackEnabled)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun isHapticFeedbackEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isHapticFeedbackEnabled()
    }

    override fun setLayoutDirection(layoutDirection: Int) {
        super.setLayoutDirection(layoutDirection)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getLayoutDirection(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getLayoutDirection()
    }

    override fun hasTransientState(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.hasTransientState()
    }

    override fun setHasTransientState(hasTransientState: Boolean) {
        super.setHasTransientState(hasTransientState)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun isAttachedToWindow(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isAttachedToWindow()
    }

    override fun isLaidOut(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isLaidOut()
    }

    override fun setWillNotDraw(willNotDraw: Boolean) {
        super.setWillNotDraw(willNotDraw)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun willNotDraw(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.willNotDraw()
    }

    override fun setWillNotCacheDrawing(willNotCacheDrawing: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setWillNotCacheDrawing(willNotCacheDrawing)
    }

    override fun willNotCacheDrawing(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.willNotCacheDrawing()
    }

    override fun isClickable(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isClickable()
    }

    override fun setClickable(clickable: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setClickable(clickable)
    }

    override fun isLongClickable(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isLongClickable()
    }

    override fun setLongClickable(longClickable: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setLongClickable(longClickable)
    }

    override fun isContextClickable(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isContextClickable()
    }

    override fun setContextClickable(contextClickable: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setContextClickable(contextClickable)
    }

    override fun setPressed(pressed: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setPressed(pressed)
    }

    override fun dispatchSetPressed(pressed: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchSetPressed(pressed)
    }

    override fun isPressed(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isPressed()
    }

    override fun isSaveEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isSaveEnabled()
    }

    override fun setSaveEnabled(enabled: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setSaveEnabled(enabled)
    }

    override fun getFilterTouchesWhenObscured(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getFilterTouchesWhenObscured()
    }

    override fun setFilterTouchesWhenObscured(enabled: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setFilterTouchesWhenObscured(enabled)
    }

    override fun isSaveFromParentEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isSaveFromParentEnabled()
    }

    override fun setSaveFromParentEnabled(enabled: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setSaveFromParentEnabled(enabled)
    }

    override fun getFocusable(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getFocusable()
    }

    override fun isScreenReaderFocusable(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isScreenReaderFocusable()
    }

    override fun setScreenReaderFocusable(screenReaderFocusable: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setScreenReaderFocusable(screenReaderFocusable)
    }

    override fun isAccessibilityHeading(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isAccessibilityHeading()
    }

    override fun setAccessibilityHeading(isHeading: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setAccessibilityHeading(isHeading)
    }

    override fun focusSearch(direction: Int): View {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.focusSearch(direction)
    }

    override fun setKeyboardNavigationCluster(isCluster: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setKeyboardNavigationCluster(isCluster)
    }

    override fun keyboardNavigationClusterSearch(currentCluster: View?, direction: Int): View {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.keyboardNavigationClusterSearch(currentCluster, direction)
    }

    override fun dispatchUnhandledMove(focused: View?, direction: Int): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchUnhandledMove(focused, direction)
    }

    override fun setDefaultFocusHighlightEnabled(defaultFocusHighlightEnabled: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setDefaultFocusHighlightEnabled(defaultFocusHighlightEnabled)
    }

    override fun getFocusables(direction: Int): ArrayList<View> {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getFocusables(direction)
    }

    override fun addFocusables(views: ArrayList<View>?, direction: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.addFocusables(views, direction)
    }

    override fun addFocusables(views: ArrayList<View>?, direction: Int, focusableMode: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.addFocusables(views, direction, focusableMode)
    }

    override fun addKeyboardNavigationClusters(views: MutableCollection<View>, direction: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.addKeyboardNavigationClusters(views, direction)
    }

    override fun findViewsWithText(
        outViews: ArrayList<View>?,
        searched: CharSequence?,
        flags: Int
    ) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.findViewsWithText(outViews, searched, flags)
    }

    override fun getTouchables(): ArrayList<View> {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTouchables()
    }

    override fun addTouchables(views: ArrayList<View>?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.addTouchables(views)
    }

    override fun isAccessibilityFocused(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isAccessibilityFocused()
    }

    override fun restoreDefaultFocus(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.restoreDefaultFocus()
    }

    override fun getImportantForAccessibility(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getImportantForAccessibility()
    }

    override fun setAccessibilityLiveRegion(mode: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setAccessibilityLiveRegion(mode)
    }

    override fun getAccessibilityLiveRegion(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAccessibilityLiveRegion()
    }

    override fun setImportantForAccessibility(mode: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setImportantForAccessibility(mode)
    }

    override fun isImportantForAccessibility(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isImportantForAccessibility()
    }

    override fun getParentForAccessibility(): ViewParent {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getParentForAccessibility()
    }

    override fun addChildrenForAccessibility(outChildren: ArrayList<View>?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.addChildrenForAccessibility(outChildren)
    }

//    override fun setTransitionVisibility(visibility: Int) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.setTransitionVisibility(visibility)
//    }

    override fun dispatchNestedPrePerformAccessibilityAction(
        action: Int,
        arguments: Bundle?
    ): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchNestedPrePerformAccessibilityAction(action, arguments)
    }

    override fun performAccessibilityAction(action: Int, arguments: Bundle?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.performAccessibilityAction(action, arguments)
    }

    override fun dispatchStartTemporaryDetach() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchStartTemporaryDetach()
    }

    override fun onStartTemporaryDetach() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onStartTemporaryDetach()
    }

    override fun dispatchFinishTemporaryDetach() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchFinishTemporaryDetach()
    }

    override fun onFinishTemporaryDetach() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onFinishTemporaryDetach()
    }

    override fun getKeyDispatcherState(): KeyEvent.DispatcherState {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getKeyDispatcherState()
    }

    override fun dispatchKeyEventPreIme(event: KeyEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchKeyEventPreIme(event)
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchKeyEvent(event)
    }

    override fun dispatchKeyShortcutEvent(event: KeyEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchKeyShortcutEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchTouchEvent(event)
    }

    override fun onFilterTouchEventForSecurity(event: MotionEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onFilterTouchEventForSecurity(event)
    }

    override fun dispatchTrackballEvent(event: MotionEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchTrackballEvent(event)
    }

    override fun dispatchCapturedPointerEvent(event: MotionEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchCapturedPointerEvent(event)
    }

    override fun dispatchGenericMotionEvent(event: MotionEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchGenericMotionEvent(event)
    }

    override fun dispatchHoverEvent(event: MotionEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchHoverEvent(event)
    }

    override fun dispatchGenericPointerEvent(event: MotionEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchGenericPointerEvent(event)
    }

    override fun dispatchGenericFocusedEvent(event: MotionEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchGenericFocusedEvent(event)
    }

    override fun dispatchWindowFocusChanged(hasFocus: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchWindowFocusChanged(hasFocus)
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onWindowFocusChanged(hasWindowFocus)
    }

    override fun hasWindowFocus(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.hasWindowFocus()
    }

    override fun dispatchVisibilityChanged(changedView: View, visibility: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchVisibilityChanged(changedView, visibility)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onVisibilityChanged(changedView, visibility)
    }

    override fun dispatchDisplayHint(hint: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchDisplayHint(hint)
    }

    override fun onDisplayHint(hint: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onDisplayHint(hint)
    }

    override fun dispatchWindowVisibilityChanged(visibility: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchWindowVisibilityChanged(visibility)
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onWindowVisibilityChanged(visibility)
    }

    override fun onVisibilityAggregated(isVisible: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onVisibilityAggregated(isVisible)
    }

    override fun getWindowVisibility(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getWindowVisibility()
    }

    override fun getWindowVisibleDisplayFrame(outRect: Rect?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.getWindowVisibleDisplayFrame(outRect)
    }

    override fun dispatchConfigurationChanged(newConfig: Configuration?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchConfigurationChanged(newConfig)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onConfigurationChanged(newConfig)
    }

    override fun isInTouchMode(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isInTouchMode()
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onKeyPreIme(keyCode, event)
    }

    override fun onKeyShortcut(keyCode: Int, event: KeyEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onKeyShortcut(keyCode, event)
    }

    override fun onCheckIsTextEditor(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onCheckIsTextEditor()
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onCreateInputConnection(outAttrs)
    }

    override fun checkInputConnectionProxy(view: View?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.checkInputConnectionProxy(view)
    }

    override fun createContextMenu(menu: ContextMenu?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.createContextMenu(menu)
    }

    override fun getContextMenuInfo(): ContextMenu.ContextMenuInfo {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getContextMenuInfo()
    }

    override fun onCreateContextMenu(menu: ContextMenu?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onCreateContextMenu(menu)
    }

    override fun onTrackballEvent(event: MotionEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onTrackballEvent(event)
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onGenericMotionEvent(event)
    }

    override fun onHoverEvent(event: MotionEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onHoverEvent(event)
    }

    override fun isHovered(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isHovered()
    }

    override fun setHovered(hovered: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setHovered(hovered)
    }

    override fun onHoverChanged(hovered: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onHoverChanged(hovered)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onTouchEvent(event)
    }

    override fun cancelLongPress() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.cancelLongPress()
    }

    override fun setTouchDelegate(delegate: TouchDelegate?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTouchDelegate(delegate)
    }

    override fun getTouchDelegate(): TouchDelegate {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTouchDelegate()
    }

    override fun bringToFront() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.bringToFront()
    }

    override fun onScrollChanged(horiz: Int, vert: Int, oldHoriz: Int, oldVert: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchDraw(canvas)
    }

    override fun setScrollX(value: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setScrollX(value)
    }

    override fun setScrollY(value: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setScrollY(value)
    }

    override fun getDrawingRect(outRect: Rect?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.getDrawingRect(outRect)
    }

    override fun getMatrix(): Matrix {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getMatrix()
    }

    override fun getCameraDistance(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCameraDistance()
    }

    override fun setCameraDistance(distance: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setCameraDistance(distance)
    }

    override fun getRotation(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getRotation()
    }

    override fun setRotation(rotation: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setRotation(rotation)
    }

    override fun getRotationY(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getRotationY()
    }

    override fun setRotationY(rotationY: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setRotationY(rotationY)
    }

    override fun getRotationX(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getRotationX()
    }

    override fun setRotationX(rotationX: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setRotationX(rotationX)
    }

    override fun getScaleX(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getScaleX()
    }

    override fun setScaleX(scaleX: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setScaleX(scaleX)
    }

    override fun getScaleY(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getScaleY()
    }

    override fun setScaleY(scaleY: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setScaleY(scaleY)
    }

    override fun getPivotX(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getPivotX()
    }

    override fun setPivotX(pivotX: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setPivotX(pivotX)
    }

    override fun getPivotY(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getPivotY()
    }

    override fun setPivotY(pivotY: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setPivotY(pivotY)
    }

    override fun isPivotSet(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isPivotSet()
    }

    override fun resetPivot() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.resetPivot()
    }

    override fun getAlpha(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAlpha()
    }

    override fun forceHasOverlappingRendering(hasOverlappingRendering: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.forceHasOverlappingRendering(hasOverlappingRendering)
    }

    override fun hasOverlappingRendering(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.hasOverlappingRendering()
    }

    override fun setAlpha(alpha: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setAlpha(alpha)
    }

//    override fun setTransitionAlpha(alpha: Float) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.setTransitionAlpha(alpha)
//    }

//    override fun getTransitionAlpha(): Float {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.getTransitionAlpha()
//    }

//    override fun setForceDarkAllowed(allow: Boolean) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.setForceDarkAllowed(allow)
//    }

//    override fun isForceDarkAllowed(): Boolean {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.isForceDarkAllowed()
//    }

    override fun isDirty(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isDirty()
    }

    override fun getX(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getX()
    }

    override fun setX(x: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setX(x)
    }

    override fun getY(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getY()
    }

    override fun setY(y: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setY(y)
    }

    override fun getZ(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getZ()
    }

    override fun setZ(z: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setZ(z)
    }

    override fun getElevation(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getElevation()
    }

    override fun setElevation(elevation: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setElevation(elevation)
    }

    override fun getTranslationX(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTranslationX()
    }

    override fun setTranslationX(translationX: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText", "translationX", translationX)
        super.setTranslationX(translationX)
    }

    override fun getTranslationY(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTranslationY()
    }

    override fun setTranslationY(translationY: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTranslationY(translationY)
    }

    override fun getTranslationZ(): Float {
        val translationZ = super.getTranslationZ()
        if (App.DEBUG) L.m(javaClass, "S=MyEditText", "translationZ", translationZ)
        return translationZ
    }

    override fun setTranslationZ(translationZ: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTranslationZ(translationZ)
    }

//    override fun setAnimationMatrix(matrix: Matrix?) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.setAnimationMatrix(matrix)
//    }

//    override fun getAnimationMatrix(): Matrix? {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.getAnimationMatrix()
//    }

    override fun getStateListAnimator(): StateListAnimator {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getStateListAnimator()
    }

    override fun setStateListAnimator(stateListAnimator: StateListAnimator?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setStateListAnimator(stateListAnimator)
    }

    override fun setClipToOutline(clipToOutline: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setClipToOutline(clipToOutline)
    }

    override fun setOutlineProvider(provider: ViewOutlineProvider?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setOutlineProvider(provider)
    }

    override fun getOutlineProvider(): ViewOutlineProvider {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getOutlineProvider()
    }

    override fun invalidateOutline() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.invalidateOutline()
    }

    override fun setOutlineSpotShadowColor(color: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setOutlineSpotShadowColor(color)
    }

    override fun getOutlineSpotShadowColor(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getOutlineSpotShadowColor()
    }

    override fun setOutlineAmbientShadowColor(color: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setOutlineAmbientShadowColor(color)
    }

    override fun getOutlineAmbientShadowColor(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getOutlineAmbientShadowColor()
    }

    override fun getHitRect(outRect: Rect?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.getHitRect(outRect)
    }

    override fun getFocusedRect(r: Rect?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.getFocusedRect(r)
    }

    override fun getGlobalVisibleRect(r: Rect?, globalOffset: Point?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getGlobalVisibleRect(r, globalOffset)
    }

    override fun offsetTopAndBottom(offset: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.offsetTopAndBottom(offset)
    }

    override fun offsetLeftAndRight(offset: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.offsetLeftAndRight(offset)
    }

//    override fun getLayoutParams(): ViewGroup.LayoutParams {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.getLayoutParams()
//    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setLayoutParams(params)
    }

    override fun scrollTo(x: Int, y: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.scrollTo(x, y)
    }

    override fun scrollBy(x: Int, y: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.scrollBy(x, y)
    }

    override fun awakenScrollBars(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.awakenScrollBars()
    }

    override fun awakenScrollBars(startDelay: Int): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.awakenScrollBars(startDelay)
    }

    override fun awakenScrollBars(startDelay: Int, invalidate: Boolean): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.awakenScrollBars(startDelay, invalidate)
    }

    override fun invalidate(dirty: Rect?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.invalidate(dirty)
    }

    override fun invalidate(l: Int, t: Int, r: Int, b: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.invalidate(l, t, r, b)
    }

    override fun invalidate() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.invalidate()
    }

    override fun isOpaque(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isOpaque()
    }

    override fun getHandler(): Handler {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getHandler()
    }

    override fun post(action: Runnable?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.post(action)
    }

    override fun postDelayed(action: Runnable?, delayMillis: Long): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.postDelayed(action, delayMillis)
    }

    override fun postOnAnimation(action: Runnable?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.postOnAnimation(action)
    }

    override fun postOnAnimationDelayed(action: Runnable?, delayMillis: Long) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.postOnAnimationDelayed(action, delayMillis)
    }

    override fun removeCallbacks(action: Runnable?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.removeCallbacks(action)
    }

    override fun postInvalidate() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.postInvalidate()
    }

    override fun postInvalidate(left: Int, top: Int, right: Int, bottom: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.postInvalidate(left, top, right, bottom)
    }

    override fun postInvalidateDelayed(delayMilliseconds: Long) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.postInvalidateDelayed(delayMilliseconds)
    }

    override fun postInvalidateDelayed(
        delayMilliseconds: Long,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.postInvalidateDelayed(delayMilliseconds, left, top, right, bottom)
    }

    override fun postInvalidateOnAnimation() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.postInvalidateOnAnimation()
    }

    override fun postInvalidateOnAnimation(left: Int, top: Int, right: Int, bottom: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.postInvalidateOnAnimation(left, top, right, bottom)
    }

    override fun computeScroll() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.computeScroll()
    }

    override fun isHorizontalFadingEdgeEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isHorizontalFadingEdgeEnabled()
    }

    override fun setHorizontalFadingEdgeEnabled(horizontalFadingEdgeEnabled: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setHorizontalFadingEdgeEnabled(horizontalFadingEdgeEnabled)
    }

    override fun isVerticalFadingEdgeEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isVerticalFadingEdgeEnabled()
    }

    override fun setVerticalFadingEdgeEnabled(verticalFadingEdgeEnabled: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setVerticalFadingEdgeEnabled(verticalFadingEdgeEnabled)
    }

    override fun getTopFadingEdgeStrength(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTopFadingEdgeStrength()
    }

    override fun getBottomFadingEdgeStrength(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getBottomFadingEdgeStrength()
    }

    override fun getLeftFadingEdgeStrength(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getLeftFadingEdgeStrength()
    }

    override fun getRightFadingEdgeStrength(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getRightFadingEdgeStrength()
    }

    override fun isHorizontalScrollBarEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isHorizontalScrollBarEnabled()
    }

    override fun setHorizontalScrollBarEnabled(horizontalScrollBarEnabled: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled)
    }

    override fun isVerticalScrollBarEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isVerticalScrollBarEnabled()
    }

    override fun setVerticalScrollBarEnabled(verticalScrollBarEnabled: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setVerticalScrollBarEnabled(verticalScrollBarEnabled)
    }

    override fun setScrollbarFadingEnabled(fadeScrollbars: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setScrollbarFadingEnabled(fadeScrollbars)
    }

    override fun isScrollbarFadingEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isScrollbarFadingEnabled()
    }

    override fun getScrollBarDefaultDelayBeforeFade(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getScrollBarDefaultDelayBeforeFade()
    }

    override fun setScrollBarDefaultDelayBeforeFade(scrollBarDefaultDelayBeforeFade: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setScrollBarDefaultDelayBeforeFade(scrollBarDefaultDelayBeforeFade)
    }

    override fun getScrollBarFadeDuration(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getScrollBarFadeDuration()
    }

    override fun setScrollBarFadeDuration(scrollBarFadeDuration: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setScrollBarFadeDuration(scrollBarFadeDuration)
    }

    override fun getScrollBarSize(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getScrollBarSize()
    }

    override fun setScrollBarSize(scrollBarSize: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setScrollBarSize(scrollBarSize)
    }

    override fun setScrollBarStyle(style: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setScrollBarStyle(style)
    }

    override fun getScrollBarStyle(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getScrollBarStyle()
    }

    override fun computeHorizontalScrollRange(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.computeHorizontalScrollRange()
    }

    override fun computeHorizontalScrollOffset(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.computeHorizontalScrollOffset()
    }

    override fun computeHorizontalScrollExtent(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.computeHorizontalScrollExtent()
    }

    override fun computeVerticalScrollRange(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.computeVerticalScrollRange()
    }

    override fun computeVerticalScrollOffset(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.computeVerticalScrollOffset()
    }

    override fun computeVerticalScrollExtent(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.computeVerticalScrollExtent()
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.canScrollHorizontally(direction)
    }

    override fun canScrollVertically(direction: Int): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.canScrollVertically(direction)
    }

    override fun onDraw(canvas: Canvas?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onDraw(canvas)
    }

    override fun onAttachedToWindow() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onAttachedToWindow()
    }

    override fun onScreenStateChanged(screenState: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onScreenStateChanged(screenState)
    }

    override fun onRtlPropertiesChanged(layoutDirection: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onRtlPropertiesChanged(layoutDirection)
    }

    override fun canResolveLayoutDirection(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.canResolveLayoutDirection()
    }

    override fun isLayoutDirectionResolved(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isLayoutDirectionResolved()
    }

    override fun onDetachedFromWindow() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onDetachedFromWindow()
    }

    override fun getWindowAttachCount(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getWindowAttachCount()
    }

    override fun getWindowToken(): IBinder {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getWindowToken()
    }

    override fun getWindowId(): WindowId {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getWindowId()
    }

    override fun getApplicationWindowToken(): IBinder {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getApplicationWindowToken()
    }

    override fun getDisplay(): Display {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getDisplay()
    }

    override fun onCancelPendingInputEvents() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onCancelPendingInputEvents()
    }

    override fun saveHierarchyState(container: SparseArray<Parcelable>?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.saveHierarchyState(container)
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchSaveInstanceState(container)
    }

    override fun onSaveInstanceState(): Parcelable? {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onSaveInstanceState()
    }

    override fun restoreHierarchyState(container: SparseArray<Parcelable>?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.restoreHierarchyState(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchRestoreInstanceState(container)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onRestoreInstanceState(state)
    }

    override fun getDrawingTime(): Long {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getDrawingTime()
    }

    override fun setDuplicateParentStateEnabled(enabled: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setDuplicateParentStateEnabled(enabled)
    }

    override fun isDuplicateParentStateEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isDuplicateParentStateEnabled()
    }

    override fun setLayerType(layerType: Int, paint: Paint?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setLayerType(layerType, paint)
    }

    override fun setLayerPaint(paint: Paint?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setLayerPaint(paint)
    }

    override fun getLayerType(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getLayerType()
    }

    override fun buildLayer() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.buildLayer()
    }

    override fun setDrawingCacheEnabled(enabled: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setDrawingCacheEnabled(enabled)
    }

    override fun isDrawingCacheEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isDrawingCacheEnabled()
    }

    override fun getDrawingCache(): Bitmap {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getDrawingCache()
    }

    override fun getDrawingCache(autoScale: Boolean): Bitmap {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getDrawingCache(autoScale)
    }

    override fun destroyDrawingCache() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.destroyDrawingCache()
    }

    override fun setDrawingCacheBackgroundColor(color: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setDrawingCacheBackgroundColor(color)
    }

    override fun getDrawingCacheBackgroundColor(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getDrawingCacheBackgroundColor()
    }

    override fun buildDrawingCache() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.buildDrawingCache()
    }

    override fun buildDrawingCache(autoScale: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.buildDrawingCache(autoScale)
    }

    override fun isInEditMode(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isInEditMode()
    }

    override fun isPaddingOffsetRequired(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isPaddingOffsetRequired()
    }

    override fun getLeftPaddingOffset(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getLeftPaddingOffset()
    }

    override fun getRightPaddingOffset(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getRightPaddingOffset()
    }

    override fun getTopPaddingOffset(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTopPaddingOffset()
    }

    override fun getBottomPaddingOffset(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getBottomPaddingOffset()
    }

    override fun isHardwareAccelerated(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isHardwareAccelerated()
    }

    override fun setClipBounds(clipBounds: Rect?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setClipBounds(clipBounds)
    }

    override fun getClipBounds(): Rect {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getClipBounds()
    }

    override fun getClipBounds(outRect: Rect?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getClipBounds(outRect)
    }

    override fun draw(canvas: Canvas?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.draw(canvas)
    }

    override fun getOverlay(): ViewOverlay {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getOverlay()
    }

    override fun getSolidColor(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getSolidColor()
    }

    override fun isLayoutRequested(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isLayoutRequested()
    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.layout(l, t, r, b)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onFinishInflate() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onFinishInflate()
    }

    override fun getResources(): Resources {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getResources()
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.verifyDrawable(who)
    }

    override fun drawableStateChanged() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.drawableStateChanged()
    }

    override fun drawableHotspotChanged(x: Float, y: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.drawableHotspotChanged(x, y)
    }

    override fun dispatchDrawableHotspotChanged(x: Float, y: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchDrawableHotspotChanged(x, y)
    }

    override fun refreshDrawableState() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.refreshDrawableState()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onCreateDrawableState(extraSpace)
    }

    override fun jumpDrawablesToCurrentState() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.jumpDrawablesToCurrentState()
    }

    override fun setBackgroundColor(color: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setBackgroundColor(color)
    }

    override fun setBackgroundResource(resId: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setBackgroundResource(resId)
    }

    override fun setBackground(background: Drawable?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setBackground(background)
    }

    override fun setBackgroundDrawable(background: Drawable?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setBackgroundDrawable(background)
    }

    override fun getBackground(): Drawable {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getBackground()
    }

    override fun setBackgroundTintList(tint: ColorStateList?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setBackgroundTintList(tint)
    }

    override fun getBackgroundTintList(): ColorStateList? {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getBackgroundTintList()
    }

    override fun setBackgroundTintMode(tintMode: PorterDuff.Mode?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setBackgroundTintMode(tintMode)
    }


    override fun getBackgroundTintMode(): PorterDuff.Mode? {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getBackgroundTintMode()
    }

    override fun getForeground(): Drawable {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getForeground()
    }

    override fun setForeground(foreground: Drawable?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setForeground(foreground)
    }

    override fun getForegroundGravity(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getForegroundGravity()
    }

    override fun setForegroundGravity(gravity: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setForegroundGravity(gravity)
    }

    override fun setForegroundTintList(tint: ColorStateList?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setForegroundTintList(tint)
    }

    override fun getForegroundTintList(): ColorStateList? {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getForegroundTintList()
    }

    override fun setForegroundTintMode(tintMode: PorterDuff.Mode?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setForegroundTintMode(tintMode)
    }

    override fun getForegroundTintMode(): PorterDuff.Mode? {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getForegroundTintMode()
    }

    override fun onDrawForeground(canvas: Canvas?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onDrawForeground(canvas)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setPadding(left, top, right, bottom)
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setPaddingRelative(start, top, end, bottom)
    }

//    override fun getSourceLayoutResId(): Int {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.getSourceLayoutResId()
//    }

    override fun getPaddingTop(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getPaddingTop()
    }

    override fun getPaddingBottom(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getPaddingBottom()
    }

    override fun getPaddingLeft(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getPaddingLeft()
    }

    override fun getPaddingStart(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getPaddingStart()
    }

    override fun getPaddingRight(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getPaddingRight()
    }

    override fun getPaddingEnd(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getPaddingEnd()
    }

    override fun isPaddingRelative(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isPaddingRelative()
    }

    override fun setSelected(selected: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setSelected(selected)
    }

    override fun dispatchSetSelected(selected: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchSetSelected(selected)
    }

    override fun isSelected(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isSelected()
    }

    override fun setActivated(activated: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setActivated(activated)
    }

    override fun dispatchSetActivated(activated: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchSetActivated(activated)
    }

    override fun isActivated(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isActivated()
    }

    override fun getViewTreeObserver(): ViewTreeObserver {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getViewTreeObserver()
    }

    override fun getRootView(): View {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getRootView()
    }

//    override fun transformMatrixToGlobal(matrix: Matrix) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.transformMatrixToGlobal(matrix)
//    }

//    override fun transformMatrixToLocal(matrix: Matrix) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.transformMatrixToLocal(matrix)
//    }

    override fun getLocationOnScreen(outLocation: IntArray?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.getLocationOnScreen(outLocation)
    }

    override fun getLocationInWindow(outLocation: IntArray?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.getLocationInWindow(outLocation)
    }

    override fun setId(id: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setId(id)
    }

    override fun getId(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getId()
    }

//    override fun getUniqueDrawingId(): Long {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.getUniqueDrawingId()
//    }

    override fun getTag(): Any {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTag()
    }

    override fun getTag(key: Int): Any {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTag(key)
    }

    override fun setTag(tag: Any?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTag(tag)
    }

    override fun setTag(key: Int, tag: Any?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTag(key, tag)
    }

    override fun getBaseline(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getBaseline()
    }

    override fun isInLayout(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isInLayout()
    }

    override fun requestLayout() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.requestLayout()
    }

    override fun forceLayout() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.forceLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun getSuggestedMinimumHeight(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getSuggestedMinimumHeight()
    }

    override fun getSuggestedMinimumWidth(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getSuggestedMinimumWidth()
    }

    override fun getMinimumHeight(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getMinimumHeight()
    }

    override fun setMinimumHeight(minHeight: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setMinimumHeight(minHeight)
    }

    override fun getMinimumWidth(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getMinimumWidth()
    }

    override fun setMinimumWidth(minWidth: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setMinimumWidth(minWidth)
    }

//    override fun getAnimation(): Animation {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.getAnimation()
//    }

    override fun startAnimation(animation: Animation?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.startAnimation(animation)
    }

    override fun clearAnimation() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.clearAnimation()
    }

    override fun setAnimation(animation: Animation?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setAnimation(animation)
    }

    override fun onAnimationStart() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onAnimationStart()
    }

    override fun onAnimationEnd() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onAnimationEnd()
    }

    override fun onSetAlpha(alpha: Int): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onSetAlpha(alpha)
    }

    override fun playSoundEffect(soundConstant: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.playSoundEffect(soundConstant)
    }

    override fun performHapticFeedback(feedbackConstant: Int): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.performHapticFeedback(feedbackConstant)
    }

    override fun performHapticFeedback(feedbackConstant: Int, flags: Int): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.performHapticFeedback(feedbackConstant, flags)
    }

    override fun setSystemUiVisibility(visibility: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setSystemUiVisibility(visibility)
    }

    override fun getSystemUiVisibility(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getSystemUiVisibility()
    }

    override fun getWindowSystemUiVisibility(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getWindowSystemUiVisibility()
    }

    override fun onWindowSystemUiVisibilityChanged(visible: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onWindowSystemUiVisibilityChanged(visible)
    }

    override fun dispatchWindowSystemUiVisiblityChanged(visible: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchWindowSystemUiVisiblityChanged(visible)
    }

    override fun setOnSystemUiVisibilityChangeListener(l: OnSystemUiVisibilityChangeListener?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setOnSystemUiVisibilityChangeListener(l)
    }

    override fun dispatchSystemUiVisibilityChanged(visibility: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchSystemUiVisibilityChanged(visibility)
    }

    override fun onDragEvent(event: DragEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onDragEvent(event)
    }

    override fun dispatchDragEvent(event: DragEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
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
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
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
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
    }

    override fun getOverScrollMode(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getOverScrollMode()
    }

    override fun setOverScrollMode(overScrollMode: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setOverScrollMode(overScrollMode)
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setNestedScrollingEnabled(enabled)
    }

    override fun isNestedScrollingEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isNestedScrollingEnabled()
    }

    override fun startNestedScroll(axes: Int): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
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
        offsetInWindow: IntArray?
    ): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun setTextDirection(textDirection: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTextDirection(textDirection)
    }

    override fun getTextDirection(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTextDirection()
    }

    override fun canResolveTextDirection(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.canResolveTextDirection()
    }

    override fun isTextDirectionResolved(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isTextDirectionResolved()
    }

    override fun setTextAlignment(textAlignment: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTextAlignment(textAlignment)
    }

    override fun getTextAlignment(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTextAlignment()
    }

    override fun canResolveTextAlignment(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.canResolveTextAlignment()
    }

    override fun isTextAlignmentResolved(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isTextAlignmentResolved()
    }

    override fun onResolvePointerIcon(event: MotionEvent?, pointerIndex: Int): PointerIcon {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onResolvePointerIcon(event, pointerIndex)
    }

    override fun setPointerIcon(pointerIcon: PointerIcon?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setPointerIcon(pointerIcon)
    }

    override fun getPointerIcon(): PointerIcon {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getPointerIcon()
    }

    override fun hasPointerCapture(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.hasPointerCapture()
    }

    override fun requestPointerCapture() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.requestPointerCapture()
    }

    override fun releasePointerCapture() {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.releasePointerCapture()
    }

    override fun onPointerCaptureChange(hasCapture: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.onPointerCaptureChange(hasCapture)
    }

    override fun dispatchPointerCaptureChanged(hasCapture: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.dispatchPointerCaptureChanged(hasCapture)
    }

    override fun onCapturedPointerEvent(event: MotionEvent?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onCapturedPointerEvent(event)
    }

    override fun setOnCapturedPointerListener(l: OnCapturedPointerListener?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setOnCapturedPointerListener(l)
    }

    override fun animate(): ViewPropertyAnimator {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.animate()
    }

    override fun getTransitionName(): String {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTransitionName()
    }

    override fun setTooltipText(tooltipText: CharSequence?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTooltipText(tooltipText)
    }

    override fun getTooltipText(): CharSequence? {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTooltipText()
    }

    override fun addOnUnhandledKeyEventListener(listener: OnUnhandledKeyEventListener?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.addOnUnhandledKeyEventListener(listener)
    }

    override fun removeOnUnhandledKeyEventListener(listener: OnUnhandledKeyEventListener?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.removeOnUnhandledKeyEventListener(listener)
    }

    override fun onPreDraw(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onPreDraw()
    }

    override fun setAutoSizeTextTypeWithDefaults(autoSizeTextType: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setAutoSizeTextTypeWithDefaults(autoSizeTextType)
    }

    override fun setAutoSizeTextTypeUniformWithConfiguration(
        autoSizeMinTextSize: Int,
        autoSizeMaxTextSize: Int,
        autoSizeStepGranularity: Int,
        unit: Int
    ) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setAutoSizeTextTypeUniformWithConfiguration(
            autoSizeMinTextSize,
            autoSizeMaxTextSize,
            autoSizeStepGranularity,
            unit
        )
    }

    override fun setAutoSizeTextTypeUniformWithPresetSizes(presetSizes: IntArray, unit: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, unit)
    }

    override fun getAutoSizeTextType(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAutoSizeTextType()
    }

    override fun getAutoSizeStepGranularity(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAutoSizeStepGranularity()
    }

    override fun getAutoSizeMinTextSize(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAutoSizeMinTextSize()
    }

    override fun getAutoSizeMaxTextSize(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAutoSizeMaxTextSize()
    }

    override fun getAutoSizeTextAvailableSizes(): IntArray {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getAutoSizeTextAvailableSizes()
    }

    override fun setTypeface(tf: Typeface?, style: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTypeface(tf, style)
    }

    override fun setTypeface(tf: Typeface?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTypeface(tf)
    }

    override fun getDefaultEditable(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getDefaultEditable()
    }

    override fun getDefaultMovementMethod(): MovementMethod {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getDefaultMovementMethod()
    }

    override fun getText(): Editable? {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getText()
    }

    override fun length(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.length()
    }

    override fun getEditableText(): Editable {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getEditableText()
    }

    override fun getLineHeight(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getLineHeight()
    }

    override fun setKeyListener(input: KeyListener?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setKeyListener(input)
    }

    override fun getCompoundPaddingTop(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCompoundPaddingTop()
    }

    override fun getCompoundPaddingBottom(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCompoundPaddingBottom()
    }

    override fun getCompoundPaddingLeft(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCompoundPaddingLeft()
    }

    override fun getCompoundPaddingRight(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCompoundPaddingRight()
    }

    override fun getCompoundPaddingStart(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCompoundPaddingStart()
    }

    override fun getCompoundPaddingEnd(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCompoundPaddingEnd()
    }

    override fun getExtendedPaddingTop(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getExtendedPaddingTop()
    }

    override fun getExtendedPaddingBottom(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getExtendedPaddingBottom()
    }

    override fun getTotalPaddingLeft(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTotalPaddingLeft()
    }

    override fun getTotalPaddingRight(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTotalPaddingRight()
    }

    override fun getTotalPaddingStart(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTotalPaddingStart()
    }

    override fun getTotalPaddingEnd(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTotalPaddingEnd()
    }

    override fun getTotalPaddingTop(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTotalPaddingTop()
    }

    override fun getTotalPaddingBottom(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTotalPaddingBottom()
    }

    override fun setCompoundDrawables(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setCompoundDrawables(left, top, right, bottom)
    }

    override fun setCompoundDrawablesWithIntrinsicBounds(
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    }

    override fun setCompoundDrawablesWithIntrinsicBounds(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    }

    override fun setCompoundDrawablesRelative(
        start: Drawable?,
        top: Drawable?,
        end: Drawable?,
        bottom: Drawable?
    ) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setCompoundDrawablesRelative(start, top, end, bottom)
    }

    override fun setCompoundDrawablesRelativeWithIntrinsicBounds(
        start: Int,
        top: Int,
        end: Int,
        bottom: Int
    ) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
    }

    override fun setCompoundDrawablesRelativeWithIntrinsicBounds(
        start: Drawable?,
        top: Drawable?,
        end: Drawable?,
        bottom: Drawable?
    ) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
    }

    override fun getCompoundDrawables(): Array<Drawable> {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCompoundDrawables()
    }

    override fun getCompoundDrawablesRelative(): Array<Drawable> {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCompoundDrawablesRelative()
    }

    override fun setCompoundDrawablePadding(pad: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setCompoundDrawablePadding(pad)
    }

    override fun getCompoundDrawablePadding(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCompoundDrawablePadding()
    }

    override fun setCompoundDrawableTintList(tint: ColorStateList?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setCompoundDrawableTintList(tint)
    }

    override fun getCompoundDrawableTintList(): ColorStateList {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCompoundDrawableTintList()
    }

    override fun setCompoundDrawableTintMode(tintMode: PorterDuff.Mode?) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setCompoundDrawableTintMode(tintMode)
    }

    override fun getCompoundDrawableTintMode(): PorterDuff.Mode {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCompoundDrawableTintMode()
    }

    override fun setFirstBaselineToTopHeight(firstBaselineToTopHeight: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setFirstBaselineToTopHeight(firstBaselineToTopHeight)
    }

    override fun setLastBaselineToBottomHeight(lastBaselineToBottomHeight: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setLastBaselineToBottomHeight(lastBaselineToBottomHeight)
    }

    override fun getFirstBaselineToTopHeight(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getFirstBaselineToTopHeight()
    }

    override fun getLastBaselineToBottomHeight(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getLastBaselineToBottomHeight()
    }

//    override fun setTextSelectHandle(textSelectHandle: Drawable) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.setTextSelectHandle(textSelectHandle)
//    }

//    override fun setTextSelectHandle(textSelectHandle: Int) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.setTextSelectHandle(textSelectHandle)
//    }

//    override fun getTextSelectHandle(): Drawable? {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.getTextSelectHandle()
//    }

//    override fun setTextSelectHandleLeft(textSelectHandleLeft: Drawable) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.setTextSelectHandleLeft(textSelectHandleLeft)
//    }

//    override fun setTextSelectHandleLeft(textSelectHandleLeft: Int) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.setTextSelectHandleLeft(textSelectHandleLeft)
//    }

//    override fun getTextSelectHandleLeft(): Drawable? {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.getTextSelectHandleLeft()
//    }

//    override fun setTextSelectHandleRight(textSelectHandleRight: Drawable) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.setTextSelectHandleRight(textSelectHandleRight)
//    }

//    override fun setTextSelectHandleRight(textSelectHandleRight: Int) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.setTextSelectHandleRight(textSelectHandleRight)
//    }

//    override fun getTextSelectHandleRight(): Drawable? {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.getTextSelectHandleRight()
//    }

//    override fun setTextCursorDrawable(textCursorDrawable: Drawable?) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.setTextCursorDrawable(textCursorDrawable)
//    }

//    override fun setTextCursorDrawable(textCursorDrawable: Int) {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        super.setTextCursorDrawable(textCursorDrawable)
//    }

//    override fun getTextCursorDrawable(): Drawable? {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.getTextCursorDrawable()
//    }

    override fun setTextAppearance(context: Context?, resId: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTextAppearance(context, resId)
    }

    override fun setTextAppearance(resId: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTextAppearance(resId)
    }

    override fun getTextLocale(): Locale {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTextLocale()
    }

    override fun getTextLocales(): LocaleList {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTextLocales()
    }

    override fun setTextLocale(locale: Locale) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTextLocale(locale)
    }

    override fun setTextLocales(locales: LocaleList) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTextLocales(locales)
    }

    override fun getTextSize(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTextSize()
    }

    override fun setTextSize(size: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTextSize(size)
    }

    override fun setTextSize(unit: Int, size: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTextSize(unit, size)
    }

    override fun getTextScaleX(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTextScaleX()
    }

    override fun setTextScaleX(size: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTextScaleX(size)
    }

    override fun getTypeface(): Typeface {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTypeface()
    }

    override fun setElegantTextHeight(elegant: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setElegantTextHeight(elegant)
    }

    override fun setFallbackLineSpacing(enabled: Boolean) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setFallbackLineSpacing(enabled)
    }

    override fun isFallbackLineSpacing(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isFallbackLineSpacing()
    }

    override fun isElegantTextHeight(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isElegantTextHeight()
    }

    override fun getLetterSpacing(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getLetterSpacing()
    }

    override fun setLetterSpacing(letterSpacing: Float) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setLetterSpacing(letterSpacing)
    }

    override fun getFontFeatureSettings(): String? {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getFontFeatureSettings()
    }

    override fun getFontVariationSettings(): String? {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getFontVariationSettings()
    }

    override fun setBreakStrategy(breakStrategy: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setBreakStrategy(breakStrategy)
    }

    override fun getBreakStrategy(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getBreakStrategy()
    }

    override fun setHyphenationFrequency(hyphenationFrequency: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setHyphenationFrequency(hyphenationFrequency)
    }

    override fun getHyphenationFrequency(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getHyphenationFrequency()
    }

    override fun getTextMetricsParams(): PrecomputedText.Params {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getTextMetricsParams()
    }

    override fun setTextMetricsParams(params: PrecomputedText.Params) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setTextMetricsParams(params)
    }

    override fun setJustificationMode(justificationMode: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setJustificationMode(justificationMode)
    }

    override fun getJustificationMode(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getJustificationMode()
    }

    override fun setFontFeatureSettings(fontFeatureSettings: String?) {
        super.setFontFeatureSettings(fontFeatureSettings)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setFontVariationSettings(fontVariationSettings: String?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.setFontVariationSettings(fontVariationSettings)
    }

    override fun setTextColor(color: Int) {
        super.setTextColor(color)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setTextColor(colors: ColorStateList?) {
        super.setTextColor(colors)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setHighlightColor(color: Int) {
        super.setHighlightColor(color)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getHighlightColor(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getHighlightColor()
    }

    override fun setShadowLayer(radius: Float, dx: Float, dy: Float, color: Int) {
        super.setShadowLayer(radius, dx, dy, color)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getShadowRadius(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getShadowRadius()
    }

    override fun getShadowDx(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getShadowDx()
    }

    override fun getShadowDy(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getShadowDy()
    }

    override fun getShadowColor(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getShadowColor()
    }

    override fun getPaint(): TextPaint {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getPaint()
    }

    override fun getUrls(): Array<URLSpan> {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getUrls()
    }

    override fun setGravity(gravity: Int) {
        super.setGravity(gravity)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getGravity(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getGravity()
    }

    override fun getPaintFlags(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getPaintFlags()
    }

    override fun setPaintFlags(flags: Int) {
        super.setPaintFlags(flags)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setHorizontallyScrolling(whether: Boolean) {
        super.setHorizontallyScrolling(whether)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setMinLines(minLines: Int) {
        super.setMinLines(minLines)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getMinLines(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getMinLines()
    }

    override fun setMinHeight(minPixels: Int) {
        super.setMinHeight(minPixels)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getMinHeight(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getMinHeight()
    }

    override fun setMaxLines(maxLines: Int) {
        super.setMaxLines(maxLines)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getMaxLines(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getMaxLines()
    }

    override fun setMaxHeight(maxPixels: Int) {
        super.setMaxHeight(maxPixels)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getMaxHeight(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getMaxHeight()
    }

    override fun setLines(lines: Int) {
        super.setLines(lines)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setHeight(pixels: Int) {
        super.setHeight(pixels)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setMinEms(minEms: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setMinEms(minEms)
    }

    override fun getMinEms(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getMinEms()
    }

    override fun setMinWidth(minPixels: Int) {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        super.setMinWidth(minPixels)
    }

    override fun getMinWidth(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getMinWidth()
    }

    override fun setMaxEms(maxEms: Int) {
        super.setMaxEms(maxEms)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getMaxEms(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getMaxEms()
    }

    override fun setMaxWidth(maxPixels: Int) {
        super.setMaxWidth(maxPixels)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getMaxWidth(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getMaxWidth()
    }

    override fun setEms(ems: Int) {
        super.setEms(ems)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setWidth(pixels: Int) {
        super.setWidth(pixels)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setLineSpacing(add: Float, mult: Float) {
        super.setLineSpacing(add, mult)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getLineSpacingMultiplier(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getLineSpacingMultiplier()
    }

    override fun getLineSpacingExtra(): Float {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getLineSpacingExtra()
    }

    override fun setLineHeight(lineHeight: Int) {
        super.setLineHeight(lineHeight)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun append(text: CharSequence?, start: Int, end: Int) {
        super.append(text, start, end)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setFreezesText(freezesText: Boolean) {
        super.setFreezesText(freezesText)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getFreezesText(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getFreezesText()
    }

//    override fun setText(text: CharSequence?, type: BufferType?) {
//        super.setText(text, type)
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//    }

    override fun getHint(): CharSequence {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getHint()
    }

//    override fun isSingleLine(): Boolean {
//         if(App.DEBUG) L.m(javaClass, "S=MyEditText")
//        return super.isSingleLine()
//    }

    override fun setInputType(type: Int) {
        super.setInputType(type)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setRawInputType(type: Int) {
        super.setRawInputType(type)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getInputType(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getInputType()
    }

    override fun setImeOptions(imeOptions: Int) {
        super.setImeOptions(imeOptions)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getImeOptions(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getImeOptions()
    }

    override fun setImeActionLabel(label: CharSequence?, actionId: Int) {
        super.setImeActionLabel(label, actionId)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getImeActionLabel(): CharSequence {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getImeActionLabel()
    }

    override fun getImeActionId(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getImeActionId()
    }

    override fun setOnEditorActionListener(l: OnEditorActionListener?) {
        super.setOnEditorActionListener(l)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun onEditorAction(actionCode: Int) {
        super.onEditorAction(actionCode)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setPrivateImeOptions(type: String?) {
        super.setPrivateImeOptions(type)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getPrivateImeOptions(): String {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getPrivateImeOptions()
    }

    override fun setInputExtras(xmlResId: Int) {
        super.setInputExtras(xmlResId)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getInputExtras(create: Boolean): Bundle {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getInputExtras(create)
    }

    override fun setImeHintLocales(hintLocales: LocaleList?) {
        super.setImeHintLocales(hintLocales)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getImeHintLocales(): LocaleList? {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getImeHintLocales()
    }

    override fun getError(): CharSequence {
        return super.getError()
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setError(error: CharSequence?) {
        super.setError(error)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setError(error: CharSequence?, icon: Drawable?) {
        super.setError(error, icon)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.setFrame(l, t, r, b)
    }

    override fun setFilters(filters: Array<out InputFilter>?) {
        super.setFilters(filters)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getFilters(): Array<InputFilter> {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getFilters()
    }

    override fun isTextSelectable(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isTextSelectable()
    }

    override fun setTextIsSelectable(selectable: Boolean) {
        super.setTextIsSelectable(selectable)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getLineCount(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getLineCount()
    }

    override fun getLineBounds(line: Int, bounds: Rect?): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getLineBounds(line, bounds)
    }

    override fun extractText(request: ExtractedTextRequest?, outText: ExtractedText?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.extractText(request, outText)
    }

    override fun setExtractedText(text: ExtractedText?) {
        super.setExtractedText(text)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun onCommitCompletion(text: CompletionInfo?) {
        super.onCommitCompletion(text)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun onCommitCorrection(info: CorrectionInfo?) {
        super.onCommitCorrection(info)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun beginBatchEdit() {
        super.beginBatchEdit()
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun endBatchEdit() {
        super.endBatchEdit()
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun onBeginBatchEdit() {
        super.onBeginBatchEdit()
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun onEndBatchEdit() {
        super.onEndBatchEdit()
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun onPrivateIMECommand(action: String?, data: Bundle?): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onPrivateIMECommand(action, data)
    }

    override fun setIncludeFontPadding(includepad: Boolean) {
        super.setIncludeFontPadding(includepad)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getIncludeFontPadding(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getIncludeFontPadding()
    }

    override fun bringPointIntoView(offset: Int): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.bringPointIntoView(offset)
    }

    override fun moveCursorToVisibleOffset(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.moveCursorToVisibleOffset()
    }

    override fun debug(depth: Int) {
        super.debug(depth)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getSelectionStart(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getSelectionStart()
    }

    override fun getSelectionEnd(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getSelectionEnd()
    }

    override fun hasSelection(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.hasSelection()
    }

    override fun setSingleLine() {
        super.setSingleLine()
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setSingleLine(singleLine: Boolean) {
        super.setSingleLine(singleLine)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setAllCaps(allCaps: Boolean) {
        super.setAllCaps(allCaps)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun isAllCaps(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isAllCaps()
    }

    override fun setEllipsize(ellipsis: TextUtils.TruncateAt?) {
        super.setEllipsize(ellipsis)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setMarqueeRepeatLimit(marqueeLimit: Int) {
        super.setMarqueeRepeatLimit(marqueeLimit)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getMarqueeRepeatLimit(): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getMarqueeRepeatLimit()
    }

    override fun getEllipsize(): TextUtils.TruncateAt {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getEllipsize()
    }

    override fun setSelectAllOnFocus(selectAllOnFocus: Boolean) {
        super.setSelectAllOnFocus(selectAllOnFocus)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setCursorVisible(visible: Boolean) {
        super.setCursorVisible(visible)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun isCursorVisible(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isCursorVisible()
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun addTextChangedListener(watcher: TextWatcher?) {
        super.addTextChangedListener(watcher)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun removeTextChangedListener(watcher: TextWatcher?) {
        super.removeTextChangedListener(watcher)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun clearComposingText() {
        super.clearComposingText()
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun didTouchFocusSelect(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.didTouchFocusSelect()
    }

    override fun setScroller(s: Scroller?) {
        super.setScroller(s)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun isInputMethodTarget(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isInputMethodTarget()
    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.onTextContextMenuItem(id)
    }

    override fun isSuggestionsEnabled(): Boolean {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.isSuggestionsEnabled()
    }

    override fun setCustomSelectionActionModeCallback(actionModeCallback: ActionMode.Callback?) {
        super.setCustomSelectionActionModeCallback(actionModeCallback)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getCustomSelectionActionModeCallback(): ActionMode.Callback {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCustomSelectionActionModeCallback()
    }

    override fun setCustomInsertionActionModeCallback(actionModeCallback: ActionMode.Callback?) {
        super.setCustomInsertionActionModeCallback(actionModeCallback)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun getCustomInsertionActionModeCallback(): ActionMode.Callback {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getCustomInsertionActionModeCallback()
    }

    override fun getOffsetForPosition(x: Float, y: Float): Int {
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
        return super.getOffsetForPosition(x, y)
    }

    override fun setSelection(start: Int, stop: Int) {
        super.setSelection(start, stop)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun setSelection(index: Int) {
        super.setSelection(index)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun selectAll() {
        super.selectAll()
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }

    override fun extendSelection(index: Int) {
        super.extendSelection(index)
        if (App.DEBUG) L.m(javaClass, "S=MyEditText")
    }
}