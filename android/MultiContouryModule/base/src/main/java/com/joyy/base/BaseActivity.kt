package com.joyy.base

import androidx.appcompat.app.AppCompatActivity

/**
 * Time:2021/9/22 17:35
 * Author: flannery
 * Description:
 */
abstract class BaseActivity<T : Unique> : AppCompatActivity() {

    protected var unique: T? = null
        get() {
            if (field == null) {
                field = onCreateUnique()
            }
            return field
        }

    open fun onCreateUnique(): T? {
        return null
    }
}