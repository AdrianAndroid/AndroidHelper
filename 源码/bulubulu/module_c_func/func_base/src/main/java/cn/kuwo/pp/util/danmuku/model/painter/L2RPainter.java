package cn.kuwo.pp.util.danmuku.model.painter;

import cn.kuwo.pp.util.danmuku.model.DanMuModel;
import cn.kuwo.pp.util.danmuku.model.channel.DanMuChannel;

/**
 * Created by android_ls on 2016/12/7.
 */
public class L2RPainter extends DanMuPainter {

    @Override
    protected void layout(DanMuModel danMuView, DanMuChannel danMuChannel) {
        if (danMuView.getX() >= (danMuChannel.width + danMuView.getWidth())) {
            danMuView.setAlive(false);
            return;
        }
        danMuView.setStartPositionX(danMuView.getX() + danMuView.getSpeed() * (1 + 0.5f));
    }

}
