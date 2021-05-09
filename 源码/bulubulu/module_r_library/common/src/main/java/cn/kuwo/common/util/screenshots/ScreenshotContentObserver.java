package cn.kuwo.common.util.screenshots;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

public class ScreenshotContentObserver extends ContentObserver {
    private static final String TAG = "ScreenshotContentObserver";

    //分享时使用的tag
    public static final String SCREEN_SHOT_SHARE_TAG = "ScreenShotShare";
    //URI匹配
    private static final String EXTERNAL_CONTENT_URI_MATCHER = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString();
    //查询的表字段
    private static final String[] PROJECTION = new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED};
    //根据时间降序排序
    private static final String SORT_ORDER = MediaStore.Images.Media.DATE_ADDED + " DESC";
    //时间判断间隔标准
    private static final long TIME_MATCH_MILLISECOND = 1500L;
    //英文名称判断标准
    private static final String DISPLAY_NAME_EN = "screenshot";
    //中文名称判断标准
    private static final String DISPLAY_NAME_CN = "截屏";
    public static final String MANUFACTURER_OF_HARDWARE_XIAOMI = "xiaomi";
    public static final String MANUFACTURER_OF_HARDWARE_VIVO = "vivo";

    //媒体内容监听器
    private ContentResolver mContentResolver;
    //文件监听器
    private FileObserver fileObserver;
    //最后监听到的文件路径，用于去除重复事件
    private String mLastObservePath;
//    //屏幕宽度
//    private int mScreenWidth;
//    //屏幕高度
//    private int mScreenHeight;
    //截屏事件回调
    private Listener mListener;

    public interface Listener {
        //截屏事件来源区分
        int FROM_TYPE_SYSTEM = 1;//来自系统截屏
        int FROM_TYPE_ACTION_SHEET = 2;//来自sheet的截屏按钮
        int FROM_TYPE_MULTI_SHARE = 3;//来自多点分享

        void onDetectScreenshot(Uri uri, String path, int fromType);
    }

    //系统截屏文件目录
    public static final class ScreenShotPath {
        public static final String NORMAL = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_PICTURES
                + File.separator + "Screenshots" + File.separator;

        public static final String XIAOMI = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Screenshots" + File.separator;

        public static final String VIVO = Environment.getExternalStorageDirectory()
                + File.separator + "截屏" + File.separator;
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    public ScreenshotContentObserver(Context context/*, int width, int height*/) {
        super(null);
//        mScreenWidth = width;
//        mScreenHeight = height;
        //判断版本是否小于6.0
        if (Build.VERSION.SDK_INT < 23) {
            final String screenshotPath;
            //判断厂商，设置监听目录
            if (android.os.Build.MANUFACTURER.equalsIgnoreCase(MANUFACTURER_OF_HARDWARE_XIAOMI)) {
                screenshotPath = ScreenShotPath.XIAOMI;
            } else if (android.os.Build.MANUFACTURER.equalsIgnoreCase(MANUFACTURER_OF_HARDWARE_VIVO)) {
                screenshotPath = ScreenShotPath.VIVO;
            } else {
                screenshotPath = ScreenShotPath.NORMAL;
            }
            //创建文件监听器
            fileObserver = new FileObserver(screenshotPath, FileObserver.CREATE) {
                @Override
                public void onEvent(int event, String path) {
//                    if (QLog.isDevelopLevel()) {
//                        QLog.d(TAG, QLog.CLR, "onEvent->time:" + System.currentTimeMillis() + ", path:" + path);
//                    }
                    //过滤错误参数及事件
                    if (TextUtils.isEmpty(path) || event != FileObserver.CREATE) {
                        return;
                    }
                    //过滤重复事件以及新MIUI截屏临时文件
                    if (path.equalsIgnoreCase(mLastObservePath) || path.contains("temp")) {
                        return;
                    }
                    //通知回调
                    if (mListener != null) {
                        mListener.onDetectScreenshot(null, screenshotPath + path, Listener.FROM_TYPE_SYSTEM);
                        mLastObservePath = path;
                    }
                }
            };
        } else {
            mContentResolver = context.getContentResolver();
        }
        registerToSystem();
    }

    /**
     * 清除资源
     */
    public void onDestroy() {
        unregisterToSystem();
        fileObserver = null;
        mContentResolver = null;
        mListener = null;
    }

    /**
     * 开始监听
     */
    private void registerToSystem() {
        //判断版本是否小于6.0
        if (Build.VERSION.SDK_INT < 23) {
            fileObserver.startWatching();
        } else {
            mContentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, this);
        }
    }

    /**
     * 关闭监听
     */
    private void unregisterToSystem() {
        //判断版本是否小于6.0
        if (Build.VERSION.SDK_INT < 23) {
            fileObserver.stopWatching();
        } else {
            //不需要监听的时候，一定要把原来的ContentObserver注销掉。
            mContentResolver.unregisterContentObserver(this);
        }
    }

    /**
     * 媒体内容监听器回调
     *
     * @param selfChange
     */
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        //从API16开始，才有两个参数的onChange方法，所以这里要主动调用下面的onChange方法。
        onChange(selfChange, null);
    }

    /**
     * 媒体内容监听器回调
     *
     * @param selfChange
     * @param uri
     */
    @Override
    public void onChange(boolean selfChange, Uri uri) {
        Log.i("tag", "");
        // 若调用父类方法就死循环了
//        super.onChange(selfChange,uri);
        Cursor cursor = null;
        if (uri == null) { //API16以下版本
            try {
                cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PROJECTION, null, null, SORT_ORDER);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    //完整路径
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    //添加图片的时间，单位秒
                    long addTime = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                    //加个过滤条件必须是3S内的图片，且路径中包含截图字样“screenshot”
                    if (matchAddTime(addTime) && matchPath(path) /*&& matchSize(path)*/) {
                        if (path != null) {
                            if (path.equalsIgnoreCase(mLastObservePath)) {
                                return;
                            } else {
                                mLastObservePath = path;
                            }
                        }
                        //这就是系统截屏的图片了，这里测试发现需要等待几百MS，才能加载到图片。因此具体实现时，最好在独立线程，每隔100MS尝试加载一次，做好超时处理。
                        if (mListener != null) {
                            mListener.onDetectScreenshot(uri, path, Listener.FROM_TYPE_SYSTEM);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else { //API16及以上版本
            try {
                if (uri.toString().startsWith(EXTERNAL_CONTENT_URI_MATCHER)) {
                    cursor = mContentResolver.query(uri, PROJECTION, null, null, SORT_ORDER);
                    if (cursor != null && cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        //完整路径
                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        //添加图片的时间，单位秒
                        long addTime = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                        if (matchAddTime(addTime) && matchPath(path) /*&& matchSize(path)*/) {
                            if (path != null) {
                                if (path.equalsIgnoreCase(mLastObservePath)) {
                                    return;
                                } else {
                                    mLastObservePath = path;
                                }
                            }
                            //这就是系统截屏的图片了
                            if (mListener != null) {
                                mListener.onDetectScreenshot(uri, path, Listener.FROM_TYPE_SYSTEM);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 添加时间与当前时间不超过1.5s,大部分时候不超过1s。
     *
     * @param addTime 图片添加时间，单位:秒
     */
    private boolean matchAddTime(long addTime) {
        return System.currentTimeMillis() - addTime * 1000 < TIME_MATCH_MILLISECOND;
    }

    /**
     * 已调查的手机截屏图片的路径中带有screenshot
     */
    private boolean matchPath(String filePath) {
        String lower = filePath.toLowerCase();
        return lower.contains(DISPLAY_NAME_EN) || lower.contains(DISPLAY_NAME_CN);
    }

//    /**
//     * 尺寸不大于屏幕尺寸（发现360奇酷手机可以对截屏进行裁剪）
//     */
//    private boolean matchSize(String filePath) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(filePath, options);
//        return mScreenWidth >= options.outWidth && mScreenHeight >= options.outHeight;
//    }
}




