package cn.kuwo.kwMp3Recorder;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.util.Log;

/**
 * Created by Administrator on 2019/4/16.
 * @brief 该类实现了录音和mp3编码器功能
 */

public class mp3Encoder implements IRecorder{
    static {
        System.loadLibrary("mp3Encoder");
    }
    String TAG = "mp3Encoder";
    private long mNativeInstance = 0;
    private int sampleRate = 44100;
    private int channelNumber = AudioFormat.CHANNEL_OUT_STEREO;
    private int bitRate = AudioFormat.ENCODING_PCM_16BIT;

    @Override
    public void initParams(int sampleRate, int channelNumber, int bitRate) {
        this.sampleRate = sampleRate;
        this.channelNumber = channelNumber;
        this.bitRate = bitRate;
    }

    @Override
    public boolean startRecord(String filePath) {
        if(mNativeInstance == 0)
        {
            mNativeInstance = _start(sampleRate, channelNumber, bitRate, filePath);
            return mNativeInstance!=0;
        }
        return false;
    }

    @Override
    public void stopRecord() {
        if(mNativeInstance != 0)
            _stop(mNativeInstance);
        mNativeInstance = 0;
    }

    private native long _start(int sampleRate, int channelNumber, int bitRate, String outPath);

    private native void _stop(long nativeInstance);

}
