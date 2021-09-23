package com.elbbbird.android.socialsdk.sso.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.elbbbird.android.socialsdk.WeChat;
import com.elbbbird.android.socialsdk.otto.WechatPayEvent;
import com.elbbbird.android.socialsdk.share.wechat.WeChatShareProxy;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

/**
 * 微信授权，分享回调activity
 * Created by zhanghailong-ms on 2015/7/11.
 */

public class WXCallbackActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        IWXAPI api = WeChat.getIWXAPIInstance();
        if (null != api)
            api.handleIntent(intent, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        IWXAPI api = WeChat.getIWXAPIInstance();
        if (null != api)
            api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH)
            WeChatSSOProxy.authComplete((SendAuth.Resp) resp);
        else if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX)
            WeChatShareProxy.shareComplete((SendMessageToWX.Resp) resp);
        else if (resp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            EventBus.getDefault().post(new WechatPayEvent(resp.errCode, resp.errStr));
        }
        finish();
    }
}
