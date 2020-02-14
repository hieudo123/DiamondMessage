package com.example.hieudo.diamondmessage.others.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appscyclone.aclibrary.view.ACRecyclerView
import com.appscyclone.aclibrary.view.adapter.ACBaseAdapter
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.others.constant.AppConstant
import com.example.hieudo.diamondmessage.others.holders.ItemReceiveMessageViewHolder
import com.example.hieudo.diamondmessage.others.holders.ItemSendMessageViewHolder
import com.quickblox.chat.QBChatService
import com.quickblox.chat.model.QBChatMessage

class MessageAdapter : ACBaseAdapter<ACRecyclerView.ACBaseViewHolder<*>> {
    private lateinit var messagesData: MutableList<QBChatMessage>

    constructor(data: MutableList<QBChatMessage>) : super(data) {
        messagesData = data
    }

    override fun getItemViewType(position: Int): Int {
        if (messagesData[position].senderId == QBChatService.getInstance().user.id)
            return AppConstant.ITEM_SEND_MESSAGE_TYPE
        return AppConstant.ITEM_RECEIVE_MESSAGE_TYPE
    }
    override fun onCreateBaseViewHolder(parent: ViewGroup?, viewType: Int): ACRecyclerView.ACBaseViewHolder<*>? {
        val li = LayoutInflater.from(parent!!.context)
        when (viewType) {
            AppConstant.ITEM_SEND_MESSAGE_TYPE -> {
                val itemViewSend: View =
                    li.inflate(R.layout.item_send_message, parent, false)
                return ItemSendMessageViewHolder(itemViewSend)
            }
            AppConstant.ITEM_RECEIVE_MESSAGE_TYPE -> {
                val itemViewReceive: View =
                    li.inflate(R.layout.item_receive_message, parent, false)
                return ItemReceiveMessageViewHolder(itemViewReceive)
            }
        }
        return null
    }
}