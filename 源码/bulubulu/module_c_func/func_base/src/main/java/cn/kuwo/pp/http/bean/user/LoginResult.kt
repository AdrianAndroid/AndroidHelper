package cn.kuwo.pp.http.bean.user

import com.google.gson.Gson

data class LoginResult(
    var hasName: Int,
    var isBind: Int,
    val token: String,
    val uid: String,
    var thirdName: String,
    var accessToken: String,
    var openId: String,
    var userSig: String,
    var inviteCodeUser: String?, // 是否是邀请码注册新用户。1-是。0-不是
    var userInfo: UserInfo?
) {

    // 是否是邀请码注册新用户。1-是。0-不是
    fun isInviteCodeUser() = "1" == inviteCodeUser


    companion object {
        fun fromJson(json: String): LoginResult {
            val gson = Gson()
            return gson.fromJson(json, LoginResult::class.java)
        }
    }
}
