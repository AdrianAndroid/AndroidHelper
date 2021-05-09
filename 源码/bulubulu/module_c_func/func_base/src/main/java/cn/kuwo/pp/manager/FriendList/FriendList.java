package cn.kuwo.pp.manager.FriendList;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageStatus;
import com.tencent.qcloud.uikit.business.chat.model.MessageInfo;
import com.tencent.qcloud.uikit.business.chat.model.MessageInfoUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import cn.kuwo.common.app.App;
import cn.kuwo.common.event.LoginEvent;
import cn.kuwo.common.event.LogoutEvent;
import cn.kuwo.common.util.ArrayUtils;
import cn.kuwo.common.util.L;
import cn.kuwo.pp.event.UnreadMessageEvent;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.CustomizeMessage;
import cn.kuwo.pp.manager.SystemMessageManager;
import cn.kuwo.pp.manager.UserInfoManager;
import io.reactivex.Observable;
import kotlin.jvm.functions.Function1;

public final class FriendList {

    public static final String TEXT_HELLO_TOME = "哇哦(๑•̀ㅂ•́)و✧～看起来在很多事上TA都和你态度相同喔～ 对方申请做您心尖尖上的宝贝，接受请求吗？";
    public static final String TEXT_SAY_HELLO = "哇哦(๑•̀ㅂ•́)و✧～看起来在很多事上你都和TA态度相同喔～ TA已经注意到你了喔，不如和TA打个招呼吧？";

    public static final int DEFAULT_PAGE_SIZE = 30;

    public FriendList(int type) {
        if (App.DEBUG) L.L(getClass(), "FriendList type=" + type);
        this.type = type;
        EventBus.getDefault().register(this);
    }

    public boolean isSuccess() {
        if (App.DEBUG) L.L(getClass(), "isSuccess");
        return success;
    }

    public boolean isFinished() {
        if (App.DEBUG) L.L(getClass(), "isFinished");
        return finished;
    }

    public boolean hasUser(final String uid) {
        if (App.DEBUG) L.L(getClass(), "hasUser");
        return index.containsKey(uid);
    }

    public FriendListItem getUser(final String uid) {
        if (App.DEBUG) L.L(getClass(), "getUser");
        return index.get(uid);
    }

    public void addUser(FriendListItem item) {
        if (App.DEBUG) L.L(getClass(), "addUser");
        if (hasUser(item.user.getUid())) { // 如果有这个用户的话，先删掉
            deleteUser(item.user.getUid());
        }
        // 判断是哪个列表的FriendList
        if (type != FriendListManager.RELATION_NONE && !item.systemMessage) {
            // 删掉这个用户，但是并没有访问网络
            FriendListManager.getInstance().getList(FriendListManager.RELATION_NONE).deleteUser(item.user.getUid());
        }
        // 将这个条目添加到第一位去
        data.add(0, item); // 添加到最顶部
        index.put(item.user.getUid(), item);
        addStrangerUser(item);
    }

    public void deleteUser(String uid) {
        if (App.DEBUG) L.L(getClass(), "deleteUser");
        FriendListItem item = getUser(uid);
        if (item != null) {
            data.remove(item);
            index.remove(uid);
        }
    }

    private void addStrangerUser(FriendListItem item) {
        if (App.DEBUG) L.L(getClass(), "addStrangerUser");
        if (type != FriendListManager.RELATION_NONE) {
            return;
        }
        //添加邂逅
        Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().strangerAdd(item.user.getUid());
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        if (App.DEBUG) L.L(getClass(), "addStrangerUser", "onNext");
                    }

                    @Override
                    public void _onError(ApiException e) {
                        if (App.DEBUG) L.L(getClass(), "addStrangerUser", "_onError");
                    }
                });
    }

    // 更新系统消息
    public void updateSystemMessageItem(FriendListItem item) {
        if (App.DEBUG) L.L(getClass(), "updateSystemMessageItem");
        for (FriendListItem dataItem : data) {
            if (dataItem.systemMessage) {
                data.remove(dataItem);
                break;
            }
        }
        // 把item添加到第一位去
        addUser(item);
    }

    public ArrayList<FriendListItem> getData() {
        if (App.DEBUG) L.L(getClass(), "getData");
        return data;
    }

    // 请求数据
    public FriendList requestData() {
        if (App.DEBUG) L.L(getClass(), "requestData");
        finished = false;

        Observable ob = getObservable();
        RetrofitClient.getInstance().execute(ob,
                new CustomObserver<BaseListData<FriendListItem>>() {
                    @Override
                    public void onNext(BaseListData<FriendListItem> o) {
                        if (App.DEBUG) L.L(getClass(), "request", "onNext");
                        if (isFinished()) {
                            return;
                        }
                        // 获取列表
                        ArrayList<FriendListItem> list = (ArrayList<FriendListItem>) o.getList();
                        if (list.size() > 0) {
                            getInfoFromTIM(list);
                            add(list);
                        }
                        if (list.size() == DEFAULT_PAGE_SIZE) {
                            ++next_page; // 一次性获取全部的数据
                            requestData();
                        } else {
                            finished = true;
                            success = true;

                            if (type == FriendListManager.RELATION_BOTH) {
                                updateSystemMessageItem(SystemMessageManager.getInstance().getFriendItem());
                            }

                            EventBus.getDefault().post(
                                    new FriendListManager.FriendListUpdated_event(type, true)
                            );

                            EventBus.getDefault().post(new UnreadMessageEvent());
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        if (App.DEBUG) L.L(getClass(), "requestData", "_onError");
                        finished = true;
                        success = false;
                        EventBus.getDefault().post(
                                new FriendListManager
                                        .FriendListUpdated_event(type, false)
                        );
                    }
                });
        return this;
    }

    //  根据类型获取不同的id
    private Observable getObservable() {
        if (App.DEBUG) L.L(getClass(), "getObservalbe");
        String uid = UserInfoManager.INSTANCE.getUid();
        Observable ob = null;
        switch (type) {
            case FriendListManager.RELATION_FOLLOWED:
                ob = RetrofitClient.getApiService().friendListFollowed(uid, next_page, DEFAULT_PAGE_SIZE);
                break;
            case FriendListManager.RELATION_BE_FOLLOWED:
                ob = RetrofitClient.getApiService().friendListMine(uid, next_page, DEFAULT_PAGE_SIZE);
                break;
            case FriendListManager.RELATION_BOTH:
                ob = RetrofitClient.getApiService().friendListBoth(uid, next_page, DEFAULT_PAGE_SIZE);
                break;
            case FriendListManager.RELATION_NONE:
                ob = RetrofitClient.getApiService().strangerList(next_page, DEFAULT_PAGE_SIZE);
                break;
        }
        return ob;
    }

    // 获取问读消息的数量
    public int getUnreadMessageCount() {
        if (App.DEBUG) L.L(getClass(), "getUnreadMessageCount");
        int count = 0;

        for (FriendListItem item : data) {
            count += item.unReadMsgNum;
        }

        return count;
    }

    // 获取详情
    private void getInfoFromTIM(ArrayList<FriendListItem> newData) {
        if (App.DEBUG) L.L(getClass(), "getInfoFromTIME");
        for (FriendListItem item : newData) {
            updateMessageInfo(item);
        }
    }


    // 更新MessageInfo
    public static void updateMessageInfo(FriendListItem item) {
        if (App.DEBUG) L.L(FriendList.class, "updateMessageInfo");
        TIMConversation conv = TIMManager.getInstance().getConversation(TIMConversationType.C2C, item.user.getUid());
        TIMMessage lastMsg = conv.getLastMsg();

        item.lastMessageTime = System.currentTimeMillis();
        if (lastMsg != null && lastMsg.getElementCount() > 0) {
            item.lastMessageTime = lastMsg.timestamp();
            item.unReadMsgNum = conv.getUnreadMessageNum();
            String text = "";
            if (lastMsg.status() == TIMMessageStatus.HasRevoked) {
                if (lastMsg.isSelf()) {
                    text = "您撤回了一条消息";
                } else {
                    text = "对方撤回了一条消息";
                }
            } else if (lastMsg.getElement(0) instanceof TIMCustomElem) {
                int type = CustomizeMessage.getType(lastMsg);
                if (type == CustomizeMessage.MESSAGE_HELLO) {
                    if (lastMsg.isSelf()) {
                        text = TEXT_SAY_HELLO;
                        if (App.DEBUG)
                            L.m(FriendList.class, "TEXT_HELLO", "updateMessageInfo", item.user.getName());
                        item.expireType = 0;
                        if (item.lastMessageTime > 0) {
                            long nowSeconds = (Calendar.getInstance().getTimeInMillis() / 1000);
                            long d = nowSeconds - item.lastMessageTime;
                            if (d > 60 * 60 * 48) {
                                item.expireType = 2;
                            } else if (d > 60 * 60 * 24) {
                                item.expireType = 1;
                            }
                        }
                    } else {
                        text = TEXT_HELLO_TOME;
                    }
                } else if (type == CustomizeMessage.MESSAGE_GIFT) {
                    if (lastMsg.isSelf()) {
                        text = "[向对方表达爱意]";
                    } else {
                        text = "[对方向你表达爱意]";
                    }
                } else if (type == CustomizeMessage.MESSAGE_PK_INVITE || type == CustomizeMessage.MESSAGE_PK_PICK) {
                    text = "投票邀请";
                } else {
                    text = "[自定义消息]";
                }
            } else {
                MessageInfo msgInfo = MessageInfoUtil.TIMMessage2MessageInfo(lastMsg, false);
                text = msgInfo.getExtra().toString();
            }
            item.lastMessage = text;
            if (App.DEBUG) {
                L.m(FriendList.class, "lastMessage>>", item.lastMessage, item.lastMessageTime, "updateMessageInfo", "myuid:" + UserInfoManager.INSTANCE.getUid(), item.user.getName(), "otherUId:" + item.user.getUid());
            }
        }
    }

    // 获取Message的提示
    public static String getMessageTip(TIMMessage lastMsg) {
        if (App.DEBUG) L.L(FriendList.class, "getMessageTipe");
        String text = "";
        if (lastMsg != null && lastMsg.getElementCount() > 0) {
            if (lastMsg.status() == TIMMessageStatus.HasRevoked) {
                if (lastMsg.isSelf()) {
                    text = "您撤回了一条消息";
                } else {
                    text = "对方撤回了一条消息";
                }
            } else if (lastMsg.getElement(0) instanceof TIMCustomElem) {
                int type = CustomizeMessage.getType(lastMsg);
                if (type == CustomizeMessage.MESSAGE_HELLO) {
                    if (App.DEBUG)
                        L.m(FriendList.class, "TEXT_HELLO", "getMessageTip", text);
                    if (lastMsg.isSelf()) {
                        text = TEXT_SAY_HELLO;
                    } else {
                        text = TEXT_HELLO_TOME;
                    }
                } else if (type == CustomizeMessage.MESSAGE_GIFT) {
                    if (lastMsg.isSelf()) {
                        text = "[向对方表达爱意]";
                    } else {
                        text = "[对方向你表达爱意]";
                    }
                } else if (type == CustomizeMessage.MESSAGE_PK_INVITE || type == CustomizeMessage.MESSAGE_PK_PICK) {
                    text = "投票邀请";
                } else {
                    text = "[自定义消息]";
                }
            } else {
                MessageInfo msgInfo = MessageInfoUtil.TIMMessage2MessageInfo(lastMsg, false);
                text = msgInfo.getExtra().toString();
            }
        }
        return text;
    }


    // 讲请求的数据加进来
    private void add(final ArrayList<FriendListItem> list) {
        if (App.DEBUG) L.L(getClass(), "add");
        data.addAll(list);
        distinctAndSort(); // 排序去重

        for (FriendListItem item : list) {
            index.put(item.user.getUid(), item);
        }
    }

    private void distinctAndSort() {
        ArrayUtils.INSTANCE.distinctBy(data, new Function1<FriendListItem, String>() {
            @Override
            public String invoke(FriendListItem friendListItem) {
                return friendListItem.user.getUid();
            }
        });
        // 进行排序， 还得去重下
//        data.sort(new Comparator<FriendListItem>() {
//            @Override
//            public int compare(FriendListItem friendListItem, FriendListItem t1) {
//                return friendListItem.lastMessageTime > t1.lastMessageTime ? -1 : 1; // 按时间排序
//            }
//        });
        Collections.sort(data, new Comparator<FriendListItem>() {
            @Override
            public int compare(FriendListItem friendListItem, FriendListItem t1) {
                return friendListItem.lastMessageTime > t1.lastMessageTime ? -1 : 1; // 按时间排序
            }
        });
    }

    public void reDistinctAndSort() {
        distinctAndSort();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        if (App.DEBUG) L.L(getClass(), "onLoginEvent");
        if (!isFinished()) {
            return;
        }
        next_page = 1;
        data = new ArrayList<FriendListItem>();
        index = new HashMap<String, FriendListItem>();
        finished = false;
        success = false;
        requestData();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutEvent(LogoutEvent event) {
        if (App.DEBUG) L.L(getClass(), "onLogoutEvent");
        next_page = 1;
        data = new ArrayList<FriendListItem>();
        index = new HashMap<String, FriendListItem>();
        finished = true;
        success = false;
    }

    private int type;
    private int next_page = 1;
    private ArrayList<FriendListItem> data = new ArrayList<FriendListItem>();
    private HashMap<String, FriendListItem> index = new HashMap<String, FriendListItem>();
    private Boolean finished = false;
    private Boolean success = false;
}
