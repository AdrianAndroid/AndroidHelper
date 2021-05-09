package cn.kuwo.common.util

object KStringUtils {
    // 避免空指针
    fun avoidNull(str: String?): String = if (str.isNullOrEmpty()) "" else str
}