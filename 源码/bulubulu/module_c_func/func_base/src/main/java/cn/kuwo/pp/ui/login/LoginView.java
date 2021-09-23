package cn.kuwo.pp.ui.login;

import org.jetbrains.annotations.NotNull;

import cn.kuwo.common.base.BaseView;
import cn.kuwo.pp.http.bean.user.LoginResult;

/**
 * @author shihc
 * @date 16/8/19
 */
public interface LoginView extends BaseView {
    void onSendVerifyCode(String mobile, boolean bind, String inviteCode);

    void onLoginSuccess(@NotNull LoginResult result);
}


