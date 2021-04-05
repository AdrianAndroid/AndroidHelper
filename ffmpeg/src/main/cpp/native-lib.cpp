#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_ffmpeg_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_ffmpeg_MainActivity_Open(JNIEnv *env, jobject thiz, jstring url_, jobject handle) {
    // 申请字符串
    const char *url = env->GetStringUTFChars(url_, 0);


    // 释放字符串
    env->ReleaseStringUTFChars(url_, url);
    return true;
}