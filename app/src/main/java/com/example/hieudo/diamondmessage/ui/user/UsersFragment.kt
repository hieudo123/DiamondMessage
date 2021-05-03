package com.example.hieudo.diamondmessage.ui.user

import android.view.View
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment

class UsersFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_users
    }

    override fun initView(view: View?) {
        showActionbar(view, "")
    }

}