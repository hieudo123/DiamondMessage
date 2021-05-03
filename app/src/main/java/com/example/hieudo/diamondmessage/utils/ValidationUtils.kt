package com.example.hieudo.diamondmessage.utils

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import com.example.hieudo.diamondmessage.R

class ValidationUtils {
    companion object{

        fun checkEmailIsValidate (email: String) = (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())

        fun checkPasswordIsValidate (password: String) = password.length >= 8

        fun checkConfirmPasswordIsValidate (password: String, confirmPassword: String) =
            confirmPassword == password

        fun checkUserNameIsValidate (userName: String) = userName.trim().isNotEmpty()
    }
}