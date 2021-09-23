package cn.kuwo.common.edittext

import android.text.Editable
import android.text.TextWatcher

open class TextWatcherImp : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(editable: Editable?) {
    }

}