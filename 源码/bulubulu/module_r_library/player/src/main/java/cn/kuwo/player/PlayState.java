package cn.kuwo.player;

public enum PlayState {
    INIT, // 启动没播过歌
    BUFFERING, // 播放中，等待缓冲
    PLAYING,
    PAUSE,
    STOP,
    ERROR
}
