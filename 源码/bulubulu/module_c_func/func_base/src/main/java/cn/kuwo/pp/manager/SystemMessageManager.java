package cn.kuwo.pp.manager;


import com.tencent.qcloud.uikit.common.BackgroundTasks;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.kuwo.base.KwTimer;
import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.util.DateFormatUtils;
import cn.kuwo.common.util.L;
import cn.kuwo.common.app.App;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.event.NewSystemMessageHintEvent;
import cn.kuwo.pp.event.SystemMessageEvent;
import cn.kuwo.pp.event.UnreadMessageEvent;
import cn.kuwo.pp.http.ConstantUrls;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.pp.http.bean.systemmessage.SystemMessageBean;
import cn.kuwo.pp.http.bean.systemmessage.SystemMessageResult;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.FriendList.FriendListItem;
import cn.kuwo.pp.manager.FriendList.FriendListManager;
import cn.kuwo.pp.util.notify.NotifyMgr;
import io.reactivex.Observable;

import static cn.kuwo.pp.manager.FriendList.FriendListManager.RELATION_BOTH;

// 接收系统消息
public class SystemMessageManager {
    private volatile static SystemMessageManager manager = new SystemMessageManager();
    private ArrayList<SystemMessageBean> mMessageList = new ArrayList<SystemMessageBean>(); // 所有的消息
    private boolean mIsLoadAll = false;
    private KwTimer mTimer; // 一个循环及时的操作
    private int mNewMessageCount = 0;

    public static SystemMessageManager getInstance() {
        return manager;
    }

    public List<SystemMessageBean> getMessageList() {
        return mMessageList;
    }

    // 使用TIM登陆的时候，初始化系统信息
    public void initSystemMessage() {
        BackgroundTasks.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateMessage(1, 10, null);
            }
        }, 5000);

        mTimer = new KwTimer(new KwTimer.Listener() {
            @Override
            public void onTimer(KwTimer timer) {
                if (App.DEBUG)
                    L.m(getClass(), "获取一次getLatestMessage", DateFormatUtils.formatHHmmss(System.currentTimeMillis()));
                getLatestMessage(true); //  每10秒钟获取一次数据
            }
        });
        mTimer.start(10 * 1000);
        // 每10秒获取一次消息
    }

    public void resetNewCount() {
        if (App.DEBUG) L.L(getClass(), "resetNewCount");
        mNewMessageCount = 0;

        FriendListItem systemMessageItem = getFriendItem();
        if (systemMessageItem != null) {
            FriendListManager.getInstance().getList(RELATION_BOTH).updateSystemMessageItem(systemMessageItem);
        }

        EventBus.getDefault().post(new UnreadMessageEvent());
        EventBus.getDefault().post(new FriendListManager.FriendListUpdated_event(RELATION_BOTH, true));
    }

    public FriendListItem getFriendItem() { // 获取列表
        FriendListItem item = new FriendListItem();
        item.systemMessage = true;
        item.expireType = 0;
        item.unReadMsgNum = mNewMessageCount;

        if (mMessageList.size() > 0) {
            SystemMessageBean systemMessageBean = mMessageList.get(mMessageList.size() - 1); // 这个应该是系统消息
            item.lastMessageTime = systemMessageBean.getTimemil();

            if (systemMessageBean.getFrom() != null) {
                item.user = systemMessageBean.getFrom().toVoiceInfo();
                item.user.setUid("");   //避免用户ID跟其他用户冲突
            } else {
                item.user = new VoiceInfo();
                item.user.setUid("");
                item.user.setName("");
                item.user.setHeadImg("");
            }
            item.lastMessage = getMessageTipText(systemMessageBean);
        } else {
            item.lastMessageTime = System.currentTimeMillis();
            item.user = new VoiceInfo();
            item.user.setUid("");
            item.user.setName("");
            item.user.setHeadImg("");
            item.lastMessage = "";
        }
        if (App.DEBUG)
            L.m(SystemMessageManager.class, "lastMessage>>", item.lastMessage, item.lastMessageTime, "getFriendItem", item.user.getName());

        return item;
    }

    private String getMessageTipTitle(SystemMessageBean systemMessageBean) {
        String title = "";
        if (systemMessageBean.getMessageSource() != null) {
            if (systemMessageBean.getMessageSource().getTitle() != null && !systemMessageBean.getMessageSource().getTitle().isEmpty()) {
                return systemMessageBean.getMessageSource().getTitle();
            }
        }

        return title;
    }

    private String getMessageTipText(SystemMessageBean systemMessageBean) {
        String lastMessage = "";
        if (systemMessageBean.getSource() != null) {
            lastMessage = buildMessageText(systemMessageBean);
        } else if (systemMessageBean.getItemType() == SystemMessageBean.SystemMessageCategory.RELATION_SHIP.ordinal()) {
            if (systemMessageBean.getFrom() != null) {
                lastMessage = systemMessageBean.getFrom().getName() + "喜欢了我";
            }
        } else if (systemMessageBean.getMessageSource() != null) {
            if (systemMessageBean.getMessageSource().getType() == 1) {
                lastMessage = "[ 图片 ]";
            } else if (systemMessageBean.getMessageSource().getType() == 2) {
                lastMessage = systemMessageBean.getMessageSource().getMessage();
            } else if (systemMessageBean.getMessageSource().getType() == 3) {
                lastMessage = systemMessageBean.getMessageSource().getUrl();
            } else if (systemMessageBean.getMessageSource().getType() == 4) {
                lastMessage = systemMessageBean.getMessageSource().getMessage() + systemMessageBean.getMessageSource().getUrl();
            }
        } else if (systemMessageBean.getUserMessageSource() != null) {
            if (systemMessageBean.getUserMessageSource().getType() == 1) {
                lastMessage = systemMessageBean.getUserMessageSource().getText();
            } else if (systemMessageBean.getUserMessageSource().getType() == 2) {
                lastMessage = "[ 图片 ]";
            } else if (systemMessageBean.getUserMessageSource().getType() == 3) {
                lastMessage = systemMessageBean.getUserMessageSource().getUrl();
            } else {
                lastMessage = systemMessageBean.getUserMessageSource().getText() + systemMessageBean.getUserMessageSource().getUrl();
            }
        }

        return lastMessage;
    }

    private String buildMessageText(SystemMessageBean bean) {
        if (bean == null) {
            return "";
        }
        if (bean.getSource() == null) {
            return "";
        }

        String message = "";
        if (bean.getFrom() != null) {
            message += bean.getFrom().getName();
        }

        if (bean.getType() == SystemMessageBean.SystemMessageType.TREND_COMMENT.ordinal()) {
            message += "评论";
        } else if (bean.getType() == SystemMessageBean.SystemMessageType.PK_VOTE.ordinal()) {
            message += "参与";
        } else {
            message += "喜欢";
        }

        message += "了我的";

        if (bean.getType() == SystemMessageBean.SystemMessageType.TREND_COMMENT.ordinal() || bean.getType() == SystemMessageBean.SystemMessageType.TREND_PRISE.ordinal()) {
            if (bean.getSource() != null) {
                if (bean.getSource().getTrendType() != UserTrendBean.TrendType.TREND_USER_PUBLISH) {
                    message += "PK";
                } else {
                    message += "瞬间";
                }
            }
        } else if (bean.getType() == SystemMessageBean.SystemMessageType.COMMENT_PRISE.ordinal()) {
            message += "评论";
        } else if (bean.getType() == SystemMessageBean.SystemMessageType.PK_VOTE.ordinal()) {
            message += "PK";
        }

        return message;
    }

    // 获取最新的消息
    public void getLatestMessage(boolean notifyNew) { // 获取最后的消息
        if (App.DEBUG) L.L(getClass(), "getLatestMessage");
        if (!UserInfoManager.INSTANCE.isLogin()) {
            return;
        }

        //获取消息
        //1 评论被回复 2 评论被点赞 3 动态被评论 4 动态被喜欢 5 动态投票被参与 6 用户被喜欢 7 用户回复 8 官方回复  9 官方通知 10 动态被屏蔽 11评论被屏蔽
        Observable<BaseHttpResult<BaseListData<SystemMessageResult>>> observable = RetrofitClient.getApiService()
                .updateSystemMessage(1, 10);

        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<SystemMessageResult>>() {
                    @Override
                    public void onNext(BaseListData<SystemMessageResult> o) {
                        int newCount = 0;
                        ArrayList<SystemMessageBean> newList = new ArrayList<>();
                        for (SystemMessageResult result : o.getList()) { // 找到有最新消息
                            if (newCount == 0 && result.getNewCnt() > 0) {
                                newCount = result.getNewCnt();
                            }

                            if (newList.size() < newCount) {
                                newList.add(0, result.getBody());
                                // 发送一个新的通知
                                NotifyMgr.getInstance().showNewSystemMessageNotification(getMessageTipTitle(result.getBody()), getMessageTipText(result.getBody()));
                            }
                        }

                        if (!newList.isEmpty()) {// 不为空的话，说明有最新消息
                            if (notifyNew) {
                                mNewMessageCount += newList.size();
                            } else {
                                mNewMessageCount = 0;
                            }

                            mMessageList.addAll(newList); // 加入消息列表中

                            FriendListItem systemMessageItem = getFriendItem();
                            if (systemMessageItem != null) { //bulubulu小助手在RELATION_BOTH中，心意相通
                                FriendListManager.getInstance().getList(RELATION_BOTH).updateSystemMessageItem(systemMessageItem);
                            }

                            EventBus.getDefault().post(new SystemMessageEvent(notifyNew)); // 系统消息
                            EventBus.getDefault().post(new UnreadMessageEvent()); // 未读的消息
                            EventBus.getDefault().post(new NewSystemMessageHintEvent()); // 系统消息提示
                            EventBus.getDefault().post(new FriendListManager.FriendListUpdated_event(RELATION_BOTH, true)); // 列表
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        if (App.DEBUG) {
                            UtilsCode.INSTANCE.showLong("获取系统消息失败: " + e.getMessage());
                        }
                    }
                });
    }

    private int mRetryCount = 0;

    // 更新消息, 从后台请求
    public void updateMessage(int pn, int rn, BaseFragment view) {
        if (App.DEBUG) L.L(getClass(), "updateMessage");
        if (mIsLoadAll) {
            return;
        }

        if (!UserInfoManager.INSTANCE.isLogin()) {
            return;
        }

        Observable<BaseHttpResult<BaseListData<SystemMessageResult>>> observable = RetrofitClient.getApiService().updateSystemMessage(pn, rn);
        if (view != null) {
            observable = observable.compose(view.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        }
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<SystemMessageResult>>() {
                    @Override
                    public void onNext(BaseListData<SystemMessageResult> o) {
                        if (App.DEBUG) L.L(getClass(), "updateMessage", "onNext");
                        for (SystemMessageResult result : o.getList()) {
                            mMessageList.add(0, result.getBody()); // 消息放到前边
                            if (mNewMessageCount == 0 && result.getNewCnt() > 0) {
                                mNewMessageCount = result.getNewCnt();
                            }
                        }

                        FriendListItem systemMessageItem = getFriendItem();
                        if (systemMessageItem != null) {
                            FriendListManager.getInstance().getList(RELATION_BOTH).updateSystemMessageItem(systemMessageItem);
                        }

                        mIsLoadAll = o.getList().isEmpty();
                        EventBus.getDefault().post(new SystemMessageEvent(true));
                    }

                    @Override
                    public void _onError(ApiException e) {
                        if (App.DEBUG) L.L(getClass(), "updateMessage", "_onError");
                        if (App.DEBUG) {
                            UtilsCode.INSTANCE.showLong("获取系统消息失败: " + e.getMessage());
                        }

                        if (pn == 1 && mRetryCount < 3) {
                            mRetryCount++;// 重试次数为mRetryCount

                            BackgroundTasks.getInstance().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // 获取消息失败，需要重新获取消息
                                    updateMessage(pn, rn, view);
                                }
                            }, 5000);
                        }
                    }
                });
    }
}
