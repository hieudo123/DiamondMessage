package com.example.hieudo.diamondmessage.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
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
        val handler = Handler()
        handler.postDelayed(object :Runnable{
            override fun run() {
                if (SharePrefUtils.getUser(context).id != 0){
                    val graph = findNavController().graph
                    graph.startDestination = R.id.homeFragment
                    findNavController().graph = graph
                }
                else
                    findNavController().navigate(R.id.action_startScreenFragment_to_welcomeFragment)            }
        },3000)

    }
}