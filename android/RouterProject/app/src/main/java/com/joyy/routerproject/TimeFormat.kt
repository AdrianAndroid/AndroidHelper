package com.joyy.routerproject

/**
 * Time:2021/8/25 16:54
 * Author: flannery
 * Description: https://juejin.cn/post/6844904094658723848
 */
class TimeFormat {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { TimeFormat() }
    }
}

fun main() {
    for (i in 0..10) {
        println(TimeFormat.instance)
    }
}