package cn.kuwo.pp.ui.publish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.barlibrary.ImmersionBar;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.shuyu.textutillib.RichEditText;
import com.shuyu.textutillib.model.TopicModel;
import com.shuyu.textutillib.model.UserModel;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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

/**
 * 分享心情
 */
public class PublishTrendFragment extends BaseFragment {

    private final BaseQuickAdapter<LocalMedia, BaseViewHolder> mAdapter = new BaseQuickAdapter<LocalMedia, BaseViewHolder>(R.layout.fragment_select_picture_item) {

        @Override
        public int getItemCount() {
            int max = Math.min(BuluPictureSelector.MAX_COUNT, super.getItemCount() + 1);
            return max;
        }

        @Override
        public int getItemViewType(int position) { // 全部走convert
            return 0;//return super.getItemViewType(position);
        }

        @Nullable
        @Override
        public LocalMedia getItem(int position) {
            return super.getItem(position);
        }

        @Override
        protected void convert(BaseViewHolder helper, LocalMedia item) {
            if (item == null) { // 就是最后一个，+号
                helper.setImageResource(R.id.image, R.drawable.select_pic_add_icon);
                helper.setGone(R.id.iv_delete, false);
            } else { // 正常的个数
                ImageLoader.showImage(helper.getView(R.id.image), item.getPath());
                helper.setGone(R.id.iv_delete, true);
            }
            helper.addOnClickListener(R.id.iv_delete);
            helper.addOnClickListener(R.id.image);
        }
    };

    private RichEditText tv_content;
    private SwipeRecyclerView recycler_view;

    private void _initView(View view) {
        tv_content = view.findViewById(R.id.tv_content);
        recycler_view = view.findViewById(R.id.recycler_view);
    }

    public static PublishTrendFragment newInstance() {
        return new PublishTrendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish_card, container, false);
        _initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initRecyclerView();

        tv_content.setModelList(new ArrayList<UserModel>(), new ArrayList<TopicModel>());
    }

    private void initRecyclerView() {
        recycler_view.setAdapter(mAdapter);
        recycler_view.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recycler_view.setLongPressDragEnabled(true);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (adapter.getItem(position) == null || view.getId() == R.id.image) {
                    // 点击选择新增
                    selectPicture();
                } else if (view.getId() == R.id.iv_delete) {
                    adapter.remove(position);
                }
            }
        });

        recycler_view.setOnItemMoveListener(new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                if (mAdapter.getItem(targetHolder.getAdapterPosition()) == null) {
                    return false;
                }
                int fromPosition = srcHolder.getAdapterPosition(); // 原位置
                int toPosition = targetHolder.getAdapterPosition(); // 目标位置
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i <= toPosition; i++) {
                        Collections.swap(mAdapter.getData(), i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i <= toPosition; i++) {
                        Collections.swap(mAdapter.getData(), i, i - 1);
                    }
                }

                mAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {

            }
        });
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        ImmersionBar.with(this).keyboardEnable(true).init();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        hideSoftInput();
    }

    private void selectPicture() {
        BuluPictureSelector.bulupicture(this, mAdapter.getData(), new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                mAdapter.replaceData(result);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Subscribe
    public void onUserPublishEvent(UserPublishEvent event) {
        if (event.getPage() != PublishMainFragment.PAGE_USER_TREND) {
            return;
        }

        if (tv_content.getText().toString().length() == 0 && mAdapter.getData().size() < 2) {
            UtilsCode.INSTANCE.showShort("请输入内容或者选择图片");
            return;
        }

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("text", tv_content.getText().toString())
                .addFormDataPart("visible", "3");

        showLoadingDialog("正在发布...");


        for (LocalMedia datum : mAdapter.getData()) {
            File file = new File(BuluPictureSelector.getCompressPath(datum));
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("file", file.getName(), imageBody);
        }

//        for (int i = 0; i < mAdapter.getData().size()-1; i++) {
//            File file = new File(mAdapter.getData().get(i).getCompressPath());
//            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            builder.addFormDataPart("file", file.getName(), imageBody);
//        }

        String topicIds = getActivityViewModel(PublishViewModel.class).getTopicIds();


        List<MultipartBody.Part> parts = builder.build().parts();

        Observable<BaseHttpResult<UserTrendBean>> observable = RetrofitClient.getApiService().publishUserTrend(UserInfoManager.INSTANCE.getUid(), topicIds, parts);
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
    public Class<?> getPrintClass() {
        return getClass();
    }
}
