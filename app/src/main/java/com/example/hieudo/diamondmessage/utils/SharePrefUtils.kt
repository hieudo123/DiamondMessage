package com.example.hieudo.diamondmessage.utils

import android.content.Context
import android.preference.PreferenceManager

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
    }
}