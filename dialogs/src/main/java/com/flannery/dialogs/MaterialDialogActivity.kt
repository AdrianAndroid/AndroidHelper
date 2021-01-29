package com.flannery.dialogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ToastUtils

class MaterialDialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_dialog)


        // 正在加载中

    }

    // 正在加载中
    fun showLoading(view: View) {
        DialogUtils.showLoading("正在加载中xxx...")
    }

    fun showDeleteFriend(view: View) {
        DialogUtils.showDeleteFriends({ agree ->
            ToastUtils.showShort("" + agree)
        })
    }


}