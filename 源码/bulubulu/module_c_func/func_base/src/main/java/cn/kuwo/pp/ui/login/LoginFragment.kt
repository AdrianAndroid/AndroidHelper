package cn.kuwo.pp.ui.login

import android.content.Intent
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
import cn.kuwo.common.keyboard.KeyboardPatch
import cn.kuwo.common.util.Spanny
import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.daynight.TypefaceUtil
import cn.kuwo.networker.Urls
import cn.kuwo.pp.R
import cn.kuwo.pp.http.ConstantUrls.URL_USER_AGREEMENT
import cn.kuwo.pp.http.bean.user.LoginResult
import cn.kuwo.pp.manager.UserInfoManager
import cn.kuwo.pp.ui.web.WebFragment
import com.elbbbird.android.analytics.AnalyticsUtils
import com.elbbbird.android.analytics.UmengEventId
import com.elbbbird.android.socialsdk.SocialSDK
import com.elbbbird.android.socialsdk.otto.SSOBusEvent
import kotlinx.android.synthetic.main.fragment_login.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * @author flannery
 * 手机号登录和绑定手机号界面
 */
class LoginFragment : BaseFragment(), LoginView {
    private lateinit var mPresenter: LoginPresenter
    private var mIsBind: Boolean = false

    private var keyboard: KeyboardPatch? = null;

    companion object {
        // 跳转到新的登陆逻辑
        fun newInstance(isBind: Boolean): BaseFragment {
            val args = Bundle()
            args.putBoolean("isBind", isBind)
            val fragment = LoginManagerFragment()
//            val fragment = LoginFragment2();
            fragment.arguments = args
            return fragment
        }

        // 创建原想的登陆
        fun newInstanceLogin(isBind: Boolean): BaseFragment {
            val args = Bundle()
            args.putBoolean("isBind", isBind)
            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        addListeners()
        keyboard = KeyboardPatch(_mActivity, scrollView, view)
        keyboard?.enable()

        initChangeUrl()
    }

    override fun onStop() {
        super.onStop()

        if (!UserInfoManager.isLogin) {
            AnalyticsUtils.onEvent(activity, UmengEventId.NO_LOGIN_ENTRY, "")
        }
    }

    private fun setupView() {
        mIsBind = arguments?.getBoolean("isBind") ?: false
        if (mIsBind) {
            login_bottom_views.visibility = View.GONE
            tvTitle.text = "绑定手机号"
            tvSubTitle.text = "绑定手机号，帮助屏蔽通讯录熟人"
        } else {
            tvTitle.text = "登录"
            tvSubTitle.text = "开启你美妙的声音之旅"
        }
        cbProtocol.isChecked = true;
        ivConfirm.isEnabled = false
        ivConfirm.setImageResource(R.drawable.next_icon_disable);
        tv_phone_pre.typeface =
            Typeface.createFromAsset(resources.getAssets(), "fonts/Akrobat-kuwo-Bold.ttf")
        etPhoneNumber.typeface = null
        etPhoneNumber.hint = Spanny("请输入您的手机号", RelativeSizeSpan(0.9F))
        mPresenter = LoginPresenter(this)
    }


    private fun initChangeUrl() {
        tvTitle.setOnLongClickListener {
            // 切换环境的弹窗
            Urls.showBaseUrlDialog(_mActivity) { _, _ ->
                // 显示
                Urls.showDebuLog(_mActivity)
            }
            true
        }
    }

    private fun addListeners() {
        etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNullOrEmpty()) {
                    etPhoneNumber.typeface = null
                } else if (etPhoneNumber.typeface == null) {
                    TypefaceUtil.setTypeface(etPhoneNumber, TypefaceUtil.Akrobat_kuwo_Bold)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        ivConfirm.setOnClickListener {
            if (cbProtocol.isChecked) {
                //TODO 应该是在这里添加验证码
                mPresenter.sendVerifyCode(
                    etPhoneNumber.text.toString(),
                    mIsBind,
                    inviteCode.text.toString()
                )
            } else {
                UtilsCode.showShort("请同意bulubulu用户协议");
            }
        }
        etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                ivConfirm.isEnabled = s?.length == 11
                if (ivConfirm.isEnabled) {
                    ivConfirm.setImageResource(R.drawable.next_icon);
                } else {
                    ivConfirm.setImageResource(R.drawable.next_icon_disable)
                }
            }
        })
        ivClose.setOnClickListener {
            close()
        }
        ivWechatLogin.setOnClickListener {
            if (cbProtocol.isChecked) {
                mPresenter.weixinAuthorize()
            } else {
                UtilsCode.showShort("请同意bulubulu用户协议");
            }
        }
        ivQQLogin.setOnClickListener {
            if (cbProtocol.isChecked) {
                mPresenter.qqAuthorize()
            } else {
                UtilsCode.showShort("请同意bulubulu用户协议");
            }
        }
        tvProtocol.setOnClickListener {
            startFromMain(WebFragment.newInstance(URL_USER_AGREEMENT, "用户协议", ""))
        }
        tvProtocolTip.setOnClickListener {
            cbProtocol.isChecked = !cbProtocol.isChecked;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onOauthResult(event: SSOBusEvent) {
        mPresenter.onOauthResult(event, inviteCode.text.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_LOGIN || requestCode == com.tencent.connect.common.Constants.REQUEST_APPBAR) {
            SocialSDK.getInstance().oauthQQCallback(requestCode, resultCode, data)
        } else {
            SocialSDK.getInstance().oauthWeiboCallback(requestCode, resultCode, data)
        }
    }

    // 跳转到验证码阶段
    override fun onSendVerifyCode(
        mobile: String,
        isBind: Boolean,
        inviteCode: String
    ) {
        val viewModelStoreOwner = getViewModelStoreOwner(LoginManagerFragment::class.java)
        if (viewModelStoreOwner != null) {
            ViewModelProvider(viewModelStoreOwner).get(LoginOpModel::class.java)
                .veriftyCode.postValue(Verfity(mobile, isBind, inviteCode))

            UtilsCode.showShort("验证码发送成功，请注意查看")
        }
    }

    override fun onLoginSuccess(result: LoginResult) {
        if (result.userInfo == null) {
            UserInfoManager.onFirstLogin(result)
            startFromMain(ChooseGenderFragment.newInstance())
            return
        }
        if (result.isBind == 0) { // 未绑定手机号
            UserInfoManager.onFirstLogin(result)
            // 跳转之前 ，清理掉指定的Fragment
            //startFromMain(newInstance(true)) // 跳转到新的界面
            startWithPopFromMain(newInstance(true)) // 先清空上一个界面，在
            return
        }
        UserInfoManager.onLoginSuccess(result)
        UtilsCode.showShort("登录成功")
        close()
    }

    override fun onBackPressedSupport(): Boolean {
        ViewModelProvider(parentViewModelStoreOwner).get(LoginOpModel::class.java)
            .back.postValue(true)
        return true
    }

    private fun close() {
        ViewModelProvider(parentViewModelStoreOwner).get(LoginOpModel::class.java)
            .closeLoginManager.postValue(true)
    }


    override fun getPrintClass(): Class<*> {
        return javaClass
    }

    override fun onDestroy() {
        super.onDestroy()
        keyboard?.disable()
    }
}
