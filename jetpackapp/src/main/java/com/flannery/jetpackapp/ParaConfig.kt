//package com.flannery.jetpackapp
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//
//object ParaConfig {
//    // 定义一个私有的 `MutableLiveData`
//    private val msgLiveData = MutableLiveData<String>()
//
//    // 开放给外部获取数据更新时,提供不可变的 `LiveData` 即可;
//    fun getMsgLiveData(): LiveData<String> = msgLiveData
//
//    fun updateMsg(msg: String, inBgThread: Boolean = false) {
//        if (inBgThread) {
//            msgLiveData.postValue(msg) // 在子线程中更新数据
//        } else {
//            msgLiveData.value = msg // 在主线程中更新数据
//        }
//    }
//}