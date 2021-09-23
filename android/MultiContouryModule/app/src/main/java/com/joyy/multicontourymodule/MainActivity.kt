package com.joyy.multicontourymodule

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.joyy.base.BaseActivity
import com.joyy.base.Test
import com.joyy.base.Unique
import com.joyy.mone.SecondActivity
import com.joyy.mone.fragments.SecondFragment

class MainActivity : BaseActivity<Unique>() {

    companion object {
        val KEY = "param"
        val CHINA = "china"
        val KOREA = "korea"
        val INDIA = "india"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.chinaf).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mContainer,
                    SecondFragment().apply {
                        arguments = Bundle().apply { putString(Test.KEY, Test.CHINA) }
                    }
                ).commit()

        }
        findViewById<View>(R.id.koreaf).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mContainer,
                    SecondFragment().apply {
                        arguments = Bundle().apply { putString(Test.KEY, Test.KOREA) }
                    }
                ).commit()
        }
        findViewById<View>(R.id.indiaf).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mContainer,
                    SecondFragment().apply {
                        arguments = Bundle().apply { putString(Test.KEY, Test.INDIA) }
                    }
                ).commit()
        }





        findViewById<View>(R.id.china).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java).putExtra(KEY, CHINA))
        }
        findViewById<View>(R.id.korea).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java).putExtra(KEY, KOREA))
        }
        findViewById<View>(R.id.india).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java).putExtra(KEY, INDIA))
        }
    }
}