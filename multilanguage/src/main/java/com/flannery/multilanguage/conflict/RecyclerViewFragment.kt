package com.flannery.multilanguage.conflict

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flannery.multilanguage.R

/**
 * Time:2021/7/1 19:54
 * Author:
 * Description:
 */
class RecyclerViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val inflate = inflater.inflate(R.layout.fragment_recyclerview, container, false)

        val mRecyclerView = inflate.findViewById<RecyclerView>(R.id.mRecyclerView)
        mRecyclerView.layoutManager =
            LinearLayoutManager(inflate.context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.adapter = object : RecyclerView.Adapter<MyAdapter>() {
            override fun getItemCount(): Int = 1000
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter =
                MyAdapter(
                    LayoutInflater.from(parent.context)
                        .inflate(android.R.layout.simple_list_item_1, parent, false)
                )

            override fun onBindViewHolder(holder: MyAdapter, position: Int) {
                val mTv = holder.itemView.findViewById<TextView>(android.R.id.text1)
                mTv.setText("$position")
                mTv.setTextColor(Color.BLACK)
                mTv.setTextSize(10F)
            }
        }
        return inflate
    }

    class MyAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}