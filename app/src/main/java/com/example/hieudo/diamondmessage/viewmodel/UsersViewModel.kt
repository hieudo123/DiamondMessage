package com.example.hieudo.diamondmessage.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.hieudo.diamondmessage.base.BaseApplication
import com.example.hieudo.diamondmessage.base.BaseViewModel
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.utils.DialogUtils
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.core.request.QBPagedRequestBuilder
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser

class UsersViewModel : BaseViewModel() {
    private val qbUsersListReponse : MutableLiveData<MutableList<QBUser>> = MutableLiveData()

    init {
        showLoading(true)
        getAllUser()
    }

    fun login(email: String, password: String) : LiveData<QBUser>{
        showLoading(true)
        val loginReponse = MutableLiveData<QBUser>()
        val qbUserOrigin = QBUser()
        qbUserOrigin.email = email
        qbUserOrigin.password = password
        QBUsers.signIn(qbUserOrigin).performAsync(object : QBEntityCallback<QBUser> {
            override fun onSuccess(qbUser: QBUser?, bundle: Bundle?) {
                Log.e("LOGIN",qbUser.toString())
                qbUserOrigin.fullName = qbUser!!.fullName
                qbUserOrigin.id = qbUser.id
                QBChatService.getInstance().login(qbUserOrigin, object : QBEntityCallback<Any?> {
                    override fun onSuccess(o: Any?, bundle: Bundle) {
                        loginReponse.postValue(qbUserOrigin)
                        showLoading(false)
                    }
                    override fun onError(e: QBResponseException) {
                        setError(e.message)
                        showLoading(false)
                    }
                })
            }
            override fun onError(p0: QBResponseException?) {
                showLoading(false)
                if (p0 != null) {
                    setError(p0.message)
                }
            }
        })
        return loginReponse
    }

    private fun getAllUser(){
        val qbPagedRequestBuilder = QBPagedRequestBuilder()
        qbPagedRequestBuilder.page = 1
        qbPagedRequestBuilder.perPage = 50
        QBUsers.getUsers(qbPagedRequestBuilder).performAsync(object : QBEntityCallback<ArrayList<QBUser>>{
            override fun onSuccess(qbUsers: ArrayList<QBUser>?, p1: Bundle?) {
                showLoading(false)
                for (i in qbUsers!!.indices){
                    if (qbUsers[i].id == BaseApplication.getInstance().getCurrentUser()!!.id){
                        qbUsers.removeAt(i)
                        break
                    }
                }
                if(qbUsers.size > 0)
                 qbUsersListReponse.postValue(qbUsers)
            }
            override fun onError(exception: QBResponseException?) {
                showLoading(false)
                eventError.postValue(exception!!.message)
            }
        })
    }

    fun createPrivateChat (userId : Int): LiveData<QBChatDialog>{
        showLoading(true)
        val qbChatDialogReponse : MutableLiveData<QBChatDialog> = MutableLiveData()
        if (QBChatService.getInstance().isLoggedIn){
            val occupantIdList = ArrayList<Int>()
            occupantIdList.add(QBChatService.getInstance().user.id)
            occupantIdList.add(userId)
            var qbChatDialog = QBChatDialog()
            qbChatDialog.setOccupantsIds(occupantIdList)
            qbChatDialog = DialogUtils.buildPrivateDialog(userId)
            QBRestChatService.createChatDialog(qbChatDialog).performAsync(object : QBEntityCallback<QBChatDialog>{
                override fun onSuccess(qbChatDialog: QBChatDialog?, p1: Bundle?) {
                    showLoading(false)
                    qbChatDialogReponse.postValue(qbChatDialog)
                }
                override fun onError(e: QBResponseException?) {
                    showLoading(false)
                    eventError.postValue(e!!.message)
                }
            })
        }
        return qbChatDialogReponse
    }

    fun getQBUsersList(): LiveData<MutableList<QBUser>> = qbUsersListReponse
}