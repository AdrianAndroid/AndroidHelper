package cn.kuwo.pp.ui.login

//
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import cn.kuwo.common.base.BaseFragment
import cn.kuwo.common.util.RxCountDown
import cn.kuwo.common.util.Spanny
import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.networker.exception.ApiException
import cn.kuwo.pp.R
import cn.kuwo.pp.http.CustomObserver
import cn.kuwo.pp.http.RetrofitClient
import cn.kuwo.pp.http.bean.user.BindMobileParam
import cn.kuwo.pp.http.bean.user.LoginParam
import cn.kuwo.pp.http.bean.user.LoginResult
import cn.kuwo.pp.manager.UserInfoManager
import com.elbbbird.android.analytics.AnalyticsUtils
import com.elbbbird.android.analytics.UmengEventId
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_input_verify_code.*

class VerifyCodeFragment : BaseFragment() {
    companion object {
        fun newInstance(mobile: String, isBind: Boolean, inviteCode: String?): VerifyCodeFragment {
            val args = Bundle()
            args.putString("mobile", mobile)
            args.putBoolean("isBind", isBind)
            args.putString("inviteCode", inviteCode)
            val fragment = VerifyCodeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input_verify_code, container, false)
    }

    private val mobile: String
        get() {
            return arguments?.getString("mobile") ?: ""
        }
    private val inviteCode: String
        get() = arguments?.getString("inviteCode") ?: ""

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableToolbar(R.id.toolbar, "", false)
        tvMobileTip.text = "已向尾号 ${mobile?.subSequence(mobile.length - 4, mobile.length)} 的手机发送验证码"
        etVerifyCode.typeface = null
        etVerifyCode.hint = Spanny("请输入您的验证码", RelativeSizeSpan(0.9F))
        addListeners()
        startCountDown()
    }

    override fun onStop() {
        super.onStop()

        if (!UserInfoManager.isLogin) {
            AnalyticsUtils.onEvent(activity, UmengEventId.NO_LOGIN_SMS, "")
        }
    }

    private fun addListeners() {
        etVerifyCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    etVerifyCode.typeface = null
                } else if (etVerifyCode.typeface == null) {
                    etVerifyCode.typeface = Typeface.createFromAsset(
                        resources.getAssets(),
                        "fonts/Akrobat-kuwo-Bold.ttf"
                    )
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        tvClose.setOnClickListener {
//            if (preFragment == null) {
//                _mActivity.finish()
//            } else {
//                pop()
//            }
            back() // 这个是回退
        }
        etVerifyCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 6) {
                    if (arguments?.getBoolean("isBind") == true) {
                        bingMobile(mobile, etVerifyCode.text.toString())
                    } else {
                        login(mobile, etVerifyCode.text.toString(), inviteCode)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        btnSendSms.setOnClickListener {
            sendSms(mobile)
        }
    }

    private fun startCountDown() {
        RxCountDown.countDown(60).compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    btnSendSms.visibility = View.GONE
                }

                override fun onNext(t: Int) {
                    tvCountDown.text = "${t}秒"
                }

                override fun onError(e: Throwable) {
                    btnSendSms.visibility = View.VISIBLE
                    btnSendSms.text = "重新发送"
                }

                override fun onComplete() {
                    btnSendSms.visibility = View.VISIBLE
                    btnSendSms.text = "重新发送"
                }

            })
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        showSoftInput(etVerifyCode)
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        hideSoftInput()
    }

    private fun login(mobile: String?, verifyCode: String, inviteCode: String) {
        val loginParam = LoginParam(mobile, verifyCode)
        loginParam.inviteCode = inviteCode // 邀请码

        val observable = RetrofitClient.getApiService().login(loginParam)
            .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        RetrofitClient.getInstance().execute(observable, object : CustomObserver<LoginResult>() {
            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                showLoadingDialog("正在登录")
                hideSoftInput()
            }

            override fun onNext(result: LoginResult) {
                if (result.hasName == 0 || result.userInfo == null) {
                    UserInfoManager.onFirstLogin(result)
                    startChooseGender()
                    return
                }
                UserInfoManager.onLoginSuccess(result)
                UtilsCode.showShort("登录成功")
//                if (result.userInfo?.sex == 1) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    MultipleStatusView.isNightMode = false
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    MultipleStatusView.isNightMode = true
//                }
//                startActivity(Intent(_mActivity, MainActivity::class.java))
//                _mActivity.finish()
//                pop()
                closeLogin()
            }

            override fun _onError(e: ApiException) {
                UtilsCode.showShort("登录失败：${e.message}")
            }

            override fun onCompleteOrError() {
                super.onCompleteOrError()
                dismissLoadingDialog()
            }
        })
    }

    private fun sendSms(mobile: String) {
        if (mobile.isNullOrBlank()) {
            UtilsCode.showShort("请输入正确的手机号")
            return
        }
        val observable = RetrofitClient.getApiService().sendSms(mobile, 1)
            .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        RetrofitClient.getInstance().execute(observable, object : CustomObserver<Any>() {
            override fun onSubscribe(d: Disposable) {
                showLoadingDialog("正在发送验证码")
                super.onSubscribe(d)
            }

            override fun onNext(result: Any) {
                UtilsCode.showShort("验证码发送成功，请注意查看")
                startCountDown()
            }

            override fun _onError(e: ApiException) {
                UtilsCode.showShort("发送验证码失败:${e.message}")
            }

            override fun onCompleteOrError() {
                super.onCompleteOrError()
                dismissLoadingDialog()
            }
        })
    }

    private fun bingMobile(mobile: String, verifyCode: String) {
        val observable =
            RetrofitClient.getApiService().bind(
                UserInfoManager.uid,
                UserInfoManager.token,
                BindMobileParam(
                    UserInfoManager.loginResult!!.accessToken,
                    UserInfoManager.loginResult!!.openId,
                    1,
                    mobile,
                    verifyCode,
                    1
                )
            )
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        RetrofitClient.getInstance().execute(observable, object : CustomObserver<Any>() {
            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                showLoadingDialog("正在绑定手机号")
                hideSoftInput()
            }

            override fun onNext(result: Any) {
                UserInfoManager.loginResult?.isBind = 1
                UserInfoManager.loginResult?.userInfo?.phoneNumber = mobile
                startChooseGender()
                UtilsCode.showShort("绑定成功")
            }

            override fun _onError(e: ApiException) {
                UtilsCode.showShort("绑定失败：${e.message}")
            }

            override fun onCompleteOrError() {
                super.onCompleteOrError()
                dismissLoadingDialog()
            }
        })
    }

    override fun onBackPressedSupport(): Boolean {
        ViewModelProvider(parentViewModelStoreOwner).get(LoginOpModel::class.java)
            .back.postValue(true)
        return true
    }

    private fun startChooseGender() {
        ViewModelProvider(parentViewModelStoreOwner).get(LoginOpModel::class.java)
            .gender.postValue(true)
    }

    private fun closeLogin() {
        ViewModelProvider(parentViewModelStoreOwner).get(LoginOpModel::class.java)
            .closeLoginManager.postValue(true)
    }

    private fun back() {
        ViewModelProvider(parentViewModelStoreOwner).get(LoginOpModel::class.java)
            .back.postValue(true)
    }


    override fun getPrintClass(): Class<*> {
        return javaClass
    }
}
