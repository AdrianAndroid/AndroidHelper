package com.joyy.loglibrary.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import java.io.File

/**
 * Time:2021/7/27 15:37
 * Author:
 * Description:
 */
object StorageUtils {
    private const val EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE"

    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> if card is mounted and app has appropriate permission. Else -
     * Android defines cache directory on device's file system.
     *
     * @param context Application context
     * @return Cache {@link java.io.File directory}
     */
    fun getCacheDir(context: Context): File {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            return context.externalCacheDir!! // 暂时不允许为空
        }
        var appCacheDir: File = context.cacheDir
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() && hasExternalStoragePermission(context)) {
            getExternalCacheDir(context)?.let {
                appCacheDir = it
            }
        }
        return appCacheDir
    }

    private fun getExternalCacheDir(context: Context): File? {
        //可写，return
        val file = context.externalCacheDir
        if (file != null && file.canWrite()) {
            return context.externalCacheDir
        }

        val dataDir = File(File(Environment.getExternalStorageDirectory(), "Android"), "data")
        val appCacheDir = File(File(dataDir, context.packageName), "cache")
        //不存在并且创建失败，return null ； 不可写，return null
        if ((!appCacheDir.exists() && !appCacheDir.mkdirs()) || !appCacheDir.canWrite()) {
            return null
        }
        return appCacheDir
    }

    private fun hasExternalStoragePermission(context: Context): Boolean {
        return context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION) == PackageManager.PERMISSION_GRANTED
    }
}