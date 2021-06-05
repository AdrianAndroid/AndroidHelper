package cn.kuwo.kwMp3Recorder;

import android.media.AudioFormat;

import org.ijkplayer.IjkUtil;

import cn.kuwo.base.KwTimer;
import cn.kuwo.common.util.RxUtilKt;
import cn.kuwo.common.utilscode.UtilsCode;

public class BuluRecorder implements KwTimer.Listener {
    private KwTimer kwTimer;
    private long mRecordStartTime;

    public boolean isRecording() {
        return isRecording;
    }

    private boolean isRecording;        //是否正在录制中状态
    private IRecordCallback mCallback;  //可以设置录音状态回调，因为有定时停止录制需求，所以有此设置
    private IRecorder mRecorder;        //真正实现录音的类
    private String mMp3FileName;

    /**
     * 使用默认参数录制音频
     * @param saveFile
     * @param limitTime
     */
    public void startRecord(String saveFile,int limitTime){
        startRecord(saveFile,limitTime,44100,AudioFormat.CHANNEL_IN_STEREO,AudioFormat.ENCODING_PCM_16BIT);
    }

    /**
     * 启动录音功能
     * @param saveFile：录音保存位置,文件扩展名必须是小写.mp3
     * @param limitTime：录音定时停止时间，毫秒为单位。0为不设置定时停止功能
     * @param sampleRate:录音参数：采样频率
     * @param channelNumber：录音参数：声道数
     * @param bitRate：录音参数：比特率
     */
    public void startRecord(String saveFile,int limitTime,int sampleRate, int channelNumber, int bitRate){
        if(isRecording){
            if(mCallback!=null){
                mCallback.onFailed(IRecordCallback.ERROR_IS_RUNNING,"已经录制中");
                return;
            }
        }
        if(mRecorder == null){
            mRecorder = buildKwRecorder();
        }
        mRecorder.initParams(sampleRate,channelNumber,bitRate);
        isRecording = true;
        mMp3FileName = saveFile;
        mRecorder.startRecord(saveFile);
        mRecordStartTime = System.currentTimeMillis();
        if(mCallback!=null){
            mCallback.onStart(saveFile);
        }
        if(limitTime>1000){
            startTimer(limitTime);
        }
    }

    public void stopRecord(){
        stopTimer();
        if(mRecorder!=null&&isRecording){
            isRecording =false;
            mRecorder.stopRecord();
            long runTime = System.currentTimeMillis()-mRecordStartTime;
            if(runTime<5000){
                if(mCallback!=null){
                    mCallback.onFailed(IRecordCallback.ERROR_TOO_SMALL,"录制时间太短,请录制5-60秒时长");
                }
            }else{
                createWavByMp3();
            }
        }
    }

    private void createWavByMp3() {
        if(UtilsCode.INSTANCE.isFileExists(mMp3FileName)){
            final String wavFileName = mMp3FileName.replace(".mp3",".wav");
            RxUtilKt.runOnIOThread(new Runnable() {
                @Override
                public void run() {
                    IjkUtil.convertAudio2Wav(mMp3FileName,wavFileName,1,11);
                    RxUtilKt.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mCallback!=null){
                                mCallback.onEnd(mMp3FileName,wavFileName,true);
                            }
                        }
                    });
                }
            });
        }
    }

    //==============================================================================
    @Override
    public void onTimer(KwTimer timer) {
        stopTimer();
        if(mRecorder!=null&&isRecording){
            isRecording = false;
            mRecorder.stopRecord();
            createWavByMp3();
        }
    }


    //==============================================================================
    private IRecorder buildKwRecorder(){
        return new mp3Encoder();
    }
    /**
     * 启动定时器，参数是毫秒
     * @param timeValue
     */
    private void startTimer(int timeValue){
        stopTimer();
        if(kwTimer == null){
            kwTimer = new KwTimer(this);
            kwTimer.start(timeValue);
        }
    }

    private void stopTimer() {
        if(kwTimer!=null&&kwTimer.isRunnig()){
            kwTimer.stop();
            kwTimer = null;
        }
    }

    public void removeCallback() {
        this.mCallback = null;
    }

    public void setCallback(IRecordCallback mCallback) {
        this.mCallback = mCallback;
    }
    //==============================================================================
    private static BuluRecorder mInstance;
    public static BuluRecorder getInstance(){
        if(mInstance==null){
            synchronized (BuluRecorder.class){
                if(mInstance == null){
                    mInstance = new BuluRecorder();
                }
            }
        }
        return mInstance;
    }

    public BuluRecorder() {
        isRecording = false;
    }
}
