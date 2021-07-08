package com.flannery.multilanguage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class ExpandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);

        RecyclerView mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @NotNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false)
                ) {

                };
            }

            @Override
            public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
                ((TextView) holder.itemView.findViewById(android.R.id.text1)).setText("position = " + position);
            }

            @Override
            public int getItemCount() {
                return 100;
            }
        });

    }
}