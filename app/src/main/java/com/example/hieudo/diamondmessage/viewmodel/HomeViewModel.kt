package com.example.hieudo.diamondmessage.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hieudo.diamondmessage.base.BaseViewModel
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.utils.DialogUtils
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.core.request.QBRequestGetBuilder
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser

class HomeViewModel : BaseViewModel() {
    private val limit = 20
    private val skip = 20 + limit
    private val data :ArrayList<QBChatDialog>  = ArrayList()
    private val qbChatDialogsReponse : MutableLiveData<MutableList<QBChatDialog>> = MutableLiveData()


    init {
        showLoading(true)
        getListDialog(isRefresh = true, isLoadMore = false)
    }

    fun getqbChatDialogsReponse() = qbChatDialogsReponse

    fun login(email: String, password: String) : LiveData<QBUser> {
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

    fun getListDialog(isRefresh : Boolean,isLoadMore: Boolean) {
        val qbDialogRequestBuilder = QBRequestGetBuilder()
        qbDialogRequestBuilder.limit = limit
        QBRestChatService.getChatDialogs(null, qbDialogRequestBuilder).performAsync(object : QBEntityCallback<ArrayList<QBChatDialog?>?>{
            override fun onSuccess(p0: ArrayList<QBChatDialog?>?, p1: Bundle?) {
                showLoading(false)
                if (p0!!.size > 0){
                    if (isRefresh)
                        data.clear()
                    if (data.size <=0)
                        getDialogHasLastMessage(p0)
                    else if(isLoadMore)
                        getDialogHasLastMessage(p0)
                }
            }
            override fun onError(p0: QBResponseException?) {
                setError(p0!!.message)
                showLoading(false)
            }
        })
    }

    private fun getDialogHasLastMessage(qbChatDialogs: ArrayList<QBChatDialog?>?) {

        for (i in qbChatDialogs!!.indices) {
            if (qbChatDialogs[i]!!.lastMessage != null) {
                data.add(qbChatDialogs[i]!!)
                Log.e("RECIPIENT_ID", qbChatDialogs[i]!!.recipientId.toString())
            }
        }
        qbChatDialogsReponse.postValue(data)
    }


}