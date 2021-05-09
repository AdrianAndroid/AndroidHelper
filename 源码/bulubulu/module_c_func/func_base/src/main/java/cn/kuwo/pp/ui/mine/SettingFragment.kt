package cn.kuwo.pp.ui.mine

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.kuwo.common.app.App
import cn.kuwo.common.base.BaseFragment
import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.networker.Urls
import cn.kuwo.pp.BuildConfig
import cn.kuwo.pp.R
import cn.kuwo.pp.manager.UserInfoManager
import cn.kuwo.pp.ui.main.MainFragment
import cn.kuwo.pp.ui.web.WebFragment
import com.elbbbird.android.analytics.AnalyticsUtils
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 * 设置页面
 */
class SettingFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return attachToSwipeBack(inflater.inflate(R.layout.fragment_setting, container, false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableToolbar(R.id.toolbar, "设置", true)
        mToolbar.toolbar.setNavigationIcon(R.drawable.icon_back)

        tvUserId.text = "个人ID：" + UserInfoManager.uid

        addListeners()
    }

    override fun onBackBtnClicked(view: View?) {
        if (preFragment == null) {
            _mActivity.finish()
        } else {
            pop()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addListeners() {

        if (App.DEBUG) {
            tvDeveloper.append(if (Urls.isDebugUrl()) "测试环境" else "线上环境")
            tvDeveloper.append(App.getInstance().appGetChannel())
            tvDeveloper.visibility = View.VISIBLE
            tvDeveloper.setOnClickListener {
                startFromMain(DeveloperFragment())
            }
        }

        btnLogout.setOnClickListener {
            UserInfoManager.onLogoutSuccess()
            UtilsCode.showShort("退出登录成功")
            AnalyticsUtils.loginOff() // 统计退出情况
            popTo(MainFragment::class.java, false, {}, 0)
            //mainViewModel.onTabItemClicked.postValue(1/*中间的那个是1*/)
            btnLogout.isEnabled = false //点击之后就再也不能点击了
        }


        tvUserPolicy.setOnClickListener {
            start(
                WebFragment.newInstance(
                    "https://h5app.kuwo.cn/m/3dab9c3a/server.html",
                    "用户协议",
                    ""
                )
            )
        }
        tvPrivacyAgreement.setOnClickListener {
            start(
                WebFragment.newInstance(
                    "https://h5app.kuwo.cn/m/3d724391/secret.html",
                    "隐私政策",
                    ""
                )
            )
        }
        tvFeedBack.setOnClickListener {
            startFromMain(FeedBackFragment.newInstance());
        }

        // 显示版本号
        tvVersionName.text = "版本号：v${UtilsCode.getAppVersionName()}"
        tvVersionName.setOnLongClickListener {
            UtilsCode.showLong("打包时间:${App.getInstance().appPackageTime()}")
            true
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
    }

    override fun getPrintClass(): Class<*> {
        return javaClass
    }
}