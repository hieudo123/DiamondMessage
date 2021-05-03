package com.example.hieudo.diamondmessage.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {

    private val _eventShowLoading = MutableLiveData<Boolean>()

    val eventShowLoading: MutableLiveData<Boolean>
    get() = this._eventShowLoading


    private val _eventError = MutableLiveData<String>()

    val everError: MutableLiveData<String>
    get() = this._eventError
}