package com.joyy.loggerproject

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.joyy.log_core.KLog
import java.lang.Exception

class MainActivity : Base3Activity() {

    val TAG = "[MainActivity]";

    fun log(msg: String) {
//        Log.e(TAG, msg)
        L.log(msg)
    }

    val list = arrayListOf(
        Data("开启两个线程") {

            object : Thread() {
                override fun run() {
                    repeat(1000) {
//                        KLog.e { "$it" }
                        KLog.et(Throwable("[ONE] 第${it}个 ${System.currentTimeMillis()}"))
                    }
                }
            }.start()

            object : Thread() {
                override fun run() {
                    repeat(1000) {
//                        KLog.e { "$it" }
                        KLog.et(Throwable("[TWO] 第${it}个 ${System.currentTimeMillis()}"))
                    }
                }
            }.start()

            object : Thread() {
                override fun run() {
                    repeat(1000) {
//                        KLog.e { "$it" }
                        KLog.et(Throwable("[THREE] 第${it}个 ${System.currentTimeMillis()}"))
                    }
                }
            }.start()
        },
        Data("开启一个线程") {
            object : Thread() {
                override fun run() {
                    repeat(1000) {
//                        KLog.e { "$it" }
                        KLog.et(Throwable("第${it}个 ${System.currentTimeMillis()}"))
                    }
                }
            }.start()
        },
        Data("SecondActivity") {
            startActivity(Intent(this, SecondActivity::class.java))
        },
        Data("打印log") {
            Log.v(TAG, "打印第一个LOG")
            Log.d(TAG, "打印第一个LOG")
            Log.i(TAG, "打印第一个LOG")
            Log.w(TAG, "打印第一个LOG")
            Log.e(TAG, "打印第一个LOG")
        },
        Data("打印log Throwable") {
            Log.v(TAG, "打印第一个LOG", Throwable("Throwable"))
            Log.d(TAG, "打印第一个LOG", Throwable("Throwable"))
            Log.i(TAG, "打印第一个LOG", Throwable("Throwable"))
            Log.w(TAG, "打印第一个LOG", Throwable("Throwable"))
            Log.e(TAG, "打印第一个LOG", Throwable("Throwable"))
        },
        Data("getStackTraceString") {
            log(Log.getStackTraceString(Throwable("Throwable")))
        },
        Data("KLog") {
//            KLog.v("")
//            KLog.d()
//            KLog.i()
//            KLog.w()
//            KLog.e()
        },
        Data("KLog Throwable") {
            try {
                throw IllegalArgumentException("测试异常")
            } catch (e: Exception) {
                KLog.vt(e)
                KLog.dt(e)
                KLog.it(e)
                KLog.wt(e)
                KLog.et(e)
            }
        },
        Data("lamda") {
        }
    )

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mRecyclerView = findViewById<RecyclerView>(R.id.mRecyclerView)

        repeat(100) {
            list.add(Data("$it") {})
        }

        mRecyclerView.adapter = object : RecyclerView.Adapter<VH>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
                val button = Button(parent.context)
                button.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                );
                return VH(button)
            }

            override fun onBindViewHolder(holder: VH, position: Int) {
                val data = list[position]
                (holder.itemView as Button).run {
                    text = data.title
                    setOnClickListener { data.callback() }
                }
            }

            override fun getItemCount(): Int {
                return list.size
            }
        }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    data class Data(val title: String, val callback: () -> Unit)
}