package com.joyy.loglibrary.hiddo

/**
 * 提供基本的hiido上报
 * Created by MoHuaQing on 2018/7/10.
 */
interface IHiidoApi {
    /**
     * 事件统计
     *
     * @param eventId 自定义事件ID,在HiidoStatistic中选参进行传递
     */
    fun reportTimesEvent(uid: Long, eventId: String)

    /**
     * 事件统计
     *
     * @param eventId 自定义事件ID,在HiidoStatistic中选参进行传递
     * @param label 自定义事件Label ,在HiidoStatistic中选参进行传递,可为空
     */
    fun reportTimesEvent(uid: Long, eventId: String, label: String)

    /**
     * 事件统计
     *
     * @param eventId 自定义事件ID,在HiidoStatistic中选参进行传递
     * @param label 自定义事件Label ,在HiidoStatistic中选参进行传递,可为空
     * @param properties 该参数以key,value的形式上报数据，
     * 如果value为数值类型，将会统计数值的总数与平均数，
     * 如果value为字符串类型，将会统计value出现的频度及分布情况。
     */
    fun reportTimesEvent(uid: Long, eventId: String, label: String, properties: Map<String, *>)

    /**
     * 上报返回码类型质量数据到Matrix平台
     *
     * @param sCode Metrics专用的服务ID,需要先申请才能使用
     * @param uri 指标名称,用户自定义,无需申请
     * @param timeConsumption 耗时,单位:毫秒
     * @param retCode 返回码，默认0与200是成功，也可在Matrix后台定义
     */
    fun reportReturnCode(sCode: Int, uri: String, timeConsumption: Long, retCode: String)

    /**
     * 上报计数类型质量数据到Matrix平台
     *
     * @param sCode Metrics专用的服务ID,需要先申请才能使用
     * @param uri 指标名称,用户自定义,无需申请
     * @param countName 统计名称
     * @param count 累加值
     * @param times 次数
     */
    fun reportMatrixCount(sCode: Int, uri: String, countName: String, count: Long, times: Int)

    fun reportMatrixCount(sCode: Int, uri: String, countName: String, count: Long)

    fun reportStatisticContent(
        act: String,
        intFields: Map<String, Integer>,
        longFields: Map<String, Long>,
        stringFields: Map<String, String>
    )
}