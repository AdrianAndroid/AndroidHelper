package com.joyy.androidproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.joyy.utils.ActivityUtils
import kotlinx.android.synthetic.main.activity_main2.mRecyclerView

//@Destination(
//    url = "app/MainActivity",
//    description = "MainActivity"
//)
class MainActivity : AppCompatActivity() {

    val items = arrayListOf<String>(

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        initButtons()
        initRecyclerView()
    }

    fun test() {

    }

    private fun initButtons() {
        findViewById<Button>(R.id.btnPackage).setOnClickListener {
            ActivityUtils.startActivity(this, "com.henley.appmanage.activity.MainActivity")
        }

        findViewById<Button>(R.id.btnColors).setOnClickListener {
            ActivityUtils.startActivity(this, "com.joyy.colors.ColorsActivity")
        }
    }

    private fun initRecyclerView() {
        mRecyclerView.adapter = object : RecyclerView.Adapter<VH>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
                val view = LayoutInflater.from(parent.context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false)
                return VH(view)
            }

            override fun onBindViewHolder(holder: VH, position: Int) {
                holder.itemView.findViewById<TextView>(android.R.id.text1).text = items[position]
            }

            override fun getItemCount(): Int = items.count()

        }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)
}