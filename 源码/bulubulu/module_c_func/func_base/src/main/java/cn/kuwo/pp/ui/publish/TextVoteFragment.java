package cn.kuwo.pp.ui.publish;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.R;
import cn.kuwo.pp.event.UserPublishEvent;
import cn.kuwo.pp.event.UserPublishSuccessEvent;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.pp.manager.UserInfoManager;
import io.reactivex.Observable;
import okhttp3.MultipartBody;

public class TextVoteFragment extends BaseFragment {
    EditText edVoteTitle;
    EditText edOptionOne;
    EditText edOptionTwo;

    private void _initView(View view) {
        edVoteTitle = view.findViewById(R.id.edVoteTitle);
        edOptionOne = view.findViewById(R.id.edOptionOne);
        edOptionTwo = view.findViewById(R.id.edOptionTwo);
    }


    private int mEditHeight;

    public static TextVoteFragment newInstance() {
        return new TextVoteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_vote, container, false);
        _initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
    }

    private void initListener() {
        edVoteTitle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                edVoteTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mEditHeight = edVoteTitle.getHeight();
            }
        });

        edVoteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) edVoteTitle.getLayoutParams();
                if (edVoteTitle.getLineCount() <= 1) {
                    layoutParams.height = mEditHeight;
                    edVoteTitle.setLayoutParams(layoutParams);
                } else {
                    layoutParams.height = (mEditHeight * 3) / 2;
                    edVoteTitle.setLayoutParams(layoutParams);
                }
            }
        });
    }

    @Subscribe
    public void onUserPublishEvent(UserPublishEvent event) {
        if (event.getPage() != PublishMainFragment.PAGE_TEXT_VOTE) {
            return;
        }

        if (edVoteTitle.getText().toString().length() == 0) {
            UtilsCode.INSTANCE.showShort("请输入投票的标题");
            return;
        }
        if (edOptionOne.getText().toString().length() == 0) {
            UtilsCode.INSTANCE.showShort("请输入选项一的内容");
            return;
        }
        if (edOptionTwo.getText().toString().length() == 0) {
            UtilsCode.INSTANCE.showShort("请输入选项二的内容");
            return;
        }

        showLoadingDialog("正在发布...");

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("qtype", "1")
                .addFormDataPart("text", edVoteTitle.getText().toString())
                .addFormDataPart("optionOne", edOptionOne.getText().toString())
                .addFormDataPart("optionTwo", edOptionTwo.getText().toString())
                .addFormDataPart("visible", "3");

        List<MultipartBody.Part> parts = builder.build().parts();
//        String topic = "";
//        for(int i=0; i<mTopicList.size(); i++){
//            TopicItemBean bean = mTopicList.get(i);
//            topic += bean.getId();
//
//            if(i != mTopicList.size() - 1){
//                topic += ",";
//            }
//        }

        String topicIds = getActivityViewModel(PublishViewModel.class).getTopicIds();

        Observable<BaseHttpResult<UserTrendBean>> observable = RetrofitClient.getApiService().publishQuestion(UserInfoManager.INSTANCE.getUid(), topicIds, parts);
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<UserTrendBean>() {
                    @Override
                    public void onNext(UserTrendBean o) {
                        dismissLoadingDialog();
                        UtilsCode.INSTANCE.showLong("发布成功");
                        EventBus.getDefault().post(new UserPublishSuccessEvent(o));
                    }

                    @Override
                    public void _onError(ApiException e) {
                        dismissLoadingDialog();
                        UtilsCode.INSTANCE.showLong("发布失败");
                    }
                });
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
