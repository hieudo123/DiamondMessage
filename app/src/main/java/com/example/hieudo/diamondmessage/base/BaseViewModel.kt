package com.example.hieudo.diamondmessage.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val eventShowLoading = MutableLiveData<Boolean> ()
    val eventError = MutableLiveData<String>()

    fun showLoading(isShow : Boolean){
        eventShowLoading.value = isShow
    }

    fun setError(error: String?){
        eventError.value = error
    }
}