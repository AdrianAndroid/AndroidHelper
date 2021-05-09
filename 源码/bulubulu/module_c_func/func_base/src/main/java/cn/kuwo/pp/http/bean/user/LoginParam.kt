package cn.kuwo.pp.http.bean.user

/**
 * @author shihc
 */
public var QQLoginParam = LoginParam()

class LoginParam() {
    constructor(phoneNumber: String?, verifyCode: String?) : this() {
        this.authType = 1
        this.phoneNumber = phoneNumber
        this.verifyCode = verifyCode
    }

    constructor(authType: Int, accessToken: String?, openId: String?) : this() {
        this.authType = authType
        this.accessToken = accessToken
        this.openId = openId
    }

    /**
     * authType : 1
     * phoneNumber : 18101321024
     * verifyCode : 614006
     */

    var authType: Int = 0
    var phoneNumber: String? = null
    var verifyCode: String? = null
    var inviteCode: String? = null
    /**
     * 以下两个字段第三方登录使用
     */
    var accessToken: String? = null
    var openId: String? = null
    /**
     * 以下两个字段token登录时使用
     */
    var token: String? = null
    var uid: String? = null
}
