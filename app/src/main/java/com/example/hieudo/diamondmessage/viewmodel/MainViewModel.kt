package com.example.hieudo.diamondmessage.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hieudo.diamondmessage.base.BaseViewModel
import com.quickblox.auth.session.QBSession
import com.quickblox.auth.session.QBSessionManager
import com.quickblox.auth.session.QBSessionManager.QBSessionListener
import com.quickblox.auth.session.QBSessionParameters
import com.quickblox.chat.QBChatService
import com.quickblox.chat.exception.QBChatException
import com.quickblox.chat.listeners.QBChatDialogMessageListener
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException


class MainViewModel : BaseViewModel(){
    private val qbChatDialogReponse: MutableLiveData<QBChatDialog> = MutableLiveData()

    fun getQBChatDialog(): LiveData<QBChatDialog> = qbChatDialogReponse
    fun setQBChatDialog(qbChatDialog: QBChatDialog){
        qbChatDialogReponse.value = qbChatDialog
    }

    fun inCommingMessage(){
        if (QBChatService.getInstance().isLoggedIn){
            val incommingMessageManager = QBChatService.getInstance().incomingMessagesManager
            incommingMessageManager.addDialogMessageListener(object : QBChatDialogMessageListener{
                override fun processMessage(p0: String?, qbChatMessage: QBChatMessage?, p2: Int?) {
                    Log.e("MESSAGE", qbChatMessage!!.body)
                }

                override fun processError(p0: String?, e: QBChatException?, qbChatMessage: QBChatMessage?, p3: Int?) {
                    eventError.postValue(e!!.message)
                }
            })
        }
    }

    fun sendMessage(message : String){
        if (qbChatDialogReponse.value != null){
            val qbChatDialog = qbChatDialogReponse.value
            val qbChatMessage = QBChatMessage()
            qbChatMessage.body = message
            qbChatMessage.senderId = QBChatService.getInstance().user.id
            qbChatMessage.dateSent = (System.currentTimeMillis()/1000)
            qbChatMessage.setSaveToHistory(true)
            qbChatDialog?.sendMessage(qbChatMessage, object  : QBEntityCallback<Void> {
                override fun onSuccess(p0: Void?, p1: Bundle?) {
                    Log.e("MESSAGE",qbChatMessage.body)
                }
                override fun onError(e: QBResponseException?) {
                    eventError.postValue(e!!.message)
                }
            })
        }
    }

    fun addSessionListener(){
        QBSessionManager.getInstance().addListener(object : QBSessionListener {
            override fun onSessionCreated(session: QBSession) {
                //calls when session was created firstly or after it has been expired
                Log.e("MainViewModel","onSessionCreated()")
            }

            override fun onSessionUpdated(sessionParameters: QBSessionParameters) {
                //calls when user signed in or signed up
                //QBSessionParameters stores information about signed in user.
                Log.e("MainViewModel","onSessionUpdated()")
            }

            override fun onSessionDeleted() {
                //calls when user signed Out or session was deleted
            }

            override fun onSessionRestored(session: QBSession) {
                //calls when session was restored from local storage
            }

            override fun onSessionExpired() {
                //calls when session is expired
            }

            override fun onProviderSessionExpired(provider: String) {
                //calls when provider's access token is expired or invalid
            }
        })
    }
}