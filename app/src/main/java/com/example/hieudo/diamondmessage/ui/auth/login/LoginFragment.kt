package com.example.hieudo.diamondmessage.ui.auth.login

import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment(), View.OnClickListener {
    private lateinit var loginViewModel: LoginViewModel

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initView(view: View?) {
        showActionbar(view,"")
        fragLogin_tvLogin.setOnClickListener(this)
        fragLogin_tvForgotPassword.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fragLogin_tvLogin ->{

            }
            R.id.fragLogin_tvForgotPassword -> {
                findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
            }
        }
    }


}