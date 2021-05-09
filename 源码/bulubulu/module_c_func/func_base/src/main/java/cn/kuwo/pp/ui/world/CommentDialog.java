package cn.kuwo.pp.ui.world;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.qcloud.uikit.common.utils.SoftKeyBoardUtil;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;

import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.dialog.BaseBottomDialog;
import cn.kuwo.common.util.CustomLoadMoreView;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.common.view.MultipleStatusView;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.QuestionModel;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.pp.http.bean.comment.CommentBean;
import cn.kuwo.pp.http.bean.comment.CommentItem;
import cn.kuwo.pp.http.bean.user.UserInfo;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.ui.mine.UserInfoFragment;
import cn.kuwo.pp.ui.world.adapter.CommentItemAdapter;

/**
 * 评论
 */
public class CommentDialog extends BaseBottomDialog {
    public static String KEY_COMMENT_SOURCE = "comment_source";
    public static String KEY_COMMENT_SOURCE_ID = "comment_source_id";


    interface OnUserCallBackListener {
        void onUserCallback(UserInfo userInfo);
    }

    private RecyclerView recyclerView;
    private TextView tvTitle;
    private ImageView ivClose;
    private View viewClose;
    private EditText editComment;
    private MultipleStatusView statusView;

    private void _initView(View view) {
        recyclerView = view.findViewById(R.id.rv_comments);
        tvTitle = view.findViewById(R.id.tv_title);
        ivClose = view.findViewById(R.id.iv_close);
        viewClose = view.findViewById(R.id.viewClose);
        editComment = view.findViewById(R.id.edPublishComment);
        statusView = view.findViewById(R.id.multipleStatusView);
    }

    private CommentPresenter mPresenter;
    private CommentItemAdapter mAdapter;
    private int mSourceType;
    private String mSourceId;
    private int mStartPage = 1;
    private OnUserCallBackListener listener;

    public static CommentDialog newInstance(UserTrendBean bean, BaseFragment parent) {
        CommentDialog dialog = new CommentDialog();
        WeakReference<BaseFragment> baseFragmentWeakReference = new WeakReference<>(parent);
        dialog.setListener(new OnUserCallBackListener() {
            @Override
            public void onUserCallback(UserInfo userInfo) {
                dialog.dismiss();
                BaseFragment baseFragment = baseFragmentWeakReference.get();
                if (baseFragment != null) {
                    baseFragment.start(UserInfoFragment.newInstance(null, userInfo));
                }
            }
        });

        Bundle args = new Bundle();
        args.putInt(KEY_COMMENT_SOURCE, 1);
        args.putString(KEY_COMMENT_SOURCE_ID, bean.getId());

        dialog.setArguments(args);
        return dialog;
    }

    public static CommentDialog newInstance(QuestionModel model, BaseFragment parent) {
        CommentDialog dialog = new CommentDialog();
        WeakReference<BaseFragment> baseFragmentWeakReference = new WeakReference<>(parent);
        dialog.setListener(new OnUserCallBackListener() {
            @Override
            public void onUserCallback(UserInfo userInfo) {
                dialog.dismiss();
                BaseFragment baseFragment = baseFragmentWeakReference.get();
                if (baseFragment != null) {
                    baseFragment.start(UserInfoFragment.newInstance(null, userInfo));
                }
            }
        });

        Bundle args = new Bundle();
        args.putInt(KEY_COMMENT_SOURCE, Integer.parseInt(model.getSourceType()));
        args.putString(KEY_COMMENT_SOURCE_ID, model.getId());

        dialog.setArguments(args);
        return dialog;
    }


    public void setListener(OnUserCallBackListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_comment, container, false);
        _initView(view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSourceType = getArguments().getInt(KEY_COMMENT_SOURCE);
        mSourceId = getArguments().getString(KEY_COMMENT_SOURCE_ID);

        mAdapter = new CommentItemAdapter(null);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        tvTitle.setText("评论 (" + mAdapter.getItemCount() + ")");
        initListeners();

        statusView.showLoading();

        mPresenter = new CommentPresenter(this);
        mPresenter.getComment(mSourceType, mSourceId, mStartPage);
    }

    private void initListeners() {
        viewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        editComment.setHorizontallyScrolling(false);
        editComment.setMaxLines(Integer.MAX_VALUE);
        editComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (editComment.getText().toString().length() == 0) {
                        return true;
                    }
                    mPresenter.sendComment(mSourceType, mSourceId, editComment.getText().toString());

                    statusView.setVisibility(View.GONE);
                    mAdapter.addData(0, genNewComment(editComment.getText().toString()));
                    mAdapter.notifyDataSetChanged();

                    UtilsCode.INSTANCE.showShort("发表评论成功");
                    SoftKeyBoardUtil.hideKeyBoard(editComment);
                    editComment.setText("");

                    return true;
                }
                return false;
            }
        });

        statusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusView.showLoading();
                mPresenter.getComment(mSourceType, mSourceId, mStartPage);
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CommentItem item = mAdapter.getItem(position);
                if (view.getId() == R.id.ivUserHeader && listener != null) {
                    assert item != null;
                    listener.onUserCallback(item.getUserInfo());
                } else if (view.getId() == R.id.ivPraise) {
                    assert item != null;
                    if (item.getLiked() == 1) {
                        mPresenter.likeComment(mSourceType, mSourceId, item, position, false);
                    } else {
                        mPresenter.likeComment(mSourceType, mSourceId, item, position, true);
                    }
                }
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mStartPage > 1) {
                    mPresenter.getComment(mSourceType, mSourceId, mStartPage);
                }
            }
        }, recyclerView);
    }

    public void onGetComment(boolean success, int pn, CommentBean o) {
        if (success) {
            if (o.getComments() != null && o.getComments().size() > 0) {
                if (pn == 1) {
                    mAdapter.replaceData(o.getComments());
                    mStartPage = pn;
                } else {
                    mAdapter.addData(o.getComments());
                }
                mAdapter.notifyDataSetChanged();

                if (o.isMore()) {
                    mStartPage++;
                } else {
                    mAdapter.loadMoreEnd();
                }

                statusView.setVisibility(View.GONE);
                tvTitle.setText("评论 (" + mAdapter.getData().size() + ")");
            } else if (pn == 1) {
                statusView.setEmptyViewResId(R.layout.look_for_friend_empty);
                statusView.showEmpty();
            } else {
                mAdapter.loadMoreEnd();
            }
        } else {
            UtilsCode.INSTANCE.showShort("获取评论失败");
            statusView.showError("获取评论失败");
        }
    }

    public void onLikeComment(int position, CommentItem item, boolean isLike) {
        if (isLike) {
            item.setLiked(1);
            UtilsCode.INSTANCE.showShort("点赞评论成功");
        } else {
            item.setLiked(0);
            UtilsCode.INSTANCE.showShort("取消点赞评论成功");
        }

        mAdapter.notifyItemChanged(position);
    }

    private CommentItem genNewComment(String content) {
        try {
            content = URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }

        CommentItem item = new CommentItem();
        item.setUid(Integer.parseInt(UserInfoManager.INSTANCE.getUid()));
        item.setUserInfo(UserInfoManager.INSTANCE.getUserInfo());
        item.setTimes(System.currentTimeMillis());
        item.setContent(content);
        return item;
    }
}
