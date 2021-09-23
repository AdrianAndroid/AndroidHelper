package cn.kuwo.common.utilscode

import android.app.Activity
import android.app.Application
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.blankj.utilcode.util.*
import java.io.File
import java.text.DateFormat
import java.util.*

// UtilsCode， 便于隔离第三方库
public object UtilsCode {
    private fun checkInitialize() {
        if (Utils.getApp() != null) {
            error("Assertion failed, 使用前请先在Application中初始化")
        }
    }

    fun initApplication(context: Application) {
        Utils.init(context) // 初始化工具类
    }

    fun getTopActivity(): Activity? {
        checkInitialize()
        return ActivityUtils.getTopActivity()
    }

    fun showShort(str: String) {
        ToastUtils.showShort(str)
    }

    fun showLong(str: String) {
        ToastUtils.showLong(str)
    }

    fun getApp(): Application {
        return Utils.getApp()
    }


    ///////////////////
    //FileIOUtils
    //////////////////
    fun readFile2BytesByChannel(filePath: String): ByteArray? {
        return FileIOUtils.readFile2BytesByChannel(filePath);
    }


    ///////////////////
    //FileUtils
    //////////////////
    fun isFileExists(fileName: String): Boolean {
        return FileUtils.isFileExists(fileName)
    }

    fun getFileExtension(filePath: String): String? {
        return FileUtils.getFileExtension(filePath)
    }

    fun isFileExists(fileName: File): Boolean {
        return FileUtils.isFileExists(fileName)
    }

    fun createOrExistsDir(fileName: String): Boolean {
        return FileUtils.createOrExistsDir(fileName)
    }


    fun createOrExistsDir(fileName: File): Boolean {
        return FileUtils.createOrExistsDir(fileName)
    }

    fun deleteFile(fileName: String): Boolean {
        return FileUtils.deleteFile(fileName)
    }


    fun deleteFile(fileName: File): Boolean {
        return FileUtils.deleteFile(fileName)
    }

    ///////////////////
    //TimeUtils
    //////////////////
    fun millis2String(millis: Long): String? {
        return TimeUtils.millis2String(millis)
    }

    fun millis2String(millis: Long, format: DateFormat): String? {
        return TimeUtils.millis2String(millis, format)
    }

    fun string2Date(time: String?, @NonNull format: DateFormat): Date? {
        return TimeUtils.string2Date(time, format)
    }

    fun date2String(date: Date?, format: DateFormat): String? {
        return TimeUtils.date2String(date, format)
    }

    fun isNotEmpty(obj: Any): Boolean {
        return ObjectUtils.isNotEmpty(obj)
    }

    fun isEmpty(obj: Any): Boolean {
        return ObjectUtils.isEmpty(obj)
    }

    fun getFriendlyTimeSpanByNow(millis: Long): String? {
        return TimeUtils.getFriendlyTimeSpanByNow(millis)
    }

    fun getZodiac(date: Date?): String? {
        return TimeUtils.getZodiac(date)
    }


    ///////////////////
    //StringUtils
    //////////////////
    fun null2Length0(p0: String?): String {
        return StringUtils.null2Length0(p0)
    }

    fun equals(s1: CharSequence?, s2: CharSequence?): Boolean {
        return StringUtils.equals(s1, s2)
    }


    ///////////////////
    //ResourceUtils
    //////////////////
    fun readAssets2String(assetsFilePath: String): String {
        return ResourceUtils.readAssets2String(assetsFilePath);
    }


    ///////////////////
    //ImageUtils
    //////////////////
    fun save(src: Bitmap, file: File, format: Bitmap.CompressFormat): Boolean {
        return ImageUtils.save(src, file, format);
    }

    fun save(src: Bitmap, fileName: String, format: Bitmap.CompressFormat): Boolean {
        return ImageUtils.save(src, fileName, format);
    }

    fun view2Bitmap(view: View?): Bitmap? {
        return ImageUtils.view2Bitmap(view)
    }

    fun bitmap2Drawable(bitmap: Bitmap): Drawable? {
        return ImageUtils.bitmap2Drawable(bitmap)
    }

    fun getBitmap(@DrawableRes resId: Int): Bitmap? {
        return ImageUtils.getBitmap(resId)
    }

    fun toRound(bitmap: Bitmap?): Bitmap? {
        return ImageUtils.toRound(bitmap)
    }


    fun save(
        src: Bitmap?,
        filePath: String?,
        format: CompressFormat?,
        recycle: Boolean
    ): Boolean {
        return ImageUtils.save(src, filePath, format, recycle)
    }


    fun scale(src: Bitmap?, newWidth: Int, newHeight: Int): Bitmap? {
        return ImageUtils.scale(src, newWidth, newHeight, false)
    }


    ///////////////////
    //ScreenUtils
    //////////////////
    fun getScreenWidth(): Int {
        return ScreenUtils.getScreenWidth()
    }

    fun getScreenHeight(): Int {
        return ScreenUtils.getScreenHeight()
    }

    ///////////////////
    //SDCardUtils
    //////////////////
    fun getSDCardPathByEnvironment(): String? {
        return SDCardUtils.getSDCardPathByEnvironment()
    }

    fun getSDCardPaths(): MutableList<String>? {
        return SDCardUtils.getSDCardPaths()
    }

    ///////////////////
    //AppUtils
    //////////////////
    fun getAppVersionCode(): Int {
        return AppUtils.getAppVersionCode()
    }

    fun getAppVersionName(): String? {
        return AppUtils.getAppVersionName()
    }


    ///////////////////
    //DeviceUtils
    //////////////////
    fun getAndroidID(): String {
        return DeviceUtils.getAndroidID()
    }

    fun getModel(): String? {
        return DeviceUtils.getModel()
    }


    ///////////////////
    //ThreadUtils
    //////////////////
    /**
     * Executes the given task in a cached thread pool.
     *
     * @param task The task to execute.
     * @param <T>  The type of the task's result.
    </T> */
    fun <T> executeByCached(doInBackground: () -> T) {
        ThreadUtils.executeByCached(object : ThreadUtils.SimpleTask<T>() {
            override fun doInBackground(): T {
                return doInBackground.invoke()
            }

            override fun onSuccess(result: T) {

            }

        })
    }

    fun <T> executeByCached(doInBackground: () -> T, onSuccess: ((T) -> Unit)? = null) {
        ThreadUtils.executeByCached(object : ThreadUtils.SimpleTask<T>() {
            override fun doInBackground(): T {
                return doInBackground.invoke()
            }

            override fun onSuccess(result: T) {
                onSuccess?.invoke(result)
            }

        })
    }

    fun <T> executeByCached(
        doInBackground: () -> T,
        onSuccess: ((T) -> Unit)? = null,
        onCancel: (() -> Unit)? = null,
        onFail: ((Throwable?) -> Unit)? = null
    ) {
        ThreadUtils.executeByCached(object : ThreadUtils.Task<T>() {
            override fun doInBackground(): T {
                return doInBackground.invoke()
            }

            override fun onSuccess(result: T) {
                onSuccess?.invoke(result)
            }

            override fun onCancel() {
                onCancel?.invoke()
            }

            override fun onFail(t: Throwable?) {
                onFail?.invoke(t)
            }

        })
    }


    ///////////////////
    //SizeUtils
    //////////////////
    fun dp2px(dpValue: Float): Int {
        return SizeUtils.dp2px(dpValue)
    }

    fun sp2px(spValue: Float): Int {
        return SizeUtils.sp2px(spValue)
    }


    ///////////////////
    //NetworkUtils
    //////////////////
    fun isConnected(): Boolean {
        return NetworkUtils.isConnected()
    }

    fun isWifiConnected(): Boolean {
        return NetworkUtils.isWifiConnected()
    }


    ///////////////////
    //FragmentUtils
    //////////////////
    public fun getTopShow(fragmentManager: FragmentManager): Fragment? {
        return FragmentUtils.getTopShow(fragmentManager)
    }

}
