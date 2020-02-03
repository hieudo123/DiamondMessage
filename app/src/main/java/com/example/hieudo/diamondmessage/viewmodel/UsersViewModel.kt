package com.example.hieudo.diamondmessage.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.hieudo.diamondmessage.base.BaseViewModel
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.core.request.QBPagedRequestBuilder
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser

class UsersViewModel : BaseViewModel() {
    val qbUsersList : MutableLiveData<MutableList<QBUser>> = MutableLiveData()

    fun getAllUser(){
        showLoading(true)
        val qbPagedRequestBuilder = QBPagedRequestBuilder()
        qbPagedRequestBuilder.page = 1
        qbPagedRequestBuilder.perPage = 50
        QBUsers.getUsers(qbPagedRequestBuilder).performAsync(object : QBEntityCallback<ArrayList<QBUser>>{
            override fun onSuccess(qbUsers: ArrayList<QBUser>?, p1: Bundle?) {
                showLoading(false)
            }
            override fun onError(exception: QBResponseException?) {
                showLoading(false)
                eventError.postValue(exception!!.message)
            }
        })

    }
}