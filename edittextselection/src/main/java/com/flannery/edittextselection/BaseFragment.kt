package com.flannery.edittextselection

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Time:2021/6/23 20:06
 * Author:
 * Description:
 */
open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    open fun getMyClass(): Class<*>? = null


}