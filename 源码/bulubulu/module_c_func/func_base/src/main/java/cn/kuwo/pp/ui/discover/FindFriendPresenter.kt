package cn.kuwo.pp.ui.discover

import cn.kuwo.common.app.App
import cn.kuwo.common.base.BasePresenter
import cn.kuwo.common.util.L
import cn.kuwo.pp.http.CustomObserver
import cn.kuwo.pp.http.RetrofitClient
import cn.kuwo.pp.http.bean.BaseListData
import cn.kuwo.pp.http.bean.PlayLogInfo
import cn.kuwo.pp.http.bean.voice.VoiceInfo
import cn.kuwo.networker.exception.ApiException
import cn.kuwo.pp.manager.FriendList.FriendListManager
import com.trello.rxlifecycle2.android.FragmentEvent

class FindFriendPresenter : BasePresenter {
    var mView: FindFriendFragment

    constructor(mVIew: FindFriendFragment) {
        this.mView = mVIew
    }

    public fun like(info: VoiceInfo,vid:String){
        if (App.DEBUG) L.L(javaClass,  "like")
        val observable = RetrofitClient.getApiService().sendLikeOpt(info.uid,vid,"1").compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        RetrofitClient.getInstance().execute(observable,object : CustomObserver<Object>(){
            override fun onNext(t: Object) {
               // mView?.onLikeSuccess(listenUId,vid)
            }

            override fun _onError(e: ApiException?) {
              //  mView?.onLikeFailed(e?.code,e?.message)
            }

        })

        FriendListManager.getInstance().follow(info, mView, null)
    }

    public fun unlike(listenUId:String,vid:String){
        val observable = RetrofitClient.getApiService().sendLikeOpt(listenUId,vid,"2").compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        RetrofitClient.getInstance().execute(observable,object : CustomObserver<Object>(){
            override fun onNext(t: Object) {
              //  mView?.onUnLikeSuccess(listenUId,vid)
            }

            override fun _onError(e: ApiException?) {
              //  mView?.onUnLikeFailed(e?.code,e?.message)
            }

        })
    }

    public fun getRecommendUsers(card:String?,sex:String?) {
        val observable = RetrofitClient.getApiService().getRecommendVoices(card, sex).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        RetrofitClient.getInstance().execute(observable, object : CustomObserver<BaseListData<VoiceInfo>>() {
            override fun onNext(listData: BaseListData<VoiceInfo>) {
                mView?.showUserList(listData.list)
            }

            override fun _onError(e: ApiException) {
                mView?.showUserListFail(e.message)
            }
        })
    }

    override fun onDestroy() {
    }

    fun sendPlayLog(voiceUid: String?, vid: String?) {
        val observable = RetrofitClient.getApiService().sendPlayVoiceEvent(vid,voiceUid).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        RetrofitClient.getInstance().execute(observable,object : CustomObserver<PlayLogInfo>(){
            override fun onNext(t: PlayLogInfo) {
              //  mView.onRecPlayLogNum(t.leftNum)
            }

            override fun _onError(e: ApiException?) {
                e?.printStackTrace()
            }

        })
    }
}