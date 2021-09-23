package cn.kuwo.pp.http.bean.user

import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.pp.R
import cn.kuwo.pp.http.bean.voice.VoiceInfo
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class UserInfo(
    val city: String,
    val prov: String,
    var age: String?,
    val authType: Int, // 1 短信验证码登陆 3 第三方登陆 6token登陆 6 小程序登陆 7 苹果登陆
    val birthday: String,
    val createTime: String,
    val name: String,
    var phoneNumber: String,
    var headImg: String,
    val sex: Int,
    val singer: Int,
    val status: Int,
    @SerializedName(value = "uid", alternate = ["id"])
    val uid: String,
    var integral: Int,
    var matchValue: Int,
    val updateTime: String,
    val extendData: JsonObject,
    var inviteCodeUser: String? = null
) {

    companion object {
        fun fromJson(json: String): UserInfo {
            val gson = Gson()
            return gson.fromJson(json, UserInfo::class.java)
        }
    }

    fun isInviteCodeUser(): Boolean {
        return "1" == inviteCodeUser
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }

    fun toVoiceInfo(): VoiceInfo{
        val voice = VoiceInfo()
        voice.uid = uid
        voice.headImg = headImg
        voice.age = age
        voice.city = city
        voice.name = name
        voice.integral = integral
        voice.sex = sex.toString()
        voice.matchValue = matchValue

        return voice
    }

    fun getSexText(): String {
        if (sex == 2) {
            return "&#xe6d8;"
        } else {
            return "&#xe6d7;"
        }
    }

    fun getBirthdayDate(): Date? {
        return UtilsCode.string2Date(birthday, SimpleDateFormat("yyyy-MM-dd"))
    }

    fun getDefaultHeadImage(): Int {
        return if (sex == 2) R.drawable.default_header_pic_famale_round else R.drawable.default_header_pic_male_round
    }

    fun getNewDefaultHeadImage(): Int {
        return if (sex == 2) R.drawable.default_header_girl else R.drawable.default_header_boy
    }
}