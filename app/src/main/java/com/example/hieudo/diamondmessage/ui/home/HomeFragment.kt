package com.example.hieudo.diamondmessage.ui.home

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment
import com.example.hieudo.diamondmessage.others.constant.AppConstant
import com.example.hieudo.diamondmessage.utils.SharePrefUtils
import com.example.hieudo.diamondmessage.viewmodel.LoginViewModel
import com.example.hieudo.diamondmessage.viewmodel.UsersViewModel
import com.quickblox.chat.QBChatService
import com.quickblox.users.model.QBUser

class HomeFragment : BaseFragment() {
    private lateinit var loginViewModel: LoginViewModel
    private var usersViewModel : UsersViewModel ?= null

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View?) {
        showActionbar(view,"")
        setUpUsersViewModel()
        setUpLoginViewModel()
    }

    private fun setUpUsersViewModel() {
        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        usersViewModel!!.qbUsersList.observe(this, Observer {

        })
        usersViewModel!!.eventShowLoading.observe(this, Observer {
            if (it)
                showLoading()
            else
                hideLoading()
        })
        usersViewModel!!.eventError.observe(this, Observer {
            Toast.makeText(context!!,it,Toast.LENGTH_SHORT).show()
        })
    }

    private fun setUpLoginViewModel() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val email = SharePrefUtils.getString(context,AppConstant.EMAIL)
        val password = SharePrefUtils.getString(context,AppConstant.PASSWORD)
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()){
            if (!QBChatService.getInstance().isLoggedIn)
                loginViewModel.login(email,password)
        }
        loginViewModel.loginReponse.observe(this, Observer {
            handlerUserData(it)
        })
        loginViewModel.eventError.observe(this, Observer {
            Toast.makeText(context!!,it,Toast.LENGTH_SHORT).show()
        })
        loginViewModel.eventShowLoading.observe(this, Observer {
            if (it)
                showLoading()
            else
                hideLoading()
        })
    }

    private fun handlerUserData(it: QBUser?) {
        SharePrefUtils.setString(this.context!!, AppConstant.EMAIL,it!!.email)
        SharePrefUtils.setString(this.context!!, AppConstant.PASSWORD,it.password)
        showActionbar(view,it.fullName)
        setCurrentUser(it)
    }
}