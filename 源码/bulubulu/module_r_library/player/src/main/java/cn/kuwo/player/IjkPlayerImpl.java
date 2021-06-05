package cn.kuwo.player;

import android.os.PowerManager;
import android.util.Log;

import org.ijkplayer.IMediaPlayer;
import org.ijkplayer.IjkMediaPlayer;

import cn.kuwo.base.KwTimer;
import cn.kuwo.common.app.App;

import java.io.IOException;

/**
 * 包装ijkPlayer对象，实现抽象播放器对外接口
 *
 */
public class IjkPlayerImpl implements IPlayer,KwTimer.Listener {
    /**
     * ijk自有的播放器状态定义
     */
    public static final int STATE_ERROR = -1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_PREPARING = 1;
    public static final int STATE_PREPARED = 2;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_PAUSED = 4;
    public static final int STATE_PLAYBACK_COMPLETED = 5;
    public static final int STATE_SUSPEND = 6;
    public static final int STATE_RESUME = 7;
    public static final int STATE_BUFFERING = 8;
    public static final int STATE_SUSPEND_UNSUPPORTED = 9;

    private static final String TAG = "IjkPlayerImpl";
    private IjkMediaPlayer mPlayer;
    private int 	mCurrentState = STATE_IDLE;
    private int 	mTargetState = STATE_IDLE;
    private int mCurrentBufferPercentage;
    private boolean mSeekFlag;
    private IPlayCallback callbackList;
    private KwTimer mTimer = null;
    private int mContinuePos = 0;

    public IjkPlayerImpl() {
        mPlayer = new IjkMediaPlayer();
        mPlayer.setWakeMode(App.getInstance(), PowerManager.PARTIAL_WAKE_LOCK);
        //最大缓冲时间
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "last-high-water-mark-ms",     5000);
        //设置启动缓冲时间
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "first-high-water-mark-ms",     2000);
        mPlayer.setOnPreparedListener(mPreparedListener);
        mPlayer.setOnCompletionListener(mCompletionListener);
        mPlayer.setOnErrorListener(mErrorListener);
        mPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
        mPlayer.setOnInfoListener(mInfoListener);
        mPlayer.setOnSeekCompleteListener(mSeekCompleteListener);
    }

    @Override
    public PlayState getPlayStste() {
        switch (mCurrentState){
            case STATE_IDLE:
                return PlayState.INIT;
            case STATE_PREPARING:
                return PlayState.BUFFERING;
            case STATE_PREPARED:
                return PlayState.BUFFERING;
            case STATE_PLAYING:
                return PlayState.PLAYING;
            case STATE_PAUSED:
                return PlayState.PAUSE;
            case STATE_ERROR:
                return PlayState.ERROR;
            case STATE_BUFFERING:
                return PlayState.BUFFERING;
            case STATE_PLAYBACK_COMPLETED:
                return PlayState.STOP;
            default:
                return PlayState.INIT;
        }
    }

    @Override
    public String getDataSource() {
        if(mPlayer!=null){
            return mPlayer.getDataSource();
        }
        return null;
    }

    @Override
    public long getProgress() {
        if(mPlayer!=null&&mCurrentState!=STATE_IDLE&&mCurrentState!=STATE_ERROR&&mCurrentState!=STATE_PLAYBACK_COMPLETED){
            try{
                return (int) mPlayer.getCurrentPosition();
            }catch (Exception e){

            }
        }
        return 0;
    }

    @Override
    public long getDuration() {
        if(mPlayer!=null&&mCurrentState!=STATE_IDLE&&mCurrentState!=STATE_ERROR){
            try{
                return (int) mPlayer.getDuration();
            }catch (Exception e){

            }
        }
        return 0;
    }

    @Override
    public boolean isPlaying() {
        return mCurrentState == STATE_PLAYING;
    }

    @Override
    public void setCallback(IPlayCallback callback) {
        this.callbackList = callback;
    }

    @Override
    public void play(String dataSource) {
        if(mPlayer!=null){
            try {
                mPlayer.setDataSource(dataSource);
                callbackList.onPlayStart();  //设置好数据源就触发onStart
                startTimer();
                mPlayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
                callbackList.onError(PlayError.ERROR_PREPARE,"数据加载异常");
            }
        }
    }

    /**
     * 暂停之后再次继续播放
     */
    public void start() {
        if(mPlayer != null){
            mPlayer.start();
            mCurrentState = STATE_PLAYING;
        }
    }

    @Override
    public void pause() {
        if(mPlayer != null){
            mPlayer.pause();
            mCurrentState = STATE_PAUSED;
            callbackList.onPlayPause();
        }
    }

    @Override
    public void seek(long mec) {
        if(mPlayer != null){
            mPlayer.seekTo(mec);
        }
    }

    @Override
    public void resume() {
        if(mPlayer != null){
            mPlayer.start();
            mCurrentState = STATE_PLAYING;
            callbackList.onPlayStart();
        }
    }

    @Override
    public void stop() {
        if(mPlayer != null){
            mPlayer.stop();
            if(mCurrentState!=STATE_PLAYBACK_COMPLETED){
                mCurrentState = STATE_PLAYBACK_COMPLETED;
                callbackList.onPlayStop();
            }
        }
        stopTimer();
    }

    @Override
    public void release() {
        stopTimer();
        if(mPlayer != null){
            mPlayer.stop();
            if(mCurrentState!=STATE_PLAYBACK_COMPLETED){
                mCurrentState = STATE_PLAYBACK_COMPLETED;
                callbackList.onPlayStop();
            }
            mPlayer.release();
            mPlayer = null;
        }
    }

    //=========================================================================================
    /**
     * Callback：播放器准备完成
     * Not Used Now
     */
    IMediaPlayer.OnPreparedListener mPreparedListener = new IMediaPlayer.OnPreparedListener() {
        public void onPrepared(IMediaPlayer mp) {
            Log.i(TAG, "onPrepared");
            mCurrentState = STATE_PREPARED;
            mTargetState = STATE_PLAYING;
            if (mTargetState == STATE_PLAYING) {
                start();
                callbackList.onPlaying();
            }
        }
    };

    /**
     * Callback：播放完成
     */
    private IMediaPlayer.OnCompletionListener mCompletionListener = new IMediaPlayer.OnCompletionListener() {
        public void onCompletion(IMediaPlayer mp) {
            Log.i(TAG, "onCompletion");
            if(mCurrentState!=STATE_PLAYBACK_COMPLETED){
                mCurrentState = STATE_PLAYBACK_COMPLETED;
                callbackList.onPlayStop();
            }
            mCurrentState = STATE_PLAYBACK_COMPLETED;
            mTargetState = STATE_PLAYBACK_COMPLETED;
        }
    };

    /**
     * Callback：播放错误
     */
    private IMediaPlayer.OnErrorListener mErrorListener = new IMediaPlayer.OnErrorListener() {
        public boolean onError(IMediaPlayer mp, int framework_err, int impl_err) {
            Log.i(TAG, "Error: " + framework_err + ", " + impl_err);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            stopTimer();
            switch(impl_err)
            {
                case IMediaPlayer.MEDIA_ERROR_EIO:
                    callbackList.onError(PlayError.IO_ERROR,"网络受到神秘力量干扰?");
                    break;
                case IMediaPlayer.MEDIA_ERROR_ENOENT:
                    callbackList.onError(PlayError.FILENOTEXIST,"网络受到神秘力量干扰.");
                    break;
                case IMediaPlayer.MEDIA_ERROR_SERVER_DIED:
                case IMediaPlayer.MEDIA_ERROR_CONNECTION_REFUSED:
                case IMediaPlayer.MEDIA_ERROR_NETWORK_UNREACHABLE:
                    callbackList.onError(PlayError.NO_NETWORK,"网络受到神秘力量干扰!");
                    break;
                default :
                    callbackList.onError(PlayError.ERROR_UNKNOWN,"网络受到神秘力量干扰");
                    break;
            }
            return true;
        }
    };

    /**
     * Callback：缓冲进度(0-100)
     * 直播时总是0
     */
    private IMediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            if (mCurrentBufferPercentage != percent) {
                mCurrentBufferPercentage = percent;
                callbackList.onBuffering(mCurrentBufferPercentage);
            }
        }
    };

    /**
     * Callback：?????
     */
    private IMediaPlayer.OnInfoListener mInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer mp, int arg1, int arg2) {
            Log.i(TAG, "onInfo: (" + arg1 + "," + arg2 + " )");

            switch (arg1) {
                case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                    Log.i(TAG, "onInfo: (MEDIA_INFO_VIDEO_TRACK_LAGGING)");
                    break;
                case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    Log.i(TAG, "onInfo: (MEDIA_INFO_VIDEO_RENDERING_START)");
                    mCurrentState = STATE_PLAYING;
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    Log.i(TAG, "onInfo: (MEDIA_INFO_BUFFERING_START)");
                    if(mCurrentState == STATE_PLAYING) {
                        if(!mSeekFlag) {
                            mCurrentState = STATE_BUFFERING;
                            callbackList.onBuffering(0);
                        }
                    }
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    Log.i(TAG, "onInfo: (MEDIA_INFO_BUFFERING_END)");
                    if(mCurrentState == STATE_BUFFERING) {
                        if (!mSeekFlag) {
                            callbackList.onPlaying();
                        }
                        mCurrentState = STATE_PLAYING;
                    }
                    break;
                case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                    Log.i(TAG, "onInfo: (MEDIA_INFO_NETWORK_BANDWIDTH: " + arg2 + ")");
                    break;
                case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                    Log.i(TAG, "onInfo: (MEDIA_INFO_BAD_INTERLEAVING)");
                    break;
                case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                    Log.i(TAG, "onInfo: (MEDIA_INFO_NOT_SEEKABLE)");
                    break;
                case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                    Log.i(TAG, "onInfo: (MEDIA_INFO_METADATA_UPDATE)");
                    break;
                case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:
                    Log.i(TAG, "onInfo: (MEDIA_INFO_UNSUPPORTED_SUBTITLE)");
                    break;
                case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:
                    Log.i(TAG, "onInfo: (MEDIA_INFO_SUBTITLE_TIMED_OUT)");
                    break;
                case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
//                    mVideoRotationDegree = arg2;
                    Log.i(TAG, "onInfo: (MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + arg2 + ")");
//                    setVideoRotation(arg2);
                    break;
                case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                    Log.i(TAG, "onInfo: (MEDIA_INFO_AUDIO_RENDERING_START):");
                    mCurrentState = STATE_PLAYING;
                    break;
                case IMediaPlayer.MEDIA_INFO_AUTOSTOPPED:
                    Log.w(TAG, "onInfo: (MEDIA_INFO_AUTOSTOPPED):");
//                    restartAuto();
                    break;
            }

            return true;
        }
    };

    /**
     * Callback：Seek完成事件
     */
    private IMediaPlayer.OnSeekCompleteListener mSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(IMediaPlayer mp) {
            Log.i(TAG, "onSeekComplete");
            callbackList.onSeekCompleted();
            mSeekFlag = false;
        }
    };

    private void startTimer(){
        if (mTimer == null) {
            mTimer = new KwTimer(this);
        }
        mTimer.setListener(this);
        mTimer.start(100);
    }

    private void stopTimer() {
        if (mTimer != null) {
            mTimer.stop();
        }
    }

    @Override
    public void onTimer(KwTimer timer) {
        if(mPlayer!=null&&mPlayer.isPlaying()){
            if(getDuration()>0){
                float present = getProgress()*100.0f/getDuration();
                if(present>96f){
                    present = 100f;
                }
                callbackList.onProgress((int) present);
            }
        }
    }

}
