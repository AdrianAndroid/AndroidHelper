#include <jni.h>
#include <string>
#include <android/log.h>
#include <android/native_window.h>
#include <android/native_window_jni.h>

#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,"testff",__VA_ARGS__)

extern "C" {
#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libavcodec/jni.h>
#include <libswscale/swscale.h> //像素格式转换
#include <libswresample/swresample.h>
}

#include <iostream>

using namespace std;

static double r2d(AVRational r) {
    return r.num == 0 || r.den == 0 ? 0. : (double) r.num / (double) r.den;
}

long long GetNowMs() {
    struct timeval tv;
    gettimeofday(&tv, NULL);
    int sec = tv.tv_sec % 360000;
    long long t = sec * 1000 + tv.tv_usec / 1000;
    return t;
}


extern "C"
JNIEXPORT
jint JNI_OnLoad(JavaVM *vm, void *res) {
    av_jni_set_java_vm(vm, 0);
    return JNI_VERSION_1_4;
}



extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_ffmpeg_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++ ";
    hello += avcodec_configuration();
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_ffmpeg_MainActivity_Open(JNIEnv *env, jobject thiz, jstring url_, jobject handle) {
    const char *url = env->GetStringUTFChars(url_, 0);

    // TODO
    FILE *fp = fopen(url, "rb");
    if (!fp) {
        LOGW("File %s open failed!", url);
    } else {
        LOGW("File %s open succes!", url);
        fclose(fp);
    }


    env->ReleaseStringUTFChars(url_, url);
    return true;
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_ffmpeg_XPlay_Open(JNIEnv *env, jobject thiz, jstring url_, jobject surface) {
    const char *path = env->GetStringUTFChars(url_, 0);

    //初始化解封装
    av_register_all();
    //初始化网络
    avformat_network_init();

    avcodec_register_all();

    //打开文件
    AVFormatContext *ic = NULL;
    //char path[] = "/sdcard/video.flv";
    int re = avformat_open_input(&ic,path,0,0);
    if(re != 0)
    {
        LOGW("avformat_open_input failed!:%s",av_err2str(re));
        return;
    }
    LOGW("avformat_open_input %s success!",path);
    //获取流信息
    re = avformat_find_stream_info(ic,0);
    if(re != 0)
    {
        LOGW("avformat_find_stream_info failed!");
    }
    LOGW("duration = %lld nb_streams = %d",ic->duration,ic->nb_streams);

    int fps = 0;
    int videoStream = 0;
    int audioStream = 1;

    for(int i = 0; i < ic->nb_streams; i++)
    {
        AVStream *as = ic->streams[i];
        if(as->codecpar->codec_type == AVMEDIA_TYPE_VIDEO)
        {
            LOGW("视频数据");
            videoStream = i;
            fps = r2d(as->avg_frame_rate);

            LOGW("fps = %d,width=%d height=%d codeid=%d pixformat=%d",fps,
                 as->codecpar->width,
                 as->codecpar->height,
                 as->codecpar->codec_id,
                 as->codecpar->format
            );
        }
        else if(as->codecpar->codec_type ==AVMEDIA_TYPE_AUDIO )
        {
            LOGW("音频数据");
            audioStream = i;
            LOGW("sample_rate=%d channels=%d sample_format=%d",
                 as->codecpar->sample_rate,
                 as->codecpar->channels,
                 as->codecpar->format
            );
        }
    }
    //ic->streams[videoStream];
    //获取音频流信息
    audioStream = av_find_best_stream(ic,AVMEDIA_TYPE_AUDIO,-1,-1,NULL,0);
    LOGW("av_find_best_stream audioStream = %d",audioStream);
    //////////////////////////////////////////////////////////
    //打开视频解码器
    //软解码器
    AVCodec *codec = avcodec_find_decoder(ic->streams[videoStream]->codecpar->codec_id);
    //硬解码
    codec = avcodec_find_decoder_by_name("h264_mediacodec");
    if(!codec)
    {
        LOGW("avcodec_find failed!");
        return;
    }
    //解码器初始化
    AVCodecContext *vc = avcodec_alloc_context3(codec);
    avcodec_parameters_to_context(vc,ic->streams[videoStream]->codecpar);

    vc->thread_count = 8;
    //打开解码器
    re = avcodec_open2(vc,0,0);
    //vc->time_base = ic->streams[videoStream]->time_base;
    LOGW("vc timebase = %d/ %d",vc->time_base.num,vc->time_base.den);
    if(re != 0)
    {
        LOGW("avcodec_open2 video failed!");
        return;
    }

    //////////////////////////////////////////////////////////
    //打开音频解码器
    //软解码器
    AVCodec *acodec = avcodec_find_decoder(ic->streams[audioStream]->codecpar->codec_id);
    //硬解码
    //codec = avcodec_find_decoder_by_name("h264_mediacodec");
    if(!acodec)
    {
        LOGW("avcodec_find failed!");
        return;
    }
    //音频解码器初始化
    AVCodecContext *ac = avcodec_alloc_context3(acodec);
    avcodec_parameters_to_context(ac,ic->streams[audioStream]->codecpar);
    ac->thread_count = 8;
    //打开解码器
    re = avcodec_open2(ac,0,0);
    if(re != 0)
    {
        LOGW("avcodec_open2  audio failed!");
        return;
    }
    //读取帧数据
    AVPacket *pkt = av_packet_alloc();
    AVFrame *frame = av_frame_alloc();
    long long start = GetNowMs();
    int frameCount = 0;


    //初始化像素格式转换的上下文
    SwsContext *vctx = NULL;
    int outWidth = 1280;
    int outHeight = 720;
    char *rgb = new char[1920*1080*4];
    char *pcm = new char[48000*4*2];

    //音频重采样上下文初始化
    SwrContext *actx = swr_alloc();
    actx = swr_alloc_set_opts(actx,
                              av_get_default_channel_layout(2),
                              AV_SAMPLE_FMT_S16,ac->sample_rate,
                              av_get_default_channel_layout(ac->channels),
                              ac->sample_fmt,ac->sample_rate,
                              0,0 );
    re = swr_init(actx);
    if(re != 0)
    {
        LOGW("swr_init failed!");
    }
    else
    {
        LOGW("swr_init success!");
    }

    //显示窗口初始化
    ANativeWindow *nwin = ANativeWindow_fromSurface(env,surface);
    ANativeWindow_setBuffersGeometry(nwin,outWidth,outHeight,WINDOW_FORMAT_RGBA_8888);
    ANativeWindow_Buffer wbuf;

    for(;;)
    {
        //超过三秒
        if(GetNowMs() - start >= 3000)
        {
            LOGW("now decode fps is %d",frameCount/3);
            start = GetNowMs();
            frameCount = 0;
        }

        int re = av_read_frame(ic,pkt);
        if(re != 0)
        {

            LOGW("读取到结尾处!");
            int pos = 20 * r2d(ic->streams[videoStream]->time_base);
            av_seek_frame(ic,videoStream,pos,AVSEEK_FLAG_BACKWARD|AVSEEK_FLAG_FRAME );
            continue;
        }

        AVCodecContext *cc = vc;
        if(pkt->stream_index == audioStream)
            cc=ac;

        //发送到线程中解码
        re = avcodec_send_packet(cc,pkt);
        //清理
        int p = pkt->pts;
        av_packet_unref(pkt);

        if(re != 0)
        {
            LOGW("avcodec_send_packet failed!");
            continue;
        }

        for(;;)
        {
            re = avcodec_receive_frame(cc,frame);
            if(re !=0)
            {
                //LOGW("avcodec_receive_frame failed!");
                break;
            }
            //LOGW("avcodec_receive_frame %lld",frame->pts);
            //如果是视频帧
            if(cc == vc)
            {
                frameCount++;
                vctx = sws_getCachedContext(vctx,
                                            frame->width,
                                            frame->height,
                                            (AVPixelFormat)frame->format,
                                            outWidth,
                                            outHeight,
                                            AV_PIX_FMT_RGBA,
                                            SWS_FAST_BILINEAR,
                                            0,0,0
                );
                if(!vctx)
                {
                    LOGW("sws_getCachedContext failed!");
                }
                else
                {
                    uint8_t *data[AV_NUM_DATA_POINTERS] = {0};
                    data[0] =(uint8_t *)rgb;
                    int lines[AV_NUM_DATA_POINTERS] = {0};
                    lines[0] = outWidth * 4;
                    int h = sws_scale(vctx,
                                      (const uint8_t **)frame->data,
                                      frame->linesize,0,
                                      frame->height,
                                      data,lines);
                    LOGW("sws_scale = %d",h);
                    if(h >= 0)
                    {
                        ANativeWindow_lock(nwin,&wbuf,0);
                        uint8_t *dst = (uint8_t*)wbuf.bits;
                        memcpy(dst,rgb,outWidth*outHeight*4);
                        ANativeWindow_unlockAndPost(nwin);
                    }
                }

            }
            else //音频
            {
                uint8_t *out[2] = {0};
                out[0] = (uint8_t*) pcm;

                //音频重采样
                int len = swr_convert(actx,out,
                                      frame->nb_samples,
                                      (const uint8_t**)frame->data,
                                      frame->nb_samples);
                LOGW("swr_convert = %d",len);
            }

        }

        //////////////////////


    }
    delete rgb;
    delete pcm;



    //关闭上下文
    avformat_close_input(&ic);



    env->ReleaseStringUTFChars(url_, path);
}