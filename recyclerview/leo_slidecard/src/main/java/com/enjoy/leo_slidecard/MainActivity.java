package com.enjoy.leo_slidecard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.enjoy.leo_slidecard.adapter.UniversalAdapter;
import com.enjoy.leo_slidecard.adapter.ViewHolder;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private UniversalAdapter<SlideCardBean> adapter;
    private List<SlideCardBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new SlideCardLayoutManager());

        mDatas = SlideCardBean.initDatas();
        adapter = new UniversalAdapter<SlideCardBean>(this, mDatas, R.layout.item_swipe_card) {

            @Override
            public void convert(ViewHolder viewHolder, SlideCardBean slideCardBean) {
                viewHolder.setText(R.id.tvName, slideCardBean.getName());
                viewHolder.setText(R.id.tvPrecent, slideCardBean.getPostition() + "/" + mDatas.size());
                Glide.with(MainActivity.this)
                        .load(slideCardBean.getUrl())
                        .into((ImageView) viewHolder.getView(R.id.iv));
            }
        };
        rv.setAdapter(adapter);
        // 初始化数据
        CardConfig.initConfig(this);

        SlideCallback slideCallback = new SlideCallback(rv, adapter, mDatas);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(slideCallback);
        itemTouchHelper.attachToRecyclerView(rv);

    }
}
