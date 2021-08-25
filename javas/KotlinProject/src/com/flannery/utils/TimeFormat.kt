package com.flannery.utils

import java.text.SimpleDateFormat
import java.util.*

// 除非根据AS动态生成类
class TimeFormat {
    val year_mon_day = "year.mon.day"
    val year_mon_day_hour_min = "year.mon.day hour:min"

    val hashMap = hashMapOf<String, SimpleDateFormat>() // ["year_mon_day":"year.mon.da"]


    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { TimeFormat() }

        fun format(key: String, date: Date): String {
            return instance.hashMap[key]?.format(date) ?: ""
        }
    }


}

//
//fun main() {
//    TimeFormat.format()
//}