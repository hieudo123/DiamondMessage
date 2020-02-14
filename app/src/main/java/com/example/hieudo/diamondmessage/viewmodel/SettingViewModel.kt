package com.example.hieudo.diamondmessage.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hieudo.diamondmessage.base.BaseViewModel
import com.quickblox.chat.QBChatService
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.QBUsers

class SettingViewModel : BaseViewModel() {


    fun logOut() : LiveData<Boolean>{
        showLoading(true)
        val qbLogOutReponse : MutableLiveData<Boolean> = MutableLiveData()
        if (QBChatService.getInstance().isLoggedIn){
            QBChatService.getInstance().logout(object : QBEntityCallback<Void>{
                override fun onSuccess(p0: Void?, p1: Bundle?) {
                    QBUsers.signOut().performAsync(object : QBEntityCallback<Void>{
                        override fun onSuccess(p0: Void?, p1: Bundle?) {
                            showLoading(false)
                            qbLogOutReponse.postValue(true)
                        }

                        override fun onError(e: QBResponseException?) {
                            showLoading(false)
                            eventError.postValue(e!!.message)
                        }
                    })
                }
                override fun onError(e: QBResponseException?) {
                    showLoading(false)
                    eventError.postValue(e!!.message)
                }
            })
        }
        return qbLogOutReponse
    }
}