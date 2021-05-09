package cn.kuwo.player;

/**
 * 应用抽象播放回调定义
 * 不和具体播放器实现相关联
 * onStart -> onBuffering ->onPlaying -> onProgress -> onPause/onStop
 */
public interface IPlayCallback {
    /**
     * 这是播放器触发prepared前回调。方便界面提前变化
     */
    void onPlayStart();
    /**
     * 缓冲状态
     * @param bufPercent：缓冲百分比 0-100
     */
    void onBuffering(int bufPercent);
    void onPlaying();
    /**
     * 播放进度回调函数(0-100)
     */
    void onProgress(int nProgress);
    void onPlayPause();
    void onPlayStop();
    void onSeekCompleted();
    void onError(PlayError code,String errExtMsg);
}
