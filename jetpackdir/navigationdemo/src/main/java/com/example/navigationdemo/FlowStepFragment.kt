package com.example.navigationdemo

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.AttributeSet
import android.view.*
import android.view.animation.Animation
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.flannery.utils.K

/**
 * Time:2021/6/30 09:58
 * Author:
 * Description:
 */
class FlowStepFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        K.m(javaClass)
        return inflater.inflate(R.layout.flowstep_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        K.m(javaClass)
        val value = arguments?.getInt("KEY", 0) ?: 0
        K.m(javaClass, "value=$value")
        view.findViewById<TextView>(R.id.textView3).text = "$value"
        stepTo(FlowStepFragment::class, Bundle().apply { putInt("KEY", 1 + value) }) // 测试能装几个
        view.findViewById<View>(R.id.stepNext).setOnClickListener {
            stepTo(FlowStepFragment::class, Bundle().apply { putInt("KEY", 1 + value) })
        }
        view.findViewById<View>(R.id.popBack).setOnClickListener {
            popBack()
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        K.m(javaClass)
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        K.m(javaClass)
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        K.m(javaClass)
        return super.onCreateAnimator(transit, enter, nextAnim)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        K.m(javaClass)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        K.m(javaClass)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        K.m(javaClass)
    }

    override fun onStart() {
        super.onStart()
        K.m(javaClass)
    }

    override fun onResume() {
        super.onResume()
        K.m(javaClass)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        K.m(javaClass)
    }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
        super.onMultiWindowModeChanged(isInMultiWindowMode)
        K.m(javaClass)
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode)
        K.m(javaClass)
    }

    override fun onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment: Boolean) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment)
        K.m(javaClass)
    }

    override fun onPause() {
        super.onPause()
        K.m(javaClass)
    }

    override fun onStop() {
        super.onStop()
        K.m(javaClass)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        K.m(javaClass)
    }

    override fun onDestroy() {
        super.onDestroy()
        K.m(javaClass)
    }

    override fun onDetach() {
        super.onDetach()
        K.m(javaClass)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        K.m(javaClass)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        K.m(javaClass)
    }

    override fun onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu()
        K.m(javaClass)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        K.m(javaClass)
    }

    override fun onOptionsMenuClosed(menu: Menu) {
        super.onOptionsMenuClosed(menu)
        K.m(javaClass)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return super.onContextItemSelected(item)
        K.m(javaClass)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        K.m(javaClass)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        K.m(javaClass)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        K.m(javaClass)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        K.m(javaClass)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        K.m(javaClass)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        K.m(javaClass)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        K.m(javaClass)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        K.m(javaClass)
        return super.onGetLayoutInflater(savedInstanceState)
    }

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        K.m(javaClass)
    }

    override fun onInflate(activity: Activity, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(activity, attrs, savedInstanceState)
        K.m(javaClass)
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        K.m(javaClass)
    }
}