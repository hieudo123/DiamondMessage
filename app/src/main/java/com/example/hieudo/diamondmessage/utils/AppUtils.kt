package com.example.hieudo.diamondmessage.utils

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.TextView
import com.example.hieudo.diamondmessage.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

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
        fun convertChatTime(context: Context?, dataTime: Long, formatType: String?): String? {
            val now = System.currentTimeMillis() / 1000
            val messageTime = Calendar.getInstance()
            messageTime.timeInMillis = dataTime * 1000
            val today = Calendar.getInstance()
            val timetemp = now - dataTime
            return if (timetemp < 86400 && today[Calendar.DATE] == messageTime[Calendar.DATE]) {
                val format =
                    SimpleDateFormat("hh:mm a", Locale.US)
                format.format(dataTime * 1000)
            } else {
                val format =
                    SimpleDateFormat(formatType, Locale.US)
                format.format(dataTime * 1000)
            }
        }

        fun alertDialog(context: Context, title: String, message: String){
            MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(context.resources.getString(R.string.common_ok)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}