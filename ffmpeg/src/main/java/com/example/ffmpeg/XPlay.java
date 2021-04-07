package com.example.ffmpeg;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.widget.inline.InlineContentView;

public class XPlay extends GLSurfaceView implements Runnable, SurfaceHolder.Callback {

    public XPlay(Context context) {
        super(context);
    }

    public XPlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void run() {
        //视频的播放
        Open("/sdcard/1080.mp4", getHolder().getSurface());
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(this).start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

    }

    public native void Open(String url, Object surface);
}
