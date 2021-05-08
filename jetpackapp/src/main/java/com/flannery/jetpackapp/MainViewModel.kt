package com.flannery.jetpackapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val _firstContent = MutableLiveData<String>().apply {
        value = "第一个文本"
    }
    val firstContent: LiveData<String> = _firstContent

    private val _secondContent = MutableLiveData<String>().apply {
        value = "第二个文本"
    }
    val secondContent: LiveData<String> = _secondContent

    // 在主线程更新LiveData对象，调用了MutableLiveData的setValue方法，下面会分析
    fun changeFirstContent(text: String) {
        _firstContent.value = text
    }

    // 在工作线程更新LiveData对象，调用了MutableLiveData的postValue方法，下面会分析
    fun changeSecondContent(text: String) =
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _secondContent.postValue(text)
            }
        }


}