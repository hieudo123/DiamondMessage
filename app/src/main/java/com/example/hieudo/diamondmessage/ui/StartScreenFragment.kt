package com.example.hieudo.diamondmessage.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment
import com.example.hieudo.diamondmessage.others.constant.AppConstant
import com.example.hieudo.diamondmessage.utils.SharePrefUtils
import com.example.hieudo.diamondmessage.viewmodel.RegisterQBViewModel
import com.quickblox.chat.QBChatService
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException

class StartScreenFragment : BaseFragment() {
    var registerQBViewModel : RegisterQBViewModel ?= null
    override fun getLayoutId(): Int {
        return  R.layout.fragment_start_screen

    }

    override fun initView(view: View?) {
        setUpRegisterQBViewModel()
    }

    private fun setUpRegisterQBViewModel() {
        registerQBViewModel = ViewModelProviders.of(this).get(RegisterQBViewModel::class.java)
        registerQBViewModel!!.registerSession()
        registerQBViewModel!!.eventError.observe(this, Observer {

        })
        registerQBViewModel!!.registerQBReponse.observe(this, Observer {
            if (it != null){
                if (!SharePrefUtils.getString(context!!,AppConstant.EMAIL).isNullOrEmpty()
                    && !SharePrefUtils.getString(context!!,AppConstant.PASSWORD).isNullOrEmpty()){
                    findNavController().navigate(R.id.action_startScreenFragment_to_homeFragment)
                }
                else
                    findNavController().navigate(R.id.action_startScreenFragment_to_welcomeFragment)
            }
        })
    }
}