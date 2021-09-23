package com.elbbbird.android.socialsdk.otto;

/**
 * Created by shihc on 2017/3/20.
 */

public class WechatPayEvent {
    /**
     0	成功	展示成功页面
     -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
     -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
     */
    private int errCode;
    private String errStr;

    public WechatPayEvent(int errCode, String errStr) {
        this.errCode = errCode;
        this.errStr = errStr;
    }

    public boolean isSuccess(){
        return errCode == 0;
    }

    public boolean isCancel(){
        return errCode == -2;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrStr() {
        return errStr;
    }
}
