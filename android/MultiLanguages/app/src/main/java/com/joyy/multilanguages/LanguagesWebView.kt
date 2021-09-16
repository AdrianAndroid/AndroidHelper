package com.joyy.multilanguages

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import com.joyy.languages.MultiLanguages

/**
 * Time:2021/9/16 16:16
 * Author: flannery
 * Description:
 */
class LanguagesWebView(context: Context, attrs: AttributeSet?) : WebView(context, attrs) {
    init {
        // 修复WebView初始化时候修改Activity语种配置的问题
        MultiLanguages.updateAppLanguage(context)
    }
}