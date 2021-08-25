package com.flannery.design_pattern

import com.flannery.design_pattern.decorator.DataSourceDecorator

object Utils {

    fun log(any: Any, vararg str: String) {
        if (str.size > 1) {
            println("[${any.javaClass.simpleName}] ${str.first()}")
        } else {
            val append = StringBuilder().append("[${any.javaClass.simpleName}] ")
            for (s in str) {
                append.append(s).append(",")
            }
            println(append.toString())
        }
    }

}