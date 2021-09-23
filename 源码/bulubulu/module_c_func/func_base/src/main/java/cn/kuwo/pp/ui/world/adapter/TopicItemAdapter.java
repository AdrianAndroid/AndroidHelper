package cn.kuwo.pp.ui.world.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhl.userguideview.GuideUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.ArrayUtils;
import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.util.L;
import cn.kuwo.common.app.App;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.pp.http.bean.user.UserInfo;
import kotlin.jvm.functions.Function1;

public class TopicItemAdapter extends BaseQuickAdapter<TopicItemBean, BaseViewHolder> {
    private Random random = new Random();
    private HashSet<String> hashSet = new HashSet<>();


    public TopicItemAdapter(List<TopicItemBean> dataList) {
        super(R.layout.item_topic_list, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicItemBean item) {
        try {

            helper.addOnClickListener(R.id.ivTopicStart);

            helper.setText(R.id.tvTopicTitle, item.getName());
            setBgColor(helper.getView(R.id.itemLayout), item);

            helper.setText(R.id.tvTopicDetail, item.getQuestionCnt() + "道pk题目");
            helper.setText(R.id.tvNumbers, item.getUserCnt() + "人参与");

            ImageView ivUser1 = helper.getView(R.id.ivUser1);
            ImageView ivUser2 = helper.getView(R.id.ivUser2);
            ImageView ivUser3 = helper.getView(R.id.ivUser3);

            ivUser1.setVisibility(View.GONE);
            ivUser2.setVisibility(View.GONE);
            ivUser3.setVisibility(View.GONE);
            ivUser1.setImageBitmap(null);
            ivUser2.setImageBitmap(null);
            ivUser3.setImageBitmap(null);

            ArrayList<UserInfo> userInfos = new ArrayList<>(item.getUserList());
            ArrayUtils.INSTANCE.distinctBy(hashSet, userInfos, new Function1<UserInfo, String>() {
                @Override
                public String invoke(UserInfo t) {
                    return t.getUid();
                }
            });

            if (checkPosition(userInfos, 0)) {
                ivUser1.setVisibility(View.VISIBLE);
                UserInfo userInfo = userInfos.get(0);
                ImageLoader.showCircleImage(
                        ivUser1
                        , userInfo.getHeadImg()
                        , userInfo.getDefaultHeadImage()
                );
                if (App.DEBUG) L.m(getClass(), 0, item.getName(), userInfo.getHeadImg());
            } else {
                ivUser1.setVisibility(View.GONE);
                if (App.DEBUG) L.m(getClass(), 0, item.getName(), "》》》没有数据的《《《");
            }
            if (checkPosition(userInfos, 1)) {
                ivUser2.setVisibility(View.VISIBLE);
                UserInfo userInfo = userInfos.get(1);
                ImageLoader.showCircleImage(
                        ivUser2
                        , userInfo.getHeadImg()
                        , userInfo.getDefaultHeadImage()
                );
                if (App.DEBUG) L.m(getClass(), 1, item.getName(), userInfo.getHeadImg());
            } else {
                ivUser2.setVisibility(View.GONE);
                if (App.DEBUG) L.m(getClass(), 1, item.getName(), "》》》没有数据的《《《");
            }
            if (checkPosition(userInfos, 2)) {
                ivUser3.setVisibility(View.VISIBLE);
                UserInfo userInfo = userInfos.get(2);
                ImageLoader.showCircleImage(
                        ivUser3
                        , userInfo.getHeadImg()
                        , userInfo.getDefaultHeadImage()
                );
                if (App.DEBUG) L.m(getClass(), 2, item.getName(), userInfo.getHeadImg());
            } else {
                if (App.DEBUG) L.m(getClass(), 2, item.getName(), "》》》没有数据的《《《");
                ivUser3.setVisibility(View.GONE);
            }


        } catch (Exception e) {
            e.printStackTrace();
            if (App.DEBUG) L.m(getClass(), e.getMessage());
        }
    }

    // 检查位置
    private boolean checkPosition(List<?> list, int position) {
        if (list != null && position < list.size()) {
            return true;
        }
        return false;
    }

    private void setBgColor(View view, TopicItemBean bean) {
        int index = bean.getColorIndex();
        if (index < 0) {
            index = random.nextInt(4);
            bean.setColorIndex(index);
        }

        switch (index) {
            case 0:
                view.setBackgroundColor(Color.parseColor("#C273DE"));
                break;
            case 1:
                view.setBackgroundColor(Color.parseColor("#6E96FF"));
                break;
            case 2:
                view.setBackgroundColor(Color.parseColor("#FFC058"));
                break;
            case 3:
                view.setBackgroundColor(Color.parseColor("#3DD6D9"));
                break;
        }
    }


}
