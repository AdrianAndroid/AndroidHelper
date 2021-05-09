package cn.kuwo.pp.ui.web;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.util.List;

import cn.kuwo.common.app.App;
import cn.kuwo.common.utilscode.UtilsCode;


/**
 * Created by yaj on 2017/1/3.
 */

public class ChooseImageUtil {
    public static final int PHOTO = 0x11;
    public static final int GALLERY = 0x12;

    public static final int MINE = 1;
    public static final int CHILD = 2;
    public static final int WEB = 3;
    public static final int QRCODE = 4;
    public static final int WX = 5;

    public static final int MINE_CROP = 0x13;
    public static final int MINE_CROP_PICTURE = 0x14;
    public static final int WEB_CROP = 0x33;
    public static final int WEB_QRCODE = 0x34;
    public static final int WX_CROP = 0x54;

    private final static String LINEND = "\r\n";
    private final static String BOUNDARY = "---------------------------251811773417148"; // 数据分隔线
    private final static String PREFIX = "--";
    private final static String CHARSET = "utf-8";

    private int mType;

    public ChooseImageUtil() {

    }

    public ChooseImageUtil(int type) {
        mType = type;
    }


    public void openGallery() {
        //去本地相册
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        ResolveInfo reInfo = App.getInstance().getPackageManager().resolveActivity(openAlbumIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (reInfo == null) {
            UtilsCode.INSTANCE.showShort("请先安装相册");
            return;
        }
        startActivityForResult(openAlbumIntent);
    }

    private void startActivityForResult(Intent intent) {
        Activity activity = UtilsCode.INSTANCE.getTopActivity();
        int resultType = getActivityResultType();
        if (activity != null && resultType > 0) {
            activity.startActivityForResult(intent, resultType);
        }
    }

    private int getActivityResultType() {
        if (mType == MINE) {
            return ChooseImageUtil.MINE_CROP;
        } else if (mType == WEB) {
            return ChooseImageUtil.WEB_CROP;
        } else if (mType == QRCODE) {
            return ChooseImageUtil.WEB_QRCODE;
        } else if (mType == WX) {
            return ChooseImageUtil.WX_CROP;
        }
        return 0;
    }

    /**
     * 获取系统相册返回的图片文件路径信息
     *
     * @param context
     * @param uri：传入的相册中的ＵＲＩ
     * @return
     */
    public static String getAlbumPictureFilePath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // 兼容外置SDCard情况 @zhouchong
                else {
                    return getStorageDirectoryWithType(context, split[0]) + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                if (id != null && id.contains("raw:")) {
                    return id.replace("raw:", "");
                } else {
                    try {
                        final Uri contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                        return getDataColumn(context, contentUri, null, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * zhouchong: 根据存储设备类型获取文件路径
     *
     * @param context
     * @param type
     * @return
     */
    private static String getStorageDirectoryWithType(Context context, String type) {
        List<String> infos = UtilsCode.INSTANCE.getSDCardPaths();
        // 从当前读取到的sd卡信息中查找
        if (infos != null && !infos.isEmpty()) {
            for (String path : infos) {
                if (path.contains(type)) {
                    return path;
                }
            }
        }
        // 不存在则使用拼接的url
        return getScanRootFile() + "/" + type;
    }


    private final static String STORAGE = "/storage";
    private final static String MNT = "/mnt";

    /**
     * 返回扫描的根目录
     */
    public static File getScanRootFile() {
        File rootFile = new File(STORAGE);//4.2及以上有storage目录的使用storage
        if (!rootFile.exists()) {
            rootFile = new File(MNT);//4.2以下没有storage的使用mnt
        }
        return rootFile;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     *                      [url=home.php?mod=space&uid=7300]@return[/url] The value of
     *                      the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri,
                                        String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }
}
