package com.example.hieudo.diamondmessage.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.hieudo.diamondmessage.base.BaseViewModel
import com.example.hieudo.diamondmessage.utils.ValidationUtils
import com.quickblox.auth.QBAuth
import com.quickblox.auth.session.QBSession
import com.quickblox.chat.QBChatService
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser


class RegisterViewModel : BaseViewModel() {
    val registerReponse : MutableLiveData<QBUser> = MutableLiveData()
    val loginReponse : MutableLiveData<QBUser> = MutableLiveData()
    val isValidation : MutableLiveData<Boolean> = MutableLiveData()


    fun checkEmailValid(email :String, password :String, context: Context) {
        val error = ValidationUtils.checkEmailValid(email,password, context)
        if (error.isEmpty())
            isValidation.value = true
        else
            eventError.value = error
    }

    fun register(email :String, password :String, fullName :String){
        showLoading(true)
        val qbUser  = QBUser()
        qbUser.email = email
        qbUser.fullName = fullName
        qbUser.password = password
        QBUsers.signUp(qbUser).performAsync(object  : QBEntityCallback<QBUser>{
            override fun onSuccess(qbUser: QBUser?, bundle: Bundle?) {
                registerReponse.postValue(qbUser)
                showLoading(false)
            }
            override fun onError(e: QBResponseException?) {
                showLoading(false)
                setError(e!!.message)
            }
        })
    }

    fun login(email: String, password: String){
        showLoading(true)
        val qbUserOrigin : QBUser  = QBUser()
        qbUserOrigin.email = email
        qbUserOrigin.password = password
        QBUsers.signIn(qbUserOrigin).performAsync(object : QBEntityCallback<QBUser> {
            override fun onSuccess(qbUser: QBUser?, bundle: Bundle?) {
                qbUserOrigin.fullName = qbUser!!.fullName
                qbUserOrigin.id = qbUser.id
                QBChatService.getInstance().login(qbUserOrigin, object : QBEntityCallback<Any?> {
                    override fun onSuccess(o: Any?, bundle: Bundle) {
                        showLoading(false)
                        loginReponse.postValue(qbUserOrigin)
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
    }
}