package com.joyy.base

import androidx.fragment.app.Fragment

/**
 * Time:2021/9/22 17:35
 * Author: flannery
 * Description:
 */
abstract class BaseFragment<T> : Fragment() {

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