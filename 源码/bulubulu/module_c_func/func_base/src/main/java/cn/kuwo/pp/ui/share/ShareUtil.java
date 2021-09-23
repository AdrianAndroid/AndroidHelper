package cn.kuwo.pp.ui.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Picture;
import android.media.MediaScannerConnection;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elbbbird.android.share.ShareItem;
import com.elbbbird.android.socialsdk.Constants;
import com.elbbbird.android.socialsdk.SocialSDK;
import com.elbbbird.android.socialsdk.model.SocialShareScene;
import com.flannery.kuwowebview.KwWebView;

import java.util.ArrayList;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.KwDirs;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.R;

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
        return new SocialShareScene(shareId, "", shareType, bitmap, desc);
    }

    public static SocialShareScene getSocialShareScene(String shareId, String bitmapPath, int shareType, String desc) {
//        return new SocialShareScene(shareId, "", shareType, bitmap, desc);
        return new SocialShareScene(shareId, shareType, bitmapPath, desc);
    }

    public static SocialShareScene getSocialShareScene(String shareId, int shareType, String title, String desc, String url) {
        return new SocialShareScene(shareId, shareType, title, desc, "", url);
    }

    public static View generateShareView(Context context, int resId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(resId, null, false);
        return rootView;
    }

    public static void onItemClick(ShareCardDialog dialog, BaseQuickAdapter adapter, View view, int position) {
        View shareView = ShareUtil.generateShareView(dialog.getContext(), dialog.getLayoutResId());
        dialog.setPreviewInfo(shareView, true);

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = ShareUtil.createImage(shareView);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        String filePath = KwDirs.getDCIMDir/*getCacheBitmapAbsolutePath*/(null);
//                        String fileName = System.currentTimeMillis() + ".jpg";
//                        String fileNamePath = filePath + fileName;
//                        cn.kuwo.common.util.ImageUtils.saveBitmap2DCIM(dialog.getContext(), bitmap, filePath, fileName);

                        String fileNamePath = KwDirs.getFilePictureDir(System.currentTimeMillis() + ".jpg");
                        UtilsCode.INSTANCE.save(bitmap, fileNamePath, Bitmap.CompressFormat.JPEG);

                        SocialShareScene scene;
                        ShareItem item = (ShareItem) adapter.getData().get(position);
                        switch (item.getId()) {
                            case 1:
                                //ShareUtil.shareToPhoto(dialog, shareView);
                                UtilsCode.INSTANCE.showShort("保存图片到" + fileNamePath);
                                break;
                            case 2: {
                                scene = ShareUtil.getSocialShareScene(dialog.getShareId(), fileNamePath, SocialShareScene.SHARE_TYPE_WECHAT_TIMELINE, "PK分享");
                                SocialSDK.getInstance().shareToWeChatTimeline(dialog.getActivity(), Constants.Weixin.APP_ID, scene);
                            }
                            break;
                            case 3: {
                                scene = ShareUtil.getSocialShareScene(dialog.getShareId(), fileNamePath, SocialShareScene.SHARE_TYPE_WECHAT, "PK分享");
                                SocialSDK.getInstance().shareToWeChat(dialog.getActivity(), Constants.Weixin.APP_ID, scene);
                            }
                            break;
                            case 4: {
                                scene = ShareUtil.getSocialShareScene(dialog.getShareId(), fileNamePath, SocialShareScene.SHARE_TYPE_QQ, "PK分享");
                                SocialSDK.getInstance().shareToQQ(dialog.getActivity(), Constants.QQ.APP_ID, scene);
                            }
                            break;
                            case 5: {
                                scene = ShareUtil.getSocialShareScene(dialog.getShareId(), fileNamePath, SocialShareScene.SHARE_TYPE_QZONE, "PK分享");
                                SocialSDK.getInstance().shareToQZone(dialog.getActivity(), Constants.QQ.APP_ID, scene);
                            }
                            break;
                        }
                    }
                }, 100);
            }
        }, 200);
    }

    public static void shareToPhoto(ShareCardDialog dialog, View rootView) {
        String baseRootDir = UtilsCode.INSTANCE.getSDCardPathByEnvironment();
        if (TextUtils.isEmpty(baseRootDir)) {
            UtilsCode.INSTANCE.showShort("SD卡不可用");
            return;
        }
        String saveFilePath = baseRootDir + "/bulu/pic";
        if (!UtilsCode.INSTANCE.createOrExistsDir(saveFilePath)) {
            UtilsCode.INSTANCE.showShort("创建目录失败");
            return;
        }
        int backColor = ContextCompat.getColor(App.getInstance(), R.color.colorWindowBackground);
        String picFilePath = saveFilePath + "/bulu" + dialog.getShareId() + ".jpg";
        rootView.setBackgroundColor(backColor);
        Bitmap bmp = UtilsCode.INSTANCE.view2Bitmap(rootView);
        rootView.setBackgroundColor(Color.TRANSPARENT);
        Bitmap saveBmp = mosaicBitmapVertical(backColor, bmp);
        if (saveBmp != null) {
            boolean saveFlag = UtilsCode.INSTANCE.save(saveBmp, picFilePath, Bitmap.CompressFormat.JPEG);
            if (saveFlag) {
                MediaScannerConnection.scanFile(dialog.getActivity(), new String[]{picFilePath}, null, null);
                UtilsCode.INSTANCE.showShort("保存图片到：" + picFilePath);
            } else {
                UtilsCode.INSTANCE.showShort("保存图片失败！");
            }
        }
    }

    public static Bitmap createWebImage(KwWebView webView) {

        //X5内核不能兼容onDraw方法
        Picture picture = webView.capturePicture();
        int width = picture.getWidth();
        int height = picture.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        picture.draw(canvas);

//        webView.getX5WebViewExtension().snapshotWholePage(canvas, false, false); //结果不清晰

        //添加二维码底部
//        View qrView = generateShareView(webView.getContext(), R.layout.view_share_web);
//        Bitmap qrBitmap = createImage(qrView);
//        int backColor = getResources().getColor(R.color.colorWindowBackground);
//        Bitmap saveBmp = mosaicBitmapVertical(backColor, bitmap, qrBitmap);
        return bitmap;
    }

    public static void captureWebView(ShareCardDialog dialog, KwWebView webView) {
        //X5内核不能兼容onDraw方法
        Picture picture = webView.capturePicture();
        int width = picture.getWidth();
        int height = picture.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        picture.draw(canvas);

        View qrView = generateShareView(webView.getContext(), R.layout.view_share_web);
        Bitmap qrBitmap = createImage(qrView);
        int backColor = ContextCompat.getColor(App.getInstance(), R.color.colorWindowBackground);
        Bitmap saveBmp = mosaicBitmapVertical(backColor, bitmap, qrBitmap);

        String baseRootDir = UtilsCode.INSTANCE.getSDCardPathByEnvironment();
        if (TextUtils.isEmpty(baseRootDir)) {
            UtilsCode.INSTANCE.showShort("SD卡不可用");
            return;
        }
//        String saveFilePath = baseRootDir + "/bulu/pic";
//        if(!FileUtils.createOrExistsDir(saveFilePath)){
//            UtilsCode.INSTANCE.showShort("创建目录失败");
//            return;
//        }
//        String picFilePath = saveFilePath + "/bulu"+System.currentTimeMillis()+".jpg";

        String picFilePath = KwDirs.getCacheBitmapAbsolutePath(System.currentTimeMillis() + ".jpg");
        boolean saveFlag = UtilsCode.INSTANCE.save(saveBmp, picFilePath, Bitmap.CompressFormat.JPEG);
        if (saveFlag) {
            MediaScannerConnection.scanFile(dialog.getActivity(), new String[]{picFilePath}, null, null);
            UtilsCode.INSTANCE.showShort("保存图片到：" + picFilePath);
        } else {
            UtilsCode.INSTANCE.showShort("保存图片失败！");
        }
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
}
