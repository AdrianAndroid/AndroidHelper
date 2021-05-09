package cn.kuwo.pp.ui.world;


import com.google.gson.JsonObject;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.comment.CommentBean;
import cn.kuwo.pp.http.bean.comment.CommentItem;
import cn.kuwo.pp.manager.UserInfoManager;
import io.reactivex.Observable;

public class CommentPresenter {
    private CommentDialog mView;

    public CommentPresenter(CommentDialog dialog){
        mView = dialog;
    }

    public void getComment(int sourceType, String sourceId, int pn){
        Observable<BaseHttpResult<CommentBean>> observable = RetrofitClient.getApiService().getComments(sourceType, sourceId,2, pn).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<CommentBean>() {
                    @Override
                    public void onNext(CommentBean o) {
                        mView.onGetComment(true, pn, o);
                    }

                    @Override
                    public void _onError(ApiException e) {
                        mView.onGetComment(false, pn, null);
                    }
                });
    }

    public void sendComment(int sourceType, String sourceId, String content){
        try{
            content = URLEncoder.encode(content, "UTF-8");
        }catch (UnsupportedEncodingException e){ }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("content", content);

        Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().sendComment(sourceType, sourceId, UserInfoManager.INSTANCE.getUid(), UserInfoManager.INSTANCE.getToken(), jsonObject).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getComment(sourceType, sourceId, 1);
                    }

                    @Override
                    public void _onError(ApiException e) {
                        //UtilsCode.INSTANCE.showShort("发表评论失败: " + e.getMessage());
                    }
                });
    }

    public void likeComment(int sourceType, String sourceId, CommentItem item, int position, boolean like){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("commentId", item.getCommentId() + "");

        if(like){
            Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().likeComment(sourceType, sourceId, UserInfoManager.INSTANCE.getUid(), UserInfoManager.INSTANCE.getToken(), jsonObject).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            RetrofitClient.getInstance().execute(observable,
                    new CustomObserver<Object>() {
                        @Override
                        public void onNext(Object o) {
                            mView.onLikeComment(position, item, like);
                        }

                        @Override
                        public void _onError(ApiException e) {
                            UtilsCode.INSTANCE.showShort("点赞评论失败");
                        }
                    });
        }else{
            Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().unLikeComment(sourceType, sourceId, UserInfoManager.INSTANCE.getUid(), UserInfoManager.INSTANCE.getToken(), jsonObject).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            RetrofitClient.getInstance().execute(observable,
                    new CustomObserver<Object>() {
                        @Override
                        public void onNext(Object o) {
                            mView.onLikeComment(position, item, like);
                        }

                        @Override
                        public void _onError(ApiException e) {
                            UtilsCode.INSTANCE.showShort("取消点赞评论失败");
                        }
                    });
        }
    }

}
