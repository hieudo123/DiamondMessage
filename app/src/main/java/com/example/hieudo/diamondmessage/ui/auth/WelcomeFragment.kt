package com.example.hieudo.diamondmessage.ui.auth

import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : BaseFragment(), View.OnClickListener {

    override fun getLayoutId(): Int {
        return R.layout.fragment_welcome
    }

    override fun initView(view: View?) {
        activity?.window?.statusBarColor = context?.let { ContextCompat.getColor(it, R.color.mainColor) }!!
        fragWelcome_tvLogin.setOnClickListener(this)
        fragWelcome_tvRegister.setOnClickListener(this)


    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.fragWelcome_tvLogin ->{
                findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
            }
            R.id.fragWelcome_tvRegister ->{
                findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
            }
        }
    }
}