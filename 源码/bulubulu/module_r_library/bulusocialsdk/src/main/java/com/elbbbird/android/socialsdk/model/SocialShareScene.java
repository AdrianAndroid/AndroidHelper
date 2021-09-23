package com.elbbbird.android.socialsdk.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 社会化分享数据类
 * Created by zhanghailong-ms on 2015/11/23.
 */
public class SocialShareScene implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public static final int SHARE_TYPE_DEFAULT = 0;
    public static final int SHARE_TYPE_WEIBO = 1;
    public static final int SHARE_TYPE_WECHAT = 2;
    public static final int SHARE_TYPE_WECHAT_TIMELINE = 3;
    public static final int SHARE_TYPE_QQ = 4;
    public static final int SHARE_TYPE_QZONE = 5;
    public static final int SHARE_TYPE_PIC = 6;//分享图片

    private String id;
    private int type;
    private String title;
    private String desc;
    private String thumbnail;
    private String url;
    private Bitmap mBitmap;
    private String shareBitmapPath; // 分享的图片
    private String mMusicUrl;

    /**
     * @param id        分享唯一标识符，可随意指定，会在分享结果ShareBusEvent中返回
     * @param type      分享类型，会在分享结果ShareBusEvent中作为platform返回
     * @param title     标题
     * @param desc      简短描述
     * @param thumbnail 缩略图网址
     * @param url       WEB网址
     */
    public SocialShareScene(String id, int type, String title, String desc, String thumbnail, String url) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.desc = desc;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    public SocialShareScene(String id, int type, String title, String desc, String thumbnail, String url, String musicUrl) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.desc = desc;
        this.thumbnail = thumbnail;
        this.url = url;
        mMusicUrl = musicUrl;
    }

    public SocialShareScene(String id, String title, String desc, String thumbnail, String url) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    public SocialShareScene(String id, String appName, int type, Bitmap bitmap, String desc) {
        this.id = id;
        this.type = type;
        this.mBitmap = bitmap;
        this.desc = desc;
    }


    public SocialShareScene(String id, int type, String shareBitmapPath, String desc) {
        this.id = id;
        this.type = type;
        this.shareBitmapPath = shareBitmapPath;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }


    public String getShareBitmapPath() {
        return shareBitmapPath;
    }

    public void setShareBitmapPath(String shareBitmapPath) {
        this.shareBitmapPath = shareBitmapPath;
    }

    public String getMusicUrl() {
        return mMusicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        mMusicUrl = musicUrl;
    }
}
