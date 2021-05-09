package com.elbbbird.android.socialsdk.share.wechat;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;


import com.elbbbird.android.socialsdk.SocialUtils;
import com.elbbbird.android.socialsdk.WeChat;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cn.kuwo.common.util.KwDirs;
import cn.kuwo.common.utilscode.UtilsCode;

/**
 * 微信分享Proxy
 * Created by zhanghailong-ms on 2015/11/23.
 */
public class WeChatShareProxy {

    private static IWXShareCallback mCallback;
    private static final int BITMAP_SAVE_THRESHOLD = 32 * 1024;

    public static void shareToWeChat(final Context context, final String appId, final String title, final String desc,
                                     final String url, final String thumbnail, final IWXShareCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeChatShareProxy.mCallback = callback;
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = url;
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = title;
                msg.description = desc;
                byte[] thumb = SocialUtils.getHtmlByteArray(thumbnail);
                if (null != thumb) {
                    msg.thumbData = SocialUtils.compressBitmap(thumb, 32);
                } else {
                    msg.thumbData = SocialUtils.compressBitmap(SocialUtils.getDefaultShareImage(context), 32);
                }

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = SocialUtils.buildTransaction("webpage");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                WeChat.getIWXAPIInstance(context, appId).sendReq(req);
            }
        }).start();

    }

    public static void shareMusicToWeChat(final Context context, final String appId, final String title, final String desc,
                                          final String url, final String musicDataUrl, final String thumbnail, final IWXShareCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeChatShareProxy.mCallback = callback;
                WXMusicObject musicObject = new WXMusicObject();
                musicObject.musicUrl = url;
                musicObject.musicDataUrl = musicDataUrl;
                //用 WXMusicObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = musicObject;
                msg.title = title;
                msg.description = desc;
                byte[] thumb = SocialUtils.getHtmlByteArray(thumbnail);
                if (null != thumb) {
                    msg.thumbData = SocialUtils.compressBitmap(thumb, 32);
                } else {
                    msg.thumbData = SocialUtils.compressBitmap(SocialUtils.getDefaultShareImage(context), 32);
                }
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = SocialUtils.buildTransaction("webpage");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                WeChat.getIWXAPIInstance(context, appId).sendReq(req);
            }
        }).start();
    }

    public static void shareImageToWeChat(final Context context, final String appId, final Bitmap bitmap, final IWXShareCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeChatShareProxy.mCallback = callback;
                //初始化 WXImageObject 和 WXMediaMessage 对象
                WXImageObject imgObj = new WXImageObject();
                //大于32k保存原图再分享
                if (bitmap.getByteCount() > BITMAP_SAVE_THRESHOLD) {
                    String filePath = Environment.getExternalStorageDirectory() + "/bulu/pic/buluweb" + System.currentTimeMillis() + ".jpg";
                    UtilsCode.INSTANCE.save(bitmap, filePath, Bitmap.CompressFormat.JPEG, true);
                    imgObj.imagePath = filePath;
                } else {
                    ByteArrayOutputStream var2 = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, var2);
                    imgObj.imageData = var2.toByteArray();
                    try {
                        var2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = imgObj;
                bitmap.recycle();
                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = SocialUtils.buildTransaction("img");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                WeChat.getIWXAPIInstance(context, appId).sendReq(req);
            }
        }).start();

    }

    // 直接分享本地图片的地址
    public static void shareImageToWeChat(final Context context, final String appId, final String shareBitmapPath, final IWXShareCallback callback) {
        WeChatShareProxy.mCallback = callback;
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject();
        //大于32k保存原图再分享
        imgObj.imagePath = shareBitmapPath;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = SocialUtils.buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        WeChat.getIWXAPIInstance(context, appId).sendReq(req);
    }

    public static void shareToWeChatTimeline(final Context context, final String appId, final String title, final String url,
                                             final String thumbnail, final IWXShareCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeChatShareProxy.mCallback = callback;
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = url;
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = title;
                byte[] thumb = SocialUtils.getHtmlByteArray(thumbnail);
                if (null != thumb) {
                    msg.thumbData = SocialUtils.compressBitmap(thumb, 32);
                } else {
                    msg.thumbData = SocialUtils.compressBitmap(SocialUtils.getDefaultShareImage(context), 32);
                }

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = SocialUtils.buildTransaction("webpage");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                WeChat.getIWXAPIInstance(context, appId).sendReq(req);
            }
        }).start();
    }

    public static void shareMusicToWeChatTimeline(final Context context, final String appId, final String title, final String desc, final String url, final String musicUrl,
                                                  final String thumbnail, final IWXShareCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeChatShareProxy.mCallback = callback;
                WXMusicObject musicObject = new WXMusicObject();
                musicObject.musicUrl = url;
                musicObject.musicDataUrl = musicUrl;
                WXMediaMessage msg = new WXMediaMessage(musicObject);
                msg.title = title;
                msg.description = desc;
                byte[] thumb = SocialUtils.getHtmlByteArray(thumbnail);
                if (null != thumb) {
                    msg.thumbData = SocialUtils.compressBitmap(thumb, 32);
                } else {
                    msg.thumbData = SocialUtils.compressBitmap(SocialUtils.getDefaultShareImage(context), 32);
                }
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = SocialUtils.buildTransaction("webpage");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                WeChat.getIWXAPIInstance(context, appId).sendReq(req);
            }
        }).start();
    }

    public static void shareImageToWeChatTimeline(final Context context, final String appId, final Bitmap bitmap, final IWXShareCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeChatShareProxy.mCallback = callback;
                //初始化 WXImageObject 和 WXMediaMessage 对象
                WXImageObject imgObj = new WXImageObject();
                //大于32k保存原图再分享
                if (bitmap.getByteCount() > BITMAP_SAVE_THRESHOLD) {
//                    String baseRootDir = SDCardUtils.getSDCardPathByEnvironment();
//                    String saveFilePath = baseRootDir + "/bulu/pic";
//                    String picFilePath = saveFilePath + "/buluweb" + System.currentTimeMillis() + ".jpg";
                    String picFilePath = KwDirs.getCacheBitmapAbsolutePath(System.currentTimeMillis() + ".jpg");
                    UtilsCode.INSTANCE.save(bitmap, picFilePath, Bitmap.CompressFormat.JPEG);
                    imgObj.setImagePath(picFilePath);
                } else {
                    imgObj = new WXImageObject(bitmap);
                }
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = imgObj;
                bitmap.recycle();
                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = SocialUtils.buildTransaction("img");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                WeChat.getIWXAPIInstance(context, appId).sendReq(req);
            }
        }).start();

    }

    // 直接分享本地图片的地址
    public static void shareImageToWeChatTimeline(final Context context, final String appId, final String shareBitmapPath, final IWXShareCallback callback) {
        WeChatShareProxy.mCallback = callback;
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject();
        //大于32k保存原图再分享
        imgObj.setImagePath(shareBitmapPath);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = SocialUtils.buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        WeChat.getIWXAPIInstance(context, appId).sendReq(req);

    }

    public static void shareComplete(SendMessageToWX.Resp resp) {
        if (null != mCallback) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    mCallback.onSuccess();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    mCallback.onCancel();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                default:
                    mCallback.onFailure(new Exception("BaseResp.ErrCode.ERR_AUTH_DENIED"));
                    break;
            }
        }
    }
}
