package cn.kuwo.player;

/**
 * 应用抽象播放回调定义
 * @author shihc
 */
public class PlayCallback implements IPlayCallback{
    @Override
    public void onPlayStart() {

    }

    @Override
    public void onBuffering(int bufPercent) {

    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onProgress(int nProgress) {

    }

    @Override
    public void onPlayPause() {

    }

    @Override
    public void onPlayStop() {

    }

    @Override
    public void onSeekCompleted() {

    }

    @Override
    public void onError(PlayError code, String errExtMsg) {

    }
}
