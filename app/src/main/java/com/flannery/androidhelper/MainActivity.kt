package com.flannery.androidhelper

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.flannery.lottie.LottieActivity
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {

    data class Item(val image: Int, val title: String, val cls: Class<*>)

    val list = arrayListOf<Item>(
        Item(R.drawable.ic_package, "包名信息", MainActivity::class.java),
        Item(R.drawable.ic_svg, "lottie", LottieActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java),
        Item(R.drawable.ic_jingqingqidai, "敬请期待", MoreActivity::class.java)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
    }

    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    private fun initRecyclerView() {
        mRecyclerView.adapter = object : RecyclerView.Adapter<VHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
                return VHolder(layoutInflater.inflate(R.layout.item_grid, parent, false))
            }

            override fun onBindViewHolder(holder: VHolder, position: Int) {
                val item = list.get(position)
                holder.itemView.findViewById<ImageView>(R.id.imageView).setImageResource(item.image)
                holder.itemView.findViewById<TextView>(R.id.textView).setText(item.title)
                holder.itemView.setOnClickListener {
                    startActivity(Intent(application, item.cls))
                }
            }

            override fun getItemCount(): Int {
                return list.size
            }

        }
    }


//    private fun initRecyclerView() {
//
//        val installedPackages = packageManager.getInstalledPackages(0)
//        mRecyclerView.layoutManager = LinearLayoutManager(this)
//        mRecyclerView.adapter = object : RecyclerView.Adapter<VHolder>() {
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
//                val textView: TextView = TextView(applicationContext)
//                textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                return VHolder(textView);
//            }
//
//            override fun getItemCount(): Int {
//                return installedPackages.size
//            }
//
//            private fun setDefault(textView: TextView) {
//                textView.setTextColor(Color.BLACK)
//                textView.setBackgroundColor(Color.WHITE)
//                textView.paint.isFakeBoldText = false
//            }
//
//            override fun onBindViewHolder(holder: VHolder, position: Int) {
//                val textView = holder.itemView as? TextView
//                textView?.run {
//                    setDefault(this) //先恢复默认
//                    val info = installedPackages.get(position)
//                    val stringBuilder = StringBuilder()
//                    stringBuilder.append(info.packageName).append("\n")
//                            .append(info.versionName).append("\n")
//                            .append(info.versionCode).append("\n")
//                    text = stringBuilder.toString()
//
//                    if ("cn.kuwo.bulubulu" == info.packageName) {
//                        textView.paint.isFakeBoldText = true
//                        textView.setTextColor(Color.RED)
//                    }
//                }
//            }
//
//        }
//    }
}