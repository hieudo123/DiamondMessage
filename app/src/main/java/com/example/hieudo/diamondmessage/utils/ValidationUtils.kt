package com.example.hieudo.diamondmessage.utils

import android.content.Context
import android.util.Patterns
import com.example.hieudo.diamondmessage.R

class ValidationUtils {
    companion object{
        fun checkEmailValid (email :String, password :String,context: Context) : String{
            var error =""
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                error = context.resources.getString(R.string.auth_email_valid)
            }
            else if (password.length < 8){
                error = context.resources.getString(R.string.auth_password_valid)
            }
            return error
        }
    }
}