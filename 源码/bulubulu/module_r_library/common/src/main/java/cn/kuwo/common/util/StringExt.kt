package cn.kuwo.common.util

import cn.kuwo.common.utilscode.UtilsCode
import java.text.SimpleDateFormat
import java.util.*

/**
 * 根据用户生日Date数据计算年龄
 */
fun String?.getAgeByBirthday(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    var birthday = UtilsCode.string2Date(this.replace("-", ""), SimpleDateFormat("yyyyMMdd"))
    val cal = Calendar.getInstance()
    if (cal.before(birthday)) {
        return ""
    }
    val yearNow = cal.get(Calendar.YEAR)
    val monthNow = cal.get(Calendar.MONTH) + 1
    val dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH)

    cal.time = birthday
    val yearBirth = cal.get(Calendar.YEAR)
    val monthBirth = cal.get(Calendar.MONTH) + 1
    val dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH)

    var age = yearNow - yearBirth
    if (monthNow <= monthBirth) {
        if (monthNow == monthBirth) {
            // monthNow==monthBirth
            if (dayOfMonthNow < dayOfMonthBirth) {
                age--
            }
        } else {
            // monthNow>monthBirth
            age--
        }
    }
    return age.toString()
}

// 拼接多个字符串
fun String.appendArgs(vararg strs: String): String {
    val sb: StringBuilder = StringBuilder()
    strs.forEach {
        sb.append(it)
    }
    return sb.toString()
}