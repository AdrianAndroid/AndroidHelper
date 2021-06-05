package cn.kuwo.common.event;

import android.graphics.drawable.Drawable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LikeAnimEvent {
    public static final int ANIM_INIT      = 1;
    public static final int ANIM_START     = 2;
    public static final int ANIM_END       = 3;
    public static final int ANIM_TXT_END       = 8;
    public static final int ANIM_INFO       = 4;
    public static final int ANIM_PLAYING       = 5;
    public static final int ANIM_STOP       = 6;
    public static final int ANIM_STARTED       = 7;  //动画回调启动事件
    public static final int ANIM_CANCEL       = 9;  //动画回调启动事件

    private int animEvent = ANIM_INIT;
    private int srcLeft;
    private int srcTop;
    private int srcWidth;
    private int srcHeight;

    private Object srcInfo;
    private Drawable animImage;


    public int getAnimEvent() {
        return animEvent;
    }

    public void setAnimEvent(int animEvent) {
        this.animEvent = animEvent;
    }

    public int getSrcLeft() {
        return srcLeft;
    }

    public void setSrcLeft(int srcLeft) {
        this.srcLeft = srcLeft;
    }

    public int getSrcTop() {
        return srcTop;
    }

    public void setSrcTop(int srcTop) {
        this.srcTop = srcTop;
    }

    public int getSrcWidth() {
        return srcWidth;
    }

    public void setSrcWidth(int srcWidth) {
        this.srcWidth = srcWidth;
    }

    public int getSrcHeight() {
        return srcHeight;
    }

    public void setSrcHeight(int srcHeight) {
        this.srcHeight = srcHeight;
    }

    public LikeAnimEvent(int animEvent) {
        this.animEvent = animEvent;
    }
    public LikeAnimEvent(Object infoObj) {
        this.animEvent = ANIM_INFO;
        this.srcInfo = infoObj;
    }

    public LikeAnimEvent(int animEvent, int srcLeft, int srcTop, int srcWidth, int srcHeight) {
        this.animEvent = animEvent;
        this.srcLeft = srcLeft;
        this.srcTop = srcTop;
        this.srcWidth = srcWidth;
        this.srcHeight = srcHeight;
    }

    public static LikeAnimEvent getInitAnimEvent(int left, int top, int width, int height){
        LikeAnimEvent event = new LikeAnimEvent(ANIM_INIT,left,top,width,height);
        return event;
    }

    public static LikeAnimEvent getStartAnimEvent(){
        return new LikeAnimEvent(ANIM_START);
    }

    public static LikeAnimEvent getStartedEvent(){
        return new LikeAnimEvent(ANIM_STARTED);
    }

    public static LikeAnimEvent getEndAnimEvent(){
        return new LikeAnimEvent(ANIM_END);
    }
    public static LikeAnimEvent getTxtEndAnimEvent(){
        return new LikeAnimEvent(ANIM_TXT_END);
    }
    public static LikeAnimEvent getPlayingAnimEvent(){
        return new LikeAnimEvent(ANIM_PLAYING);
    }

    public static LikeAnimEvent getStopAnimEvent(){
        return new LikeAnimEvent(ANIM_STOP);
    }

    @NotNull
    public static LikeAnimEvent getUpdateInfoEvent(@Nullable Object curItem,Drawable drawable) {
        LikeAnimEvent event = new LikeAnimEvent(curItem);
        event.setAnimImage(drawable);
        return event;
    }

    public Object getSrcInfo() {
        return srcInfo;
    }

    public void setSrcInfo(Object srcInfo) {
        this.srcInfo = srcInfo;
    }

    public static LikeAnimEvent getCancelAnimEvent(){
        return new LikeAnimEvent(ANIM_CANCEL);
    }

    public Drawable getAnimImage() {
        return animImage;
    }

    public void setAnimImage(Drawable animImage) {
        this.animImage = animImage;
    }
}
