package com.joyy.webviews.api

/**
 * Time:2021/8/23 09:50
 * Author: flannery
 * Description:
 */
interface IWebJS {
    operator fun invoke(name: String?, params: String?, cbName: String?): String?
}