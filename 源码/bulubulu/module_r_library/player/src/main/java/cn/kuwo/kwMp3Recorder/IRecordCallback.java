package cn.kuwo.kwMp3Recorder;

public interface IRecordCallback {
    public static final int ERROR_IS_RUNNING = 1;   //正在录制中，启动录音失败
    public static final int ERROR_ENCODER_INIT = 2;   //录音内核初始化失败
    public static final int ERROR_TOO_SMALL = 2;   //录音时间太短，不能转码wav
    void onStart(String saveFileName);
    void onEnd(String mp3File,String wavFile,boolean isUserStop);
    void onFailed(int code,String msg);
}
