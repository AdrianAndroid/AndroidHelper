package com.flannery.webview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.webkit.WebSettings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ToastUtils
import com.tencent.smtt.sdk.WebView
import kotlinx.android.synthetic.main.activity_x5_web_view.*
import java.io.File

class X5WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_x5_web_view)

        X5.init(this)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            x5webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        x5webView.settings.blockNetworkImage = false
        x5webView.settings.javaScriptEnabled = true
        x5webView.settings.loadWithOverviewMode = true
        x5webView.isHorizontalScrollBarEnabled = false
//        x5webView.loadUrl("https://open.toutiao.com/a6885912394325983757/?utm_campaign=open&utm_medium=webview&utm_source=mi_llq_api&req_id=20210113111332010198059022113D6BB4&dt=Redmi+K30+5G&label=open_news_xiaomi&a_t=3016136204061682943934529566e313&gy=874f5cf5fd87903004c69753d5fefe7f5904aeaf0a593c437e8ffcc3489fbc0aec08acfe531a9ddc87b77b8bcdfd2fe7c3ae350b530a30261a219352c5003dcd25f5e143af29a0956893080058412d9908d0cfdc2d9bb1809be45664a60eed81a5311797bdb8b69851b697a1d70ae7726fd354c12744017f7e74d33f8b230b09&crypt=2042&item_id=6885912394325983757&docid=6885912394325983757&cp=cn-toutiao&itemtype=news&version=2&mibusinessId=miuibrowser&env=production&category=news_entertainment&cateCode=%E6%8E%A8%E8%8D%90&mi_source=miuibrowser&share=wechat")
//        x5webView.loadUrl("https://open.toutiao.com/a6901975378982765064/?utm_campaign=open&utm_medium=webview&utm_source=mi_llq_api&req_id=202101131528280101980650414E12E74F&dt=Redmi+K30+5G&gy=65ad2eac522e54eda37f4b958cc9b2258fcae4e3587cebbbacb7c9f6503a8a4a5218dfd0dcf1983b7cc5dbbd3d227d2b97ca2c1c560481ebcd32b32372b5993d43aac8d6411f961390a07becf771be2a947a1b9ec9c0ef25872eca119534f1bed9af3412059e53154e4d4d1b9d4aa95bf01d6a1cc4531385c37786677899dff2&crypt=569&label=open_news_xiaomi&a_t=3016136204061682943934529566e313&item_id=6901975378982765064&docid=6901975378982765064&cp=cn-toutiao&itemtype=news&version=2&mibusinessId=miuibrowser&env=production&category=news_edu&cateCode=%E6%8E%A8%E8%8D%90&mi_source=miuibrowser&share=wechat")
        x5webView.loadUrl("https://baike.baidu.com/item/%E7%99%BE%E5%BA%A6/6699")

        xtCapture.setOnClickListener {
            captureShareBitmap()
        }
    }


    public fun captureShareBitmap() {

        var longCapture: Bitmap? = null
        if (x5webView.x5WebViewExtension == null) {
            longCapture = captureWebViewFromShareUtil(x5webView)
        } else {
            //captureX5WebViewUnsharpLow(x5webView) // 不是高清
            try {
                longCapture = captureX5WebViewUnsharpHigh(x5webView)
            } catch (e: OutOfMemoryError) {
                longCapture = captureX5WebViewUnsharpLow(x5webView)
                e.printStackTrace()
            }
        }
        // 保存
        if (longCapture != null) {
            val value =
                "${baseContext.externalCacheDir.absolutePath}${File.separator}${System.currentTimeMillis()}.jpg"
            ImageUtils.save(
                longCapture,
                value,
                Bitmap.CompressFormat.JPEG
            )
            ToastUtils.showLong(value)
        } else {
            ToastUtils.showLong("bitmap null")
        }
    }

    private fun captureX5WebViewUnsharpHigh(x5webView: WebView): Bitmap? {
        if (x5webView.x5WebViewExtension != null) {
            x5webView.isDrawingCacheEnabled = true
            x5webView.buildDrawingCache(true)

            val wholeWidth = x5webView.computeHorizontalScrollRange()
            val wholeHeight = x5webView.computeVerticalScrollRange()

            val wholePageBitmap = Bitmap.createBitmap(
                wholeWidth,
                wholeHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(wholePageBitmap)
            canvas.scale(
                (wholeWidth / x5webView.contentWidth).toFloat(),
                (wholeHeight / x5webView.contentHeight).toFloat()
            )
            x5webView.x5WebViewExtension.snapshotWholePage(canvas, false, false)

            x5webView.isDrawingCacheEnabled = false
            x5webView.buildDrawingCache(false)
            return wholePageBitmap
        } else {
            return null
        }
    }


    private fun captureWebViewFromShareUtil(x5webView: WebView): Bitmap? {
        val picture = x5webView.capturePicture()
        val width = picture.width
        val height = picture.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        picture.draw(canvas)
        return bitmap
    }

    // 图片模糊
    private fun captureX5WebViewUnsharpLow(x5webView: WebView): Bitmap? {
        val x5WebViewExtension = x5webView.x5WebViewExtension;
        if (x5WebViewExtension != null) {
            x5webView.isDrawingCacheEnabled = true
            x5webView.buildDrawingCache(true)
            val contentWidth = x5webView.contentWidth
            val contentHeight = x5webView.contentHeight

            val bitmap = Bitmap.createBitmap(
                contentWidth,
                contentHeight,
                Bitmap.Config.RGB_565
            )
            val canvas = Canvas(bitmap)
            x5WebViewExtension.snapshotWholePage(canvas, false, false)
            x5webView.isDrawingCacheEnabled = false
            x5webView.buildDrawingCache(false)

            return bitmap
        } else {
            ToastUtils.showShort("getX5WebViewExtension() != null")
            return null
        }
    }


}