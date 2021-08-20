package com.joyy.webviews.js.handler

import com.joyy.webviews.js.IJsCallBack

/**
 * Time:2021/8/20 15:49
 * Author: flannery
 * Description:
 */
interface IModuleJS {
    fun setCallBack(cb: IJsCallBack)
    fun moduleName(): String
}