package cn.kuwo.player;

import android.annotation.SuppressLint;
import android.app.Service;
import android.media.AudioManager;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.kuwo.common.app.App;
import cn.kuwo.common.utilscode.UtilsCode;

/**
 * 自定义播放器
 * 内部根据要播放音频格式不同，动态切换不同播放器去播放
 * mp3使用ijk,pcm使用audiotrack
 */
public class BuluPlayer implements IPlayCallback {
    private IPlayer mPlayer;        //保存当前使用的播放器实现对象，根据音频格式不同，可以是ijkplayer，mediaplayer,audiotrack等
    private KwOnAudioFocusChangeListener onAudioFocusChangeListener;
    private AudioManager audioMgr;
    private List<IPlayCallback> callbackList = Collections.synchronizedList(new ArrayList<IPlayCallback>(3));
    private Map<String, List<IPlayCallback>> singleUrlCallback = new HashMap<>();

    public void addPlayCallback(IPlayCallback callback) {
        if (callback == null) {
            return;
        }
        if (callbackList.contains(callback)) {
            return;
        }
        callbackList.add(callback);
    }

    public void addPlayCallback(String url, IPlayCallback callback) {
        if (callback == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            addPlayCallback(callback);
            return;
        }
        List<IPlayCallback> callbacks = singleUrlCallback.get(url);
        if (callbacks == null) {
            callbacks = new ArrayList<>();
        }
        if (callbacks.contains(callback)) {
            return;
        }
        callbacks.add(callback);
        singleUrlCallback.put(url, callbacks);
    }

    public void removePlayCallback(String url, IPlayCallback callback) {
        if (callback == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            removePlayCallback(callback);
            return;
        }
        List<IPlayCallback> callbacks = singleUrlCallback.get(url);
        if (UtilsCode.INSTANCE.isNotEmpty(callbacks) && callbacks.contains(callback)) {
            callbacks.remove(callback);
        }
    }

    public void removePlayCallback(IPlayCallback callback) {
        if (callback == null) {
            return;
        }
        callbackList.remove(callback);
    }

    public void play(String dataSource) {
        releasePlayer(false);  //释放当前正在播放音频
        requestAudioFocus();
        mPlayer = buildMp3Player();
        if (mPlayer != null) {
            mPlayer.play(dataSource);
        }
    }

    public void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    public void resume() {
        if (mPlayer != null) {
            mPlayer.resume();
        }
    }

    public void autoSwitch() {
        if (mPlayer != null && mPlayer.getDataSource() != null) {
            if (isPlaying()) {
                pause();
            } else {
                resume();
            }
        }
    }

    public void stop() {
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }

    public void release() {
        if (mPlayer != null) {
            mPlayer.release();
        }
    }

    public void seek(long mec) {
        if (mPlayer != null) {
            mPlayer.seek(mec);
        }
    }

    public String getDataSource() {
        if (mPlayer != null) {
            return mPlayer.getDataSource();
        }
        return null;
    }

    public PlayState getCurrentState() {
        if (mPlayer != null) {
            return mPlayer.getPlayStste();
        }
        return PlayState.INIT;
    }

    public void releasePlayer(boolean abandonFocus) {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        if (abandonFocus) {
            abandonAudioFocus();
        }
    }

    public boolean isPlaying() {
        if (mPlayer != null) {
            return mPlayer.isPlaying();
        }
        return false;
    }

    //===========================================================================================
    private IPlayer buildMp3Player() {
        IPlayer player = new IjkPlayerImpl();
        player.setCallback(mInstance);  //因为要实现绑定多个监听功能，所以让播放器管理类自己实现接口，然后循环通知外面客户端
        return player;
    }

    private void requestAudioFocus() {
        try {
            if (onAudioFocusChangeListener == null) {
                onAudioFocusChangeListener = new KwOnAudioFocusChangeListener();
            }
        } catch (Throwable e) {
            return;
        }
        try {
            if (audioMgr.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN) != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                audioMgr.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
            }
        } catch (Throwable e) {
        }
        try {
            if (audioMgr.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_ALARM,
                    AudioManager.AUDIOFOCUS_GAIN) != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                audioMgr.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_ALARM,
                        AudioManager.AUDIOFOCUS_GAIN);
            }
        } catch (Throwable e) {
        }
    }

    private void abandonAudioFocus() {
        if (onAudioFocusChangeListener != null) {
            audioMgr.abandonAudioFocus(onAudioFocusChangeListener);
            onAudioFocusChangeListener = null;
        }
    }

    //------------------------------------------------------------------------------------------
    @Override
    public void onPlayStart() {
        for (int i = 0; i < callbackList.size(); i++) {
            callbackList.get(i).onPlayStart();
        }
        if (UtilsCode.INSTANCE.isNotEmpty(singleUrlCallback.get(mPlayer.getDataSource()))) {
            for (IPlayCallback callback : singleUrlCallback.get(mPlayer.getDataSource())) {
                callback.onPlayStart();
            }
        }
    }

    @Override
    public void onBuffering(int bufPercent) {
        for (int i = 0; i < callbackList.size(); i++) {
            callbackList.get(i).onBuffering(bufPercent);
        }
        if (UtilsCode.INSTANCE.isNotEmpty(singleUrlCallback.get(mPlayer.getDataSource()))) {
            for (IPlayCallback callback : singleUrlCallback.get(mPlayer.getDataSource())) {
                callback.onBuffering(bufPercent);
            }
        }
    }

    @Override
    public void onPlaying() {
        for (int i = 0; i < callbackList.size(); i++) {
            callbackList.get(i).onPlaying();
        }
        if (UtilsCode.INSTANCE.isNotEmpty(singleUrlCallback.get(mPlayer.getDataSource()))) {
            for (IPlayCallback callback : singleUrlCallback.get(mPlayer.getDataSource())) {
                callback.onPlaying();
            }
        }
    }

    @Override
    public void onProgress(int nProgress) {
        for (int i = 0; i < callbackList.size(); i++) {
            callbackList.get(i).onProgress(nProgress);
        }
        if (UtilsCode.INSTANCE.isNotEmpty(singleUrlCallback.get(mPlayer.getDataSource()))) {
            for (IPlayCallback callback : singleUrlCallback.get(mPlayer.getDataSource())) {
                callback.onProgress(nProgress);
            }
        }
    }

    @Override
    public void onPlayPause() {
        for (int i = 0; i < callbackList.size(); i++) {
            callbackList.get(i).onPlayPause();
        }
        if (UtilsCode.INSTANCE.isNotEmpty(singleUrlCallback.get(mPlayer.getDataSource()))) {
            for (IPlayCallback callback : singleUrlCallback.get(mPlayer.getDataSource())) {
                callback.onPlayPause();
            }
        }
    }

    @Override
    public void onPlayStop() {
        for (int i = 0; i < callbackList.size(); i++) {
            callbackList.get(i).onPlayStop();
        }
        if (UtilsCode.INSTANCE.isNotEmpty(singleUrlCallback.get(mPlayer.getDataSource()))) {
            for (IPlayCallback callback : singleUrlCallback.get(mPlayer.getDataSource())) {
                callback.onPlayStop();
            }
        }
    }

    @Override
    public void onSeekCompleted() {
        for (int i = 0; i < callbackList.size(); i++) {
            callbackList.get(i).onSeekCompleted();
        }
        if (UtilsCode.INSTANCE.isNotEmpty(singleUrlCallback.get(mPlayer.getDataSource()))) {
            for (IPlayCallback callback : singleUrlCallback.get(mPlayer.getDataSource())) {
                callback.onSeekCompleted();
            }
        }
    }

    @Override
    public void onError(PlayError code, String errExtMsg) {
        for (int i = 0; i < callbackList.size(); i++) {
            callbackList.get(i).onError(code, errExtMsg);
        }
        if (UtilsCode.INSTANCE.isNotEmpty(singleUrlCallback.get(mPlayer.getDataSource()))) {
            for (IPlayCallback callback : singleUrlCallback.get(mPlayer.getDataSource())) {
                callback.onError(code, errExtMsg);
            }
        }
    }

    public boolean isPlayingUrl(@Nullable String url) {
        if (mPlayer != null && url != null && url.equals(mPlayer.getDataSource()) && (mPlayer.isPlaying() || mPlayer.getPlayStste() == PlayState.PAUSE)) {
            return true;
        }
        return false;
    }


    //---------------------------------------------------------------------------------------

    @SuppressLint("NewApi")
    private class KwOnAudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {
        @Override

        public void onAudioFocusChange(final int focusChange) {

            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS:// 你已经丢失了音频焦点比较长的时间了．你必须停止所有的音频播放．因为预料到你可能很长时间也不能再获音频焦点，所以这里是清理你的资源的好地方．比如，你必须释放
                case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                    // 音频焦点丢失不做处理，继续播放
                    onLostAudioFocus(true);
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:// 你临时性的丢掉了音频焦点，但是你被允许继续以低音量播放，而不是完全停止．
                    lowerVolume();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:// 你临时性的丢掉了音频焦点，很快就会重新获得．你必须停止所有的音频播放，但是可以保留你的资源，因为你可能很快就能重新获得焦点．
                    // 双卡手机的副卡接听或者拨打电话会走到这一步（不会被电话状态监听器监听到 MyPhoneStateListener）
                    onLostAudioFocus(true);
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE:
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                    resumeVolume();
                    onGainAudioFocus();
                    break;
            }
        }
    }

    private void resumeVolume() {

    }

    private void lowerVolume() {

    }

    private void onGainAudioFocus() {

    }

    private void onLostAudioFocus(boolean isAudioFocus) {

    }

    //===========================================================================================
    private static BuluPlayer mInstance;

    public static BuluPlayer getInstance() {
        if (mInstance == null) {
            synchronized (BuluPlayer.class) {
                if (mInstance == null) {
                    mInstance = new BuluPlayer();
                }
            }
        }
        return mInstance;
    }

    public BuluPlayer() {
        audioMgr = (AudioManager) App.getInstance().getSystemService(Service.AUDIO_SERVICE);
    }


}
