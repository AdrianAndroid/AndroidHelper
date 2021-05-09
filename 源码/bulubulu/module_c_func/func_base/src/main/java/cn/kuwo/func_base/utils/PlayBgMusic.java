package cn.kuwo.func_base.utils;

import android.media.MediaPlayer;

import cn.kuwo.common.app.App;
import cn.kuwo.pp.R;

public class PlayBgMusic {

    private static MediaPlayer mBgMusicPlayer;

    public static void rePlayBgMusic() {
        stopBgMusic(); // 先停止
        playBgMusic();
    }

    public static void playBgMusic() {
        if (isPlaying()) return; // 不再重复播放
        if (mBgMusicPlayer == null) {
            mBgMusicPlayer = MediaPlayer.create(App.getInstance(), R.raw.friend_test_bg);
            mBgMusicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mBgMusicPlayer.start();
                }
            });
        }
        mBgMusicPlayer.start();
    }

    public static void stopBgMusic() {
        if (mBgMusicPlayer != null) {
            if (mBgMusicPlayer.isPlaying()) {
                mBgMusicPlayer.stop();
            }
            mBgMusicPlayer.release();
            mBgMusicPlayer.setOnCompletionListener(null);
            mBgMusicPlayer = null;
        }
    }

    public static boolean isPlaying() {
        return mBgMusicPlayer != null && mBgMusicPlayer.isPlaying();
    }


}
