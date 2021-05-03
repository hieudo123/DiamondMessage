package com.example.hieudo.diamondmessage.ui.auth.register

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment
import com.example.hieudo.diamondmessage.factories.RegisterViewModelFactory
import com.example.hieudo.diamondmessage.others.enums.RegisterState
import com.example.hieudo.diamondmessage.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

class RegisterFragment : BaseFragment(){

    @Inject
    lateinit var registerViewModelFactory: RegisterViewModelFactory

    private  lateinit var registerViewModel: RegisterViewModel

    override fun getLayoutId(): Int {
        return R.layout.fragment_register
    }

    override fun initView(view: View?) {
        showActionbar(view,"")
        getActivityComponent()?.inject(this)
        registerViewModel = ViewModelProviders.of(this, registerViewModelFactory).get(RegisterViewModel::class.java)
        handleShowLoading()
        handleError()
        handleResult()
        fragRegister_tvRegister.setOnClickListener {
            registerViewModel.submitRegister(fragRegister_etUsername.text.toString(),
                fragRegister_etEmail.text.toString(), fragRegister_etPassword.text.toString(),
                fragRegister_etConfirmPassword.text.toString())

        }
    }

    private fun handleResult(){
        registerViewModel.registerState.observe(this, Observer {
            when(it){
                RegisterState.EMAIL_INVALID -> {
                    showAlertDialog(getString(R.string.register_fail_title), getString(R.string.auth_email_valid))
                }
                RegisterState.PASSWORD_INVALID -> {
                    showAlertDialog(getString(R.string.register_fail_title), getString(R.string.auth_password_valid))
                }
                RegisterState.CONFIRM_PASSWORD_INVALID ->{
                    showAlertDialog(getString(R.string.register_fail_title), getString(R.string.auth_confirm_password_valid))
                }
                RegisterState.REGISTER_COMPLETED -> {

                }
            }
        })
    }

    private fun handleShowLoading() {
        registerViewModel.eventShowLoading.observe(this, Observer {
            if(it)
                showLoading()
            else
                hideLoading()
        })
    }

    private fun handleError() {
        registerViewModel.everError.observe(this, Observer { error ->
            showAlertDialog(getString(R.string.register_fail_title), error)
        })
    }

    private fun showAlertDialog(title: String, message: String){
        context?.let {
            AppUtils.alertDialog(it, getString(R.string.register_fail_title), message)
        }
    }

}