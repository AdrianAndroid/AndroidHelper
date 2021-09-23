package cn.kuwo.pp.util.danmuku.model.channel;

import android.content.Context;
import android.graphics.Canvas;

import java.util.List;

import cn.kuwo.pp.util.danmuku.control.dispatcher.IDanMuDispatcher;
import cn.kuwo.pp.util.danmuku.control.speed.SpeedController;
import cn.kuwo.pp.util.danmuku.model.DanMuModel;
import cn.kuwo.pp.util.danmuku.model.collection.DanMuConsumedPool;
import cn.kuwo.pp.util.danmuku.model.collection.DanMuConsumer;
import cn.kuwo.pp.util.danmuku.model.collection.DanMuProducedPool;
import cn.kuwo.pp.util.danmuku.model.collection.DanMuProducer;
import cn.kuwo.pp.util.danmuku.model.painter.DanMuPainter;
import cn.kuwo.pp.util.danmuku.view.IDanMuParent;

/**
 * Created by android_ls on 2016/12/7.
 */
public class DanMuPoolManager implements IDanMuPoolManager {

    private DanMuConsumer danMuConsumer;
    private DanMuProducer danMuProducer;

    private DanMuConsumedPool danMuConsumedPool;
    private DanMuProducedPool danMuProducedPool;

    private boolean isStart;

    public DanMuPoolManager(Context context, IDanMuParent danMuParent) {
        danMuConsumedPool = new DanMuConsumedPool(context);
        danMuProducedPool = new DanMuProducedPool(context);
        danMuConsumer = new DanMuConsumer(danMuConsumedPool, danMuParent);
        danMuProducer = new DanMuProducer(danMuProducedPool, danMuConsumedPool);
    }

    public void forceSleep() {
        danMuConsumer.forceSleep();
    }

    public void releaseForce() {
        danMuConsumer.releaseForce();
    }

    @Override
    public void hide(boolean hide) {
        danMuConsumedPool.hide(hide);
    }

    @Override
    public void hideAll(boolean hideAll) {
        danMuConsumedPool.hideAll(hideAll);
    }

    @Override
    public void startEngine() {
        if (!isStart) {
            isStart = true;
            danMuConsumer.start();
            danMuProducer.start();
        }
    }

    @Override
    public void setDispatcher(IDanMuDispatcher iDanMuDispatcher) {
        danMuProducedPool.setDanMuDispatcher(iDanMuDispatcher);
    }

    @Override
    public void setSpeedController(SpeedController speedController) {
        danMuConsumedPool.setSpeedController(speedController);
    }

    @Override
    public void divide(int width, int height) {
        danMuProducedPool.divide(width, height);
        danMuConsumedPool.divide(width,height);
    }

    @Override
    public void addDanMuView(int index, DanMuModel danMuView) {
        danMuProducer.produce(index, danMuView);
    }

    @Override
    public void jumpQueue(List<DanMuModel> danMuViews) {
        danMuProducer.jumpQueue(danMuViews);
    }

    public void release() {
        isStart = false;
        danMuConsumer.release();
        danMuProducer.release();
        danMuConsumedPool = null;
    }

    /**
     * drawing entrance
     *
     * @param canvas
     */
    public void drawDanMus(Canvas canvas) {
        danMuConsumer.consume(canvas);
    }

    public void addPainter(DanMuPainter danMuPainter, int key) {
        danMuConsumedPool.addPainter(danMuPainter, key);
    }

}
