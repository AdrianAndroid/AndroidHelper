package com.flannery.androidhelper.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.flannery.androidhelper.R
import com.flannery.androidhelper.login.ui.main.LoginFragment
import com.flannery.androidhelper.login.ui.main.LoginManagerFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginManagerFragment.newInstance())
                .commitNow()
        }
    }
}