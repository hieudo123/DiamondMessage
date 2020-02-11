package com.example.hieudo.diamondmessage.utils

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import android.text.TextUtils
import com.example.hieudo.diamondmessage.data.models.UserModel
import com.example.hieudo.diamondmessage.others.constant.AppConstant
import com.google.gson.Gson


class SharePrefUtils {
    companion object{
        fun getString(context: Context?, key: String?): String? {
            val preferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            return preferences.getString(key, "")
        }

        fun setString(
            context: Context?, key: String?,
            content: String?
        ) {
            val edit =
                PreferenceManager.getDefaultSharedPreferences(context)
                    .edit()
            edit.putString(key, content)
            edit.apply()
        }

        fun saveUser(userModel: UserModel, context: Context?){
            setString(context,AppConstant.PREF_USER,Gson().toJson(userModel))
        }

        fun getUser(context: Context?) : UserModel{
            val jsonUser = getString(context, AppConstant.PREF_USER)
            var user: UserModel? = null
            if (!TextUtils.isEmpty(jsonUser)) {
                return  Gson().fromJson(jsonUser, UserModel::class.java)
            }
            return UserModel(0,"","","")
        }
        @SuppressLint("CommitPrefEdits")
        fun removeAllData(context: Context?) {
            val mySharePref = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = mySharePref.edit()
            editor.clear()
            editor.apply()
        }
    }
}