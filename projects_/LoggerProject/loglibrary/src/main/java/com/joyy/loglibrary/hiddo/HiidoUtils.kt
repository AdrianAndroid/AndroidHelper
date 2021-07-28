package com.joyy.loglibrary.hiddo

/**
 * 使用此utils时，项目需要引入依赖：com.yy.mobile.architecture.hiidoapi  并初始化
 * Created by MoHuaQing on 2018/7/10.
 */
object HiidoUtils {

    var hiidoProxy: IHiidoApi? = null

    @JvmStatic
    fun reportTimesEvent(uid: Long, eventId: String) {
        hiidoProxy?.reportTimesEvent(uid, eventId)
    }

    @JvmStatic
    fun reportTimesEvent(uid: Long, eventId: String, label: String) {
        hiidoProxy?.reportTimesEvent(uid, eventId, label)
    }

    @JvmStatic
    fun reportTimesEvent(uid: Long, eventId: String, label: String, properties: Map<String, *>) {
        hiidoProxy?.reportTimesEvent(uid, eventId, label, properties)
    }

    @JvmStatic
    fun reportReturnCode(sCode: Int, uri: String, timeConsumption: Long, retCode: String) {
        hiidoProxy?.reportReturnCode(sCode, uri, timeConsumption, retCode)
    }

    @JvmStatic
    fun reportMatrixCount(sCode: Int, uri: String, countName: String, count: Long, times: Int) {
        hiidoProxy?.reportMatrixCount(sCode, uri, countName, count, times)
    }

    @JvmStatic
    fun reportMatrixCount(sCode: Int, uri: String, countName: String, count: Long) {
        hiidoProxy?.reportMatrixCount(sCode, uri, countName, count)
    }

    @JvmStatic
    fun reportStatisticContent(
        act: String,
        intFields: Map<String, Integer>,
        longFields: Map<String, Long>,
        stringFields: Map<String, String>
    ) {
        hiidoProxy?.reportStatisticContent(act, intFields, longFields, stringFields)
    }
}