package cn.kuwo.common.util;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;


import java.io.File;

import cn.kuwo.common.app.App;
import cn.kuwo.common.utilscode.UtilsCode;

// by haiping 所有得到的路径末尾都已经有了"/"符号了，切记！
public final class KwDirs {

//    // 大于100的不缓存
//    public static final int SD_ROOT = 0, // 第一SD卡
//            HOME = 2,            // sdcard/KuwoLiveShow
//            CACHE = 4,           // cn.kuwo.base.cache存储数据的位置
//            LYRICS = 5,           // 歌词目录，旧版资源也在
//            PLAYCACHE = 7,           // 播放缓存
//            CRASH = 8,           // 崩溃堆栈文件
//            TEMP = 9,           // 一个单纯的缓存路径，每次主动退出程序都会清空
//            PICTURE = 12,          // 图片目录
//            UPDATE = 13,          // SD卡Download目录
//            CONFIG = 15,          // 存放服务器配置
//            MAX_ID = 26;          // 保持最大

//
//    private static String[] dirs = new String[MAX_ID];
//    private static final String SD_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
//    private static final String HOME_PATH = SD_ROOT_PATH + "boom" + File.separator;
//    private static final String HOME_PATH_FOR_HIDE = HOME_PATH // 发现 sdcard/kuwo.zhp 这个文件，则所有目录可见，用于查找问题
//            + (new File(SD_ROOT_PATH + File.separator + "kuwo.zhp").exists() ? "" : ".");

//    // 所有得到的路径末尾都已经有了"/"符号了，切记！
//    public static String getDir(final int dirID) {
//        int savePos = dirID < 100 ? dirID : dirID - 100;
//        String dirPath = dirs[savePos];
//        if (dirPath != null) {
//            createOrExits(dirPath);
//            return dirPath;
//        }
//        dirPath = "";
//        switch (dirID) {
//            case SD_ROOT:
//                dirPath = SD_ROOT_PATH;
//                break;
//            case HOME:
//                dirPath = HOME_PATH;
//                break;
//            case CACHE:
//                dirPath = HOME_PATH_FOR_HIDE + "data";
//                break;
//            case LYRICS:
//                dirPath = HOME_PATH + "lyrics";
//                break;
//            case PLAYCACHE:
//                dirPath = HOME_PATH_FOR_HIDE + "playcache";
//                break;
//            case CRASH:
//                dirPath = HOME_PATH_FOR_HIDE + "crash";
//                break;
//            case TEMP:
//                dirPath = HOME_PATH_FOR_HIDE + "temp";
//                break;
//            case CONFIG:
//                dirPath = HOME_PATH_FOR_HIDE + "config";
//                break;
//            case PICTURE:
//                dirPath = HOME_PATH + "picture";
//                break;
//            case UPDATE:
//                dirPath = SD_ROOT_PATH + "Download";
//                break;
//            default:
//                //     KwDebug.classicAssert(false);
//                break;
//        } // switch
//
//        if (!TextUtils.isEmpty(dirPath) && !dirPath.endsWith(File.separator)) {
//            dirPath += File.separator;
//        }
//
//        if (dirID < 100) {
//            dirs[savePos] = dirPath;
//        }
//        createOrExits(dirPath);
//        return dirPath;
//    }
//
//    private static void createOrExits(String dirPath) {
//        File dir = new File(dirPath);
//        if (!dir.exists()) {
//            try {
//                dir.mkdirs();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private KwDirs() {
    }

    private static String appendNames(String path, String appendName) {
        if (TextUtils.isEmpty(appendName)) {
            return path + File.separator;
        } else {
            return path + File.separator + appendName;
        }
    }

    // 获取图片的临时地址 Android/data/package name/cache
    public static String getCachePicturepDir(String appendName) {
        File externalCacheDir = App.getInstance().getExternalCacheDir();
        if (!externalCacheDir.exists()) {
            externalCacheDir.mkdirs();
        }

        return appendNames(externalCacheDir.getAbsolutePath(), appendName);
    }

    // 得到Android/file/Picture下的目录
    public static String getFilePictureDir(String appendName) {
        File externalCacheDir = App.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!externalCacheDir.exists()) {
            externalCacheDir.mkdirs();
        }
//        return externalCacheDir.getAbsolutePath() + File.separator + StringUtils.null2Length0(appendName);
        return appendNames(externalCacheDir.getAbsolutePath(), appendName);
    }

    public static String getSDCardBuluPicDir(String appendName) {
        String baseRootDir = UtilsCode.INSTANCE.getSDCardPathByEnvironment();
        String saveFilePath = baseRootDir + "/bulu/pic";
        //String picFilePath = saveFilePath + "/buluweb" + System.currentTimeMillis() + ".jpg";

        File file = new File(saveFilePath);
        if (!file.exists()) {
            file.mkdir();
        }
//        return file.getAbsolutePath() + File.separator + StringUtils.null2Length0(appendName);
        return appendNames(file.getAbsolutePath(), appendName);
    }


    public static String getDCIMDir(String appendName) {
        String filePath;
        if (Build.BRAND.equals("xiaomi")) { // 小米手机brand.equals("xiaomi")
            filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera";
        } else if (Build.BRAND.equalsIgnoreCase("Huawei")) {
            filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera";
        } else { // Meizu 、Oppo
            filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM";
        }
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return appendNames(filePath, appendName);
    }

    /**
     * @param appendName 后面追加的名称
     * @return
     */
    public static String getCacheBitmapAbsolutePath(String appendName) {
        return getFilePictureDir(appendName);
    }


    /**
     * 获取日志
     */

}
