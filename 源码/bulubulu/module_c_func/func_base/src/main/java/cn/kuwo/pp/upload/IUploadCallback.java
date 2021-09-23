package cn.kuwo.pp.upload;

/**
 * 上传文件回调接口
 */
public interface IUploadCallback {
    void onUploadStart(IUploader uploader);
    void onUploadProcess(IUploader uploader,long percent,long total);
    void onUploadSuccess(IUploader uploader,String url);
    void onUploadFailed(IUploader uploader,int code,String msg);
}
