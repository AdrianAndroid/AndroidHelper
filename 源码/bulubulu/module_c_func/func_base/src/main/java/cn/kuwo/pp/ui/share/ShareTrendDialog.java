package cn.kuwo.pp.ui.share;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.uikit.common.utils.ScreenUtil;


import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.pp.ui.world.adapter.PersonImageAdapter;

public class ShareTrendDialog extends ShareCardDialog {

    private UserTrendBean mModel;

    public static ShareTrendDialog newInstance(UserTrendBean model) {
        ShareTrendDialog dialog = new ShareTrendDialog(model);
        return dialog;
    }

    public ShareTrendDialog(UserTrendBean model) {
        mModel = model;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_share_trend, container, false);
//        _initView(view);
        _initView(view);
        return view;
    }

    @Override
    public String getShareId() {
        return mModel.getId();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.view_share_user_trend;
    }

    @Override
    public void setPreviewInfo(View rootView, boolean sharePic) {
        TextView tvUserName = rootView.findViewById(R.id.tvUserName);
        ImageView ivUserHeader = rootView.findViewById(R.id.ivUserHeader);
        TextView tvContent = rootView.findViewById(R.id.tvContent);
        RecyclerView rv_image = rootView.findViewById(R.id.rv_image);

        if (mModel.getUser() != null) {
            ImageLoader.showCircleImage(ivUserHeader, mModel.getUser().getHeadImg(), mModel.getUser().getNewDefaultHeadImage());
            tvUserName.setText(mModel.getUser().getName());
        }

        tvContent.setText(mModel.getText());

        if (mModel.getPicIds() == null) {
            rv_image.setVisibility(View.GONE);
        } else {
            if (sharePic) {
                RecyclerView rvDialogImage = getView().getRootView().findViewById(R.id.rv_image);
                rv_image.getLayoutParams().height = (int) (((float) rvDialogImage.getMeasuredHeight() / (float) rvDialogImage.getMeasuredWidth()) * (float) ScreenUtil.getScreenWidth(getContext()));
                Bitmap bgBitmap = Bitmap.createBitmap(rvDialogImage.getMeasuredWidth(), rvDialogImage.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                rvDialogImage.draw(new Canvas(bgBitmap));
                rv_image.setBackground(UtilsCode.INSTANCE.bitmap2Drawable(bgBitmap));
            } else {
                PersonImageAdapter adapter = new PersonImageAdapter(mModel.getPicIds());
                adapter.bindToRecyclerView(rv_image);
                rv_image.setAdapter(adapter);

                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 12);
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        switch (mModel.getPicIds().size()) {
                            case 1:
                                return 12;
                            case 2:
                            case 4:
                                return 6;
                            case 3:
                            case 6:
                            case 9:
                                return 4;
                            case 5: {
                                if (position < 2) {
                                    return 6;
                                } else {
                                    return 4;
                                }
                            }
                            case 7: {
                                if (position < 3) {
                                    return 4;
                                } else {
                                    return 3;
                                }
                            }
                            case 8:
                                return 3;
                        }

                        return 4;
                    }
                });
                rv_image.setLayoutManager(layoutManager);
                rv_image.invalidate();
            }
        }
    }
}
