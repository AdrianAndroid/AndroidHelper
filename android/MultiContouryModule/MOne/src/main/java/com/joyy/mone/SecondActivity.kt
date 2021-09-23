package com.joyy.mone

import android.os.Bundle
import android.widget.TextView
import com.joyy.base.BaseActivity
import com.joyy.mone.unique.SecondUnique
import com.joyy.mone.unique.china.ChinaSecondUnique
import com.joyy.mone.unique.india.IndiaSecondUnique

@Deprecated("不用")
class SecondActivity : BaseActivity<SecondUnique>() {
    companion object {
        val KEY = "param"
        val CHINA = "china"
        val KOREA = "korea"
        val INDIA = "india"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val mTitle = findViewById<TextView>(R.id.mTitle)
        mTitle.text = unique?.getText()
        unique?.setTextColor(mTitle)
    }

    override fun onCreateUnique(): SecondUnique? {
        intent.getStringExtra(KEY)?.let { key ->
            if (CHINA == key) {
                return ChinaSecondUnique()
            } else if (INDIA == key) {
                return IndiaSecondUnique()
            } else if (KOREA == key) {
            }
        }
        return null
    }
}