package cn.kuwo.pp.util

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import cn.kuwo.common.util.ImageLoader
import cn.kuwo.pp.R
import cn.kuwo.pp.http.bean.user.UserInfo
import cn.kuwo.pp.manager.UserInfoManager

object HeaderRoundImageHelper {
    fun showCircleImg(fragment: Fragment) {

        try {
            val findViewById = fragment.view?.findViewById<ImageView>(R.id.ivHeaderImg)
            findViewById?.setImageResource(R.drawable.default_header)
            val headImg = UserInfoManager.userInfo?.headImg
            if (UserInfoManager.isLogin && findViewById != null && !TextUtils.isEmpty(headImg)
            ) {
                val newDefaultHeadImage = UserInfoManager.userInfo!!.getNewDefaultHeadImage()
                ImageLoader.showCircleImage(findViewById, headImg, newDefaultHeadImage)
            }
//            else {
//                findViewById?.setImageResource(R.drawable.default_header)
//            }

            setInviteVisiable(
                fragment.view,
                UserInfoManager.isLogin && UserInfoManager.isInviteCodeUser
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun showCircleImg(view: View?, headImg: String?, defaultHeadImg: Int, inviteUser: Boolean) {
        val findViewById = view?.findViewById<ImageView>(R.id.ivHeaderImg)
        if (UserInfoManager.isLogin && findViewById != null && !TextUtils.isEmpty(headImg)
        ) {
            ImageLoader.showCircleImage(findViewById, headImg, defaultHeadImg)
        } else {
            findViewById?.setImageResource(R.drawable.default_header)
        }
        // 是否邀请码--角标
        view?.findViewById<ImageView>(R.id.ivInviteImg)?.let {
            if (inviteUser) {
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
        }

        setInviteVisiable(view, UserInfoManager.isLogin && UserInfoManager.isInviteCodeUser)
    }

    // 是否显示邀请码
    private fun setInviteVisiable(view: View?, visiable: Boolean) {
        val ivInviteImg = view?.findViewById<View>(R.id.ivInviteImg)
        val ivInviteOval = view?.findViewById<View>(R.id.ivInviteOval)

        if (visiable) {
            ivInviteImg?.visibility = View.VISIBLE
            ivInviteOval?.visibility = View.VISIBLE
        } else {
            ivInviteImg?.visibility = View.GONE
            ivInviteOval?.visibility = View.GONE
        }
    }


    fun setCircleImgClickListener(fragment: Fragment, listener: View.OnClickListener) {
        fragment.view?.let { setCircleImgClickListener(it, listener) }
    }

    private fun setCircleImgClickListener(view: View?, listener: View.OnClickListener) {
        val findViewById = view?.findViewById<View>(R.id.ivHeaderImg)
        findViewById?.setOnClickListener(listener)
    }

}