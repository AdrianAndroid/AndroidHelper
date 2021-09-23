package cn.kuwo.pp.ui.discover

import cn.kuwo.common.base.BaseView
import cn.kuwo.pp.http.bean.voice.VoiceInfo

interface LookForFriendView:BaseView {
    fun showUserList(list: MutableList<VoiceInfo>)
    fun showUserListFailed(code:Int?,msg:String?)
    fun onLikeSuccess(listenUid:String,vid:String)
    fun onLikeFailed(code:Int?,msg:String?)
    fun onUnLikeSuccess(listenUid:String,vid:String)
    fun onUnLikeFailed(code:Int?,msg:String?)
    fun onRecPlayLogNum(num: Int)
}