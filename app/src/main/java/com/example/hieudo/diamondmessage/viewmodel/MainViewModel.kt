package com.example.hieudo.diamondmessage.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hieudo.diamondmessage.base.BaseViewModel
import com.example.hieudo.diamondmessage.others.constant.AppConstant
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.exception.QBChatException
import com.quickblox.chat.listeners.QBChatDialogMessageListener
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.chat.request.QBMessageGetBuilder
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.SmackException.NotConnectedException
import org.jivesoftware.smack.XMPPConnection
import java.util.*


class MainViewModel : BaseViewModel(){
    private val qbChatDialogReponse: MutableLiveData<QBChatDialog> = MutableLiveData()


    fun getQBChatDialog(): LiveData<QBChatDialog> = qbChatDialogReponse

    fun setQBChatDialog(qbChatDialog: QBChatDialog){
        qbChatDialogReponse.value = qbChatDialog
    }

    fun inCommingMessage(): LiveData<QBChatMessage>{
        val messageData  : MutableLiveData<QBChatMessage> = MutableLiveData()
        if (QBChatService.getInstance().isLoggedIn){
            val incommingMessageManager = QBChatService.getInstance().incomingMessagesManager
            incommingMessageManager.addDialogMessageListener(object : QBChatDialogMessageListener{
                override fun processMessage(p0: String?, qbChatMessage: QBChatMessage?, p2: Int?) {
                    Log.e("MESSAGE", qbChatMessage!!.body)
                    messageData.postValue(qbChatMessage)
                }

                override fun processError(p0: String?, e: QBChatException?, qbChatMessage: QBChatMessage?, p3: Int?) {
                    eventError.postValue(e!!.message)
                }
            })
        }
        return messageData
    }

    fun getHistoryChat() : LiveData<MutableList<QBChatMessage>> {
        val messagesData : MutableLiveData<MutableList<QBChatMessage>> = MutableLiveData()
        showLoading(true)
        try {
            QBChatService.getInstance().enterActiveState()
        } catch (e: NotConnectedException) {
            e.printStackTrace()
        }
        val messageGetBuilder = QBMessageGetBuilder()
        messageGetBuilder.limit = 100
        messageGetBuilder.sortDesc(AppConstant.QB_DATE_SENT)
        QBRestChatService.getDialogMessages(qbChatDialogReponse.value, messageGetBuilder)
            .performAsync(object : QBEntityCallback<ArrayList<QBChatMessage?>> {
                override fun onSuccess(qbChatMessages: ArrayList<QBChatMessage?>, bundle: Bundle) {
                    if (qbChatMessages.size > 0) {
                        messagesData.postValue(qbChatMessages as MutableList<QBChatMessage>)
                    }
                    showLoading(false)
                }
                override fun onError(e: QBResponseException) {
                    showLoading(false)
                    setError(e.message)
                }
            })
        return messagesData
    }

    fun sendMessage(message : String): LiveData<QBChatMessage> {
        val messageData : MutableLiveData<QBChatMessage> = MutableLiveData()
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
                    messageData.postValue(qbChatMessage)
                }
                override fun onError(e: QBResponseException?) {
                    eventError.postValue(e!!.message)
                }
            })
        }
        return messageData
    }

    fun leaveDialog() {
        try {
            QBChatService.getInstance().enterInactiveState()
        } catch (e: NotConnectedException) {
            e.printStackTrace()
        }
    }

    fun addConnectionListener(){
       QBChatService.getInstance().addConnectionListener(object :ConnectionListener{
           override fun connected(p0: XMPPConnection?) {
               Log.e("QBChatService","connected")
           }
           override fun connectionClosed() {
               Log.e("QBChatService","connectionClosed")
           }
           override fun connectionClosedOnError(p0: Exception?) {
               Log.e("QBChatService","connectionClosedOnError")
           }
           override fun reconnectionSuccessful() {
               Log.e("QBChatService","reconnectionSuccessful")
           }
           override fun authenticated(p0: XMPPConnection?, p1: Boolean) {
               Log.e("QBChatService","authenticated")
               showLoading(false)
           }
           override fun reconnectionFailed(p0: Exception?) {
               Log.e("QBChatService","reconnectionFailed")
           }
           override fun reconnectingIn(p0: Int) {
               Log.e("QBChatService","reconnectingIn")
           }
       })
    }
}