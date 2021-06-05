package cn.kuwo.pp.util.danmuku;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.L;
import cn.kuwo.common.app.App;
import cn.kuwo.pp.util.danmuku.control.DanMuController;
import cn.kuwo.pp.util.danmuku.control.speed.SpeedController;
import cn.kuwo.pp.util.danmuku.model.DanMuModel;
import cn.kuwo.pp.util.danmuku.view.IDanMuParent;
import cn.kuwo.pp.util.danmuku.view.OnDanMuParentViewTouchCallBackListener;
import cn.kuwo.pp.util.danmuku.view.OnDanMuTouchCallBackListener;
import cn.kuwo.pp.util.danmuku.view.OnDanMuViewTouchListener;

/**
 * Created by android_ls on 2016/12/7.
 */
public class DanMuView extends View implements IDanMuParent {

    private DanMuController danMuController;
    private volatile ArrayList<OnDanMuViewTouchListener> onDanMuViewTouchListeners;
    //    private OnDanMuParentViewTouchCallBackListener onDanMuParentViewTouchCallBackListener;
    private boolean drawFinished = false;

    private Object lock = new Object();

    public DanMuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public void jumpQueue(List<DanMuModel> danMuViews) {
        if (App.DEBUG) L.m(getClass());
        danMuController.jumpQueue(danMuViews);
    }

    @Override
    public void addAllTouchListener(List<DanMuModel> onDanMuTouchCallBackListeners) {
        if (App.DEBUG) L.m(getClass());
        this.onDanMuViewTouchListeners.addAll(onDanMuTouchCallBackListeners);
    }

    public DanMuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (App.DEBUG) L.m(getClass());
        init(context);
    }

    private void init(Context context) {
        if (App.DEBUG) L.m(getClass());
        onDanMuViewTouchListeners = new ArrayList<>();
        if (danMuController == null) {
            danMuController = new DanMuController(this);
        }
    }

    public void prepare() {
        if (App.DEBUG) L.m(getClass());
        prepare(null);
    }

    public void prepare(SpeedController speedController) {
        if (App.DEBUG) L.m(getClass());
        if (danMuController != null) {
            danMuController.setSpeedController(speedController);
            danMuController.prepare();
        }
    }

    public void release() {
        if (App.DEBUG) L.m(getClass());
//        onDetectHasCanTouchedDanMusListener = null;
//        onDanMuParentViewTouchCallBackListener = null;
        clear();
        if (danMuController != null) {
            danMuController.release();
        }
        danMuController = null;
    }

    private void addDanMuView(final DanMuModel danMuView) {
        if (App.DEBUG) L.m(getClass());
        if (danMuView == null) {
            return;
        }
        if (danMuController != null) {
            if (danMuView.enableTouch()) {
                onDanMuViewTouchListeners.add(danMuView); // 监听事件
            }
            danMuController.addDanMuView(-1, danMuView);
        }
    }

//    public void setOnDanMuParentViewTouchCallBackListener(OnDanMuParentViewTouchCallBackListener onDanMuParentViewTouchCallBackListener) {
//        if(App.DEBUG) L.m(getClass());
//        this.onDanMuParentViewTouchCallBackListener = onDanMuParentViewTouchCallBackListener;
//    }

    @Override
    public boolean hasCanTouchDanMus() {
        if (App.DEBUG) L.m(getClass());
        return onDanMuViewTouchListeners.size() > 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (App.DEBUG) L.m(getClass());
        if (hasCanTouchDanMus()) {
            getParent().requestDisallowInterceptTouchEvent(false); // 父布局不能拦截
        }

        // 感觉锁哟的都要处理一下
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        if (action == MotionEvent.ACTION_DOWN) {
            for (OnDanMuViewTouchListener onDanMuViewTouchListener : onDanMuViewTouchListeners) {
                if (onDanMuViewTouchListener.onTouch(event.getX(), event.getY())) {
                    return true;// 在点击范围内
                }
            }
        } else if (action == MotionEvent.ACTION_UP) {
            for (int i = 0; i < onDanMuViewTouchListeners.size(); i++) {
                OnDanMuViewTouchListener onDanMuViewTouchListener = onDanMuViewTouchListeners.get(i);
                boolean onTouched = onDanMuViewTouchListener.onTouch(event.getX(), event.getY());
                OnDanMuTouchCallBackListener onTouchCallBackListener = ((DanMuModel) onDanMuViewTouchListener).getOnTouchCallBackListener();
                if (onTouchCallBackListener != null && onTouched) {
                    if (App.DEBUG) L.m(getClass(), "onTouched", onTouched);
                    onTouchCallBackListener.callBack((DanMuModel) onDanMuViewTouchListener);
                    return true;
                }
            }
            return super.onTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }

    // 添加单个弹幕
    @Override
    public void add(DanMuModel danMuView) {
        if (App.DEBUG) L.m(getClass());
        danMuView.enableMoving(true); // 可以移动
        addDanMuView(danMuView);
    }

    public void lockDraw() {
        if (!danMuController.isChannelCreated()) {
            return;
        }
        synchronized (lock) {
            if (Build.VERSION.SDK_INT >= 16) {
                this.postInvalidateOnAnimation();
            } else {
                this.postInvalidate();
            }
            if ((!drawFinished)) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                }
            }
            drawFinished = false;
        }
    }

    @Override
    public void forceSleep() {
        if (App.DEBUG) L.m(getClass());
        danMuController.forceSleep();
    }

    @Override
    public void forceWake() {
        if (App.DEBUG) L.m(getClass());
        danMuController.forceWake();
    }

    private void unLockDraw() {
        synchronized (lock) {
            drawFinished = true;
            lock.notifyAll();
        }
    }

    @Override
    public void clear() {
        if (App.DEBUG) L.m(getClass());
        onDanMuViewTouchListeners.clear();
    }

    @Override
    public void remove(DanMuModel danMuView) {
        if (App.DEBUG) L.m(getClass());
        onDanMuViewTouchListeners.remove(danMuView);
    }

    public interface OnDetectHasCanTouchedDanMusListener {
        void hasNoCanTouchedDanMus(boolean hasDanMus);
    }

    public void detectHasCanTouchedDanMus() {
        for (int i = 0; i < onDanMuViewTouchListeners.size(); i++) {
            if (!((DanMuModel) onDanMuViewTouchListeners.get(i)).isAlive()) {
                onDanMuViewTouchListeners.remove(i);
                i--;
            }
        }
//        if (onDanMuViewTouchListeners.size() == 0) {
//            if (onDetectHasCanTouchedDanMusListener != null) {
//                onDetectHasCanTouchedDanMusListener.hasNoCanTouchedDanMus(false);
//            }
//        } else {
//            if (onDetectHasCanTouchedDanMusListener != null) {
//                onDetectHasCanTouchedDanMusListener.hasNoCanTouchedDanMus(true);
//            }
//        }
    }

    @Override
    public void hideNormalDanMuView(boolean hide) {
        if (App.DEBUG) L.m(getClass());
        danMuController.hide(hide);
    }

    @Override
    public void hideAllDanMuView(boolean hideAll) {
        if (App.DEBUG) L.m(getClass());
        danMuController.hideAll(hideAll);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (App.DEBUG) L.m(getClass());
        detectHasCanTouchedDanMus();
        if (danMuController != null) {
            danMuController.initChannels(canvas);
            danMuController.draw(canvas);
        }
        unLockDraw();
    }

    @Override
    public void add(int index, DanMuModel danMuView) {
        if (App.DEBUG) L.m(getClass());
        danMuController.addDanMuView(index, danMuView);
    }

//    public OnDetectHasCanTouchedDanMusListener onDetectHasCanTouchedDanMusListener;
//
//    public void setOnDanMuExistListener(OnDetectHasCanTouchedDanMusListener onDetectHasCanTouchedDanMusListener) {
//        if(App.DEBUG) L.m(getClass());
//        this.onDetectHasCanTouchedDanMusListener = onDetectHasCanTouchedDanMusListener;
//    }
}
