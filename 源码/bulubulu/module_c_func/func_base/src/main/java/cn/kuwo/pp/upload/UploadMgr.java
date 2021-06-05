package cn.kuwo.pp.upload;

import android.Manifest;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;

/**
 * 本地录音文件上传工具类
 */
public class UploadMgr {

    List<IUploader> mUploaders = Collections.synchronizedList(new LinkedList<IUploader>());

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET})
    public void uploadToQiniu(String fileName,IUploadCallback callback){
        IUploader uploader = new QiniuUploader();
        uploader.upload(fileName,callback);
        mUploaders.add(uploader);
    }

    public boolean removeUploader(IUploader uploader){
        if(uploader!=null){
            return mUploaders.remove(uploader);
        }
        return false;
    }

    private static UploadMgr _instance;
    public static UploadMgr getInstance(){
        if(_instance ==  null){
            synchronized (UploadMgr.class){
                if(_instance == null){
                    _instance = new UploadMgr();
                }
            }
        }
        return _instance;
    }
}
