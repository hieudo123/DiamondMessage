package com.example.hieudo.diamondmessage.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class KeyboardUtils {
    companion object{
        @SuppressLint("ClickableViewAccessibility")
        fun setupUI(view: View, activity: Activity) {
            if (view !is EditText) {
                view.setOnTouchListener { v: View?, event: MotionEvent? ->
                    val handler = Handler()
                    handler.postDelayed({ hideSoftKeyboard(activity) }, 30)
                    false
                }
            }
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val innerView = view.getChildAt(i)
                    setupUI(innerView, activity)
                }
            }
        }

        fun hideSoftKeyboard(activity: Activity) {
            try {
                val inputMethodManager = activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                val view = activity.currentFocus
                if (view != null && inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    if (view is EditText) {
                        clearFocusEditText(arrayOf(view))
                    }
                }
            } catch (ignored: Exception) {
            }
        }

        private fun clearFocusEditText(editTexts: Array<EditText>) {
            for (editText in editTexts) {
                editText.clearFocus()
            }
        }

        fun showSoftKeyboard(view: View?, activity: Activity) {
            val imm =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }

        fun showSoftKeyboardForEditText(editText: EditText, activity: Activity) {
            editText.postDelayed({
                editText.requestFocus()
                editText.setSelection(editText.text.length)
                val imm =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(
                    InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                )
            }, 300)
        }
    }
}