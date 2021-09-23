package cn.kuwo.pp.http.bean.user

/**
 * @param authType 授权类型
 * @param bind 1-绑定，2-解绑
 */
data class BindMobileParam(
    val accessToken: String,
    val openId: String,
    val authType: Int,
    val phoneNumber: String,
    val verifyCode: String,
    val bind: Int
) {
    constructor(authType: Int, phoneNumber: String, verifyCode: String, bind: Int) :
            this("", "", authType, phoneNumber, verifyCode, bind)
}