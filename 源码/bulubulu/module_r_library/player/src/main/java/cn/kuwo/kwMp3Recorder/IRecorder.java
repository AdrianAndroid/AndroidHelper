package cn.kuwo.kwMp3Recorder;

public interface IRecorder {
    void initParams(int sampleRate, int channelNumber, int bitRate);
    boolean startRecord(String filePath);
    void stopRecord();
}
