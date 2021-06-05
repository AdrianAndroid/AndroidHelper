package cn.kuwo.player;

/**
 * ijk内核播放器特定回调定义
 */
public interface IIjkPlayCallback {
	/**
     * 播放器正式开始播放回调函数
     */
    void onStartPlaying();

    /**
     * 缓冲开始回调函数
     */
    void onStartBuffering();
    
    /**
     * 缓冲结束毁掉函数
     */
    void onEndBuffering();
    
    /**
     * 缓冲回调函数
     * @param cachePercent 缓冲进度百分比
     */
    void onBuffering(float cachePercent);

    /**
     * 播放进度回调函数(0-100)
     */
    void onProgress(int nProgress);

    /**
     * 播放暂停回调
     */
    void onPlayerPaused();

    /**
     * 播放停止回调函数
     */
    void onPlayerStopped();

    /**
     * 播放器出错回调函数
     */
    void onEncounteredError(int err);

    /**
     * seek完成的回调
     */
    void onSeekComplete();
}
