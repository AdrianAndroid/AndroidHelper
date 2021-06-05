package cn.kuwo.pp.util.danmuku.model.painter;

import cn.kuwo.pp.util.danmuku.model.DanMuModel;
import cn.kuwo.pp.util.danmuku.model.channel.DanMuChannel;

/**
 * Created by android_ls on 2016/12/7.
 */
public class R2LPainter extends DanMuPainter {

    @Override
    protected void layout(DanMuModel danMuView, DanMuChannel danMuChannel) {
        if (danMuView.getX() - danMuView.getSpeed() <= -danMuView.getWidth()) {
            danMuView.setAlive(false);
            return;
        }
        danMuView.setStartPositionX(danMuView.getX() - danMuView.getSpeed());
    }
}

