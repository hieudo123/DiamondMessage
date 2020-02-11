package com.example.hieudo.diamondmessage.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.hieudo.diamondmessage.base.BaseViewModel
import com.quickblox.auth.QBAuth
import com.quickblox.auth.session.QBSession
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException

class RegisterQBViewModel : BaseViewModel() {
    val registerQBReponse : MutableLiveData<QBSession> = MutableLiveData()

    fun registerSession (){
        QBAuth.createSession().performAsync(object : QBEntityCallback<QBSession> {
            override fun onSuccess(qbSession: QBSession?, p1: Bundle?) {
                registerQBReponse.postValue(qbSession)
            }
            override fun onError(p0: QBResponseException?) {
                eventError.postValue(p0!!.message)
            }
        })
    }


}