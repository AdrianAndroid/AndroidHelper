package com.flannery.edittextselection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * Time:2021/6/23 20:08
 * Author:
 * Description:
 */
class SecondFragment : BaseFragment() {

    override fun getMyClass(): Class<*>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        l1("SecondFragment parentFragmentManager: $parentFragmentManager")
        l1("SecondFragment childFragmentManager: $childFragmentManager")
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

}