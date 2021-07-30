package com.joyy.log_core.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import java.io.File

/**
 * Time:2021/7/29 16:58
 * Author: flannery
 * Description:
 */

object DiskCacheUtils {

    private const val EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE"

    @JvmStatic
    fun getCacheDir(context: Context, uniqueName: String): File {
        return getCacheDir(context, false, uniqueName)
    }

    @JvmStatic
    fun getCacheDir(context: Context, internal: Boolean, uniqueName: String): File {
        val cachePath: String = if (internal) {
            context.cacheDir.path
        } else {
            // Check if media is mounted or storage is built-in, if so, try and use external cache dir
            // otherwise use internal cache dir
            if (isExternalStorageAvailable || !isExternalStorageRemovable()) {
                getExternalCacheDir(context)?.path ?: context.cacheDir.path
            } else {
                context.cacheDir.path
            }
        }

        return File(cachePath + File.separator + uniqueName)
    }

    private fun isExternalStorageRemovable(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            Environment.isExternalStorageRemovable()
        } else true
    }

    private fun getExternalCacheDir(context: Context): File? {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            return context.externalCacheDir
        }

        return if (hasExternalStoragePermission(context)) {
            File(Environment.getExternalStorageDirectory().path)
        } else {
            context.externalCacheDir
        }
    }

    private val isExternalStorageAvailable: Boolean
        get() {
            return checkExternalStorageAvailable()
        }

    private fun checkExternalStorageAvailable(): Boolean {
        var mExternalStorageAvailable = false
        var mExternalStorageWriteable = false
        val state = Environment.getExternalStorageState()

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageWriteable = true
            mExternalStorageAvailable = mExternalStorageWriteable
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true
            mExternalStorageWriteable = false
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageWriteable = false
            mExternalStorageAvailable = mExternalStorageWriteable
        }
        return mExternalStorageAvailable
    }

    private fun hasExternalStoragePermission(context: Context): Boolean {
        return context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION) == PackageManager.PERMISSION_GRANTED
    }
}