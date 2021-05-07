package com.flannery.dialogs

import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner

/**
 *
 */

object DialogUtils {


    var dialog: MaterialDialog? = null

    private fun initMaterialDialog(fragment: Fragment? = null) {
        val activity = fragment?.activity ?: UtilCode.getTopActivity()
        activity?.let { context ->
            dialog = MaterialDialog(context)
        }
        if (fragment is LifecycleOwner) {
            dialog?.lifecycleOwner(fragment)
        } else if (activity is LifecycleOwner) {
            dialog?.lifecycleOwner(activity)
        }
        dialog?.debugMode(false)
    }

    // 正在加载中...
    fun showLoading(title: String?, fragment: Fragment? = null) {
        dismissDialog() // 先取消上一个，再加载最新的
        initMaterialDialog(fragment)
        dialog?.show {
            customView(R.layout.materialdialog_progress)
        }
        title?.let { text ->
            dialog?.onShow {
                val customView = it.getCustomView()
                customView.findViewById<TextView>(R.id.progressTitle)?.text = text
            }
        }
    }

    // 确定要删除好友吗
    fun showDeleteFriends(listener: ((agree: Boolean) -> Unit), fragment: Fragment? = null) {
        val text = "确定要删除好友吗？"
        showContentPositiveNegative(text, listener, fragment)
    }

    //"确认退出嘛？没有关注TA的话，以后可能不会再碰到TA喔
    fun showAttensionDialog(listener: ((agree: Boolean) -> Unit), fragment: Fragment? = null) {
        showContentPositiveNegative("确认退出嘛？没有关注TA的话，以后可能不会再碰到TA喔", listener, fragment)
    }

    //网络受到神秘力量干扰
    fun showNetworkErrorDialog(fragment: Fragment? = null) {
        showContentPositiveKnow("网络受到神秘力量干扰", {

        }, fragment)
    }

    //错过你是TA的损失，更有趣的灵魂在等你发现哦～
    fun showMissFriends(listener: ((agree: Boolean) -> Unit), fragment: Fragment? = null) {
        showContentPositiveKnow("错过你是TA的损失，更有趣的灵魂在等你发现哦～", listener, fragment)
    }

    //你的账号在另一台手机上登录，您已被迫下线，如非本人操作，请及时修改密码"
    fun showForceOfflineDialog(listener: ((agree: Boolean) -> Unit), fragment: Fragment? = null) {
        showContentPositiveNegative("确认退出嘛？没有关注TA的话，以后可能不会再碰到TA喔", listener, fragment)
    }

    // 我们需要获取存储空间，为你缓存音乐文件；否则，您将无法正常使用该应用；设置路径：设置->应用->bulubulu->权限
    fun showStoragePermissionDialog(
        listener: ((agree: Boolean) -> Unit),
        fragment: Fragment? = null
    ) {
        showContentPositiveNegative(
            "我们需要获取存储空间，为你缓存音乐文件；否则，您将无法正常使用该应用；设置路径：设置->应用->bulubulu->权限",
            listener,
            fragment
        )
    }

    //model.getTrendType() == UserTrendBean.TrendType.TREND_USER_PUBLISH ? "确认删除该动态?" : "确认删除该投票",
    fun showDeleteDynamicOrVote(
        isDynamicNotVote: Boolean,
        listener: ((agree: Boolean) -> Unit),
        fragment: Fragment? = null
    ) {
        showContentPositiveNegative(
            if (isDynamicNotVote) "确认删除该动态?" else "确认删除该投票",
            listener,
            fragment, "确认", "取消"
        )
    }

    // 恭喜挑战成功哦～
    fun showChallengeSuccessDialog(
        listener: ((agree: Boolean) -> Unit),
        fragment: Fragment? = null
    ) {
        showContentPositiveKnow(
            "恭喜挑战成功哦～", listener, fragment, "查看结果"
        )
    }


    fun showPrivicyDialog(
        listener: ((agree: Boolean) -> Unit),
        fragment: Fragment? = null
    ) {
        showContentPositiveKnow(
            "您需同意本提示方可使用本软件", listener, fragment, "我知道了"
        )
    }


    fun showContentPositiveKnow(
        content: String,
        listener: ((agree: Boolean) -> Unit),
        fragment: Fragment? = null,
        positiveText: String? = "知道了"
    ) {
        dismissDialog()
        initMaterialDialog(fragment)
        dialog?.show {
            title(null, content)
            positiveButton(null, positiveText) {
                listener.invoke(true)
            }
        }
    }

    // 便于java调用
    fun showContentPositiveNegativeOkCancel(
        content: String,
        listener: ((agree: Boolean) -> Unit),
        fragment: Fragment? = null
    ) {
        showContentPositiveNegative(content, listener, fragment)
    }


    fun showContentPositiveNegative(
        content: String,
        listener: ((agree: Boolean) -> Unit),
        fragment: Fragment? = null,
        positiveText: String? = "确定",
        negativeText: String? = "取消"
    ) {
        dismissDialog()
        initMaterialDialog(fragment)
        dialog?.show {
            title(null, content)
            positiveButton(null, positiveText) {
                listener.invoke(true)
            }
            negativeButton(null, negativeText) {
                listener.invoke(false)
            }
        }
    }


    fun dismissDialog() {
        dialog?.dismiss()
        dialog = null
    }

}