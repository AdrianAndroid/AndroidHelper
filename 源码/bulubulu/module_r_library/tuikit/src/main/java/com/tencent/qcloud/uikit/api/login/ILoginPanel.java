package com.tencent.qcloud.uikit.api.login;

import com.tencent.qcloud.uikit.business.login.view.ILoginEvent;


public interface ILoginPanel {
    /**
     * 设置登录界面事件监听{@link ILoginEvent}
     *
     * @param event
     */
    void setLoginEvent(ILoginEvent event);
}
