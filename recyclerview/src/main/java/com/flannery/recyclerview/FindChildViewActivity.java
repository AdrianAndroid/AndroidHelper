package com.flannery.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

// https://blog.csdn.net/csc48656/article/details/72943260
public class FindChildViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_child_view);

        RecyclerView recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<String> strings = Arrays.asList(
                "s",
                "ss",
                "sss",
                "ssss",
                "sssds",
                "sssds",
                "sssds",
                "sdsss",
                "ssfss",
                "ss1ss",
                "ss2ss",
                "ss3ss",
                "sss4s",
                "sss5s",
                "sss6s",
                "sss7s",
                "sss8s",
                "sss9s",
                "sss0s"
        );
        recycleView.setAdapter(new ListAdapter(new DiffUtil.ItemCallback() {
            @Override
            public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
                return false;
            }
        }) {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View inflate = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
                return new RecyclerView.ViewHolder(inflate) {

                };
            }


            @Override
            protected Object getItem(int position) {
                return strings.get(position);
            }

            @Override
            public int getItemCount() {
                return strings.size();
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ((TextView) holder.itemView.findViewById(android.R.id.text1)).setText(strings.get(position));
            }
        });


        //利用滚动监听器来监听滚动时间，在onScrolled（）方法中调用findChildViewUnder()方法
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //由于返回的是一个view，需要调用findViewById方法找到子类的view
                View v = recyclerView.findChildViewUnder(100, 100);
                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                Log.v("scroll", "child:" + name);
            }
        });

    }


}