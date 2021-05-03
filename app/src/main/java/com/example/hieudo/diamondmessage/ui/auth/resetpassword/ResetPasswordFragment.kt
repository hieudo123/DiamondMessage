package com.example.hieudo.diamondmessage.ui.auth.resetpassword

import android.view.View
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment

class ResetPasswordFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return  R.layout.fragment_reset_password
    }

    override fun initView(view: View?) {
        showActionbar(view,"")
    }
}