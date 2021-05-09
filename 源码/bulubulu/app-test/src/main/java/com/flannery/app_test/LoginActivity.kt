package com.flannery.app_test

import android.os.Bundle
import cn.kuwo.common.base.BaseActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(supportFragmentManager!=null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_id, LoginTestFragment())
                .commitNow()
        }

    }


}