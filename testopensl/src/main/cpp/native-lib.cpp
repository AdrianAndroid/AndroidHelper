/*******************************************************************************
**                                                                            **
**                     Jiedi(China nanjing)Ltd.                               **
**	               创建：夏曹俊，此代码可用作为学习参考                       **
*******************************************************************************/

/*****************************FILE INFOMATION***********************************
**
** Project       : FFmpeg
** Description   : FFMPEG项目创建示例
** Contact       : xiacaojun@qq.com
**        博客   : http://blog.csdn.net/jiedichina
**		视频课程 : 网易云课堂	http://study.163.com/u/xiacaojun		
				   腾讯课堂		https://jiedi.ke.qq.com/				
				   csdn学院		http://edu.csdn.net/lecturer/lecturer_detail?lecturer_id=961	
**                 51cto学院	http://edu.51cto.com/lecturer/index/user_id-12016059.html	
** 				   下载最新的ffmpeg版本 ffmpeg.club
**                 
**   安卓流媒体播放器 课程群 ：23304930 加入群下载代码和交流
**   微信公众号  : jiedi2007
**		头条号	 : 夏曹俊
**
*******************************************************************************/
//！！！！！！！！！ 加群23304930下载代码和交流

#include <jni.h>
#include <string>
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include <android/log.h>
#define LOGD(FORMAT,...) __android_log_print(ANDROID_LOG_ERROR,"ywl5320",FORMAT,##__VA_ARGS__);


static SLObjectItf  engineSL = NULL; //对应的引擎对象
SLEngineItf CreateSL()
{
    SLresult re;
    SLEngineItf en;
    // 1. 创建引擎
    re = slCreateEngine(&engineSL,0,0,0,0,0);
    if(re != SL_RESULT_SUCCESS) return NULL;
    // 2. 等待创建
    re = (*engineSL)->Realize(engineSL,SL_BOOLEAN_FALSE);
    if(re != SL_RESULT_SUCCESS) return NULL;
    // 3. 获取接口
    re = (*engineSL)->GetInterface(engineSL,SL_IID_ENGINE,&en);
    if(re != SL_RESULT_SUCCESS) return NULL;
    return en;
}

extern "C"
JNIEXPORT jstring

JNICALL
Java_aplay_testopensl_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";

    //1 创建引擎
    SLEngineItf eng = CreateSL();
    if(eng)
    {
        LOGD("CreateSL success！ ");
    }
    else
    {
        LOGD("CreateSL failed！ ");
    }
    // 2.创建混音器
    SLObjectItf  mix = NULL;
    SLresult  re = 0;
    re = (*eng)->CreateOutputMix(eng, &mix, 0, 0, 0);
    if(re != SL_RESULT_SUCCESS)
    {
        LOGD("SL_RESULT_SUCCESS failed!");
    }
    re = (*mix)->Realize(mix,SL_BOOLEAN_FALSE);
    if(re != SL_RESULT_SUCCESS)
    {
        LOGD("(*mix)->Realize failed!");
    }

    SLDataLocator_OutputMix outmix = {SL_DATALOCATOR_OUTPUTMIX, mix};
    SLDataSink audioSink = {&outmix, 0};

    // 3. 配置音频信息
    // 缓冲队列
    SLDataLocator_AndroidSimpleBufferQueue  que = {SL_DATALOCATOR_ANDROIDSIMPLEBUFFERQUEUE, 10};
    //音频格式
    SLDataFormat_PCM pcm = {
            SL_DATAFORMAT_PCM,
            2, //声道数
            SL_SAMPLINGRATE_44_1,
            SL_PCMSAMPLEFORMAT_FIXED_16,
            SL_PCMSAMPLEFORMAT_FIXED_16,
            SL_SPEAKER_FRONT_LEFT | SL_SPEAKER_FRONT_RIGHT,
            SL_BYTEORDER_LITTLEENDIAN //字节序，小端
    };

    return env->NewStringUTF(hello.c_str());
}
