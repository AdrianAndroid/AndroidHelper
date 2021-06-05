package com.flannery.html

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_html.*
import java.lang.Exception

class HtmlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_html)
        textView.typeface = Typeface.createFromAsset(assets, "iconfont.ttf")


        button.setOnClickListener {
            editText.text?.toString()?.let { str ->
                textView.setText(Html.fromHtml(str)) // 设置文字
            }
        }

        //&#xe6d2;
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val initList = initList()
        mRecyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val itemView =
                    layoutInflater.inflate(R.layout.my_simple_list_item_1, parent, false)
                val tv = itemView.findViewById<TextView>(R.id.text1)
                // 设置字体库
                tv.typeface = Typeface.createFromAsset(assets, "iconfont.ttf")
                // 设置字体大小
                // tv.setTextSize(12F)
                return MyHolder(itemView)
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val get = initList.get(position)
                val textview = holder.itemView.findViewById<TextView>(R.id.text1)
                try {
                    val toString = get[position].toString()
                    System.out.println(toString)
                    val fromHtml: Spanned = Html.fromHtml(toString)
                    textview.setText(fromHtml)
                } catch (e: Exception) {
                    e.printStackTrace()
                    textview.setText("<<<------>>>")
                }
            }

            override fun getItemCount(): Int {
                return initList.size
            }

        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    private fun combination(code: String): String {
        return "&#x$code;"
    }

    private fun initList(): List<String> {
        val arrayListOf = arrayListOf(
            combination("e6c0"),
            combination("e6c1"),
            combination("e6c2"),
            combination("e6c3"),
            combination("e6c4"),
            combination("e6c5"),
            combination("e6c6"),
            combination("e6c7"),
            combination("e6c8"),
            combination("e6c9"),
            combination("e6cb"),
            combination("e6cc"),
            combination("e6cd"),
            combination("e6ce"),
            combination("e6cf"),

            combination("e6d0"),
            combination("e6d1"),
            combination("e6d2"),
            combination("e6d3"),
            combination("e6d4"),
            combination("e6d5"),
            combination("e6d6"),
            combination("e6d7"),
            combination("e6d8"),
            combination("e6d9"),
            combination("e6da"),
            combination("e6db"),
            combination("e6dc"),
            combination("e6dd"),
            combination("e6de"),
            combination("e6df"),

            combination("e6e0"),
            combination("e6e1"),
            combination("e6e2"),
            combination("e6e3"),
            combination("e6e4")
        )
//        for (s in arrayListOf) {
//            System.out.println(s)
//        }
        return arrayListOf
    }
}