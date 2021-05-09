package cn.kuwo.common.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import cn.kuwo.common.BuildConfig;
import cn.kuwo.common.utilscode.UtilsCode;

public class App extends Application {

    public static final Boolean DEBUG = BuildConfig.DEBUG;


    protected static App mInstance;

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        //startStrictMode();
        super.onCreate();
        mInstance = this;
        UtilsCode.INSTANCE.initApplication(this);
    }

    private void startStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls() //API等级11，使用StrictMode.noteSlowCode
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyDialog() //弹出违规提示对话框
                    .penaltyLog() //在Logcat 中打印违规异常信息
                    .penaltyFlashScreen() //API等级11
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects() //API等级11
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }


    public void exit() {

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        try {
            Glide.get(this).onLowMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        try {
            Glide.get(this).onTrimMemory(level);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean isMainProgress() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public String appGetUserGender() {
        return "";
    }

    // 是否登陆
    public boolean appIsLogin() {
        return false;
    }

    // uid
    public String appGetUid() {
        return "0";
    }

    //token
    public String appGetToken() {
        return "0";
    }

    public String appGetChannel() {
        return "kuwo";
    }

    public Boolean appDebugUrl() {
        return true;
    }

    public String appPackageTime() {
        return "";//打包时间, 便于测试
    }


    @Override
    public File getDir(String name, int mode) {
        return super.getDir(name, mode);
    }


    @Override
    public File getDataDir() {
        return super.getDataDir();
    }

    @Override
    public File getFilesDir() {
        return super.getFilesDir();
    }

    @Override
    public File getNoBackupFilesDir() {
        return super.getNoBackupFilesDir();
    }

    @Override
    public File getExternalFilesDir(String type) {
        return super.getExternalFilesDir(type);
    }

    @Override
    public File[] getExternalFilesDirs(String type) {
        return super.getExternalFilesDirs(type);
    }

    @Override
    public File getObbDir() {
        return super.getObbDir();
    }

    @Override
    public File[] getObbDirs() {
        return super.getObbDirs();
    }

    @Override
    public File getCacheDir() {
        return super.getCacheDir();
    }

    @Override
    public File getCodeCacheDir() {
        return super.getCodeCacheDir();
    }

    @Override
    public File getExternalCacheDir() {
        return super.getExternalCacheDir();
    }

    @Override
    public File[] getExternalCacheDirs() {
        return super.getExternalCacheDirs();
    }

    @Override
    public File[] getExternalMediaDirs() {
        return super.getExternalMediaDirs();
    }
}
