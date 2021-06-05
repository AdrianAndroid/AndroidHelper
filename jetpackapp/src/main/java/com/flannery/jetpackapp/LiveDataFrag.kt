//package com.flannery.jetpackapp
//
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import java.util.logging.Logger
//
//class LiveDataFrag : Fragment() {
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // 添加一个跟生命周期相关的 `Observer`
//        ParaConfig.getMsgLiveData().observe(this, Observer<String> { newMsg ->
//            //tv_msg.text = newMsg
//        })
//
//        // 无视生命周期, 每次数据变化时都会回调,需要自行移除observer
//        ParaConfig.getMsgLiveData().observeForever {
//            //Logger.d("observeForever: $it","tag_livedata")
//        }
//        //Log.getStackTraceString()
//    }
//}