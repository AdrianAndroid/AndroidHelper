package com.joyy.multilanguages

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.joyy.languages.MultiLanguages

/**
 * Time:2021/9/16 14:59
 * Author: flannery
 * Description:
 */
open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(MultiLanguages.attachContext(newBase))
        /**
         * androidx 方法内部调用的代理方法覆盖了我们设置的属性，所以这里再设置一次。
         * 注意：这里穿的上下文需要被覆盖过之后的，所以是this不是newBase
         */
        MultiLanguages.attachContext(this)
    }
}