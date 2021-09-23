package cn.kuwo.common.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageUtils {

    /*https://www.jianshu.com/p/ee8a95d9525d
     * 保存文件，文件名为当前日期
     */
    public static boolean saveBitmap2DCIM(Context context, Bitmap bitmap, String filePath, String fileName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveSignImage(context, fileName, bitmap);
        } else {
            File file = new File(filePath, fileName);
            if (!file.exists()) {
                file.delete();
            }
            FileOutputStream out;
            try {
                out = new FileOutputStream(file);
                // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                    out.flush();
                    out.close();
                    // 插入图库
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        ContentValues values = new ContentValues();
//                        values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
//                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//                        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//                    } else {
                    MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
//                    }
                }
            } catch (FileNotFoundException e) {
                Log.e("FileNotFoundException", "FileNotFoundException:" + e.getMessage().toString());
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                Log.e("IOException", "IOException:" + e.getMessage().toString());
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                Log.e("IOException", "IOException:" + e.getMessage().toString());
                e.printStackTrace();
                return false;
                // 发送广播，通知刷新图库的显示
            }
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
        }
        return true;

    }

    //将文件保存到公共的媒体文件夹
    //这里的filepath不是绝对路径，而是某个媒体文件夹下的子路径，和沙盒子文件夹类似
    //这里的filename单纯的指文件名，不包含路径
    public static void saveSignImage(Context context, /*String filePath,*/String fileName, Bitmap bitmap) {
        try {
            //设置保存参数到ContentValues中
            ContentValues contentValues = new ContentValues();
            //设置文件名
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            //兼容Android Q和以下版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //android Q中不再使用DATA字段，而用RELATIVE_PATH代替
                //RELATIVE_PATH是相对路径不是绝对路径
                //DCIM是系统文件夹，关于系统文件夹可以到系统自带的文件管理器中查看，不可以写没存在的名字
                contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/");
                //contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Music/signImage");
            } else {
                contentValues.put(MediaStore.Images.Media.DATA, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());
            }
            //设置文件类型
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/JPEG");
            //执行insert操作，向系统文件夹中添加文件
            //EXTERNAL_CONTENT_URI代表外部存储器，该值不变
            Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            if (uri != null) {
                //若生成了uri，则表示该文件添加成功
                //使用流将内容写入该uri中即可
                OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                    outputStream.flush();
                    outputStream.close();
                }
            }
        } catch (Exception e) {
        }
    }


}
