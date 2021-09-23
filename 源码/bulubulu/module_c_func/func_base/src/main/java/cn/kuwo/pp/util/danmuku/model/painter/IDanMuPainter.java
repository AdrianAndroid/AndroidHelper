package cn.kuwo.pp.util.danmuku.model.painter;

import android.graphics.Canvas;

import cn.kuwo.pp.util.danmuku.model.DanMuModel;
import cn.kuwo.pp.util.danmuku.model.channel.DanMuChannel;

/**
 * Created by android_ls on 2016/12/7.
 */
abstract class IDanMuPainter {

    public abstract void execute(Canvas canvas, DanMuModel danMuView, DanMuChannel danMuChannel);

    public abstract void requestLayout();

    public abstract void setAlpha(int alpha);

    public abstract void hideNormal(boolean hide);

    public abstract void hideAll(boolean hideAll);

}
