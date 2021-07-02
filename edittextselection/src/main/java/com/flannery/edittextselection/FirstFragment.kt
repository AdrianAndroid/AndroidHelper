package com.flannery.edittextselection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * Time:2021/6/23 20:07
 * Author:
 * Description:
 */
class FirstFragment : BaseFragment() {

    val viewModel by lazy {
        ViewModelProvider(requireActivity()).get("FirstFragment", FirstViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.commit {
            add(R.id.container, SecondFragment())
        }
        l1("FirstFragment , parentFragmentManager $parentFragmentManager")
        l1("FirstFragment , childFragmentManager $childFragmentManager")
    }

    private fun l1(msg: String) =
        mylog("$msg 线程name：${Thread.currentThread().name} 线程id：${Thread.currentThread().id}")

    private fun mylog(msg: String) = Log.e(MainActivity::class.simpleName, msg)

    private fun test() = runBlocking {
        repeat(8) {
            //Log.e(MainActivity::class.simpleName, "协程执行$it 线程id：${Thread.currentThread().id}")
            mylog("协程执行$it 线程id：${Thread.currentThread().id}")
            delay(1000)
        }
    }


    // 保持在上一个层级
//    val viewModel = parentFragment?.let { ViewModelProvider(it).get("FirstFragment", FirstViewModel::class.java) }

    override fun getMyClass(): Class<*>? = this::class.java
}

class FirstViewModel : ViewModel() {

}