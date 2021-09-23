package cn.kuwo.pp.ui.login

import android.Manifest
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import cn.kuwo.common.base.BaseFragment
import cn.kuwo.common.event.CloseMainActivityEvent
import cn.kuwo.common.pictureselector.BuluPictureSelector
import cn.kuwo.common.util.ImageLoader
import cn.kuwo.common.util.SP
import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.networker.exception.ApiException
import cn.kuwo.pp.R
import cn.kuwo.pp.http.CustomObserver
import cn.kuwo.pp.http.RetrofitClient
import cn.kuwo.pp.http.bean.user.LoginResult
import cn.kuwo.pp.http.bean.user.UpdateProfileParam
import cn.kuwo.pp.http.bean.user.UploadHeadResult
import cn.kuwo.pp.manager.UserInfoManager
import cn.kuwo.pp.ui.login.dialog.AddressDialog
import cn.kuwo.pp.ui.login.dialog.BirthdayDialog
import cn.kuwo.pp.ui.main.MainActivity
import com.elbbbird.android.analytics.AnalyticsUtils
import com.elbbbird.android.analytics.UmengEventId
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.gyf.barlibrary.ImmersionBar
import com.luck.picture.lib.entity.LocalMedia
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import permissions.dispatcher.*
import java.io.File
import java.nio.charset.Charset
import java.text.SimpleDateFormat

@RuntimePermissions
class EditProfileFragment : BaseFragment() {
    private var mFromVal: Int = 0
    private var headImageUri: String? = null

    companion object {
        fun newInstance(from: Int): EditProfileFragment {
            val args = Bundle()
            args.putInt("KEY_FROM", from)
            val fragment = EditProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        return attachToSwipeBack(inflate)
    }

    private fun fromLogin() = mFromVal == 0// 从登陆过来


    private fun fromSettings() = mFromVal == 1// 修改资料


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFromVal = arguments?.getInt("KEY_FROM", 0)!!

        enableToolbar(R.id.toolbar, "", mFromVal == 1)  //如果来自于“我的”界面，显示返回按钮
        mToolbar.toolbar.setNavigationIcon(R.drawable.icon_back)

        if (mFromVal == 1) {
            btnStartBulu.text = "保存信息"
        } else {
            btnStartBulu.text = "保存信息，开启bulubulu"
        }
        ImmersionBar.with(this).keyboardEnable(false).statusBarDarkFont(false).init()
        if (UserInfoManager.loginResult?.hasName == 0) {
            ImageLoader.showCircleImage(
                ivUserImage,
                UserInfoManager.voiceInfo?.headImg,
                R.drawable.default_header
            )
            evNickname.setText(UserInfoManager.loginResult?.thirdName)
        } else {
            evNickname.setText(UserInfoManager.userInfo!!.name)
            tvBirthday.text = UserInfoManager.userInfo!!.birthday
            if (!UserInfoManager.userInfo!!.prov.isNullOrEmpty()) {
                tvAddress.text =
                    "${UserInfoManager.userInfo!!.prov} ${UserInfoManager.userInfo!!.city}"
            }
            ImageLoader.showCircleImage(
                ivUserImage,
                UserInfoManager.userInfo!!.headImg,
                UserInfoManager.userInfo!!.getNewDefaultHeadImage()
            )
        }
        val jsonObject = UserInfoManager.userInfo?.extendData
        if (jsonObject?.has("prop_block_contacts") == true) {
            cbBlockContacts.isChecked = jsonObject?.get("prop_block_contacts")?.asInt == 1
        }
        addListeners()
    }

    override fun onStop() {
        super.onStop()

        if (!UserInfoManager.isLogin) {
            AnalyticsUtils.onEvent(activity, UmengEventId.NO_LOGIN_EDIT, "")
        }
    }

    private fun addListeners() {
        cbBlockContacts.setOnClickListener {
            cbBlockContacts.toggle()
        }
        btnStartBulu.setOnClickListener {
            if (_mActivity !is MainActivity) { // 目前没啥用了，但不影响
                EventBus.getDefault().post(CloseMainActivityEvent())
            }
            if (cbBlockContacts.isChecked) {
                uploadContactWithPermissionCheck()
            } else {
                saveProfile()
            }
        }
        tvBirthday.setOnClickListener {
            val birthdayDialog = BirthdayDialog.newInstance(UserInfoManager.userInfo?.birthday);
            birthdayDialog.onSelectListener = {
                tvBirthday.text = UtilsCode.date2String(it, SimpleDateFormat("yyyy-MM-dd"))
            }
            birthdayDialog.show(childFragmentManager)
        }
        tvAddress.setOnClickListener {
            val addressDialog = AddressDialog()
            addressDialog.onSelectListener = { province, city ->
                tvAddress.text = "$province $city"
            }
            addressDialog.show(childFragmentManager)
        }
        ivUserImage.setOnClickListener {
            selectPicture()
        }
    }

    public fun selectPicture() {
        BuluPictureSelector.bulupicture_usericon_circle(
            this,
            null,
            0,
            object : BuluPictureSelector.BOnResultCallbackListener<LocalMedia> {
                override fun onResult(requestCode: Int, result: MutableList<LocalMedia>?) {
                    result?.first()?.let {
                        headImageUri = BuluPictureSelector.getCutPath(it)
                        ImageLoader.showCircleImage(
                            ivUserImage,
                            headImageUri,
                            R.drawable.default_header
                        )
                    }
                }

                override fun onCancel() {

                }
            })
//        selectPic(_mActivity, object : IUIKitCallBack {
//            override fun onSuccess(data: Any?) {
//                var uri = (data as ArrayList<Uri>)[0]
//                headImageUri = uri
//                ImageLoader.showCircleImage(ivUserImage, uri, R.drawable.default_header)
//            }
//
//            override fun onError(module: String?, errCode: Int, errMsg: String?) {
//                UtilsCode.INSTANCE.showShort("选择失败")
//            }
//        })
    }

    @NeedsPermission(Manifest.permission.READ_CONTACTS)
    public fun uploadContact() {
        saveProfile()
        val cursor = _mActivity.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (cursor == null || cursor.count <= 0) {
            return
        }
        val list = JsonArray()
        while (cursor.moveToNext()) {
            var bean = JsonObject()
            bean.addProperty(
                "name",
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            )
            bean.addProperty(
                "phone",
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            )
            list.add(bean)
        }
        val param = JsonObject()
        param.add("json", list)
        cursor.close()
        RetrofitClient.getInstance()
            .execute(
                RetrofitClient.getApiService().uploadContact(UserInfoManager.uid, param),
                object : CustomObserver<Any>() {

                    override fun onNext(result: Any) {
                    }

                    override fun _onError(e: ApiException) {
                    }
                })
    }

    @OnShowRationale(Manifest.permission.READ_CONTACTS)
    fun showRationaleForContacts(request: PermissionRequest) {
        UtilsCode.showShort("没有读取联系人权限，请在设置中打开权限")
        saveProfile()
    }

    @OnPermissionDenied(Manifest.permission.READ_CONTACTS)
    fun onContactsDenied() {
        UtilsCode.showShort("拒绝授权")
        saveProfile()
    }

    @OnNeverAskAgain(Manifest.permission.READ_CONTACTS)
    fun onContactsNeverAskAgain() {
        saveProfile()
    }

    private fun saveProfile() {
        if (headImageUri != null) {
            uploadPic(headImageUri/*FileUtil.getPathFromUri(headImageUri)*/)
        } else if (UserInfoManager.isNotLogin) {
            updateProfile(
                evNickname.text.toString(),
                tvBirthday.text.toString(),
                tvAddress.text.toString(),
                UserInfoManager.voiceInfo?.headImg
            )
        } else {
            updateProfile(
                evNickname.text.toString(),
                tvBirthday.text.toString(),
                tvAddress.text.toString(),
                null
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun updateProfile(
        nickname: String?,
        birthday: String,
        address: String,
        image: String?
    ) {
        if (nickname.isNullOrBlank()) {
            UtilsCode.showShort("昵称不能为空")
            return
        }
        var param = UpdateProfileParam()
        if (nickname != UserInfoManager.userInfo?.name) {
            param.name = subNickname(nickname, 30)
        }
        param.birthday = birthday
        val split = ("${address.trim()} ").split(" ")
        param.prov = split[0]
        param.city = split[1]
        param.headImg = image
        if (UserInfoManager.isNotLogin) {
            param.sex = SP.decodeInt("gender", 1)
        }
        val jsonObject = UserInfoManager.userInfo?.extendData ?: JsonObject()
        jsonObject.addProperty("prop_block_contacts", if (cbBlockContacts.isChecked) 1 else 0)
        param.extendData = jsonObject.toString()

        val observable =
            RetrofitClient.getApiService().updateProfile(
                UserInfoManager.uid,
                UserInfoManager.token,
                UpdateProfileParam(param)
            )
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        RetrofitClient.getInstance().execute(observable, object : CustomObserver<LoginResult>() {
            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                showLoadingDialog("正在保存资料")
            }

            override fun onNext(result: LoginResult) {
                if (UserInfoManager.isNotLogin) {
                    UtilsCode.showShort("注册完成，开始bulu交友吧！")
                }
                UserInfoManager.updateUserInfo(result)
//                if (_mActivity is MainActivity) {
//                    start(MainFragment.newInstance(), ISupportFragment.SINGLETASK)
//                } else {
//                    startActivity(Intent(_mActivity, MainActivity::class.java))
//                    _mActivity.finish()
//                }
                if (fromLogin()) {
                    close()
                } else if (fromSettings()) {
                    pop()
                }
            }

            override fun _onError(e: ApiException) {
                UtilsCode.showShort("保存资料失败：${e.message}")
            }

            override fun onCompleteOrError() {
                super.onCompleteOrError()
                dismissLoadingDialog()
            }
        })
    }

    private fun subNickname(nickname: String, maxLength: Int): String {
        var buffer = StringBuffer()
        var length = 0
        for (i in 0 until nickname.length) {
            val substring = nickname.substring(i, i + 1)
            val toByteArray = substring.toByteArray(Charset.defaultCharset())
            if (toByteArray.size + length > maxLength) {
                return buffer.toString()
            } else {
                buffer.append(substring)
                length += toByteArray.size
            }
        }
        return buffer.toString()
    }

    /**
     * 上传图片
     */
    private fun uploadPic(filePath: String?) {
        if (filePath.isNullOrEmpty()) {
            return
        }
        val file = File(filePath)//filePath 图片地址
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)//表单类型
        builder.addFormDataPart(
            "file",
            file.name,
            RequestBody.create(MediaType.parse("image/png"), file)
        )

        val observable = RetrofitClient.getApiService()
            .uploadPic(UserInfoManager.uid, "1", builder.build().parts())
            .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        RetrofitClient.getInstance()
            .execute(observable, object : CustomObserver<UploadHeadResult>() {
                override fun onSubscribe(d: Disposable) {
                    super.onSubscribe(d)
                    showLoadingDialog("正在上传图片")
                }

                override fun onNext(result: UploadHeadResult) {
                    headImageUri = null
                    updateProfile(
                        evNickname.text.toString(),
                        tvBirthday.text.toString(),
                        tvAddress.text.toString(),
                        null
                    )
                }

                override fun _onError(e: ApiException?) {
                    UtilsCode.showShort("上传失败${e?.message}")
                    dismissLoadingDialog()
                }
            })
    }

//    private fun selectPic(activity: Activity, callBack: IUIKitCallBack) {
//        Matisse.from(activity)
//            .choose(MimeType.ofImage(), false)
//            .capture(true)//是否可拍照
//            .theme(R.style.Matisse_Dracula)
//            .captureStrategy(
//                CaptureStrategy(
//                    true,
//                    TUIKit.getAppContext().applicationInfo.packageName + ".uikit.fileprovider"
//                )
//            )
//            .maxSelectable(1)
//            .countable(false)
//            .gridExpectedSize(
//                activity.resources.getDimensionPixelSize(com.tencent.qcloud.uikit.R.dimen.grid_expected_size)
//            )
//            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//            .thumbnailScale(0.85f)
//            .imageEngine(GlideEngine())
//            .setOnSelectedListener { _, pathList ->
//                Log.e("onSelected", "onSelected: pathList=$pathList")
//            }
//            .originalEnable(false)//是否发送原图
//            .maxOriginalSize(10)
//            .setOnCheckedListener { isChecked ->
//                // DO SOMETHING IMMEDIATELY HERE
//                Log.e("isChecked", "onCheck: isChecked=$isChecked")
//            }
//            .forResult(callBack)
//    }

    private fun close() {
        // 登陆信息界面
        ViewModelProvider(parentViewModelStoreOwner).get(LoginOpModel::class.java)
            .closeLoginManager.postValue(true)
    }


    override fun onBackPressedSupport(): Boolean {
        if (parentViewModelStoreOwner != null) {
            ViewModelProvider(parentViewModelStoreOwner).get(LoginOpModel::class.java)
                .back.postValue(true)
            return true
        } else {
            return super.onBackPressedSupport() // 在设置页面调用
        }
    }

//    override fun onBackBtnClicked(view: View?) {
//        ViewModelProvider(parentViewModelStoreOwner).get(LoginOpModel::class.java)
//            .back.postValue(true)
//
////        super.onBackBtnClicked(view)
//    }

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
