package com.example.hieudo.diamondmessage.ui.auth

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment
import com.example.hieudo.diamondmessage.data.models.UserModel
import com.example.hieudo.diamondmessage.others.constant.AppConstant
import com.example.hieudo.diamondmessage.utils.KeyboardUtils
import com.example.hieudo.diamondmessage.utils.SharePrefUtils
import com.example.hieudo.diamondmessage.viewmodel.LoginViewModel
import com.example.hieudo.diamondmessage.viewmodel.RegisterViewModel
import com.example.hieudo.diamondmessage.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment(), View.OnClickListener {
    private lateinit var loginViewModel: LoginViewModel

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initView(view: View?) {
        showActionbar(view,"")
        if (view != null) {
            KeyboardUtils.setupUI(view, this.activity!!)
        }
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        setUpLoginViewModel(loginViewModel)
        fragLogin_tvLogin.setOnClickListener(this)
        fragLogin_tvForgotPassword.setOnClickListener(this)
    }

    private fun setUpLoginViewModel(viewModel: LoginViewModel) {
        viewModel.eventError.observe(this, Observer {
            Toast.makeText(context!!,it,Toast.LENGTH_SHORT).show()
        })
        viewModel.isValidation.observe(this, Observer {
            if (it){
                login()
            }
        })
        viewModel.eventShowLoading.observe(this, Observer {
            if (it)
                showLoading()
            else
                hideLoading()
        })
    }

    private fun login() {
        loginViewModel.login(fragLogin_etEmail.text.toString(),fragLogin_etPassword.text.toString()).observe(this, Observer {
            setCurrentUser(it)
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        })
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.fragLogin_tvLogin ->{
                loginViewModel.checkEmailValid(fragLogin_etEmail.text.toString(),fragLogin_etPassword.text.toString(),context!!)
            }
            R.id.fragLogin_tvForgotPassword ->{

            }
        }
    }

}