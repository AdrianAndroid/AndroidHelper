package cn.kuwo.pp.ui.login

import android.content.Intent
import cn.kuwo.common.app.App
import cn.kuwo.common.base.BasePresenter
import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.networker.exception.ApiException
import cn.kuwo.pp.http.CustomObserver
import cn.kuwo.pp.http.RetrofitClient
import cn.kuwo.pp.http.bean.user.LoginParam
import cn.kuwo.pp.http.bean.user.LoginResult
import com.elbbbird.android.socialsdk.SocialSDK
import com.elbbbird.android.socialsdk.otto.SSOBusEvent
import com.elbbbird.android.socialsdk.otto.SSOBusEvent.PLATFORM_QQ
import com.elbbbird.android.socialsdk.otto.SSOBusEvent.PLATFORM_WECHAT
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *
 * @author shihc
 * @date 16/9/12
 */
class LoginPresenter(private val mView: LoginView) : BasePresenter {

    fun onOauthResult(event: SSOBusEvent, inviteCode: String) {
        mView.dismissLoadingDialog()
        when (event.type) {
            SSOBusEvent.TYPE_GET_TOKEN -> loginByThird(event, inviteCode)
            SSOBusEvent.TYPE_FAILURE -> {
            }
            SSOBusEvent.TYPE_CANCEL -> {
            }
        }
    }

    fun sendVerifyCode(mobile: String, isBind: Boolean, inviteCode: String? = null) {
        if (mobile.isNullOrBlank()) {
            UtilsCode.showShort("请输入正确的手机号")
            return
        }
        val observable =
            RetrofitClient.getApiService().sendSms(mobile, if (isBind) 2 else 1)
                .compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        RetrofitClient.getInstance().execute(observable, object : CustomObserver<Any>() {
            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                //mView.showLoadingDialog("正在发送验证码")
            }

            override fun onCompleteOrError() {
                super.onCompleteOrError()
                //mView.dismissLoadingDialog()
            }

            override fun onNext(result: Any) {
                mView.onSendVerifyCode(mobile, isBind, inviteCode)
            }

            override fun _onError(e: ApiException) {
                UtilsCode.showShort("发送验证码失败:${e.message}")
            }
        })
    }

    private fun loginByThird(ssoBusEvent: SSOBusEvent?, inviteCode: String) {
        if (ssoBusEvent == null) {
            return
        }
        val loginParam = when (ssoBusEvent.platform) {
            PLATFORM_QQ -> LoginParam(2, ssoBusEvent.token.token, ssoBusEvent.token.openId)
            PLATFORM_WECHAT -> LoginParam(3, ssoBusEvent.token.token, ssoBusEvent.token.openId)
            else -> LoginParam()
        }
        loginParam.inviteCode = inviteCode
//        var loginParam = LoginParam()
//        when (ssoBusEvent.platform) {
//            PLATFORM_QQ -> {
//                loginParam = LoginParam(2, ssoBusEvent.token.token, ssoBusEvent.token.openId)
//            }
//            PLATFORM_WECHAT -> {
//                loginParam = LoginParam(3, ssoBusEvent.token.token, ssoBusEvent.token.openId)
//            }
//        }

        val observable = RetrofitClient.getApiService().login(loginParam)
            .compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        RetrofitClient.getInstance().execute(observable, object : CustomObserver<LoginResult>() {
            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                mView.showLoadingDialog("正在登录")
            }

            override fun onCompleteOrError() {
                super.onCompleteOrError()
                mView.dismissLoadingDialog()
            }

            override fun onNext(result: LoginResult) {
                result.accessToken = ssoBusEvent.token.token
                result.openId = ssoBusEvent.token.openId
                mView.onLoginSuccess(result)
            }

            override fun _onError(e: ApiException) {
                UtilsCode.showShort("登录失败：${e.message}")
            }
        })
    }

    fun qqAuthorize() {
        mView.showLoadingDialog("正在打开QQ")
        SocialSDK.getInstance().oauthQQ(UtilsCode.getTopActivity())
    }

    fun weiboAuthorize() {
        mView.showLoadingDialog("正在打开微博")
        SocialSDK.getInstance().oauthWeibo()
    }

    fun weixinAuthorize() {
        if (!SocialSDK.getInstance().isWXAppInstalled(App.getInstance())) {
            UtilsCode.showShort("您未安装微信应用")
            return
        }
        mView.showLoadingDialog("正在打开微信")
        SocialSDK.getInstance().oauthWeChat(App.getInstance())
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_LOGIN || requestCode == com.tencent.connect.common.Constants.REQUEST_APPBAR) {
            SocialSDK.getInstance().oauthQQCallback(requestCode, resultCode, data)
        } else {
            SocialSDK.getInstance().oauthWeiboCallback(requestCode, resultCode, data)
        }
    }

    fun onCreate() {

    }

    override fun onDestroy() {
        Observable.create<Any> { subscriber ->
            SocialSDK.getInstance().revoke(App.getInstance())
        }
            .subscribeOn(Schedulers.io())//异步任务在IO线程执行
            .subscribe()
    }
}
