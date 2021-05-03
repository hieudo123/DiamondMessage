package com.example.hieudo.diamondmessage.ui.chatdetail
import android.view.View
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment

class ChatDetailFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_chat_detail
    }

    override fun initView(view: View?) {
        showActionbar(view,"")

    }

}