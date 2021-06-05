package cn.kuwo.pp.upload;

public interface IUploader {
    /**
     * 上传文件到预设好的服务端
     * @param fileName:本地文件路径
     * @param callback：上传进度回调
     */
    void upload(String fileName,IUploadCallback callback);

    /**
     * 终端上传
     */
    void cancelUpload();

    /**
     * 获取上传的文件路径
     * @return
     */
    String getLocalFileName();
}
