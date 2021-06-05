package cn.kuwo.common.util

import cn.kuwo.common.BuildConfig

fun Any.assertNull(boolean: Boolean){
    if(BuildConfig.DEBUG && boolean) {
        error("Assertion failed")
    }
}