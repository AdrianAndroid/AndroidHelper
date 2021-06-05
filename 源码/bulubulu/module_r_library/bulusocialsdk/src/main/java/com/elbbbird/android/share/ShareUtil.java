package com.elbbbird.android.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.elbbbird.android.socialsdk.R;
import com.elbbbird.android.socialsdk.model.SocialShareScene;
import com.flannery.kuwowebview.KwWebView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.KwDirs;
import cn.kuwo.common.utilscode.UtilsCode;


public class ShareUtil {
    public static Bitmap createImage(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(UtilsCode.INSTANCE.getScreenWidth(), View.MeasureSpec.EXACTLY);
        view.measure(widthMeasureSpec, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    public static ArrayList<ShareItem> buildShareItem() {
        ArrayList list = new ArrayList();

        list.add(new ShareItem(1, R.drawable.layer_share_pic_, "生成图片"));
        list.add(new ShareItem(2, R.drawable.layer_share_moments, "朋友圈"));
        list.add(new ShareItem(3, R.drawable.layer_share_wechat, "微信好友"));
        list.add(new ShareItem(4, R.drawable.layer_share_qq, "QQ好友"));
        list.add(new ShareItem(5, R.drawable.share_qzone, "QQ空间"));

        return list;
    }

    public static SocialShareScene getSocialShareScene(String shareId, Bitmap bitmap, int shareType, String desc) {
        return new SocialShareScene(shareId, "PictureSelector", shareType, bitmap, desc);
    }

    public static SocialShareScene getSocialShareScene(String shareId, int shareType, String title, String desc, String url) {
        return new SocialShareScene(shareId, shareType, title, desc, "", url);
    }

    public static View generateShareView(Context context, int resId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(resId, null, false);
        return rootView;
    }

    public static Bitmap createWebImage(KwWebView webView) {

        //X5内核不能兼容onDraw方法
        Picture picture = webView.capturePicture();
        int width = picture.getWidth();
        int height = picture.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        picture.draw(canvas);
        return bitmap;
    }

    // 保存图片
    public static String saveCaptureWebView(KwWebView webView, boolean showSaveToast) {
        Bitmap saveBmp = webView.captureShareBitmap();
        String picFilePath = KwDirs.getCacheBitmapAbsolutePath(System.currentTimeMillis() + ".jpg");
        if (saveBmp != null) {
            boolean save = UtilsCode.INSTANCE.save(saveBmp, picFilePath, Bitmap.CompressFormat.JPEG);
            if (save) {
                // 保存成功
                MediaScannerConnection.scanFile(App.getInstance(), new String[]{picFilePath}, null, null);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(new File(picFilePath)));
                App.getInstance().sendBroadcast(intent);
                if (App.DEBUG) {
                    if (showSaveToast)
                        UtilsCode.INSTANCE.showShort("保存图片到：" + picFilePath + " , 大小：" + saveBmp.getByteCount() / 1000);
                } else {
                    if (showSaveToast) UtilsCode.INSTANCE.showShort("保存图片到：" + picFilePath);
                }
            } else {
                // 保存失败
                if (showSaveToast) UtilsCode.INSTANCE.showShort("保存图片失败！");
            }
            saveBmp.recycle();
        }
        return picFilePath;
    }

    public static String captureWebView(KwWebView webView, Boolean showSaveToast) {


        Bitmap bitmap = webView.captureShareBitmap();

        View qrView = generateShareView(webView.getContext(), R.layout.view_share_web);
        Bitmap qrBitmap = createImage(qrView);
        int backColor = ContextCompat.getColor(App.getInstance(), R.color.colorWindowBackground);


        if (bitmap.getWidth() < qrBitmap.getWidth()) {
            // 比他小
            bitmap = UtilsCode.INSTANCE.scale(bitmap, qrBitmap.getWidth(), bitmap.getHeight() * qrBitmap.getWidth() / bitmap.getWidth());
        } else {
            // 比他大
            qrBitmap = UtilsCode.INSTANCE.scale(qrBitmap, bitmap.getWidth(), qrBitmap.getHeight() * bitmap.getWidth() / qrBitmap.getWidth());
        }

        Bitmap saveBmp = mosaicBitmapVertical(backColor, bitmap, qrBitmap);

        String baseRootDir = UtilsCode.INSTANCE.getSDCardPathByEnvironment();
        if (TextUtils.isEmpty(baseRootDir)) {
            UtilsCode.INSTANCE.showShort("SD卡不可用");
            return baseRootDir;
        }

        String picFilePath = KwDirs.getCacheBitmapAbsolutePath(System.currentTimeMillis() + ".jpg");
        boolean saveFlag = UtilsCode.INSTANCE.save(saveBmp, picFilePath, Bitmap.CompressFormat.JPEG);
        if (saveFlag) {
            MediaScannerConnection.scanFile(App.getInstance(), new String[]{picFilePath}, null, null);
            if (showSaveToast) UtilsCode.INSTANCE.showShort("保存图片到：" + picFilePath);
        } else {
            if (showSaveToast) UtilsCode.INSTANCE.showShort("保存图片失败！");
        }

        bitmap.recycle();
        qrBitmap.recycle();
        saveBmp.recycle();
        return picFilePath;
    }


    //bitmaps 需要拼接的图片，按顺序传入
    public static Bitmap mosaicBitmapVertical(int backColor, @NonNull Bitmap... bitmaps) {
        int height = 0;
        for (int i = 0; i < bitmaps.length; i++) {
            height += bitmaps[i].getHeight();
        }
        int width = 0;
        for (int i = 0; i < bitmaps.length; i++) {
            if (width < bitmaps[i].getWidth()) {
                width = bitmaps[i].getWidth();
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(backColor);
        int drawHeight = 0;
        for (int i = 0; i < bitmaps.length; i++) {
            int left = (width - bitmaps[i].getWidth()) / 2;
            canvas.drawBitmap(bitmaps[i], left, drawHeight, null);
            drawHeight += bitmaps[i].getHeight();
        }
        return result;
    }

    // 保存bitmap文件
    public static void saveFile(Bitmap bm, String fileName, String pathFolder) throws IOException {
        File folder = new File(pathFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File myCaptureFile = new File(pathFolder, fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    //保存成功后，还要发一个系统广播通知手机有图片更新
    public static void notifySystem(Context context, File path) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(path));
        context.sendBroadcast(intent);
    }
}
