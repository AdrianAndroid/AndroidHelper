package com.flannery.recyclerview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

// https://blog.csdn.net/csc48656/article/details/72943260
public class FindCompatelyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_child_view);

        RecyclerView recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<String> strings = Arrays.asList(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23",
                "24",
                "25",
                "26",
                "27",
                "28",
                "29",
                "30",
                "31",
                "32",
                "33",
                "34",
                "35",
                "36",
                "37",
                "38",
                "39",
                "40",
                "41",
                "42",
                "43",
                "44",
                "45",
                "46",
                "47",
                "48",
                "49",
                "50",
                "51",
                "52",
                "53",
                "54",
                "55",
                "56",
                "57",
                "58",
                "59"
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


//                View v = recyclerView.findChildViewUnder(100, 100);
//                TextView textView = (TextView) v.findViewById(android.R.id.text1);
//                String name = textView.getText().toString();
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                int firstCompletelyVisible = layoutManager.findFirstCompletelyVisibleItemPosition(); // 完全可见
                int firstVisiable = layoutManager.findFirstVisibleItemPosition(); // 可见
                int lastCompletelyVisible = layoutManager.findLastCompletelyVisibleItemPosition(); // 完全可见
                int lastVisible = layoutManager.findLastVisibleItemPosition(); // 可见

                StringBuilder sb = new StringBuilder();
                sb.append("firstCompletelyVisible=").append(firstCompletelyVisible).append("-").append(strings.get(firstCompletelyVisible)).append(",");
                sb.append("firstVisiable=").append(firstVisiable).append("-").append(strings.get(firstVisiable)).append(",");
                sb.append("lastCompletelyVisible=").append(lastCompletelyVisible).append("-").append(strings.get(lastCompletelyVisible)).append(",");
                sb.append("lastVisible=").append(lastVisible).append("-").append(strings.get(lastVisible)).append(",");

                Log.v("scroll", sb.toString());
            }
        });

    }


}