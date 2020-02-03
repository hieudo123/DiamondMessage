package com.example.hieudo.diamondmessage.utils

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.TextView
import com.example.hieudo.diamondmessage.R

class AppUtils {
    companion object{
        fun showHidePassword(context :Context, editText : EditText, textView : TextView) {
            if (editText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                textView.setText(context.getString(R.string.common_hide))
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
            } else {
                textView.setText(context.getString(R.string.common_show))
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance())
            }
        }
    }
}