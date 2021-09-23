package cn.kuwo.pp.ui.share;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.uikit.common.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import cn.kuwo.common.util.CustomLoadMoreView;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.pp.ui.world.adapter.HotListAdapter;

public class ShareChallengeDialog extends ShareCardDialog {

    //    private View mContentView;
    private ArrayList<UserTrendBean> mDataList = new ArrayList<UserTrendBean>();

    public static ShareChallengeDialog newInstance(View view, List<UserTrendBean> list) {
        return new ShareChallengeDialog(view, list);
    }

    public ShareChallengeDialog(View contentView, List<UserTrendBean> list) {
//        mContentView = contentView;
        mDataList.addAll(list);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_share_challenge, container, false);
        _initView(view);
        return view;
    }

    @Override
    public String getShareId() {
        return "20200723";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.view_share_challenge;
    }

    public void setPreviewInfo(View rootView, boolean sharePic) {
        if (sharePic) {
            RecyclerView rv_image = rootView.findViewById(R.id.rv_image);
            RecyclerView rvDialogImage = getView().getRootView().findViewById(R.id.rvContents);
            rv_image.getLayoutParams().height = (int) (((float) rvDialogImage.getMeasuredHeight() / (float) rvDialogImage.getMeasuredWidth()) * (float) ScreenUtil.getScreenWidth(getContext()));
            Bitmap bgBitmap = Bitmap.createBitmap(rvDialogImage.getMeasuredWidth(), rvDialogImage.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            rvDialogImage.draw(new Canvas(bgBitmap));
            rv_image.setBackground(UtilsCode.INSTANCE.bitmap2Drawable(bgBitmap));
        } else {
            RecyclerView rvContents = rootView.findViewById(R.id.rvContents);

            HotListAdapter adapter = new HotListAdapter(getContext(), mDataList);
            adapter.setLoadMoreView(new CustomLoadMoreView());
            rvContents.setLayoutManager(new LinearLayoutManager(getContext()));
            rvContents.setAdapter(adapter);
        }
    }
}
