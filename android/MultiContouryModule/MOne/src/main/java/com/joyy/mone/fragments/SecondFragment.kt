package com.joyy.mone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.joyy.base.BaseFragment
import com.joyy.base.Test
import com.joyy.mone.R
import com.joyy.mone.unique.Fragment1Unique
import com.joyy.mone.unique.china.ChinaFragment1Unique
import com.joyy.mone.unique.india.IndiaFragment1Unique

/**
 * Time:2021/9/23 10:27
 * Author: flannery
 * Description:
 */
class SecondFragment : BaseFragment<Fragment1Unique>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mTitle = view.findViewById<TextView>(R.id.mTitle)
        mTitle.text = unique?.getText()
        unique?.setTextColor(mTitle)


        // 显示隐藏
        val iv1 = view.findViewById<View>(R.id.iv1)
        val iv2 = view.findViewById<View>(R.id.iv2)
        val iv3 = view.findViewById<View>(R.id.iv3)
        val iv4 = view.findViewById<View>(R.id.iv4)
        val iv5 = view.findViewById<View>(R.id.iv5)
        val iv6 = view.findViewById<View>(R.id.iv6)

        arrayOf(iv2, iv4, iv6).forEach {
            it.visibility = unique?.imagesDisplay() ?: View.VISIBLE
        }
    }

    override fun onCreateUnique(): Fragment1Unique? {
        arguments?.getString(Test.KEY).let { key ->
            if (Test.CHINA == key) {
                return ChinaFragment1Unique()
            } else if (Test.INDIA == key) {
                return IndiaFragment1Unique()
            } else if (Test.KOREA == key) {

            }
        }
        return super.onCreateUnique()
    }

}