package com.example.hieudo.diamondmessage.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hieudo.diamondmessage.repositories.UserRepository
import com.example.hieudo.diamondmessage.ui.auth.register.RegisterViewModel
import javax.inject.Inject

class RegisterViewModelFactory @Inject constructor(private val userRepository: UserRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(userRepository) as T
    }
}