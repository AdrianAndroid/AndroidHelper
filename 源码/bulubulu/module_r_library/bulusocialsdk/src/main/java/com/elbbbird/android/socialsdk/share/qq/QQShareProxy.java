package com.elbbbird.android.socialsdk.share.qq;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;

import com.elbbbird.android.socialsdk.R;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

import cn.kuwo.common.app.App;
import cn.kuwo.common.utilscode.UtilsCode;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

import static com.tencent.connect.share.QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD;
import static com.tencent.connect.share.QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;

/**
 * QQ分享Proxy
 * Created by zhanghailong-ms on 2015/11/24.
 */
public class QQShareProxy {

    private static Tencent tencent;

    private static Tencent getInstance(Context context, String appId) {
        if (tencent == null) {
            tencent = Tencent.createInstance(appId, context);
        }
        return tencent;
    }

    public static void shareToQQ(final Context context, final String appId, final String title, final String summary, final String url,
                                 final String imageUrl, final IUiListener listener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, App.getInstance().getString(R.string.share_app_name));
        Tencent tencent = getInstance(context, appId);
        tencent.shareToQQ((Activity) context, params, listener);
    }

    public static void shareToQQ(final Context context, final String appId, final String title, final String summary, final String url, String musicUrl,
                                 final String imageUrl, final IUiListener listener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, musicUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, App.getInstance().getString(R.string.share_app_name));
        Tencent tencent = getInstance(context, appId);
        tencent.shareToQQ((Activity) context, params, listener);
    }

    // 直接分享带本地链接的图片
    public static void shareImageToQQ(final Activity context, final String appId, final String shareBitmapPath, final IUiListener listener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareBitmapPath);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, App.getInstance().getString(R.string.share_app_name));
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        Tencent tencent = getInstance(context, appId);
        tencent.shareToQQ(context, params, listener);
    }


    public static void shareImageToQQ(final Context context, final String appId, final Bitmap bitmap, final IUiListener listener) {
        UtilsCode.INSTANCE.executeByCached(new Function0<Object>() {
            @Override
            public Object invoke() {
                Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
                String filePath = Environment.getExternalStorageDirectory() + "/boom/picture/share" + System.currentTimeMillis() + ".png";
                UtilsCode.INSTANCE.save(bitmap, filePath, Bitmap.CompressFormat.PNG, true);
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, filePath);
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, App.getInstance().getString(R.string.share_app_name));
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                Tencent tencent = getInstance(context, appId);
                tencent.shareToQQ((Activity) context, params, listener);
                return null;
            }
        });
    }

    public static void shareToQZone(final Context context, final String appId, final String title, final String summary, final String url,
                                    final String imageUrl, final IUiListener listener) {
        UtilsCode.INSTANCE.executeByCached(new Function0<Object>() {
            @Override
            public Object invoke() {
                Bundle params = new Bundle();
                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);
                ArrayList<String> imgs = new ArrayList<String>();
                imgs.add(imageUrl);
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgs);
                Tencent tencent = getInstance(context, appId);
                tencent.shareToQzone((Activity) context, params, listener);
                return null;
            }
        });
    }

    // 分享本地图片的地址
    public static void shareImageToQZone(final Context context, final String appId, final String shareBitmapPath, final IUiListener listener) {

        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
        ArrayList<String> list = new ArrayList<>();
        list.add(shareBitmapPath);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list);
        Tencent tencent = getInstance(context, appId);
        tencent.publishToQzone((Activity) context, params, listener);

    }

    public static void shareImageToQZone(final Context context, final String appId, final String title, final String summary, final Bitmap bitmap, final String url,
                                         final String imageUrl, final IUiListener listener) {
        UtilsCode.INSTANCE.executeByCached(new Function0<Object>() {
            @Override
            public Object invoke() {
                Bundle params = new Bundle();
                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
                ArrayList<String> list = new ArrayList<>();
                String filePath = Environment.getExternalStorageDirectory() + "/boom/picture/share" + System.currentTimeMillis() + ".png";
                UtilsCode.INSTANCE.save(bitmap, filePath, Bitmap.CompressFormat.PNG, true);
                list.add(filePath);
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list);
                Tencent tencent = getInstance(context, appId);
                tencent.publishToQzone((Activity) context, params, listener);
                return null;
            }
        }, new Function1<Object, Unit>() {
            @Override
            public Unit invoke(Object o) {
                return null;
            }
        });


//
//        UtilsCode.INSTANCE.executeByCached(new UtilsCode.MySimpleTask<Object>() {
//
//            @Override
//            public Object doInBackground() {
//                Bundle params = new Bundle();
//                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
//                ArrayList<String> list = new ArrayList<>();
//                String filePath = Environment.getExternalStorageDirectory() + "/boom/picture/share" + System.currentTimeMillis() + ".png";
//                UtilsCode.INSTANCE.save(bitmap, filePath, Bitmap.CompressFormat.PNG, true);
//                list.add(filePath);
//                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list);
//                Tencent tencent = getInstance(context, appId);
//                tencent.publishToQzone((Activity) context, params, listener);
//                return null;
//            }
//
//            @Override
//            public void onSuccess(Object result) {
//
//            }
//        });
    }
}
