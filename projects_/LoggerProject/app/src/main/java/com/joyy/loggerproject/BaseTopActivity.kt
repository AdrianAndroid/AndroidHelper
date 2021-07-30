package com.joyy.loggerproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.io.FileDescriptor
import java.io.PrintWriter

/**
 * Time:2021/7/29 10:47
 * Author: flannery
 * Description:
 */
open class BaseTopActivity : AppCompatActivity() {

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        L.t()
        return super.onCreateView(parent, name, context, attrs)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        L.t()
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        L.t()
    }

    override fun onStart() {
        super.onStart()
        L.t()
    }

    override fun onResume() {
        super.onResume()
        L.t()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        L.t()
    }

    override fun onPause() {
        super.onPause()
        L.t()
    }

    override fun onStop() {
        super.onStop()
        L.t()
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        L.t()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        L.t()
    }

    override fun startActivityFromFragment(fragment: Fragment, intent: Intent?, requestCode: Int) {
        super.startActivityFromFragment(fragment, intent, requestCode)
        L.t()
    }

    override fun startActivityFromFragment(fragment: Fragment, intent: Intent?, requestCode: Int, options: Bundle?) {
        super.startActivityFromFragment(fragment, intent, requestCode, options)
        L.t()
    }

    override fun dump(prefix: String, fd: FileDescriptor?, writer: PrintWriter, args: Array<out String>?) {
        super.dump(prefix, fd, writer, args)
        L.t()
    }

    override fun onDestroy() {
        super.onDestroy()
        L.t()
    }


}