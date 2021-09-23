package cn.kuwo.pp.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import cn.kuwo.player.BuluPlayer;
import cn.kuwo.player.IPlayCallback;
import cn.kuwo.player.PlayError;

/**
 * @author shihc
 * 播放按钮
 */
public class PlayButton extends IconFountTextView {
    private boolean isPlaying;
    private String mPlayUrl;
    private IPlayCallback mPlayCallback = new IPlayCallback() {
        @Override
        public void onPlayStart() {
            setPlaying(true);
        }

        @Override
        public void onBuffering(int bufPercent) {
            setPlaying(true);
        }

        @Override
        public void onPlaying() {
            setPlaying(true);

        }

        @Override
        public void onProgress(int nProgress) {

        }

        @Override
        public void onPlayPause() {
            setPlaying(false);
        }

        @Override
        public void onPlayStop() {
            setPlaying(false);

        }

        @Override
        public void onSeekCompleted() {

        }

        @Override
        public void onError(PlayError code, String errExtMsg) {
            setPlaying(false);
        }
    };

    public PlayButton(Context context) {
        this(context, null);
    }

    public PlayButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setPlaying(BuluPlayer.getInstance().isPlayingUrl(mPlayUrl));
        if (!TextUtils.isEmpty(mPlayUrl)) {
            BuluPlayer.getInstance().removePlayCallback(mPlayUrl,mPlayCallback);
            BuluPlayer.getInstance().addPlayCallback(mPlayUrl, mPlayCallback);
        }
        setOnClickListener(v -> {
            if (BuluPlayer.getInstance().isPlayingUrl(mPlayUrl)) {
                BuluPlayer.getInstance().autoSwitch();
            } else {
                post(() -> {
                    BuluPlayer.getInstance().play(mPlayUrl);
                });
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!TextUtils.isEmpty(mPlayUrl)) {
            BuluPlayer.getInstance().removePlayCallback(mPlayUrl, mPlayCallback);
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        if(isPlaying!=playing){
            isPlaying = playing;
            if (isPlaying) {
                setIconText("&#xe6d2;");
            } else {
                setIconText("&#xe6d3;");
            }
        }
    }

    public void setPlayUrl(String playUrl) {
        if(TextUtils.isEmpty(playUrl)){
            BuluPlayer.getInstance().removePlayCallback(mPlayUrl, mPlayCallback);
            mPlayUrl = playUrl;
            return;
        }
        BuluPlayer.getInstance().removePlayCallback(mPlayUrl, mPlayCallback);
        mPlayUrl = playUrl;
        BuluPlayer.getInstance().addPlayCallback(mPlayUrl, mPlayCallback);
        setPlaying(false);
    }

    public void startPlay() {
        if (!TextUtils.isEmpty(mPlayUrl)) {
            BuluPlayer.getInstance().play(mPlayUrl);
        }
    }
}
