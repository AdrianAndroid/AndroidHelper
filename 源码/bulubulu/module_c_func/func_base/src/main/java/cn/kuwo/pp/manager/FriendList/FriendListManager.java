package cn.kuwo.pp.manager.FriendList;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.util.L;
import cn.kuwo.common.util.StringExtKt;
import cn.kuwo.common.app.App;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.event.FriendChangeEvent;
import cn.kuwo.pp.event.UnreadMessageEvent;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.UserCommonBean;
import cn.kuwo.pp.http.bean.UserOnlineBean;
import cn.kuwo.pp.http.bean.user.UserInfo;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.CustomizeMessage;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.util.notify.NotifyMgr;
import io.reactivex.Observable;

import static cn.kuwo.common.util.RxUtilKt.runOnUIThread;
import static cn.kuwo.pp.manager.FriendList.FriendList.TEXT_SAY_HELLO;
import static cn.kuwo.pp.manager.FriendList.FriendList.updateMessageInfo;

/**
 * 整个列表的管理类
 */
public class FriendListManager {


    private static FriendListManager instance = new FriendListManager();

    public static FriendListManager getInstance() {
        return instance;
    }

    public static final int RELATION_NONE = 0; // 邂逅
    public static final int RELATION_BE_FOLLOWED = 1; // 喜欢我
    public static final int RELATION_FOLLOWED = 2; // 我喜欢
    public static final int RELATION_BOTH = 3; // 心意相通

    // 字面意思，列表更新
    public static final class FriendListUpdated_event {
        public FriendListUpdated_event(int type, boolean success) {
            this.type = type;
            this.success = success;
        }

        public int type;
        public boolean success;
    }

    //
    public static class ConversationRefresh_event {
        public ConversationRefresh_event(List<TIMConversation> convs) {
            this.convs = convs;
        }

        public List<TIMConversation> convs;
    }

    //
    public static class OnFollow_event {
        public OnFollow_event(int status, FriendListItem item) {
            this.status = status;
            this.item = item;
        }

        public int status;
        public FriendListItem item;
    }

    // 字面意思：删除朋友
    public static class OnDeleteFriend_event {
        public OnDeleteFriend_event(int status, FriendListItem item) {
            this.status = status;
            this.item = item;
        }

        public int status;
        public FriendListItem item;
    }


    public interface IFriendListManagerResult {
        void IFriendListManagerResult_success(int result);

        void IFriendListManagerResult_failed(String msg);
    }

    public void follow(final UserInfo user, final BaseFragment view, @Nullable final IFriendListManagerResult result) {
        follow(user.toVoiceInfo(), view, result);
    }

    // 关注朋友
    public void follow(final VoiceInfo user, final BaseFragment view, @Nullable final IFriendListManagerResult result) {
        if (App.DEBUG) L.L(getClass(), "follow");
        // 本地数据的一些更改
        boolean isFollowed = getList(RELATION_FOLLOWED).hasUser(user.getUid());
        boolean isBeFollowed = getList(RELATION_BE_FOLLOWED).hasUser(user.getUid());
        boolean isBoth = getList(RELATION_BOTH).hasUser(user.getUid());
        if (isFollowed || isBoth) {
            if (result != null) result.IFriendListManagerResult_failed("已经关注");
            return;
        }
        innFollow(user, view, isBeFollowed ? RELATION_BE_FOLLOWED : RELATION_NONE, result);
    }

    // 取消关注朋友
    public void delete(final String uid, final BaseFragment view, @Nullable final IFriendListManagerResult result) {
        if (App.DEBUG) L.L(getClass(), "delete");
        boolean isFollowed = getList(RELATION_FOLLOWED).hasUser(uid);
        boolean isBoth = getList(RELATION_BOTH).hasUser(uid);
        if (!isFollowed && !isBoth) {
            boolean isStranger = getList(RELATION_NONE).hasUser(uid);
            if (isStranger) { // 是不是陌生人
                deleteStranger(uid, view, result);
            } else if (result != null) {
                result.IFriendListManagerResult_failed("不是好友");
            }
            return;
        }
        int type = isFollowed ? RELATION_FOLLOWED : RELATION_BOTH;

        Observable ob = RetrofitClient.getApiService().friendListCancel(UserInfoManager.INSTANCE.getUid(), uid);
        if (view != null) {
            ob = ob.compose(view.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        }
        doRequest(ob, new IFriendListManagerResult() {
            @Override
            public void IFriendListManagerResult_success(final int res) {
                if (App.DEBUG) L.L(getClass(), "IFriendListManagerResult_success");
                if (result != null) result.IFriendListManagerResult_success(res);
                int new_status = (isFollowed ? RELATION_NONE : RELATION_BE_FOLLOWED);

                FriendListItem item = getList(type).getUser(uid);
                getList(type).deleteUser(uid);
                if (new_status == RELATION_BE_FOLLOWED) {
                    getList(RELATION_BE_FOLLOWED).addUser(item);
                } else if (new_status == RELATION_NONE) {
                    getList(RELATION_NONE).addUser(item);
                }

                EventBus.getDefault().post(new OnDeleteFriend_event(new_status, item));
                EventBus.getDefault().post(new UnreadMessageEvent());
                EventBus.getDefault().post(new FriendChangeEvent());
            }

            @Override
            public void IFriendListManagerResult_failed(String msg) {
                if (App.DEBUG) L.L(getClass(), "IFriendListManagerResult_failed");
                if (result != null) result.IFriendListManagerResult_failed(msg);
            }
        });
    }

    // 删掉陌生人
    private void deleteStranger(final String uid, final BaseFragment view, @Nullable final IFriendListManagerResult result) {
        if (App.DEBUG) L.L(getClass(), "deleteStranger");
        boolean isStranger = getList(RELATION_NONE).hasUser(uid);
        if (!isStranger) {
            return;
        }

        Observable ob = RetrofitClient.getApiService().strangerDel(uid);
        if (view != null) {
            ob = ob.compose(view.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        }

        RetrofitClient.getInstance().execute(ob,
                new CustomObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        if (App.DEBUG) L.L(getClass(), "deleteStranger onNext");
                        if (result != null) {
                            result.IFriendListManagerResult_success(0);
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        if (App.DEBUG) L.L(getClass(), "deleteStranger _onError");
                        if (result != null) {
                            result.IFriendListManagerResult_failed(e.getMessage());
                        }
                    }
                });
    }

    public final class StatusBean {
        int status;
    }

    public void status(final String uid, @Nullable final IFriendListManagerResult result) {
        if (App.DEBUG) L.L(getClass(), "status");
        Observable ob = RetrofitClient.getApiService().friendListStatus(UserInfoManager.INSTANCE.getUid(), uid);
        doRequest(ob, result);
    }


    public interface IGetVoiceInfoResult {
        void IGetVoiceInfoResult_success(VoiceInfo voice);

        void IGetVoiceInfoResult_failed(String msg);
    }

    // 获取信息
    public void getVoiceInfo(final String uid, final BaseFragment view, final IGetVoiceInfoResult result) {
        if (App.DEBUG) L.L(getClass(), "getVoiceInfo");
        Observable<BaseHttpResult<VoiceInfo>> observable = RetrofitClient.getApiService().getUserDetail(uid);
        if (view != null) {
            observable = observable.compose(view.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        }
        RetrofitClient.getInstance().execute(
                observable
                , new CustomObserver<VoiceInfo>() {
                    @Override
                    public void onNext(VoiceInfo o) {
                        if (App.DEBUG) L.L(getClass(), "getVoiceInfo", "onNext");
                        if (o != null) {
                            o.setUid(uid);
                            getIMVoiceInfo(uid, new IGetVoiceInfoResult() {
                                @Override
                                public void IGetVoiceInfoResult_success(VoiceInfo imVoice) {
                                    if (App.DEBUG)
                                        L.L(getClass(), "getVoiceInfo", "onNext", "IGetVoiceInfoResult_success");
                                    if (TextUtils.isEmpty(o.getCard())) {
                                        o.setCard(imVoice.getCard());
                                    }
                                    if (TextUtils.isEmpty(o.getHeadImg())) {
                                        o.setHeadImg(imVoice.getHeadImg());
                                    }
                                    if (TextUtils.isEmpty(o.getUrl())) {
                                        o.setUrl(imVoice.getUrl());
                                    }
                                    result.IGetVoiceInfoResult_success(o);
                                }

                                @Override
                                public void IGetVoiceInfoResult_failed(String msg) {
                                    if (App.DEBUG)
                                        L.L(getClass(), "getVoiceInfo", "onNext", "IGetVoiceInfoResult_failed");
                                    result.IGetVoiceInfoResult_success(o);
                                }
                            });
                        } else {
                            result.IGetVoiceInfoResult_failed("get voice info return null");
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        if (App.DEBUG) L.L(getClass(), "getVoiceInfo", "_onError");
                        result.IGetVoiceInfoResult_failed(e.getMessage());
                    }
                }
        );
    }

    public void sayHello(String uid) {
        if (App.DEBUG) L.L(getClass(), "sayHello");
        sayHello(uid, null);
    }

    private void sayHello(String uid, @Nullable final IFriendListManagerResult result) {
        if (App.DEBUG) L.L(getClass(), "sayHello", uid);
        TIMConversation conv = TIMManager.getInstance().getConversation(TIMConversationType.C2C, uid);
        TIMMessage msg = conv.getLastMsg();
        if (msg != null) {
            if (result != null) result.IFriendListManagerResult_success(0);
            return;
        }

        if (App.DEBUG) L.m(FriendList.class, "TEXT_HELLO", "sayHello");
        CustomizeMessage.send(uid, CustomizeMessage.MESSAGE_HELLO
                , null
                , TEXT_SAY_HELLO //哇哦(๑•̀ㅂ•́)و✧～看起来在很多事上你都和TA态度相同喔～ TA已经注意到你了喔，不如和TA打个招呼吧？
                , new CustomizeMessage.ICustomizeMessageResult() {
                    @Override
                    public void ICustomizeMessageResult_success(TIMMessage msg) {
                        if (App.DEBUG)
                            L.L(getClass(), "sayHello", "ICustomizeMessageResult_success");
                        if (result != null) result.IFriendListManagerResult_success(0);
                    }

                    @Override
                    public void ICustomizeMessageResult_failed(String desc) {
                        if (App.DEBUG)
                            L.L(getClass(), "sayHello", "ICustomizeMessageResult_failed");
                        if (result != null) result.IFriendListManagerResult_failed(desc);
                    }
                });
    }

    // 请求列表数据
    public void requestListData() {
        if (App.DEBUG) L.L(getClass(), "requestListData");
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                getList(RELATION_NONE).requestData();
                getList(RELATION_FOLLOWED).requestData();
                getList(RELATION_BE_FOLLOWED).requestData();
                getList(RELATION_BOTH).requestData();

                NotifyMgr.getInstance();
            }
        }, 1000);
    }

    public FriendList getList(int type) {
        if (App.DEBUG) L.L(getClass(), "getList type" + type);
        return friendLists[type];
    }

    public int getUnreadMessageCount() {
        if (App.DEBUG) L.L(getClass(), "getUnreadMessageCount");
        int count = getList(RELATION_BE_FOLLOWED).getUnreadMessageCount();
        count += getList(RELATION_FOLLOWED).getUnreadMessageCount();
        count += getList(RELATION_BOTH).getUnreadMessageCount();
        count += getList(RELATION_NONE).getUnreadMessageCount();

        return count;
    }

    // 有没有这个用户
    public boolean hasUser(String uid, int type) {
        if (App.DEBUG) L.L(getClass(), "hasUser");
        FriendList l = getList(type);
        return l.hasUser(uid);
    }

    // 获取用户信息
    public FriendListItem getUser(String uid) {
        if (App.DEBUG) L.L(getClass(), "getUser " + uid);
        FriendListItem item = getList(RELATION_BE_FOLLOWED).getUser(uid);
        if (item != null) {
            return item;
        }
        item = getList(RELATION_FOLLOWED).getUser(uid);
        if (item != null) {
            return item;
        }
        item = getList(RELATION_BOTH).getUser(uid);
        if (item != null) {
            return item;
        }
        item = getList(RELATION_NONE).getUser(uid);
        if (item != null) {
            return item;
        }

        return null;
    }

    // 是不是已经专注了的朋友
    public boolean isFollowedUser(String uid) {
        if (App.DEBUG) L.L(getClass(), "isFollowedUser ->" + uid);
        if (uid == null) {
            return false;
        }

        return hasUser(uid, RELATION_FOLLOWED) || hasUser(uid, RELATION_BOTH);
    }

    /**
     * 为了解决未读消息问题
     *
     * @param conversations
     */
    public void refreshConversations(final List<TIMConversation> conversations) {
        for (TIMConversation conv : conversations) {
            updateMessageInfoList(conv, getList(RELATION_FOLLOWED));
            updateMessageInfoList(conv, getList(RELATION_BE_FOLLOWED));
            updateMessageInfoList(conv, getList(RELATION_BOTH));
            updateMessageInfoList(conv, getList(RELATION_NONE));
        }

    }

    private void updateMessageInfoList(TIMConversation conv, FriendList list) {
        if (list.hasUser(conv.getPeer())) {
            for (FriendListItem item : list.getData()) {
                if (conv.getPeer().equals(item.user.getUid())) {
                    updateMessageInfo(item);
                }
            }
        }
    }

    // 刷新谈话, 从IM框架过来的
    public void onRefreshConversation(final List<TIMConversation> conversations) {
        if (App.DEBUG) L.L(getClass(), "onRefreshConversation");
        for (TIMConversation conv : conversations) {
            boolean isFollow = getList(RELATION_FOLLOWED).hasUser(conv.getPeer()); //我喜欢
            boolean isBeFollowed = getList(RELATION_BE_FOLLOWED).hasUser(conv.getPeer()); //喜欢我
            boolean isBoth = getList(RELATION_BOTH).hasUser(conv.getPeer()); //心意相通
            boolean isStranger = getList(RELATION_NONE).hasUser(conv.getPeer()); // 邂逅
            // 不是我喜欢的 && 不是喜欢我的 && 不是心意相通的 && 不是邂逅的
            if (!isFollow && !isBeFollowed && !isBoth && !isStranger) {
                status(conv.getPeer(), new IFriendListManagerResult() {
                    @Override
                    public void IFriendListManagerResult_success(int result) {
                        if (App.DEBUG)
                            L.L(getClass(), ",onRefreshConversation", "IFriendListManagerResult_success");
                        getVoiceInfo(conv.getPeer(), null, new IGetVoiceInfoResult() {
                            @Override
                            public void IGetVoiceInfoResult_success(VoiceInfo voice) {
                                if (App.DEBUG)
                                    L.L(getClass(), ",onRefreshConversation", "IFriendListManagerResult_success", "IGetVoiceInfoResult_success");
                                final FriendListItem item = new FriendListItem();
                                item.user = voice;
                                FriendList.updateMessageInfo(item);
                                FriendListManager.getInstance().getList(result).addUser(item);

                                ArrayList<TIMConversation> data = new ArrayList<TIMConversation>();
                                data.add(conv);
                                EventBus.getDefault().post(new ConversationRefresh_event(data));
                                NotifyMgr.getInstance().showNewMessageNotification(conv.getLastMsg());
                            }

                            @Override
                            public void IGetVoiceInfoResult_failed(String msg) {
                                if (App.DEBUG)
                                    L.L(getClass(), ",onRefreshConversation", "IFriendListManagerResult_success", "IGetVoiceInfoResult_failed");
                            }
                        });
                    }

                    @Override
                    public void IFriendListManagerResult_failed(String msg) {
                        if (App.DEBUG)
                            L.L(getClass(), "onRefreshConversation", "IFriendListManagerResult_failed");
                    }
                });
            }
        }
        // 需要更新一下列表
        FriendListManager.getInstance().refreshConversations(conversations);
        EventBus.getDefault().post(new ConversationRefresh_event(conversations));
        EventBus.getDefault().post(new UnreadMessageEvent());
    }

    // 关注朋友
    private void innFollow(final VoiceInfo user, final BaseFragment view, int status, @Nullable final IFriendListManagerResult result) {
        if (App.DEBUG) L.L(getClass(), "innFollow");
        // 网络上面的更改
        // 关注朋友的接口
        Observable ob = RetrofitClient.getApiService().friendListFollow(UserInfoManager.INSTANCE.getUid(), user.getUid());
        if (view != null) {
            ob = ob.compose(view.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        }
        doRequest(ob, new IFriendListManagerResult() {
            @Override
            public void IFriendListManagerResult_success(int res) {
                if (App.DEBUG)
                    L.L(getClass(), "innFollow", "IFriendListManagerResult_success");
                TIMConversation conv = TIMManager.getInstance().getConversation(TIMConversationType.C2C, user.getUid());
                if (conv.getLastMsg() == null) {
                    // 如果没有最新消息，就大哥招呼
                    sayHello(user.getUid(), new IFriendListManagerResult() {
                        @Override
                        public void IFriendListManagerResult_success(int res1) {
                            if (App.DEBUG)
                                L.L(getClass(), "innFollow", "IFriendListManagerResult_success", "IFriendListManagerResult_success");
                            onFollowResult(res, status);
                        }

                        @Override
                        public void IFriendListManagerResult_failed(String msg) {
                            if (App.DEBUG)
                                L.L(getClass(), "innFollow", "IFriendListManagerResult_success", "IFriendListManagerResult_failed");
                            onFollowResult(res, status);
                        }
                    });
                } else {
                    // 如果有最新消息
                    onFollowResult(res, status);
                }
            }

            // 关注朋友之后返回的结果
            // 在这个匿名内部类中抽象出来的方法
            private void onFollowResult(int res, int status) {
                if (App.DEBUG) L.L(getClass(), "innFollow", "onFollowResult");
                if (result != null) result.IFriendListManagerResult_success(res); // 先将正确结果返回回去

                // 如果没有关系，就是我喜欢 ｜ 有关系就是心意相通
                int new_status = (status == RELATION_NONE ? RELATION_FOLLOWED : RELATION_BOTH);

                // 新建立一个Item类
                FriendListItem item = new FriendListItem();
                item.user = user;
                FriendList.updateMessageInfo(item); // 更新消息

                // 如果{RELATION_NONE}是邂逅， 则删掉这里面的
                if (getList(RELATION_NONE).hasUser(user.getUid())) {
                    getList(RELATION_NONE).deleteUser(user.getUid());
                }

                // 新状态是心意相通，删掉喜欢我里面的
                if (new_status == RELATION_BOTH) {
                    getList(RELATION_BE_FOLLOWED).deleteUser(user.getUid());
                }

                // 添加这个用户
                getList(new_status).addUser(item);

                //向外通知
                // 注册的才发送
                EventBus.getDefault().post(new OnFollow_event(new_status, item));
                EventBus.getDefault().post(new FriendChangeEvent());
            }

            @Override
            public void IFriendListManagerResult_failed(String msg) {
                if (App.DEBUG)
                    L.L(getClass(), "innFollow", "IFriendListManagerResult_failed");
                if (result != null) result.IFriendListManagerResult_failed(msg);
            }
        });
    }

    private void getIMVoiceInfo(final String uid, final IGetVoiceInfoResult result) {
        if (App.DEBUG) L.L(getClass(), "getIMVoiceInfo");
        TIMFriendshipManager.getInstance().getUsersProfile(Arrays.asList(uid), false, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onSuccess(List<TIMUserProfile> p0) {
                if (App.DEBUG) L.L(getClass(), "getIMVoiceInfo", "onSuccess");
                TIMUserProfile profile = p0.get(0);
                if (profile == null) {
                    if (result != null) result.IGetVoiceInfoResult_failed("im has no this user");
                    return;
                }

                byte[] voiceBytes = profile.getCustomInfo().get("Voice");
                byte[] cardBytes = profile.getCustomInfo().get("Card");
                VoiceInfo voiceInfo = new VoiceInfo();
                voiceInfo.setUid(uid);
                voiceInfo.setName(profile.getNickName());
                voiceInfo.setHeadImg(profile.getFaceUrl());
                if (voiceBytes != null) {
                    voiceInfo.setUrl(new String(voiceBytes));
                }
                if (cardBytes != null) {
                    voiceInfo.setCard(new String(cardBytes));
                }

                String age = StringExtKt.getAgeByBirthday(UtilsCode.INSTANCE.millis2String(profile.getBirthday()));
                voiceInfo.setAge(age);
                voiceInfo.setCity(profile.getLocation());
                voiceInfo.setSex("" + profile.getGender());

                result.IGetVoiceInfoResult_success(voiceInfo);
            }

            @Override
            public void onError(int p0, String p1) {
                if (App.DEBUG) L.L(getClass(), "getIMVoiceInfo", "onError");
                if (result != null) result.IGetVoiceInfoResult_failed(p1);
            }

        });
    }


    // 发送网络请求
    private void doRequest(Observable ob, IFriendListManagerResult result) {
        if (App.DEBUG) L.L(getClass(), "doRequest");
        // 访问网络
        RetrofitClient.getInstance().execute(ob,
                new CustomObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        if (App.DEBUG) L.L(getClass(), "doRequest", "onNext");
                        if (result == null) {
                            return;
                        }
                        if (o instanceof StatusBean) {
                            StatusBean s = (StatusBean) o;
                            result.IFriendListManagerResult_success(s.status);
                        } else {
                            result.IFriendListManagerResult_success(0);
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        if (App.DEBUG) L.L(getClass(), "doRequest", "_onError");
                        if (result != null) result.IFriendListManagerResult_failed(e.getMessage());
                    }
                });
    }

    // 几个不同页面的列表信息
    private FriendList[] friendLists = {
            new FriendList(RELATION_NONE) // 邂逅
            , new FriendList(RELATION_BE_FOLLOWED) //喜欢我
            , new FriendList(RELATION_FOLLOWED) //我喜欢
            , new FriendList(RELATION_BOTH) //心意相通
    };

    public interface IGetMatchValueResult {
        void IGetMatchValueResult_success(int matchValue);
    }

    public void getMatchValue(final String oid, final BaseFragment view, final IGetMatchValueResult result) {
        if (App.DEBUG) L.L(getClass(), "getMatchValue");
        //获取匹配度接口
        RetrofitClient.getInstance().execute(RetrofitClient.getApiService().friendMatchValue(oid).compose(view.bindUntilEvent(FragmentEvent.DESTROY_VIEW)),
                new CustomObserver<Number>() {
                    @Override
                    public void onNext(Number o) {
                        if (App.DEBUG) L.L(getClass(), "getMatchValue", "onNext");
                        if (result != null) {
                            result.IGetMatchValueResult_success((int) o.floatValue());
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        if (App.DEBUG) L.L(getClass(), "getMatchValue", "_onError");
                    }
                });
    }

    public interface IGetSameCountResult {
        void IGetSameCountResult_success(int sameCount);
    }

    public void getSameCount(final String oid, final BaseFragment view, final IGetSameCountResult result) {
        if (App.DEBUG) L.L(getClass(), "getSameCount");
        RetrofitClient.getInstance().execute(RetrofitClient.getApiService().friendCommonData(oid).compose(view.bindUntilEvent(FragmentEvent.DESTROY_VIEW)),
                new CustomObserver<UserCommonBean>() {
                    @Override
                    public void onNext(UserCommonBean o) {
                        if (App.DEBUG) L.L(getClass(), "getSameCount", "onNext");
                        if (result != null) {
                            result.IGetSameCountResult_success(o.getSame());
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        if (App.DEBUG) L.L(getClass(), "getSameCount", "_onError");
                    }
                });
    }

    public interface IGetUserOnlineResult {
        void IGetUserOnlineResult_success(UserOnlineBean onlineBean);
    }

    public void getUserStatus(final String oid, final BaseFragment view, final IGetUserOnlineResult result) {
        if (App.DEBUG) L.L(getClass(), "getUserStatus");
        //返回的用户状态，目前支持的状态有：
        RetrofitClient.getInstance().execute(RetrofitClient.getApiService().queryUserOnline(oid).compose(view.bindUntilEvent(FragmentEvent.DESTROY_VIEW)),
                new CustomObserver<BaseListData<UserOnlineBean>>() {
                    @Override
                    public void onNext(BaseListData<UserOnlineBean> o) {
                        if (App.DEBUG) L.L(getClass(), "getUserStatus", "onNext");
                        if (result != null && o.getList().size() > 0) {
                            result.IGetUserOnlineResult_success(o.getList().get(0));
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        if (App.DEBUG) L.L(getClass(), "getUserStatus", "_onError");
                    }
                });
    }
}
