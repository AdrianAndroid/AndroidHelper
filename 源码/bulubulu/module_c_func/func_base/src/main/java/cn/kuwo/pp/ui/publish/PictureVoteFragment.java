package cn.kuwo.pp.ui.publish;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.List;

import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.pictureselector.BuluPictureSelector;
import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.R;
import cn.kuwo.pp.event.UserPublishEvent;
import cn.kuwo.pp.event.UserPublishSuccessEvent;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.UserInfoManager;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PictureVoteFragment extends BaseFragment {
    private int SELECT_PICTURE_RESULT_ONE = 10000;
    private int SELECT_PICTURE_RESULT_TWO = 10001;

    CardView cardViewOne;
    CardView cardViewTwo;
    EditText edVoteTitle;
    ImageView ivOptionOne;
    ImageView ivOptionTwo;

    private void _initView(View view) {
        cardViewOne = view.findViewById(R.id.cardViewOptionOneBg);
        cardViewTwo = view.findViewById(R.id.cardViewOptionTwoBg);
        edVoteTitle = view.findViewById(R.id.edVoteTitle);
        ivOptionOne = view.findViewById(R.id.ivOptionOne);
        ivOptionTwo = view.findViewById(R.id.ivOptionTwo);
    }

    private String mPictureOne;
    private String mPictureTwo;
    private int mEditHeight;

    public static PictureVoteFragment newInstance() {
        return new PictureVoteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture_vote, container, false);
        _initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
    }

    private void selectPicture(final int resultCode) {

        BuluPictureSelector.bulupicture_2(this, null, resultCode, new BuluPictureSelector.BOnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(int requestCode, List<LocalMedia> selectList) {
                if (selectList == null) return;
                // 显示两个
                if (selectList.size() == 1) {
                    String picturePath = selectList.get(0).getCompressPath();
                    if (requestCode == SELECT_PICTURE_RESULT_ONE) {
                        ImageLoader.showImage(ivOptionOne, picturePath);
                        mPictureOne = picturePath;
                    } else {
                        ImageLoader.showImage(ivOptionTwo, picturePath);
                        mPictureTwo = picturePath;
                    }
                } else if (selectList.size() == 2) {
                    String picturePath = selectList.get(0).getCompressPath();
                    ImageLoader.showImage(ivOptionOne, picturePath);
                    mPictureOne = picturePath;

                    picturePath = selectList.get(1).getCompressPath();
                    ImageLoader.showImage(ivOptionTwo, picturePath);
                    mPictureTwo = picturePath;
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//        if (requestCode != SELECT_PICTURE_RESULT_ONE && requestCode != SELECT_PICTURE_RESULT_TWO) {
//            return;
//        }
//
//        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//        if (selectList.size() == 0) {
//            return;
//        }
//
//        if (selectList.size() == 1) {
//            String picturePath = selectList.get(0).getPath();
//            if (requestCode == SELECT_PICTURE_RESULT_ONE) {
//                ImageLoader.showImage(ivOptionOne, picturePath);
//                mPictureOne = picturePath;
//            } else {
//                ImageLoader.showImage(ivOptionTwo, picturePath);
//                mPictureTwo = picturePath;
//            }
//        } else if (selectList.size() == 2) {
//            String picturePath = selectList.get(0).getPath();
//            ImageLoader.showImage(ivOptionOne, picturePath);
//            mPictureOne = picturePath;
//
//            picturePath = selectList.get(1).getPath();
//            ImageLoader.showImage(ivOptionTwo, picturePath);
//            mPictureTwo = picturePath;
//        }
//    }

    @Subscribe
    public void onUserPublishEvent(UserPublishEvent event) {
        if (event.getPage() != PublishMainFragment.PAGE_PICTURE_VOTE) {
            return;
        }

        if (edVoteTitle.getText().toString().length() == 0) {
            UtilsCode.INSTANCE.showShort("请输入投票的标题");
            return;
        }
        if (mPictureOne == null) {
            UtilsCode.INSTANCE.showShort("请选择选项一的图片");
            return;
        }
        if (mPictureTwo == null) {
            UtilsCode.INSTANCE.showShort("请选择选项二的图片");
            return;
        }

        showLoadingDialog("正在发布...");

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("qtype", "2")
                .addFormDataPart("text", edVoteTitle.getText().toString())
                .addFormDataPart("visible", "3");

        File file = new File(mPictureOne);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", file.getName(), imageBody);

        file = new File(mPictureTwo);
        imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", file.getName(), imageBody);

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


        List<MultipartBody.Part> parts = builder.build().parts();
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

    private void initListener() {
        cardViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture(SELECT_PICTURE_RESULT_ONE);
            }
        });

        cardViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture(SELECT_PICTURE_RESULT_TWO);
            }
        });

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

    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
