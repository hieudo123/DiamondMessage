package com.example.hieudo.diamondmessage.ui.chatdetail
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment
import com.example.hieudo.diamondmessage.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_chat_detail.*

class ChatDetailFragment : BaseFragment(), View.OnClickListener {
    lateinit var mainViewModel: MainViewModel
    override fun getLayoutId(): Int {
        return R.layout.fragment_chat_detail
    }

    override fun initView(view: View?) {
        showActionbar(view,"")
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        initData()
    }

    private fun initData() {
        mainViewModel.getQBChatDialog().observe(this, Observer {
            showActionbar(view,it.name)
        })

        fragChatDetail_tvSend.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id){
            R.id.fragChatDetail_tvSend->{
                mainViewModel.sendMessage(fragChatDetail_etInputMessage.text.toString())
                fragChatDetail_etInputMessage.setText("")
            }
        }
    }

}