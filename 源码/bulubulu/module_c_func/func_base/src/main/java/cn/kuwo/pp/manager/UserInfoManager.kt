package cn.kuwo.pp.manager

import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import cn.kuwo.common.app.App
import cn.kuwo.common.event.LoginEvent
import cn.kuwo.common.event.LogoutEvent
import cn.kuwo.common.util.L
import cn.kuwo.common.util.SP
import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.pp.event.EditProfileEvent
import cn.kuwo.pp.event.EditVoiceEvent
import cn.kuwo.pp.event.TimForceOfflineEvent
import cn.kuwo.pp.event.TimLoginEvent
import cn.kuwo.pp.http.bean.user.LoginResult
import cn.kuwo.pp.http.bean.user.UserInfo
import cn.kuwo.pp.http.bean.voice.VoiceInfo
import cn.kuwo.pp.manager.FriendList.FriendListManager
import com.elbbbird.android.analytics.AnalyticsUtils
import com.google.gson.Gson
import com.tencent.imsdk.*
import com.tencent.qcloud.uikit.TUIKit
import com.tencent.qcloud.uikit.common.IUIKitCallBack
import org.greenrobot.eventbus.EventBus
import java.util.*


/**
 * @author shihc
 */
object UserInfoManager {

    var loginResult: LoginResult? = null

    var voiceInfo: VoiceInfo? = null //初始化声音

    val uuid: String//UUID

    var userInfo: UserInfo? = null
        get() = loginResult?.userInfo

    var token = ""
        get() = loginResult?.token ?: ""

    var uid: String = ""
        get() {
            return loginResult?.uid ?: ""
        }

    // 是否登陆， uid不为空， userInfo（登陆状态）不为空，有名称
    val isLogin: Boolean
        get() = !TextUtils.isEmpty(uid) && userInfo != null && loginResult?.hasName != 0

    val isInviteCodeUser: Boolean
        get() = loginResult?.isInviteCodeUser() == true

    val isNotLogin: Boolean
        get() = !isLogin

    var timLoginSuccess = false
    private var timLoging = false

    init {
        var uuid = SP.decodeString("uuid")
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString()
            SP.encode("uuid", uuid)
        }
        this.uuid = uuid
        if (SP.contains("voiceInfo")) {
            try {
                voiceInfo = VoiceInfo.fromJson(SP.decodeString("voiceInfo"))
            } catch (e: Exception) {
                L.m(javaClass, "初始化声音失败")
            }
        }
        if (SP.contains("loginResult")) {
            try {
                loginResult = LoginResult.fromJson(SP.decodeString("loginResult")) //回复登陆状态
                onReceiveUserSig(uid, loginResult?.userSig)
            } catch (e: Exception) {
                L.m(javaClass, "初始化登录信息失败")
            }
        }
    }

    private fun updateHeadImage(image: String?) {
        if (App.DEBUG) L.L(javaClass, "updateHeadImage")
        if (image != null && image != loginResult?.userInfo?.headImg) {
            loginResult?.userInfo?.headImg = image
            SP.encode("loginResult", Gson().toJson(loginResult));
        }
    }

    fun updateVoice(voiceInfo: VoiceInfo) {
        if (App.DEBUG) L.L(javaClass, "updateVoice")
        this.voiceInfo = voiceInfo
        updateHeadImage(voiceInfo.headImg)
        SP.encode("voiceInfo", Gson().toJson(voiceInfo))
        EventBus.getDefault().post(EditVoiceEvent())
        updateTimVoice()
    }

    /**
     * 登陆成功
     */
    fun onLoginSuccess(result: LoginResult?) {
        if (App.DEBUG) L.L(javaClass, "onLoginSuccess")
        AnalyticsUtils.loginIn(result?.uid, result?.userInfo?.authType) // 统计登陆
        if (result != null) {
            this.loginResult = result
            SP.encode("loginResult", Gson().toJson(result))
        }
        EventBus.getDefault().post(LoginEvent())
        // 调用IM的登陆操作
        onReceiveUserSig(uid, loginResult?.userSig)
    }

    fun onAutoLoginSuccess(result: LoginResult) {
        if (App.DEBUG) L.L(javaClass, "onAutoLoginSuccess")
        this.loginResult = result
        SP.encode("loginResult", Gson().toJson(result))
    }

    fun updateUserInfo(result: LoginResult) {
        if (App.DEBUG) L.L(javaClass, "updateUserInfo")
        loginResult?.userInfo = result.userInfo
        loginResult?.isBind = result.isBind
        loginResult?.hasName = result.hasName
        SP.encode("loginResult", Gson().toJson(this.loginResult))
        EventBus.getDefault().post(EditProfileEvent())
        editTimProfile()
    }

    /**
     * 保存起来
     */
    fun onFirstLogin(result: LoginResult) {// 第一次登陆
        if (App.DEBUG) L.L(javaClass, "onFirstLogin")
        this.loginResult = result
        SP.encode("loginResult", Gson().toJson(result))
        onReceiveUserSig(uid, loginResult?.userSig)
    }

    fun onLogoutSuccess() { //退出登陆
        if (App.DEBUG) L.L(javaClass, "onLogouSuccess")
        TIMManager.getInstance().logout(null)
        loginResult = null //设置为空
        voiceInfo = null
        EventBus.getDefault().post(LogoutEvent(uid)) // 发送退出广播
        SP.remove("loginResult")
        SP.remove("voiceInfo")
        SP.clearAll()
    }

    fun isMe(uid: String): Boolean {// 是否是我本人
        if (App.DEBUG) L.L(javaClass, "isMe")
        return !TextUtils.isEmpty(uid) && TextUtils.equals(uid, uid)
    }

    fun isMale(): Boolean {//是否是男性
        if (App.DEBUG) L.L(javaClass, "isMale")
        return SP.decodeInt("gender", 1) == 1
    }

    fun getUserGender(): String { // 获取性别
        if (App.DEBUG) L.L(javaClass, "getUserGender")
        if (isLogin) {
            return userInfo?.sex.toString()
        } else {
            return SP.decodeInt("gender", 1).toString()  //未登录
        }
    }

    fun getTheme(): Int { //获取主题
        if (App.DEBUG) L.L(javaClass, "getTheme")
        if (userInfo?.sex == 1) {
            return AppCompatDelegate.MODE_NIGHT_NO
        } else {
            return AppCompatDelegate.MODE_NIGHT_YES
        }
    }

    /**
     * 在收到服务器颁发的 userSig 后，调用IMSDK的 login 接口
     * userId 用户账号
     * userSig 您服务器给这个用户账号颁发的 IMSDk 鉴权认证
     * 1. 登陆成功的时候
     * 2. 自动登陆的时候
     * 3. 第一次登陆的时候
     * 4. 登陆失败的时候
     */
    private fun onReceiveUserSig(userId: String, userSig: String?) {
        if (App.DEBUG) L.L(javaClass, "onReceiveUserSig")
        if (timLoging) {
            return
        }
        timLoging = true
        //IM登陆
        TUIKit.login(userId, TUIKit.getUserSig(userId, userSig), object : IUIKitCallBack {
            override fun onSuccess(data: Any?) {
                if (App.DEBUG) L.L(javaClass, "updateHeadImage", "TUIKit onSuccess")
                timLoging = false
                timLoginSuccess = true // 登陆成功的转台
                EventBus.getDefault().post(TimLoginEvent()) // 怎么没找到有地方使用
                editTimProfile() // 配置文件
                FriendListManager.getInstance().requestListData() //接收列表信息
                SystemMessageManager.getInstance().initSystemMessage() //初始化系统消息
            }

            override fun onError(module: String?, errCode: Int, errMsg: String?) {
                if (App.DEBUG) L.L(javaClass, "updateHeadImage", "TUIKit onError")
                timLoging = false
                timLoginSuccess = false
                if (errCode == 6208) {
                    EventBus.getDefault().post(TimForceOfflineEvent()) // 在MainActivity中强制下线
                } else {
                    UtilsCode.showShort("im登录失败:$errCode")
                }
            }
        })
    }

    private fun editTimProfile() {
        if (App.DEBUG) L.L(javaClass, "editTimProfile")
        if (userInfo == null) {
            return
        }
        if (!timLoginSuccess) {//登陆失败
            onReceiveUserSig(uid, loginResult?.userSig)
        }
        val profileMap = HashMap<String, Any>()
        profileMap[TIMUserProfile.TIM_PROFILE_TYPE_KEY_NICK] = userInfo!!.name
        if (userInfo!!.sex == 1) {
            profileMap[TIMUserProfile.TIM_PROFILE_TYPE_KEY_GENDER] = TIMFriendGenderType.GENDER_MALE
        } else if (userInfo!!.sex == 2) {
            profileMap[TIMUserProfile.TIM_PROFILE_TYPE_KEY_GENDER] =
                TIMFriendGenderType.GENDER_FEMALE
        }
        profileMap[TIMUserProfile.TIM_PROFILE_TYPE_KEY_LOCATION] = userInfo!!.city
        profileMap[TIMUserProfile.TIM_PROFILE_TYPE_KEY_FACEURL] = userInfo!!.headImg
        if (userInfo?.birthday?.isNotEmpty() == true) {
            profileMap[TIMUserProfile.TIM_PROFILE_TYPE_KEY_BIRTHDAY] = userInfo!!.birthday.replace(
                "-",
                ""
            ).toInt()
        }
        if (voiceInfo != null && voiceInfo?.url != null && voiceInfo?.card != null) {
            profileMap[TIMUserProfile.TIM_PROFILE_TYPE_KEY_CUSTOM_PREFIX + "Voice"] =
                voiceInfo!!.url
            profileMap[TIMUserProfile.TIM_PROFILE_TYPE_KEY_CUSTOM_PREFIX + "Card"] =
                voiceInfo!!.card
        }
        /**
         * "Tag_Profile_IM_Gender" -> {Integer@15331} 2
         * "Tag_Profile_IM_BirthDay" -> {Integer@15333} 19950101
         * "Tag_Profile_IM_Location" -> "北京"
         * "Tag_Profile_IM_Image" -> "http://wimg1.kuwo.cn/314338df908100f6394488c66b6e894b"
         * "Tag_Profile_Custom_Card" -> ""
         * "Tag_Profile_IM_Nick" -> "1831(测试)"
         * "Tag_Profile_Custom_Voice" -> ""
         */
        TIMFriendshipManager.getInstance().modifySelfProfile(profileMap, object : TIMCallBack {
            override fun onError(code: Int, desc: String) {
                Log.e("", "modifySelfProfile failed: $code desc$desc")
            }

            override fun onSuccess() {
                Log.e("", "modifySelfProfile success")
            }
        })
    }

    // 更新Tim的消息
    private fun updateTimVoice() {
        if (App.DEBUG) L.L(javaClass, "updateTimeVoice")
        if (voiceInfo == null) {
            return
        }
        if (!timLoginSuccess) {//登陆失败
            onReceiveUserSig(uid, loginResult?.userSig)
            return
        }
        val profileMap = HashMap<String, Any>()
        if (voiceInfo != null && voiceInfo?.url != null && voiceInfo?.card != null) {
            profileMap[TIMUserProfile.TIM_PROFILE_TYPE_KEY_CUSTOM_PREFIX + "Voice"] =
                voiceInfo!!.url
            profileMap[TIMUserProfile.TIM_PROFILE_TYPE_KEY_CUSTOM_PREFIX + "Card"] =
                voiceInfo!!.card
        }
        TIMFriendshipManager.getInstance().modifySelfProfile(profileMap, object : TIMCallBack {
            override fun onError(code: Int, desc: String) {
                if (App.DEBUG) L.L(
                    javaClass,
                    "editTimProfile",
                    "TIMFriendshipManager",
                    "onError"
                )
                L.m(javaClass, "修改im信息失败：$code ：desc$desc")
            }

            override fun onSuccess() {
                if (App.DEBUG) L.L(
                    javaClass,
                    "editTimProfile",
                    "TIMFriendshipManager",
                    "onSuccess"
                )
                L.m(javaClass, "updateTimVoice成功")
            }
        })
    }
}
