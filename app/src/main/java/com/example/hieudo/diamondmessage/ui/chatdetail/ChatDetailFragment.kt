package com.example.hieudo.diamondmessage.ui.chatdetail
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment
import com.example.hieudo.diamondmessage.others.adapters.MessageAdapter
import com.example.hieudo.diamondmessage.utils.KeyboardUtils
import com.example.hieudo.diamondmessage.viewmodel.MainViewModel
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import kotlinx.android.synthetic.main.fragment_chat_detail.*

class ChatDetailFragment : BaseFragment(), View.OnClickListener {
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter: MessageAdapter

    private val messagesData : MutableList<QBChatMessage> = arrayListOf()
    override fun getLayoutId(): Int {
        return R.layout.fragment_chat_detail
    }

    override fun initView(view: View?) {
        showActionbar(view,"")
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        initData()
        setUpRecyclerView()
    }

    private fun initData() {
        mainViewModel.getQBChatDialog().observe(this, Observer {
            showActionbar(view,it.name)
        })
        mainViewModel.getHistoryChat().observe(this, Observer {
            messagesData.addAll(it)
            adapter.notifyDataSetChanged()
        })
        mainViewModel.inCommingMessage().observe(this, Observer {
            messagesData.add(0,it)
            adapter.notifyDataSetChanged()
        })
        fragChatDetail_tvSend.setOnClickListener(this)
    }

    private fun setUpRecyclerView(){
        adapter = MessageAdapter(messagesData)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        fragChatDetail_rvMessages.setLayoutManager(linearLayoutManager)
        fragChatDetail_rvMessages.setHasFixedSize(true)
        fragChatDetail_rvMessages.isDrawingCacheEnabled = true
        fragChatDetail_rvMessages.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        fragChatDetail_rvMessages.adapter = adapter
    }

    override fun onClick(view: View?) {
        when (view!!.id){
            R.id.fragChatDetail_tvSend->{
                sendMessage(fragChatDetail_etInputMessage.text.toString())
            }
        }
    }

    private fun sendMessage(message: String) {
        mainViewModel.sendMessage(message).observe(this, Observer {
            fragChatDetail_etInputMessage.text.clear()
            messagesData.add(0,it)
            adapter.notifyDataSetChanged()
            fragChatDetail_rvMessages.verticalScrollbarPosition = 0
        })

    }

    override fun onStop() {
        super.onStop()
        mainViewModel.leaveDialog()
        KeyboardUtils.hideSoftKeyboard(activity!!)

    }
}