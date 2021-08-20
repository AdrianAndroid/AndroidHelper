package com.joyy.webviews.js.handler

/**
 * Time:2021/8/20 15:49
 * Author: flannery
 * Description:
 */
interface IJsApi {
    fun invoke(name: String?, params: String?, cbName: String?): String?
}