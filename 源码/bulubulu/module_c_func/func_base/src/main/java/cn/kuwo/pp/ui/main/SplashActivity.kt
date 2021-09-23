package cn.kuwo.pp.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import cn.kuwo.common.base.BaseActivity
import cn.kuwo.common.dialog.DialogUtils
import cn.kuwo.networker.exception.ApiException
import cn.kuwo.pp.BuildConfig
import cn.kuwo.pp.R
import cn.kuwo.pp.http.CustomObserver
import cn.kuwo.pp.http.RetrofitClient
import cn.kuwo.pp.http.bean.user.LoginParam
import cn.kuwo.pp.http.bean.user.LoginResult
import cn.kuwo.pp.manager.UserInfoManager
import cn.kuwo.pp.manager.UserInfoManager.onAutoLoginSuccess
import cn.kuwo.pp.manager.UserInfoManager.onLogoutSuccess
import cn.kuwo.pp.manager.UserInfoManager.token
import cn.kuwo.pp.manager.UserInfoManager.uid
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import java.util.concurrent.TimeUnit


/**
 * @author shihc
 */
@RuntimePermissions
class SplashActivity : BaseActivity() {

    private var firstResume = true
    private val mCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 用于第一次安装APP，进入到除这个启动activity的其他activity，点击home键，再点击桌面启动图标时，
        // 系统会重启此 activity，而不是直接打开之前已经打开过的activity，因此需要关闭此activity
        if (!isTaskRoot) {
            finish()
            return
        }
        setContentView(R.layout.activity_splash)
        setSwipeBackEnable(false)
        window.setBackgroundDrawable(null)

        autoLogin()
    }

    private fun autoLogin() {
        if (UserInfoManager.isNotLogin) {
            delayStart()
            return
        }
        val param = LoginParam()
        param.authType = 5
        param.token = token
        param.uid = uid
        val observable: Observable<*> = RetrofitClient
            .getApiService()
            .login(param)
            .compose(bindUntilEvent<Any>(ActivityEvent.DESTROY))
        RetrofitClient.getInstance().execute(observable, object : CustomObserver<LoginResult?>() {
            override fun onNext(loginResult: LoginResult) {
                if (loginResult.userInfo != null) {
                    onAutoLoginSuccess(loginResult)
                }
                delayStart()
            }

            override fun _onError(e: ApiException) {
                //token失效
                if (e.code == 11005) {
                    onLogoutSuccess()
                }
                delayStart()
            }
        })
    }

    private fun delayStart() {
        mCompositeDisposable.add(
            Observable
                .just(0)
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { integer: Int? ->
                    startMainActivityWithPermissionCheck()
                })
    }


    override fun onResume() {
        super.onResume()
        if (!firstResume) {
            firstResume = false;
        }
    }

    @NeedsPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECORD_AUDIO
    )
    fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        if (getIntent() != null && getIntent().extras != null) {
            intent.putExtras(getIntent().extras!!)
        }
        startActivity(intent)
        finish()
    }


    @OnPermissionDenied(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECORD_AUDIO
    )
    fun showDialog() {
//        if (mDialog?.isShowing == true) {
//            return
//        }
//        val content = "我们需要获取存储空间，为你缓存音乐文件；否则，您将无法正常使用该应用；设置路径：设置->应用->bulubulu->权限"
//        mDialog = AlertDialog.getInstance(this, "", content, "去设置", "取消",
//            { _: MaterialDialog?, _: DialogAction? ->
//                val intent =
//                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                val uri =
//                    Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
//                intent.data = uri
//                startActivity(intent)
//            }
//        ) { _: MaterialDialog?, _: DialogAction? -> finish() }
//        mDialog?.setCanceledOnTouchOutside(false)
//        mDialog?.setCancelable(false)
//        mDialog?.show()


        DialogUtils.showStoragePermissionDialog({ agree ->
            if (agree) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                intent.data = uri
                startActivity(intent)
            }
        }, null)
    }


    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }

}
