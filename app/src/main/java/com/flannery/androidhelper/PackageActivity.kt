package com.flannery.androidhelper

import android.content.pm.PackageInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_package.*

class PackageActivity : AppCompatActivity() {

    private lateinit var packages: MutableList<PackageInfo>
    private lateinit var installedPackages: MutableList<PackageInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package)

        packages = packageManager.getInstalledPackages(0)
        installedPackages = packages.toMutableList()
        initRecyclerView()
        initSearchView()
    }

    private fun initSearchView() {
        mSearchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i("SearchView", "onQueryTextSubmit = $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("SearchView", " onQueryTextChange = $newText")
                newText?.let {
                    installedPackages.clear()
                    val filter = packages.filter {
                        //it.packageName.startsWith(newText)
                        it.packageName.contains(Regex(newText))
                    }
                    installedPackages.addAll(filter)
                    mRecyclerView.adapter?.notifyDataSetChanged()
                }
                return false
            }

        })
    }


    private fun initRecyclerView() {

        mRecyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val textView: TextView = TextView(applicationContext)
                textView.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                return object : RecyclerView.ViewHolder(textView) {}
            }

            override fun getItemCount(): Int {
                return installedPackages.size
            }

            private fun setDefault(textView: TextView) {
                textView.setTextColor(Color.BLACK)
                textView.setBackgroundColor(Color.WHITE)
                textView.paint.isFakeBoldText = false
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val textView = holder.itemView as? TextView
                textView?.run {
                    setDefault(this) //先恢复默认
                    val info = installedPackages.get(position)
                    val stringBuilder = StringBuilder()
                    stringBuilder.append(info.packageName).append("\n")
                        .append(info.versionName).append("\n")
                        .append(info.versionCode).append("\n")
                    text = stringBuilder.toString()

                    if ("cn.kuwo.bulubulu" == info.packageName) {
                        textView.paint.isFakeBoldText = true
                        textView.setTextColor(Color.RED)
                    }
                }
            }

        }
    }
}