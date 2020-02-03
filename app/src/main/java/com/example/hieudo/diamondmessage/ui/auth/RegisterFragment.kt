package com.example.hieudo.diamondmessage.ui.auth

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment
import com.example.hieudo.diamondmessage.others.constant.AppConstant
import com.example.hieudo.diamondmessage.utils.AppUtils
import com.example.hieudo.diamondmessage.utils.KeyboardUtils
import com.example.hieudo.diamondmessage.utils.SharePrefUtils
import com.example.hieudo.diamondmessage.utils.ValidationUtils
import com.example.hieudo.diamondmessage.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment(), View.OnClickListener {
    private  lateinit var registerViewModel: RegisterViewModel
    private var fullName = ""
    override fun getLayoutId(): Int {
        return R.layout.fragment_register
    }

    override fun initView(view: View?) {
        showActionbar(view,"")
        if (view != null) {
            activity?.let { KeyboardUtils.setupUI(view, it) }
        }
        if (!this::registerViewModel.isInitialized) {
            registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
            setupRegisterViewmodel(registerViewModel)
        }
        addOnTextChange(fragRegister_etFirstName)
        addOnTextChange(fragRegister_etLastName)
        addOnTextChange(fragRegister_etEmail)
        addOnTextChange(fragRegister_etPassword)
        fragRegister_tvRegister.setOnClickListener(this)
        fragRegister_tvShow.setOnClickListener(this)
        enableRegisterButton()
    }

    private fun enableRegisterButton() {
        fragRegister_tvRegister.isEnabled = fragRegister_etFirstName.text.isNotEmpty()
                && fragRegister_etLastName.text.isNotEmpty()
                && fragRegister_etEmail.text.isNotEmpty() && fragRegister_etPassword.text.isNotEmpty()
    }

    private fun addOnTextChange(editText: EditText?) {
        editText!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(editable: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, befor: Int, count: Int) {
                if (s != null) {
                    enableRegisterButton()
                }
            }
        })
    }

    private fun setupRegisterViewmodel(viewModel: RegisterViewModel) {
        viewModel.registerReponse.observe(this , Observer {
            viewModel.login(it.email,fragRegister_etPassword.text.toString())
        })
        viewModel.loginReponse.observe(this, Observer {
            SharePrefUtils.setString(this.context!!,AppConstant.EMAIL,it.email)
            SharePrefUtils.setString(this.context!!,AppConstant.PASSWORD,it.password)
            setCurrentUser(it)
            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
        })
        viewModel.eventError.observe(this, Observer {
            Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
        })

        viewModel.isValidation.observe(this, Observer {
            if (it){
                registerViewModel.register(fragRegister_etEmail.text.toString(),fragRegister_etPassword.text.toString(),fullName)
            }
        })
        viewModel.eventShowLoading.observe(this, Observer {
            if (it)
                showLoading()
            else
                hideLoading()
        })
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.fragRegister_tvRegister ->{
                register(fragRegister_etEmail.text.toString(),
                    fragRegister_etPassword.text.toString(),
                    fragRegister_etFirstName.text.toString(),
                    fragRegister_etLastName.text.toString())
            }
            R.id.fragRegister_tvShow ->{
                AppUtils.showHidePassword(this.context!!,fragRegister_etPassword,fragRegister_tvShow)
            }
        }
    }

    fun register(email :String, password :String, firstName :String, lastName :String) {
        fullName = "$firstName $lastName"
        registerViewModel.checkEmailValid(email,password,this.context!!)
    }
}