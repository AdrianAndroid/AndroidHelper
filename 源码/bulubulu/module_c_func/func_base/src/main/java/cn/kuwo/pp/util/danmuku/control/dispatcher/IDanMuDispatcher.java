package cn.kuwo.pp.util.danmuku.control.dispatcher;

import cn.kuwo.pp.util.danmuku.model.DanMuModel;
import cn.kuwo.pp.util.danmuku.model.channel.DanMuChannel;

/**
 * Created by android_ls on 2016/12/7.
 */
public interface IDanMuDispatcher {

    void dispatch(DanMuModel iDanMuView, DanMuChannel[] danMuChannels);

}
