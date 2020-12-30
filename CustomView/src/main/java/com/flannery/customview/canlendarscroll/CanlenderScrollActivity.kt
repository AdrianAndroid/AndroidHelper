package com.flannery.customview.canlendarscroll

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.recyclerview.widget.RecyclerView
import com.flannery.customview.R
import com.flannery.customview.touchevent.L
import kotlinx.android.synthetic.main.activity_canlender_scroll.*

class CanlenderScrollActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canlender_scroll)

        rvLeft.adapter = MyAdapter(R.layout.activity_canlender_scroll_leftitem)
        rvRight.adapter = MyAdapter(R.layout.activity_canlender_scroll_rightitem)

        startHorizontal.setBackgroundColor(Color.GRAY)

        rvRight.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                L.m(javaClass, "addOnScrollListener")
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                L.m(javaClass, "addOnScrollListener", dx, dy)
                rvLeft.scrollBy(0, dy)
            }
        })
    }


    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    inner class MyAdapter(val layoutId: Int) : RecyclerView.Adapter<VH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val inflate = layoutInflater.inflate(layoutId, parent, false)
            return VH(inflate)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            // 点击模式
            holder.itemView.setOnLongClickListener {
                it.setBackgroundColor(Color.GREEN)
                // 设置模式
                if (it.parent is RightRecyclerView) {
                    // 设置编辑模式
                    (it.parent as RightRecyclerView).editMode = true
                }
                true
            }
        }

        override fun getItemCount(): Int {
            return 100;
        }

    }


}

