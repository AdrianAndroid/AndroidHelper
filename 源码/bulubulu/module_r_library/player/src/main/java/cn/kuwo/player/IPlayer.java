package cn.kuwo.player;

/**
 * 方便解耦对外播放器和真实实现播放器
 *
 */
public interface IPlayer {
    PlayState getPlayStste();       //获取当前播放状态
    String getDataSource();
    long getProgress();
    long getDuration();
    boolean isPlaying();
    void setCallback(IPlayCallback callback);  //把统一抽象的外部回调列表，传入具体播放器
    void play(String dataSource);  //播放地址，可以是本地pcm文件，也可以是在线播放http
    void pause();
    void seek(long mec);
    void resume();
    void stop();
    void release();
}
