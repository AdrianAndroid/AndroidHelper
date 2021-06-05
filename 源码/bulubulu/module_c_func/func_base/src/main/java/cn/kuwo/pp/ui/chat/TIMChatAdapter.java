package cn.kuwo.pp.ui.chat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMFaceElem;
import com.tencent.imsdk.TIMFileElem;
import com.tencent.imsdk.TIMImage;
import com.tencent.imsdk.TIMImageElem;
import com.tencent.imsdk.TIMImageType;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMSnapshot;
import com.tencent.imsdk.TIMSoundElem;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMVideo;
import com.tencent.imsdk.TIMVideoElem;
import com.tencent.imsdk.ext.message.TIMMessageExt;
import com.tencent.qcloud.uikit.R;
import com.tencent.qcloud.uikit.TUIKit;
import com.tencent.qcloud.uikit.api.chat.IChatAdapter;
import com.tencent.qcloud.uikit.api.chat.IChatProvider;
import com.tencent.qcloud.uikit.business.chat.c2c.model.C2CChatManager;
import com.tencent.qcloud.uikit.business.chat.model.MessageInfo;
import com.tencent.qcloud.uikit.business.chat.view.ChatListView;
import com.tencent.qcloud.uikit.business.chat.view.widget.ChatListEvent;
import com.tencent.qcloud.uikit.business.chat.view.widget.MessageInterceptor;
import com.tencent.qcloud.uikit.common.BackgroundTasks;
import com.tencent.qcloud.uikit.common.UIKitConstants;
import com.tencent.qcloud.uikit.common.component.audio.UIKitAudioArmMachine;
import com.tencent.qcloud.uikit.common.component.face.FaceManager;
import com.tencent.qcloud.uikit.common.component.picture.imageEngine.impl.GlideEngine;
import com.tencent.qcloud.uikit.common.component.video.VideoViewActivity;
import com.tencent.qcloud.uikit.common.utils.DateTimeUtil;
import com.tencent.qcloud.uikit.common.utils.FileUtil;
import com.tencent.qcloud.uikit.common.utils.UIUtils;
import com.tencent.qcloud.uikit.common.widget.photoview.PhotoViewActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.util.L;
import cn.kuwo.common.app.App;
import cn.kuwo.pp.http.bean.QuestionModel;
import cn.kuwo.pp.manager.CustomizeMessage;
import cn.kuwo.pp.manager.UserInfoManager;

//import static cn.kuwo.pp.manager.CustomizeMessage.MESSAGE_PK_PICK;


/**
 * @author shihc
 */
public class TIMChatAdapter extends IChatAdapter {

    private boolean mLoading = true;

    private ChatListView mRecycleView;

    private List<MessageInfo> mDataSource = new ArrayList<>();

    private ChatListEvent mListEvent;

    private MessageInterceptor mInterceptor;

    private SetDataSourceListener mDataSourceListener;

    private String mUserIcons;

    private C2CChatFragment mFragment;

    @Override
    public void setChatListEvent(ChatListEvent mListEvent) {
        this.mListEvent = mListEvent;
    }

    public void setEditor(MessageInterceptor interceptor) {
        this.mInterceptor = interceptor;
    }

    private static final int width = UIUtils.getPxByDp(100);
    private static final int height = UIUtils.getPxByDp(160);
    private static final int normal = UIUtils.getPxByDp(120);

    private static final int audio_min_width = UIUtils.getPxByDp(60);
    private static final int audio_max_width = UIUtils.getPxByDp(250);
    private static final int headerViewType = -99;
    private static List<String> downloadEles = new ArrayList();

    public void setOwnerView(C2CChatFragment fragment) {
        mFragment = fragment;
    }

    public void setUserIcons(String userIcon) {
        mUserIcons = userIcon;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == headerViewType) { // 重新加载
            LayoutInflater inflater = LayoutInflater.from(TUIKit.getAppContext());
            return new HeaderViewHolder(inflater.inflate(R.layout.chat_adapter_load_more, parent, false));
        }

        LayoutInflater inflater = LayoutInflater.from(TUIKit.getAppContext());
        RecyclerView.ViewHolder holder = new ChatTextHolder(inflater.inflate(R.layout.chat_adapter_text, parent, false));
        switch (viewType) {
            case MessageInfo.MSG_TYPE_TEXT: // 普通文字类型
                holder = new ChatTextHolder(inflater.inflate(R.layout.chat_adapter_text, parent, false));
                break;
            case MessageInfo.MSG_TYPE_TEXT + 1:
                if (mRecycleView.isDivided()) {
                    holder = new ChatTextHolder(inflater.inflate(R.layout.chat_adapter_text_self, parent, false));
                } else {
                    holder = new ChatTextHolder(inflater.inflate(R.layout.chat_adapter_text, parent, false));
                }
                break;
            case MessageInfo.MSG_TYPE_IMAGE:
            case MessageInfo.MSG_TYPE_VIDEO:
            case MessageInfo.MSG_TYPE_CUSTOM_FACE:
                holder = new ChatImageHolder(inflater.inflate(R.layout.chat_adapter_image, parent, false));
                break;
            case MessageInfo.MSG_TYPE_IMAGE + 1:
            case MessageInfo.MSG_TYPE_VIDEO + 1:
            case MessageInfo.MSG_TYPE_CUSTOM_FACE + 1:
                if (mRecycleView.isDivided())
                    holder = new ChatImageHolder(inflater.inflate(R.layout.chat_adapter_image_self, parent, false));
                else
                    holder = new ChatImageHolder(inflater.inflate(R.layout.chat_adapter_image, parent, false));
                break;
            // 音频
            case MessageInfo.MSG_TYPE_AUDIO:
                holder = new ChatAudioHolder(inflater.inflate(R.layout.chat_adapter_audio, parent, false));
                break;
            case MessageInfo.MSG_TYPE_AUDIO + 1:
                if (mRecycleView.isDivided())
                    holder = new ChatAudioHolder(inflater.inflate(R.layout.chat_adapter_audio_self, parent, false));
                else
                    holder = new ChatAudioHolder(inflater.inflate(R.layout.chat_adapter_audio, parent, false));

                break;
            // 文件 -- 没有这个需求
            case MessageInfo.MSG_TYPE_FILE:
                holder = new ChatFileHolder(inflater.inflate(R.layout.chat_adapter_file, parent, false));
                break;
            case MessageInfo.MSG_TYPE_FILE + 1:
                if (mRecycleView.isDivided())
                    holder = new ChatFileHolder(inflater.inflate(R.layout.chat_adapter_file_self, parent, false));
                else
                    holder = new ChatFileHolder(inflater.inflate(R.layout.chat_adapter_file, parent, false));
                break;
            // PK邀请
            case MessageInfo.MSG_TYPE_PK_INVITE:
            case MessageInfo.MSG_TYPE_PK_INVITE + 1:
                holder = new ChatPKHolder(inflater.inflate(R.layout.chat_adapter_pk_self, parent, false));
                break;
            //PK问题
            case MessageInfo.MSG_TYPE_PK_PICK:
            case MessageInfo.MSG_TYPE_PK_PICK + 1:
                holder = new ChatPKHolder(inflater.inflate(R.layout.chat_adapter_empty, parent, false));
                break;
        }
        if (viewType >= MessageInfo.MSG_TYPE_TIPS) {
            holder = new ChatTipsHolder(inflater.inflate(R.layout.chat_adapter_tips, parent, false));
        }

        return holder;
    }


    private int getColor(int colorInt) {
        return ContextCompat.getColor(App.getInstance(), colorInt);
    }

    private Drawable getDrawable(int drawableInt) {
        return ContextCompat.getDrawable(App.getInstance(), drawableInt);
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            if (mLoading) {
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                holder.itemView.setVisibility(View.VISIBLE);
            } else {
                holder.itemView.setVisibility(View.GONE);
                param.height = 0;
                param.width = 0;
            }
            holder.itemView.setLayoutParams(param);
            return;
        }
        //final Resources nightResources = NightModeResourceUtils.getResources();
        final MessageInfo msg = getItem(position);
        final TIMMessage timMsg = msg.getTIMMessage();
        final BaseChatHolder chatHolder = (BaseChatHolder) holder;
        if (chatHolder.progress != null) {
            Drawable drawable = chatHolder.progress.getIndeterminateDrawable();
            if (drawable != null) {
                int colorTint = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorHighlight);
                drawable.setColorFilter(colorTint, PorterDuff.Mode.SRC_ATOP);
            }
        }

        if (msg.getMsgType() == MessageInfo.MSG_TYPE_PK_PICK) {
            chatHolder.chatTime.setVisibility(View.GONE);
        } else {
            if (mRecycleView.getChatTimeBubbleId() > 0) {
                Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), mRecycleView.getChatTimeBubbleId());
                chatHolder.chatTime.setBackground(drawable);
            }
            if (mRecycleView.getChatTimeColor() != 0) {
                int color = ContextCompat.getColor(holder.itemView.getContext(), cn.kuwo.pp.R.color.colorSubTitleColor);
                chatHolder.chatTime.setTextColor(color);
            }
            if (mRecycleView.getChatTimeSize() != 0) {
                chatHolder.chatTime.setTextSize(mRecycleView.getChatTimeSize());
            }

            if (position > 1) {
                TIMMessage last = getItem(position - 1).getTIMMessage();
                if (last != null) {
                    if (timMsg.timestamp() - last.timestamp() >= 5 * 60) {
                        int txtColor = getColor(cn.kuwo.pp.R.color.colorSubTitleColor);
                        chatHolder.chatTime.setTextColor(txtColor);
                        chatHolder.chatTime.setVisibility(View.VISIBLE);
                        chatHolder.chatTime.setText(DateTimeUtil.getTimeFormatText(new Date(msg.getMsgTime())));
                    } else {
                        chatHolder.chatTime.setVisibility(View.GONE);
                    }
                }
            } else {
                int txtColor = getColor(cn.kuwo.pp.R.color.colorSubTitleColor);
                chatHolder.chatTime.setTextColor(txtColor);
                chatHolder.chatTime.setVisibility(View.VISIBLE);
                chatHolder.chatTime.setText(DateTimeUtil.getTimeFormatText(new Date(msg.getMsgTime())));
            }
        }

        if (msg.getMsgType() >= MessageInfo.MSG_TYPE_TIPS) {
            ChatTipsHolder tipsHolder = (ChatTipsHolder) holder;
            if (mRecycleView.getTipsMessageBubbleId() > 0) {
                Drawable drawable = getDrawable(mRecycleView.getTipsMessageBubbleId());
                tipsHolder.tips.setBackground(drawable);
            }
            if (mRecycleView.getTipsMessageColor() != 0) {
                int color = getColor(cn.kuwo.pp.R.color.colorSubTitleColor);
                tipsHolder.tips.setTextColor(color);
            }
            if (mRecycleView.getTipsMessageSize() != 0) {
                tipsHolder.tips.setTextSize(mRecycleView.getTipsMessageSize());
            }

            if (msg.getStatus() == MessageInfo.MSG_STATUS_REVOKE) {
                if (msg.isSelf()) {
                    tipsHolder.tips.setText("您撤回了一条消息");
                } else if (msg.isGroup()) {
                    tipsHolder.tips.setText(msg.getFromUser() + "撤回了一条消息");
                } else {
                    tipsHolder.tips.setText("对方撤回了一条消息");
                }

            } else if (msg.getMsgType() >= MessageInfo.MSG_TYPE_GROUP_CREATE && msg.getMsgType() <= MessageInfo.MSG_TYPE_GROUP_MODIFY_NOTICE) {
                if (msg.getExtra() != null) {
                    tipsHolder.tips.setText(msg.getExtra().toString());
                }
            }
            return;
        }
        if (msg.isSelf()) {
            if (chatHolder.dataView != null) {
                if (msg.getMsgType() == MessageInfo.MSG_TYPE_PK_INVITE) {
                    chatHolder.dataView.setBackground(null);
                    ((ChatPKHolder) chatHolder).layoutTotalContent.setBackground(getDrawable(cn.kuwo.pp.R.drawable.chat_pk_invite_self));
                } else {
                    if (mRecycleView.getSelfBubbleId() > 0) {
                        Drawable drawable = getDrawable(mRecycleView.getSelfBubbleId());
                        chatHolder.dataView.setBackground(drawable);
                    } else {
                        Drawable drawable = getDrawable(cn.kuwo.pp.R.drawable.tim_self_message_bg);
                        chatHolder.dataView.setBackground(drawable);
                    }
                }
            }
            chatHolder.userIcon.setVisibility(View.VISIBLE);
            ImageLoader.showCircleImage(chatHolder.userIcon, UserInfoManager.INSTANCE.getUserInfo().getHeadImg());
        } else {
            if (chatHolder.dataView != null) {
                if (msg.getMsgType() == MessageInfo.MSG_TYPE_PK_INVITE) {
                    chatHolder.dataView.setBackground(null);
                    ((ChatPKHolder) chatHolder).layoutTotalContent.setBackground(getDrawable(cn.kuwo.pp.R.drawable.chat_pk_invite_other));
                } else {
                    if (mRecycleView.getOppositeBubbleId() > 0) {
                        Drawable drawable = getDrawable(mRecycleView.getOppositeBubbleId());
                        chatHolder.dataView.setBackground(drawable);
                    } else {
                        Drawable drawable = getDrawable(cn.kuwo.pp.R.drawable.tim_other_message_bg);
                        chatHolder.dataView.setBackground(drawable);
                    }
                }
            }
            chatHolder.userIcon.setVisibility(View.VISIBLE);
            ImageLoader.showCircleImage(chatHolder.userIcon, mUserIcons);
        }

        if (mListEvent != null) {
            chatHolder.contentGroup.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListEvent.onMessageLongClick(v, position, msg);
                    return true;
                }
            });

            chatHolder.userIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListEvent.onUserIconClick(v, position, msg);
                }
            });

        }

        // 根据类型， 显示不同的条目
        switch (getItemViewType(position)) {
            case MessageInfo.MSG_TYPE_TEXT:
            case MessageInfo.MSG_TYPE_TEXT + 1:
                // 文字消息
                ChatTextHolder msgHolder = (ChatTextHolder) chatHolder;
                msgHolder.msg.setVisibility(View.VISIBLE);
                if (timMsg.getElement(0) instanceof TIMTextElem) {
                    msgHolder.chatInfoContent.setVisibility(View.VISIBLE);
                    msgHolder.greet.setVisibility(View.GONE);
                    TIMTextElem textElem = (TIMTextElem) timMsg.getElement(0);
                    FaceManager.handlerEmojiText(msgHolder.msg, textElem.getText());
                } else if (timMsg.getElement(0) instanceof TIMCustomElem) {
                    msgHolder.chatInfoContent.setVisibility(View.GONE);
                    msgHolder.greet.setVisibility(View.VISIBLE);

                    int type = CustomizeMessage.getType(timMsg);
                    if (type == CustomizeMessage.MESSAGE_HELLO) {
                        int txtColor = getColor(cn.kuwo.pp.R.color.colorSubTitleColor);
                        msgHolder.greet.setTextColor(txtColor);
                        mFragment.setHelloText(msgHolder.greet, msg.isSelf());
                    } else if (type == CustomizeMessage.MESSAGE_GIFT) {
                        int txtColor = getColor(cn.kuwo.pp.R.color.colorSubTitleColor);
                        msgHolder.greet.setTextColor(txtColor);
                        msgHolder.greet.setText(msg.isSelf() ? "向对方表达爱意" : "对方向你表达爱意");
                    }
                }
                if (mRecycleView.getContextSize() != 0) {
                    msgHolder.msg.setTextSize(mRecycleView.getContextSize());
                }
                if (msg.isSelf()) {
                    if (mRecycleView.getSelfContentColor() != 0) {
                        msgHolder.msg.setTextColor(mRecycleView.getSelfContentColor());
                    }
                } else {
                    if (mRecycleView.getOppositeContentColor() != 0) {
                        msgHolder.msg.setTextColor(mRecycleView.getOppositeContentColor());
                    }
                }
                break;
            case MessageInfo.MSG_TYPE_VIDEO:
            case MessageInfo.MSG_TYPE_VIDEO + 1:
            case MessageInfo.MSG_TYPE_IMAGE:
            case MessageInfo.MSG_TYPE_IMAGE + 1:
            case MessageInfo.MSG_TYPE_CUSTOM_FACE:
            case MessageInfo.MSG_TYPE_CUSTOM_FACE + 1:
                final ChatImageHolder imgHolder = (ChatImageHolder) chatHolder;
                imgHolder.imgData.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params;
                //自定义表情的比较特殊，优先处理
                if (msg.getMsgType() == MessageInfo.MSG_TYPE_CUSTOM_FACE) {
                    imgHolder.cover.setVisibility(View.GONE);
                    imgHolder.playBtn.setVisibility(View.GONE);
                    imgHolder.mDuration.setVisibility(View.GONE);
                    params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                    imgHolder.imgData.setLayoutParams(params);
                    if (timMsg.getElementCount() > 0) {
                        TIMFaceElem faceEle = (TIMFaceElem) timMsg.getElement(0);
                        imgHolder.imgData.setImageBitmap(FaceManager.getCustomBitmap(faceEle.getIndex(), new String(faceEle.getData())));
                    }
                    break;
                }

                if (msg.getImgWithd() < 200 && msg.getImgHeight() < 200) {
                    params = new RelativeLayout.LayoutParams(normal, normal);
                } else if (msg.getImgWithd() > msg.getImgHeight()) {
                    params = new RelativeLayout.LayoutParams(height, width);
                } else {
                    params = new RelativeLayout.LayoutParams(width, height);
                }
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                imgHolder.imgData.setLayoutParams(params);

                if (msg.getMsgType() == MessageInfo.MSG_TYPE_IMAGE) {
                    imgHolder.cover.setVisibility(View.GONE);
                    imgHolder.playBtn.setVisibility(View.GONE);
                    imgHolder.mDuration.setVisibility(View.GONE);
                    final TIMImageElem imageEle = (TIMImageElem) timMsg.getElement(0);
                    final List<TIMImage> imgs = imageEle.getImageList();
                    if (!TextUtils.isEmpty(msg.getDataPath())) {
                        GlideEngine.loadImage(imgHolder.imgData, msg.getDataPath(), null);
                    } else {
                        for (int i = 0; i < imgs.size(); i++) {
                            final TIMImage img = imgs.get(i);
                            if (img.getType() == TIMImageType.Thumb) {
                                synchronized (downloadEles) {
                                    if (downloadEles.contains(img.getUuid()))
                                        break;
                                    downloadEles.add(img.getUuid());
                                }
                                final String path = UIKitConstants.IMAGE_DOWNLOAD_DIR + img.getUuid();
                                img.getImage(path, new TIMCallBack() {
                                    @Override
                                    public void onError(int code, String desc) {
                                        downloadEles.remove(img.getUuid());
                                        if (App.DEBUG)
                                            L.m(getClass(), "ChatAdapter img getImage", code + ":" + desc);
                                    }

                                    @Override
                                    public void onSuccess() {
                                        downloadEles.remove(img.getUuid());
                                        msg.setDataPath(path);
                                        GlideEngine.loadImage(imgHolder.imgData, msg.getDataPath(), null);
                                    }
                                });
                                break;
                            }
                        }
                    }

                    imgHolder.imgData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < imgs.size(); i++) {
                                TIMImage img = imgs.get(i);
                                if (img.getType() == TIMImageType.Original) {
                                    PhotoViewActivity.mCurrentOriginalImage = img;
                                    break;
                                }
                            }
                            Intent intent = new Intent(TUIKit.getAppContext(), PhotoViewActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(UIKitConstants.IMAGE_DATA, msg.getDataPath());
                            intent.putExtra(UIKitConstants.SELF_MESSAGE, msg.isSelf());
                            TUIKit.getAppContext().startActivity(intent);
                        }
                    });
                    imgHolder.imgData.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (mListEvent != null) {
                                mListEvent.onMessageLongClick(v, position, msg);
                                return true;
                            }
                            return false;
                        }
                    });

                } else if (msg.getMsgType() == MessageInfo.MSG_TYPE_VIDEO) {
                    imgHolder.playBtn.setVisibility(View.VISIBLE);
                    imgHolder.cover.setLayoutParams(params);
                    imgHolder.mDuration.setVisibility(View.VISIBLE);
                    final TIMVideoElem videoEle = (TIMVideoElem) timMsg.getElement(0);
                    final TIMVideo video = videoEle.getVideoInfo();

                    if (!TextUtils.isEmpty(msg.getDataPath())) {
                        GlideEngine.loadImage(imgHolder.imgData, msg.getDataPath(), null);
                    } else {
                        final TIMSnapshot shotInfo = videoEle.getSnapshotInfo();
                        synchronized (downloadEles) {

                            if (downloadEles.contains(shotInfo.getUuid()))
                                break;
                            downloadEles.add(shotInfo.getUuid());
                        }

                        final String path = UIKitConstants.IMAGE_DOWNLOAD_DIR + videoEle.getSnapshotInfo().getUuid();
                        videoEle.getSnapshotInfo().getImage(path, new TIMCallBack() {
                            @Override
                            public void onError(int code, String desc) {
                                downloadEles.remove(shotInfo.getUuid());
                                if (App.DEBUG)
                                    L.m(getClass(), "ChatAdapter video getImage", code + ":" + desc);
                            }

                            @Override
                            public void onSuccess() {
                                downloadEles.remove(shotInfo.getUuid());
                                msg.setDataPath(path);
                                GlideEngine.loadImage(imgHolder.imgData, msg.getDataPath(), null);
                            }
                        });
                    }

                    String durations = "00:" + video.getDuaration();
                    if (video.getDuaration() < 10)
                        durations = "00:0" + video.getDuaration();
                    imgHolder.mDuration.setText(durations);
                    if (msg.isSelf()) {
                        imgHolder.cover.setVisibility(View.GONE);
                        registerVideoPlayClickListener(imgHolder.contentGroup, msg);
                    } else {

                        final String videoPath = UIKitConstants.VIDEO_DOWNLOAD_DIR + video.getUuid();
                        File videoFile = new File(videoPath);
                        //如果视频还未下载
                        if (!videoFile.exists()) {
                            imgHolder.cover.setVisibility(View.VISIBLE);
                            imgHolder.contentGroup.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((BaseChatHolder) holder).progress.setVisibility(View.VISIBLE);
                                    video.getVideo(videoPath, new TIMCallBack() {
                                        @Override
                                        public void onError(int code, String desc) {
                                            UIUtils.toastLongMessage("下载视频失败:" + code + "=" + desc);
                                            ((BaseChatHolder) holder).progress.setVisibility(View.GONE);
                                            msg.setStatus(MessageInfo.MSG_STATUS_DOWNLOADED);
                                        }

                                        @Override
                                        public void onSuccess() {
                                            ((BaseChatHolder) holder).progress.setVisibility(View.GONE);
                                            registerVideoPlayClickListener(imgHolder.contentGroup, msg);
                                            imgHolder.cover.setVisibility(View.GONE);
                                            //下载完后自动播放
                                            Intent intent = new Intent(TUIKit.getAppContext(), VideoViewActivity.class);
                                            intent.putExtra(UIKitConstants.CAMERA_IMAGE_PATH, msg.getDataPath());
                                            intent.putExtra(UIKitConstants.CAMERA_VIDEO_PATH, msg.getDataUri());
                                            TUIKit.getAppContext().startActivity(intent);
                                        }
                                    });
                                }
                            });
                        } else {
                            registerVideoPlayClickListener(imgHolder.contentGroup, msg);
                        }
                    }
                }
                break;

            case MessageInfo.MSG_TYPE_AUDIO:
            case MessageInfo.MSG_TYPE_AUDIO + 1:
                // 音频
                final ChatAudioHolder audioHolder = (ChatAudioHolder) chatHolder;
                audioHolder.imgPlay.setImageResource(R.drawable.voice_msg_playing_3);
                final TIMSoundElem soundElem = (TIMSoundElem) msg.getTIMMessage().getElement(0);
                audioHolder.unread.setVisibility(msg.isSelf() || new TIMMessageExt(msg.getTIMMessage()).getCustomInt() == 1 ? View.GONE : View.VISIBLE);
                int duration = 1;
                if (soundElem != null) {
                    duration = (int) soundElem.getDuration();
                }
                if (duration == 0) {
                    duration = 1;
                }
                LinearLayout.LayoutParams audioParams = (LinearLayout.LayoutParams) chatHolder.dataView.getLayoutParams();
                audioParams.width = audio_min_width + UIUtils.getPxByDp(duration * 3.2F);
                if (audioParams.width > audio_max_width) {
                    audioParams.width = audio_max_width;
                }
                chatHolder.dataView.setLayoutParams(audioParams);
                audioHolder.time.setText(duration + "''");
                audioHolder.contentGroup.setOnClickListener(view -> {
                    if (UIKitAudioArmMachine.getInstance().isPlayingRecord()) {
                        UIKitAudioArmMachine.getInstance().stopPlayRecord();
                        return;
                    }

                    if (TextUtils.isEmpty(msg.getDataPath())) {
                        UIUtils.toastLongMessage("语音文件还未下载完成");
                        return;
                    }
                    Drawable drawable = getDrawable(R.drawable.play_voice_message);
                    audioHolder.imgPlay.setImageDrawable(drawable);
                    audioHolder.unread.setVisibility(View.GONE);
                    final AnimationDrawable animationDrawable = (AnimationDrawable) audioHolder.imgPlay.getDrawable();
                    new TIMMessageExt(msg.getTIMMessage()).setCustomInt(1);
                    animationDrawable.start();
                    UIKitAudioArmMachine.getInstance().playRecord(msg.getDataPath(), () -> audioHolder.imgPlay.post(() -> {
                        animationDrawable.stop();
                        Drawable drawable1 = getDrawable(R.drawable.voice_msg_playing_3);
                        audioHolder.imgPlay.setImageDrawable(drawable1);
                    }));

                    C2CChatManager.getInstance().sendUserReadMessage(msg);
                });
                break;

            case MessageInfo.MSG_TYPE_FILE:
            case MessageInfo.MSG_TYPE_FILE + 1:
                // 下载文件
                final ChatFileHolder fileHolder = (ChatFileHolder) chatHolder;
                final TIMFileElem fileElem = (TIMFileElem) msg.getTIMMessage().getElement(0);
                final String path = msg.getDataPath();
                fileHolder.fileName.setText(fileElem.getFileName());
                String size = FileUtil.FormetFileSize(fileElem.getFileSize());
                fileHolder.fileSize.setText(size);
                fileHolder.contentGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIUtils.toastLongMessage("文件路径:" + path);
                    }
                });
                if (msg.isSelf()) {

                    if (msg.getStatus() == MessageInfo.MSG_STATUS_SENDING) {
                        fileHolder.fileStatus.setText(R.string.sending);
                    } else if (msg.getStatus() == MessageInfo.MSG_STATUS_SEND_SUCCESS || msg.getStatus() == MessageInfo.MSG_STATUS_NORMAL) {
                        fileHolder.fileStatus.setText(R.string.sended);
                    }


                } else {
                    if (msg.getStatus() == MessageInfo.MSG_STATUS_DOWNLOADING) {
                        fileHolder.fileStatus.setText(R.string.downloading);
                    } else if (msg.getStatus() == MessageInfo.MSG_STATUS_DOWNLOADED) {
                        fileHolder.fileStatus.setText(R.string.downloaded);
                    } else if (msg.getStatus() == MessageInfo.MSG_STATUS_UN_DOWNLOAD) {
                        fileHolder.fileStatus.setText(R.string.un_download);
                        fileHolder.contentGroup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                msg.setStatus(MessageInfo.MSG_STATUS_DOWNLOADING);
                                fileHolder.fileStatus.setText(R.string.downloading);
                                fileElem.getToFile(path, new TIMCallBack() {
                                    @Override
                                    public void onError(int code, String desc) {
                                        UIUtils.toastLongMessage("getToFile fail:" + code + "=" + desc);
                                        chatHolder.progress.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onSuccess() {
                                        //FileUtil.reNameFile(new File(path), fileElem.getFileName());
                                        msg.setDataPath(path);
                                        fileHolder.fileStatus.setText(R.string.downloaded);
                                        msg.setStatus(MessageInfo.MSG_STATUS_DOWNLOADED);
                                        chatHolder.progress.setVisibility(View.GONE);
                                        fileHolder.contentGroup.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                UIUtils.toastLongMessage("文件路径:" + path);

                                            }
                                        });
                                    }
                                });
                            }
                        });

                    }
                }
                break;

            case MessageInfo.MSG_TYPE_PK_INVITE:
            case MessageInfo.MSG_TYPE_PK_INVITE + 1: // PK的题目选择
                final ChatPKHolder pkHolder = (ChatPKHolder) chatHolder;
                pkHolder.layoutContentOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<String, String> updateMap = new HashMap<String, String>();
                        updateMap.put(timMsg.getMsgId(), "1");
                        CustomizeMessage.send(msg.getPeer(), CustomizeMessage.MESSAGE_PK_PICK, updateMap, "", null);

                        if (timMsg.getCustomInt() == CustomizeMessage.PKPick.PICK_NULL.ordinal()) {
                            timMsg.setCustomInt(CustomizeMessage.PKPick.MY_PICK_ONE.ordinal());
                        } else if (timMsg.getCustomInt() == CustomizeMessage.PKPick.USER_PICK_ONE.ordinal()) {
                            timMsg.setCustomInt(CustomizeMessage.PKPick.PICK_SAME_ONE.ordinal());
                        } else if (timMsg.getCustomInt() == CustomizeMessage.PKPick.USER_PICK_TWO.ordinal()) {
                            timMsg.setCustomInt(CustomizeMessage.PKPick.PICK_DIFF_ONE.ordinal());
                        }

                        notifyItemChanged(position); // 刷新
                    }
                });
                pkHolder.layoutContentTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<String, String> updateMap = new HashMap<String, String>();
                        updateMap.put(timMsg.getMsgId(), "2");
                        CustomizeMessage.send(msg.getPeer(), CustomizeMessage.MESSAGE_PK_PICK, updateMap, "", null);

                        if (timMsg.getCustomInt() == CustomizeMessage.PKPick.PICK_NULL.ordinal()) {
                            timMsg.setCustomInt(CustomizeMessage.PKPick.MY_PICK_TWO.ordinal());
                        } else if (timMsg.getCustomInt() == CustomizeMessage.PKPick.USER_PICK_ONE.ordinal()) {
                            timMsg.setCustomInt(CustomizeMessage.PKPick.PICK_DIFF_TWO.ordinal());
                        } else if (timMsg.getCustomInt() == CustomizeMessage.PKPick.USER_PICK_TWO.ordinal()) {
                            timMsg.setCustomInt(CustomizeMessage.PKPick.PICK_SAME_TWO.ordinal());
                        }

                        notifyItemChanged(position);
                    }
                });

                if (msg.getLocalTag() != null) {
                    QuestionModel model = (QuestionModel) msg.getLocalTag();
                    pkHolder.tvPKTitle.setText(model.getQuestion());
                    pkHolder.tvUserInvite.setText(msg.isSelf() ? "你发出邀请" : "向你发出邀请");

                    pkHolder.ivOptionOne.setVisibility(model.isPicQuestion() ? View.VISIBLE : View.INVISIBLE);
                    pkHolder.ivOptionTwo.setVisibility(model.isPicQuestion() ? View.VISIBLE : View.INVISIBLE);
                    pkHolder.viewOptionOneBg.setVisibility(model.isPicQuestion() ? View.INVISIBLE : View.VISIBLE);
                    pkHolder.viewOptionTwoBg.setVisibility(model.isPicQuestion() ? View.INVISIBLE : View.VISIBLE);

                    if (model.isPicQuestion()) {
                        ImageLoader.showImage(pkHolder.ivOptionOne, model.getOptionOne(), cn.kuwo.pp.R.drawable.image_rounded_placeholder);
                        ImageLoader.showImage(pkHolder.ivOptionTwo, model.getOptionTwo(), cn.kuwo.pp.R.drawable.image_rounded_placeholder);
                    } else {
                        pkHolder.layoutContentOne.setBackgroundColor(Color.parseColor("#C273DE"));
                        pkHolder.layoutContentTwo.setBackgroundColor(Color.parseColor("#6E96FF"));

                        pkHolder.tvOptionOne.setText(model.getOptionOne());
                        pkHolder.tvOptionTwo.setText(model.getOptionTwo());
                    }
                } else {
                    mFragment.getQuestionDetail(position, msg);
                }

                if (timMsg.getCustomInt() == CustomizeMessage.PKPick.PICK_NULL.ordinal()) {
                    pkHolder.tvPKStatus.setText("点击图片选择");

                    pkHolder.viewAnswerOnePercent.setVisibility(View.INVISIBLE);
                    pkHolder.viewAnswerTwoPercent.setVisibility(View.INVISIBLE);
                } else if (timMsg.getCustomInt() == CustomizeMessage.PKPick.MY_PICK_ONE.ordinal() ||
                        timMsg.getCustomInt() == CustomizeMessage.PKPick.MY_PICK_TWO.ordinal()) {
                    pkHolder.tvPKStatus.setText("等待对方选择");

                    pkHolder.tvAnswerOnePercent.setTextSize(14);
                    pkHolder.tvAnswerTwoPercent.setTextSize(14);
                    pkHolder.tvAnswerOnePercent.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    pkHolder.tvAnswerTwoPercent.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                    if (timMsg.getCustomInt() == CustomizeMessage.PKPick.MY_PICK_ONE.ordinal()) {
                        pkHolder.viewAnswerOnePercent.setVisibility(View.VISIBLE);
                        pkHolder.viewAnswerOnePercent.setBackgroundColor(Color.parseColor("#CCFFE43F"));
                        pkHolder.ivAnswerOneCheck.setVisibility(View.VISIBLE);
                        pkHolder.tvAnswerOnePercent.setText("你已选择");

                        pkHolder.viewAnswerTwoPercent.setVisibility(View.INVISIBLE);
                        pkHolder.ivAnswerTwoCheck.setVisibility(View.GONE);
                        pkHolder.tvAnswerTwoPercent.setText("");
                    } else {
                        pkHolder.viewAnswerTwoPercent.setVisibility(View.VISIBLE);
                        pkHolder.viewAnswerTwoPercent.setBackgroundColor(Color.parseColor("#CCFFE43F"));
                        pkHolder.ivAnswerTwoCheck.setVisibility(View.VISIBLE);
                        pkHolder.tvAnswerTwoPercent.setText("你已选择");

                        pkHolder.viewAnswerOnePercent.setVisibility(View.INVISIBLE);
                        pkHolder.ivAnswerOneCheck.setVisibility(View.GONE);
                        pkHolder.tvAnswerOnePercent.setText("");
                    }
                } else if (timMsg.getCustomInt() == CustomizeMessage.PKPick.USER_PICK_ONE.ordinal() ||
                        timMsg.getCustomInt() == CustomizeMessage.PKPick.USER_PICK_TWO.ordinal()) {
                    pkHolder.tvPKStatus.setText("对方已选择，只差你了哦～");

                    pkHolder.viewAnswerOnePercent.setVisibility(View.INVISIBLE);
                    pkHolder.ivAnswerOneCheck.setVisibility(View.GONE);
                    pkHolder.tvAnswerOnePercent.setText("");

                    pkHolder.viewAnswerTwoPercent.setVisibility(View.INVISIBLE);
                    pkHolder.ivAnswerTwoCheck.setVisibility(View.GONE);
                    pkHolder.tvAnswerTwoPercent.setText("");
                } else if (timMsg.getCustomInt() == CustomizeMessage.PKPick.PICK_SAME_ONE.ordinal() ||
                        timMsg.getCustomInt() == CustomizeMessage.PKPick.PICK_SAME_TWO.ordinal()) {
                    pkHolder.tvPKStatus.setText("你俩的品味出奇的相似呢～");

                    pkHolder.tvAnswerOnePercent.setTextSize(24);
                    pkHolder.tvAnswerTwoPercent.setTextSize(24);
                    pkHolder.tvAnswerOnePercent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    pkHolder.tvAnswerTwoPercent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                    if (timMsg.getCustomInt() == CustomizeMessage.PKPick.PICK_SAME_ONE.ordinal()) {
                        pkHolder.viewAnswerOnePercent.setVisibility(View.VISIBLE);
                        pkHolder.viewAnswerOnePercent.setBackgroundColor(Color.parseColor("#CCFFE43F"));
                        pkHolder.ivAnswerOneCheck.setVisibility(View.VISIBLE);
                        pkHolder.tvAnswerOnePercent.setText("我&TA");

                        pkHolder.viewAnswerTwoPercent.setVisibility(View.INVISIBLE);
                        pkHolder.ivAnswerTwoCheck.setVisibility(View.GONE);
                        pkHolder.tvAnswerTwoPercent.setText("");
                    } else {
                        pkHolder.viewAnswerTwoPercent.setVisibility(View.VISIBLE);
                        pkHolder.viewAnswerTwoPercent.setBackgroundColor(Color.parseColor("#CCFFE43F"));
                        pkHolder.ivAnswerTwoCheck.setVisibility(View.VISIBLE);
                        pkHolder.tvAnswerTwoPercent.setText("我&TA");

                        pkHolder.viewAnswerOnePercent.setVisibility(View.INVISIBLE);
                        pkHolder.ivAnswerOneCheck.setVisibility(View.GONE);
                        pkHolder.tvAnswerOnePercent.setText("");
                    }

                } else if (timMsg.getCustomInt() == CustomizeMessage.PKPick.PICK_DIFF_ONE.ordinal() ||
                        timMsg.getCustomInt() == CustomizeMessage.PKPick.PICK_DIFF_TWO.ordinal()) {
                    pkHolder.tvPKStatus.setText("不同的观点要不要battle一下？");

                    pkHolder.tvAnswerOnePercent.setTextSize(24);
                    pkHolder.tvAnswerTwoPercent.setTextSize(24);
                    pkHolder.tvAnswerOnePercent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    pkHolder.tvAnswerTwoPercent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                    if (timMsg.getCustomInt() == CustomizeMessage.PKPick.PICK_DIFF_ONE.ordinal()) {
                        pkHolder.viewAnswerOnePercent.setVisibility(View.VISIBLE);
                        pkHolder.viewAnswerOnePercent.setBackgroundColor(Color.parseColor("#CCFFE43F"));
                        pkHolder.ivAnswerOneCheck.setVisibility(View.GONE);
                        pkHolder.tvAnswerOnePercent.setText("我");

                        pkHolder.viewAnswerTwoPercent.setVisibility(View.VISIBLE);
                        pkHolder.viewAnswerTwoPercent.setBackgroundColor(Color.parseColor("#CC1FD0B8"));
                        pkHolder.ivAnswerTwoCheck.setVisibility(View.GONE);
                        pkHolder.tvAnswerTwoPercent.setText("TA");
                    } else {
                        pkHolder.viewAnswerOnePercent.setVisibility(View.VISIBLE);
                        pkHolder.viewAnswerOnePercent.setBackgroundColor(Color.parseColor("#CC1FD0B8"));
                        pkHolder.ivAnswerOneCheck.setVisibility(View.GONE);
                        pkHolder.tvAnswerOnePercent.setText("TA");

                        pkHolder.viewAnswerTwoPercent.setVisibility(View.VISIBLE);
                        pkHolder.viewAnswerTwoPercent.setBackgroundColor(Color.parseColor("#CCFFE43F"));
                        pkHolder.ivAnswerTwoCheck.setVisibility(View.GONE);
                        pkHolder.tvAnswerTwoPercent.setText("我");
                    }
                }
                break;
        }


        if (mRecycleView.getNameColor() != 0) {
            chatHolder.userName.setTextColor(mRecycleView.getNameColor());
        }
        if (mRecycleView.getNameSize() != 0) {
            chatHolder.userName.setTextSize(mRecycleView.getNameSize());
        }

        if (msg.isGroup()) {
            chatHolder.userName.setVisibility(View.VISIBLE);
            chatHolder.userName.setText(msg.getFromUser());
        } else {
            chatHolder.userName.setVisibility(View.GONE);
        }
        if (msg.getStatus() == MessageInfo.MSG_STATUS_SEND_FAIL) {
            chatHolder.status.setVisibility(View.VISIBLE);
            chatHolder.status.setImageResource(R.drawable.message_send_fail);
        } else {
            chatHolder.status.setVisibility(View.GONE);
        }


        if (msg.getMsgType() == MessageInfo.MSG_TYPE_PK_INVITE) {
            chatHolder.status.setVisibility(View.GONE);
        } else {
            if (msg.isSelf() && msg.getTIMMessage().isPeerReaded()) {
                chatHolder.status.setVisibility(View.VISIBLE);
                chatHolder.status.setImageResource(R.drawable.chat_readed);
            } else {
                chatHolder.status.setVisibility(View.GONE);
            }
        }

        if (msg.getStatus() == MessageInfo.MSG_STATUS_SENDING || msg.getStatus() == MessageInfo.MSG_STATUS_DOWNLOADING) {
            if (chatHolder.progress != null) {
                chatHolder.progress.setVisibility(View.VISIBLE);
            }
        } else {
            if (chatHolder.progress != null) {
                chatHolder.progress.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecycleView = (ChatListView) recyclerView;
    }

    public void showLoading() {
        if (mLoading) {
            return;
        }
        mLoading = true;
        notifyItemChanged(0);
    }

    public void setDataSourceListener(SetDataSourceListener dataSourceListener) {
        mDataSourceListener = dataSourceListener;
    }

    @Override
    public void notifyDataSetChanged(final int type, final int value) {
        BackgroundTasks.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoading = false;
                if (type == ChatListView.DATA_CHANGE_TYPE_REFRESH) {
                    notifyDataSetChanged();
                    mRecycleView.scrollToEnd();
                } else if (type == ChatListView.DATA_CHANGE_TYPE_ADD_BACK) {
                    notifyItemRangeInserted(mDataSource.size() + 1, value);
                    mRecycleView.scrollToEnd();
                } else if (type == ChatListView.DATA_CHANGE_TYPE_UPDATE) {
                    notifyItemChanged(value + 1);
                } else if (type == ChatListView.DATA_CHANGE_TYPE_LOAD || type == ChatListView.DATA_CHANGE_TYPE_ADD_FRONT) {
                    //加载条目为数0，只更新动画
                    if (value == 0) {
                        notifyItemChanged(0);
                    } else {
                        //加载过程中有可能之前第一条与新加载的最后一条的时间间隔不超过5分钟，时间条目需去掉，所以这里的刷新要多一个条目
                        if (getItemCount() > value) {
                            notifyItemRangeInserted(0, value);
                        } else {
                            notifyItemRangeInserted(0, value);
                        }
                    }
                } else if (type == ChatListView.DATA_CHANGE_TYPE_DELETE) {
                    notifyItemRemoved(value + 1);
                    notifyDataSetChanged();
                    mRecycleView.scrollToEnd();
                }
            }
        }, 100);
    }


    private void registerVideoPlayClickListener(View view, final MessageInfo msg) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TUIKit.getAppContext(), VideoViewActivity.class);
                intent.putExtra(UIKitConstants.CAMERA_IMAGE_PATH, msg.getDataPath());
                intent.putExtra(UIKitConstants.CAMERA_VIDEO_PATH, msg.getDataUri());
                TUIKit.getAppContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSource.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return headerViewType;
        }
        MessageInfo msg = getItem(position);
        return msg.isSelf() ? msg.getMsgType() + 1 : msg.getMsgType();
    }

    @Override
    public void setDataSource(IChatProvider provider) {
        this.mDataSource = provider.getDataSource();
        provider.attachAdapter(this);
        notifyDataSetChanged(ChatListView.DATA_CHANGE_TYPE_REFRESH, getItemCount());
        if (mDataSourceListener != null) {
            mDataSourceListener.onSetDataSource();
        }
    }

    @Override
    public MessageInfo getItem(int position) {
        if (position == 0 || mDataSource.size() == 0) {
            return null;
        }
        MessageInfo info = mDataSource.get(position - 1);
        if (mInterceptor != null) {
            mInterceptor.intercept(info);
        }
        return info;
    }

    class BaseChatHolder extends RecyclerView.ViewHolder {
        protected ImageView userIcon;
        protected ImageView status;
        protected TextView userName, chatTime;
        protected ViewGroup contentGroup, dataView;
        protected ProgressBar progress;
        protected View rootView;

        public BaseChatHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            userName = itemView.findViewById(R.id.tv_user_name);
            chatTime = itemView.findViewById(R.id.chat_time);
            userIcon = itemView.findViewById(R.id.iv_user_icon);
            contentGroup = itemView.findViewById(R.id.ll_content_group);
            dataView = itemView.findViewById(R.id.ll_msg_data_group);
            status = itemView.findViewById(R.id.message_status);
            progress = itemView.findViewById(R.id.message_sending);
            if (progress != null) {
                progress.setAlpha(0.4f);  //为了适应UI颜色
            }
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ChatTextHolder extends BaseChatHolder {
        private TextView msg;
        private TextView greet;
        private ViewGroup chatInfoContent;

        public ChatTextHolder(View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.tv_user_msg);
            greet = itemView.findViewById(R.id.tv_greet);
            chatInfoContent = itemView.findViewById(R.id.chat_info_content);
        }
    }

    class ChatImageHolder extends BaseChatHolder {
        private ImageView imgData;
        private ImageView playBtn;
        private View cover;
        private RelativeLayout mDataGroup;
        private TextView mDuration;

        public ChatImageHolder(View itemView) {
            super(itemView);
            imgData = itemView.findViewById(R.id.iv_user_image);
            playBtn = itemView.findViewById(R.id.video_play_btn);
            cover = itemView.findViewById(R.id.video_un_download_cover);
            mDataGroup = itemView.findViewById(R.id.image_data_group);
            mDuration = itemView.findViewById(R.id.video_duration);
        }
    }

    class ChatAudioHolder extends BaseChatHolder {
        private ImageView imgPlay;
        private ImageView unread;
        private TextView time;


        public ChatAudioHolder(View itemView) {
            super(itemView);
            imgPlay = itemView.findViewById(R.id.audio_play);
            unread = itemView.findViewById(R.id.unread_flag);
            time = itemView.findViewById(R.id.audio_time);
        }
    }

    class ChatFileHolder extends BaseChatHolder {
        private ImageView fileIcon;
        private TextView fileName, fileSize, fileStatus;

        public ChatFileHolder(View itemView) {
            super(itemView);
            fileIcon = itemView.findViewById(R.id.file_image);
            fileName = itemView.findViewById(R.id.file_name);
            fileSize = itemView.findViewById(R.id.file_size);
            fileStatus = itemView.findViewById(R.id.file_status);
        }
    }

    class ChatTipsHolder extends BaseChatHolder {

        private TextView tips;

        public ChatTipsHolder(View itemView) {
            super(itemView);
            chatTime = itemView.findViewById(R.id.chat_time);
            tips = itemView.findViewById(R.id.chat_tips);
        }
    }

    class ChatPKHolder extends BaseChatHolder {

        private ConstraintLayout layoutTotalContent;
        private ConstraintLayout layoutContentOne;
        private ConstraintLayout layoutContentTwo;
        private TextView tvPKStatus;
        private TextView tvPKTitle;
        private TextView tvUserInvite;

        private ImageView ivOptionOne;
        private ImageView ivOptionTwo;
        private TextView tvOptionOne;
        private TextView tvOptionTwo;

        private RelativeLayout viewOptionOneBg;
        private RelativeLayout viewOptionTwoBg;

        private FrameLayout viewAnswerOnePercent;
        private FrameLayout viewAnswerTwoPercent;

        private TextView tvAnswerOnePercent;
        private TextView tvAnswerTwoPercent;

        private ImageView ivAnswerOneCheck;
        private ImageView ivAnswerTwoCheck;

        public ChatPKHolder(View itemView) {
            super(itemView);
            layoutTotalContent = itemView.findViewById(R.id.layoutTotalContent);
            layoutContentOne = itemView.findViewById(R.id.layoutContentOne);
            layoutContentTwo = itemView.findViewById(R.id.layoutContentTwo);
            tvPKTitle = itemView.findViewById(R.id.tvPKTitle);
            tvPKStatus = itemView.findViewById(R.id.tvPKStatus);
            tvUserInvite = itemView.findViewById(R.id.tvUserInvite);

            ivOptionOne = itemView.findViewById(R.id.ivOptionOne);
            ivOptionTwo = itemView.findViewById(R.id.ivOptionTwo);
            tvOptionOne = itemView.findViewById(R.id.tvOptionOne);
            tvOptionTwo = itemView.findViewById(R.id.tvOptionTwo);

            viewOptionOneBg = itemView.findViewById(R.id.viewOptionOneBg);
            viewOptionTwoBg = itemView.findViewById(R.id.viewOptionTwoBg);

            viewAnswerOnePercent = itemView.findViewById(R.id.viewAnswerOnePercent);
            viewAnswerTwoPercent = itemView.findViewById(R.id.viewAnswerTwoPercent);

            tvAnswerOnePercent = itemView.findViewById(R.id.tvAnswerOnePercent);
            tvAnswerTwoPercent = itemView.findViewById(R.id.tvAnswerTwoPercent);

            ivAnswerOneCheck = itemView.findViewById(R.id.ivAnswerOneCheck);
            ivAnswerTwoCheck = itemView.findViewById(R.id.ivAnswerTwoCheck);
        }
    }

    public interface SetDataSourceListener {
        public void onSetDataSource();
    }
}