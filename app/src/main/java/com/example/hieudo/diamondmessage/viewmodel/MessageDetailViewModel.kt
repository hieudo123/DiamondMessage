package com.example.hieudo.diamondmessage.viewmodel

import android.os.Bundle
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

class MessageDetailViewModel : BaseViewModel() {
    private val qbChatDialogReponse: MutableLiveData<QBChatDialog> = MutableLiveData()
    private val qbMessagesReponse : MutableLiveData<MutableList<QBChatMessage>> = MutableLiveData()

    fun getQBChatDialog(): LiveData<QBChatDialog> = qbChatDialogReponse
    fun setQBChatDialog(qbChatDialog: QBChatDialog){
        qbChatDialogReponse.value = qbChatDialog
    }
    fun getQBMessages(): LiveData<MutableList<QBChatMessage>> = qbMessagesReponse
    fun getHistoryMessage(){
        showLoading(true)
        QBChatService.getInstance().enterActiveState()
        val messageBuilder = QBMessageGetBuilder()
        messageBuilder.limit = 100
        messageBuilder.sortDesc(AppConstant.QB_DATE_SENT)
        QBRestChatService.getDialogMessages(qbChatDialogReponse.value,messageBuilder).performAsync(object : QBEntityCallback<ArrayList<QBChatMessage>>{
            override fun onSuccess(qbChatMessages: ArrayList<QBChatMessage>?, p1: Bundle?) {
                eventShowLoading.postValue(false)
                qbMessagesReponse.postValue(qbChatMessages)

            }

            override fun onError(e: QBResponseException?) {
                eventShowLoading.postValue(false)
                eventError.postValue(e!!.message)
            }
        })
    }

    fun inCommingMessage(){
        val incommingMessageManager = QBChatService.getInstance().incomingMessagesManager
        incommingMessageManager.addDialogMessageListener(object : QBChatDialogMessageListener{
            override fun processMessage(p0: String?, qbChatMessage: QBChatMessage?, p2: Int?) {

            }

            override fun processError(p0: String?, e: QBChatException?, qbChatMessage: QBChatMessage?, p3: Int?) {

            }
        })
    }

    fun sendMessage(message : String){
        if (qbChatDialogReponse.value != null){
            val qbChatDialog = qbChatDialogReponse.value
            val qbChatMessage = QBChatMessage()
            qbChatMessage.body = message
            qbChatMessage.senderId = QBChatService.getInstance().user.id
            qbChatMessage.dateSent = (System.currentTimeMillis()/1000)
            qbChatMessage.setSaveToHistory(true)

            qbChatDialog?.sendMessage(qbChatMessage, object  : QBEntityCallback<Void>{
                override fun onSuccess(p0: Void?, p1: Bundle?) {
                    qbMessagesReponse.value!!.add(0,qbChatMessage)
                }
                override fun onError(e: QBResponseException?) {
                    eventError.postValue(e!!.message)
                }
            })
        }
    }



}