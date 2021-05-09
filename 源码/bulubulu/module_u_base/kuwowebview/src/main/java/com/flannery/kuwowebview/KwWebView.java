package com.flannery.kuwowebview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.util.AttributeSet;
import android.widget.Toast;

import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.sdk.WebView;

import java.util.Map;

/**
 * @date: 2018/5/11
 * @author: Yaj
 */

/**
 * 目前，由于X5 SDK WebView所提供的WebView类，是对系统WebView的聚合包装，所以：获取系统内核的WebView或者 x5内核的WebView的宽高
 * <p>
 * android.webkit.WebView webView = new android.webkit.WebView(this);
 * <p>
 * int width = webView.getWidth();
 * <p>
 * 需要采用下面的方式进行
 * <p>
 * com.tencent.smtt.sdk.WebView webView = new com.tencent.smtt.sdk.WebView(this);
 * <p>
 * int width = webView.getView().getWidth();
 */

public class KwWebView extends WebView {
    public KwWebView(Context context) {
        super(context);
    }

    public KwWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public KwWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public KwWebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
    }

    public KwWebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
    }

//
//    private Bitmap getLongCapture() {
//        WebView webview = this;
//        webview.measure(
//                MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED),
//                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
//        );
//        webview.setDrawingCacheEnabled(true);
//        webview.buildDrawingCache();
//        Bitmap longImage = Bitmap.createBitmap(
//                webview.getMeasuredWidth(),
//                webview.getMeasuredHeight(),
//                Bitmap.Config.ARGB_8888
//        );
//        // 画布的宽高和webview的网页保持一致
//        Canvas canvas = new Canvas(longImage);
//        // 画布的宽高和webview的网页保持一致
//        Canvas longCanvas = new Canvas(longImage);
//        Paint paint = new Paint();
//        canvas.drawBitmap(longImage, 0, webview.getMeasuredHeight(), paint);
//        float scale = getResources().getDisplayMetrics().density;
//
//        Bitmap x5Bitmap = Bitmap.createBitmap(
//                webview.getWidth(),
//                webview.getHeight(),
//                Bitmap.Config.ARGB_8888
//        );
//        Canvas x5Canvas = new Canvas(x5Bitmap);
//        x5Canvas.drawColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
//        // 少了这行代码就无法生成长图
//        webview.getX5WebViewExtension().snapshotWholePage(x5Canvas, false, false);
//
//        Matrix matrix = new Matrix();
//        matrix.setScale(scale, scale);
//        longCanvas.drawBitmap(x5Bitmap, matrix, paint);
//
//        //if(longImage != null) {
//        //     PhotoAssist.saveBitmap(longImage);
//        //     保存成功
//        //} else {
//        //     保存失败
//        //}
//
//        return longImage;
//    }
//
//    private Bitmap captureWebView() {
//        int wholeWidth = computeHorizontalScrollRange();
//        int wholeHeigt = computeVerticalScrollRange();
//        Bitmap x5bitmap = Bitmap.createBitmap(wholeWidth, wholeHeigt, Bitmap.Config.ARGB_8888);
//
//        Canvas x5canvas = new Canvas(x5bitmap);
//        x5canvas.scale(
//                (float) wholeWidth / (float) getContentWidth(),
//                (float) wholeHeigt / (float) getContentHeight()
//        );
//
//        if (getX5WebViewExtension() != null) {
//            getX5WebViewExtension().snapshotWholePage(x5canvas, false, false);
//        }
//        return x5bitmap;
//    }
//
//
//    private Bitmap captureScreenForRecord() {
//        // x5webview 不适合draw
//        measure(
//                MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED),
//                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
//        );
//        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
//        setDrawingCacheEnabled(true);
//        buildDrawingCache();
//
//        Bitmap bm = Bitmap.createBitmap(
//                getMeasuredWidth(),
//                getMeasuredHeight(),
//                Bitmap.Config.RGB_565
//        );
//        Canvas bitcanvas = new Canvas(bm);
//        Paint paint = new Paint();
//        int iHeight = bm.getHeight();
//        bitcanvas.drawBitmap(bm, 0, iHeight, paint);
//        draw(bitcanvas);
//        return bm;
//    }
//
//    private Bitmap captureWebViewFromShareUtil() {
//        // x5 内核不能兼容onDraw方法
//        Picture picture = capturePicture();
//        int width = picture.getWidth();
//        int height = picture.getHeight();
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
//        Canvas canvas = new Canvas(bitmap);
//        picture.draw(canvas);
//
//        return bitmap;
//    }
//
//
//    private Bitmap generateShareBitmap() {
//        //inflate(getContext(), R.layout.view_share);
//        return null;
//    }
//
//    private Bitmap captureWebViewX5ExtensionNotNull() {
//        WebView webView = this;
//
//        int wholeWidth = webView.computeHorizontalScrollRange();
//        int wholeHeight = webView.computeVerticalScrollRange();
//        Bitmap x5bitmap = Bitmap.createBitmap(wholeWidth, wholeHeight, Bitmap.Config.ARGB_8888);
//
//        Canvas x5canvas = new Canvas(x5bitmap);
//        x5canvas.scale((float) wholeWidth / (float) webView.getContentWidth(), (float) wholeHeight / (float) webView.getContentHeight());
//        if (webView.getX5WebViewExtension() == null) {
//            return null;
//        }
//
//        webView.getX5WebViewExtension().snapshotWholePage(x5canvas, false, false);
//        return x5bitmap;
//        //保存自己实现吧
//    }
//
//
//    private Bitmap captureX5WebViewUnsharpHigh() {
//        setDrawingCacheEnabled(true);
//        buildDrawingCache(true);
//
//        int wholeWidth = computeHorizontalScrollRange();
//        int wholeHeight = computeVerticalScrollRange();
//
//        Bitmap wholePageBitmap = Bitmap.createBitmap(wholeWidth, wholeHeight, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(wholePageBitmap);
//        canvas.scale(wholeWidth / getContentWidth(), wholeHeight / getContentHeight());
//        if (getX5WebViewExtension() == null) return null;
//        getX5WebViewExtension().snapshotWholePage(canvas, false, false);
//
//
//        setDrawingCacheEnabled(false);
//        buildDrawingCache(false);
//        return wholePageBitmap;
//    }

    /**
     * 截出来的图片非常模糊
     *
     * @return
     */
    private Bitmap captureX5WebViewUnsharpLow222() {
// 图片模糊
        setDrawingCacheEnabled(true);
        buildDrawingCache(true);
        int contentWidth = getContentWidth();
        int contentHeight = getContentHeight();

//        int wholeWidth = computeHorizontalScrollRange();
//        int wholeHeight = computeVerticalScrollRange();

        Bitmap bitmap = Bitmap.createBitmap(
                contentWidth,
                contentHeight,
                Bitmap.Config.RGB_565
        );
        Canvas canvas = new Canvas(bitmap);
        IX5WebViewExtension x5WebViewExtension = getX5WebViewExtension();
        if (x5WebViewExtension != null) {
            x5WebViewExtension.snapshotWholePage(canvas, false, false);
        } else {
//            UtilsCode.INSTANCE.showShort("getX5WebViewExtension() == null");
//            Uti
        }
        setDrawingCacheEnabled(false);
        buildDrawingCache(false);
        // bitmap缩放
        return bitmap;
    }


    // 分享
    public Bitmap captureShareBitmap() {
        Bitmap longCapture = null;
        WebView x5webView = this;
        if (x5webView.getX5WebViewExtension() == null) {
            longCapture = captureWebViewFromShareUtil(x5webView);
        } else {
            try {
                longCapture = captureX5WebViewUnsharpHigh(x5webView);
            } catch (OutOfMemoryError e) {
                longCapture = captureX5WebViewUnsharpLow(x5webView);
                if (BuildConfig.DEBUG)
                    Toast.makeText(getContext(), "OutOfMemoryError", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        return longCapture;
    }

    // 低清晰度的长截图
    private Bitmap captureX5WebViewUnsharpLow(WebView x5webView) {
        IX5WebViewExtension x5WebViewExtension = x5webView.getX5WebViewExtension();
        if (x5WebViewExtension != null) {
            x5webView.setDrawingCacheEnabled(true);
            x5webView.buildDrawingCache(true);
            int contentWidth = x5webView.getContentWidth();
            int contentHeight = x5webView.getContentHeight();

            Bitmap bitmap = Bitmap.createBitmap(contentWidth, contentHeight, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            x5WebViewExtension.snapshotWholePage(canvas, false, false);
            x5webView.setDrawingCacheEnabled(false);
            x5webView.buildDrawingCache(false);
            return bitmap;
        } else {
            return null;
        }
    }

    // 高清晰度的长截图
    private Bitmap captureX5WebViewUnsharpHigh(WebView x5webView) {

        return captureX5WebViewUnsharpLow(x5webView);

//        IX5WebViewExtension x5WebViewExtension = x5webView.getX5WebViewExtension();
//        if (x5WebViewExtension != null) {
//            x5webView.buildDrawingCache();
//
//            int wholeWidth = x5webView.computeHorizontalScrollRange();
//            int wholeHeight = x5webView.computeVerticalScrollRange();
//
//            Bitmap wholePageBitmap = Bitmap.createBitmap(wholeWidth, wholeHeight, Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(wholePageBitmap);
//            int contentWidth = x5webView.getContentWidth();
//            int contentHeight = x5webView.getContentHeight();
//
//
//            float sx = 1;
//            float sy = 1;
//            if (contentWidth != 0) sx = wholeWidth / contentWidth;
//            if (contentHeight != 0) sy = wholeHeight / contentHeight;
//            canvas.scale(sx, sy);
//
//            x5WebViewExtension.snapshotWholePage(canvas, false, false);
//            return wholePageBitmap;
//        } else {
//            return null;
//        }
    }

    private Bitmap captureWebViewFromShareUtil(WebView x5webView) {
        Picture picture = x5webView.capturePicture();
        int width = picture.getWidth();
        int height = picture.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        picture.draw(canvas);
        return bitmap;
    }


}
