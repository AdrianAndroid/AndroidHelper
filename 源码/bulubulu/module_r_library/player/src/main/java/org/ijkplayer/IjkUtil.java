package org.ijkplayer;

/**
 * Created by Administrator on 2019/4/17.
 */

public class IjkUtil {

    static {
        System.loadLibrary("ijkffmpeg");
        System.loadLibrary("ijkutil");
    }

    /**
     * 转换传入的mp3为wav文件，可以截取启动一部分
     * @param in 源文件 mp3路径
     * @param out 输出文件  wav路径
     * @param start 开始时间 （秒）
     * @param end 结束时间 (秒） 1-11 输出会是10秒长的
     * @return
     */
    public static int convertAudio2Wav(String in, String out, float start, float end)
    {
        return cutAudiotoWav(in, out, start, end, 1, 8000);
    }
    private static native int cutAudiotoWav(String in, String out, float start, float end, int outChannel, int outSamplerate);
}
