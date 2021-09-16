package com.joyy.multilanguages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RadioGroup
import android.widget.TextView
import com.joyy.languages.MultiLanguages
import java.util.*

class MainActivity : BaseActivity(), RadioGroup.OnCheckedChangeListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mWebView = findViewById<WebView>(R.id.wv_main_web)
        val mRadioGroup = findViewById<RadioGroup>(R.id.rg_main_languages)

        mWebView.webViewClient = WebViewClient()
        mWebView.webChromeClient = WebChromeClient()
        mWebView.loadUrl("https://developer.android.google.cn/index.html")


        //((TextView) findViewById(R.id.tv_language_activity)).setText(this.getResources().getString(R.string.current_language));
        (findViewById<TextView>(R.id.tv_main_language_application)).text =
            application.resources.getString(R.string.current_language)
//        (findViewById<TextView>(R.id.tv_main_language_system)).setText(
//            MultiLanguages.getLanguageString(
//                this,
//                MultiLanguages.getSystemLanguage(),
//                R.string.current_language
//            )
//        )

        if (MultiLanguages.isSystemLanguage()) {
            mRadioGroup.check(R.id.rb_main_language_auto)
        } else {
            val locale: Locale = MultiLanguages.getAppLanguage()
            if (Locale.CHINA == locale) {
                mRadioGroup.check(R.id.rb_main_language_cn)
            } else if (Locale.TAIWAN == locale) {
                mRadioGroup.check(R.id.rb_main_language_tw)
            } else if (Locale.ENGLISH == locale) {
                mRadioGroup.check(R.id.rb_main_language_en)
            } else {
                mRadioGroup.check(R.id.rb_main_language_auto)
            }
        }

        mRadioGroup.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {

        // 是否需要重启

        // 是否需要重启
        var restart = false

        if (checkedId == R.id.rb_main_language_auto) {
            // 跟随系统
            restart = MultiLanguages.setSystemLanguage(this) // 回调（我们可以直接kill掉应用）
        } else if (checkedId == R.id.rb_main_language_cn) {
            // 简体中文
            restart = MultiLanguages.setAppLanguage(this, Locale.CHINA)
        } else if (checkedId == R.id.rb_main_language_tw) {
            // 繁体中文
            restart = MultiLanguages.setAppLanguage(this, Locale.TAIWAN)
        } else if (checkedId == R.id.rb_main_language_en) {
            // 英语
            restart = MultiLanguages.setAppLanguage(this, Locale.ENGLISH)
        }

        if (restart) {
            // 1.使用recreate来重启Activity的效果差，有闪屏的缺陷
            // recreate();

            // 2.使用常规startActivity来重启Activity，有从左向右的切换动画，稍微比recreate的效果好一点，但是这种并不是最佳的效果
            // startActivity(new Intent(this, LanguageActivity.class));
            // finish();

            // 3.我们可以充分运用 Activity 跳转动画，在跳转的时候设置一个渐变的效果，相比前面的两种带来的体验是最佳的
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.activity_alpha_in, R.anim.activity_alpha_out)
            finish()
        }
    }
}