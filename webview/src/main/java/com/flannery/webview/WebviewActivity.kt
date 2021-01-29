package com.flannery.webview

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_webview.*
import java.io.File

class WebviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)





        button.setOnClickListener {
            if (!TextUtils.isEmpty(edittext.text)) {
                webView.loadUrl(edittext.text.toString())
            }
        }

        capture.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                captureWebView()
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), 10001
                )
            }
        }

        initWebView()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        ToastUtils.showShort("$requestCode")
    }


    // 截图
    private fun captureWebView() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                WebView.enableSlowWholeDocumentDraw() // 全局的
            }
            val bitmap: Bitmap?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                bitmap = captureWebViewLollipop(webView)
            } else {
                bitmap = captureWebViewKitKat(webView)
            }
            if (bitmap != null) {
                val value =
                    "${baseContext.externalCacheDir.absolutePath}${File.separator}${System.currentTimeMillis()}.jpg"
                ImageUtils.save(
                    bitmap,
                    value,
                    Bitmap.CompressFormat.JPEG
                )
                ToastUtils.showLong(value)
            } else {
                ToastUtils.showLong("bitmap null")
            }
        } catch (e: OutOfMemoryError) {
            ToastUtils.showShort(e.message)
        }
    }


    private fun captureWebViewKitKat(webView: WebView): Bitmap? {
        val picture = webView.capturePicture()
        val width = picture.width
        val height = picture.height
        if (width > 0 && height > 0) {
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            val canvas = Canvas()
            picture.draw(canvas)
            return bitmap
        }
        return null
    }


    private fun captureWebViewLollipop(webView: WebView): Bitmap? {

        // 生成长图
        webView.measure(
            View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED
            ),
            View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            )
        )
        webView.layout(0, 0, webView.measuredWidth, webView.measuredHeight)
        webView.isDrawingCacheEnabled = true;
        webView.buildDrawingCache()
        val longImage = Bitmap.createBitmap(
            webView.measuredWidth,
            webView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(longImage)
        val paint = Paint()
        canvas.drawBitmap(
            longImage,
            0F,
            webView.measuredHeight.toFloat(),
            paint
        )
        webView.draw(canvas)
        return longImage

        // 以下是一屏幕
        // val scale = webView.scale
        // val width = webView.width
        // val height = webView.contentHeight * scale + 0.5
        // val bitmap = Bitmap.createBitmap(width, height.toInt(), Bitmap.Config.ARGB_8888)
        // val canvas = Canvas(bitmap)
        // webView.draw(canvas)
        // return bitmap
    }


    private fun initWebView() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webView.settings.blockNetworkImage = false
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.isHorizontalScrollBarEnabled = false


        webView.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?,
                url: String?
            ): WebResourceResponse? {
                return super.shouldInterceptRequest(view, url)
            }


            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                // 使用webview加载显示的ur
//                view?.loadUrl(request.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        // https://www.lubanjava.com
        webView.loadUrl("https://open.toutiao.com/a6885912394325983757/?utm_campaign=open&utm_medium=webview&utm_source=mi_llq_api&req_id=20210113111332010198059022113D6BB4&dt=Redmi+K30+5G&label=open_news_xiaomi&a_t=3016136204061682943934529566e313&gy=874f5cf5fd87903004c69753d5fefe7f5904aeaf0a593c437e8ffcc3489fbc0aec08acfe531a9ddc87b77b8bcdfd2fe7c3ae350b530a30261a219352c5003dcd25f5e143af29a0956893080058412d9908d0cfdc2d9bb1809be45664a60eed81a5311797bdb8b69851b697a1d70ae7726fd354c12744017f7e74d33f8b230b09&crypt=2042&item_id=6885912394325983757&docid=6885912394325983757&cp=cn-toutiao&itemtype=news&version=2&mibusinessId=miuibrowser&env=production&category=news_entertainment&cateCode=%E6%8E%A8%E8%8D%90&mi_source=miuibrowser&share=wechat")
        // webView.loadUrl("http://www.baidu.com")
    }


}