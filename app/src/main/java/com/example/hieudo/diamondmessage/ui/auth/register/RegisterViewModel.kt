package com.example.hieudo.diamondmessage.ui.auth.register

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hieudo.diamondmessage.base.BaseViewModel
import com.example.hieudo.diamondmessage.others.enums.RegisterState
import com.example.hieudo.diamondmessage.repositories.UserRepository
import com.example.hieudo.diamondmessage.utils.ValidationUtils
import com.quickblox.auth.QBAuth
import com.quickblox.auth.session.QBSession
import com.quickblox.chat.QBChatService
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception


class RegisterViewModel  constructor(private val userRepository: UserRepository): BaseViewModel() {

    private val _registerState: MutableLiveData<RegisterState> = MutableLiveData()

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val registerState: LiveData<RegisterState>
    get() = _registerState

    fun submitRegister(userName: String, email: String, password: String, confirmPassword: String){

        eventShowLoading.value = true
        val state = checkFormValid(userName, email, password, confirmPassword)
        if (state == null){
            viewModelScope.launch {
                try {
                    val response = userRepository.signInWithEmailPassword(email, password).await()
                    if (response.user != null)
                        _registerState.value = RegisterState.REGISTER_COMPLETED

                }catch (e: Exception){
                    everError.value = e.message
                }
                eventShowLoading.value = false
            }
        }
        else{
            eventShowLoading.value = false
            _registerState.value = state
        }

    }

    private fun checkFormValid(userName: String, email: String, password: String, confirmPassword: String): RegisterState? {
        if(!ValidationUtils.checkEmailIsValidate(email))
            return RegisterState.EMAIL_INVALID

        if(!ValidationUtils.checkPasswordIsValidate(password))
            return RegisterState.PASSWORD_INVALID

        if(!ValidationUtils.checkConfirmPasswordIsValidate(password, confirmPassword))
            return RegisterState.CONFIRM_PASSWORD_INVALID

        if (!ValidationUtils.checkUserNameIsValidate(userName))
            return RegisterState.USERNAME_INVALID

        return null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}