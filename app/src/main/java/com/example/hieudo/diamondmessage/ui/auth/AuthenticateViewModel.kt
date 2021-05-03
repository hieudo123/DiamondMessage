package com.example.hieudo.diamondmessage.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hieudo.diamondmessage.base.BaseViewModel
import com.example.hieudo.diamondmessage.others.enums.AuthenticateState
import com.example.hieudo.diamondmessage.repositories.UserRepository
import com.google.firebase.auth.FirebaseUser

class AuthenticateViewModel : BaseViewModel(){

    private val _authenticateState = MutableLiveData<AuthenticateState>()
    private val _firebaseUser = MutableLiveData<FirebaseUser> ()

    private val repository : UserRepository = UserRepository()

    val authenticateState : MutableLiveData<AuthenticateState>
    get() = this._authenticateState

    val firebaseUser : LiveData<FirebaseUser>
    get() = this._firebaseUser


    init {
        _authenticateState.value = AuthenticateState.UNAUTHENTICATED
        getCurrentUser()
    }

    private fun getCurrentUser() {
        if (repository.getCurrentUser() != null)
            _authenticateState.value = AuthenticateState.AUTHENTICATED
    }

    fun logout() {
        repository.logout()
        _authenticateState.value = AuthenticateState.UNAUTHENTICATED
    }


}