package cn.kuwo.pp.util.danmuku.model.channel;

import java.util.List;

import cn.kuwo.pp.util.danmuku.control.dispatcher.IDanMuDispatcher;
import cn.kuwo.pp.util.danmuku.control.speed.SpeedController;
import cn.kuwo.pp.util.danmuku.model.DanMuModel;

/**
 * Created by android_ls on 2016/12/7.
 */
interface IDanMuPoolManager {
    void setSpeedController(SpeedController speedController);

    void addDanMuView(int index, DanMuModel iDanMuView);

    void jumpQueue(List<DanMuModel> danMuViews);

    void divide(int width, int height);

    void setDispatcher(IDanMuDispatcher iDanMuDispatcher);

    void hide(boolean hide);

    void hideAll(boolean hideAll);

    void startEngine();
}
