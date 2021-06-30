package com.flannery.edittextselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Time:2021/6/23 20:07
 * Author:
 * Description:
 */
class FirstFragment : BaseFragment() {

    val viewModel by lazy {
        ViewModelProvider(requireActivity()).get("FirstFragment", FirstViewModel::class.java)
    }

    // 保持在上一个层级
//    val viewModel = parentFragment?.let { ViewModelProvider(it).get("FirstFragment", FirstViewModel::class.java) }

    override fun getMyClass(): Class<*>? = this::class.java
}

class FirstViewModel : ViewModel() {

}