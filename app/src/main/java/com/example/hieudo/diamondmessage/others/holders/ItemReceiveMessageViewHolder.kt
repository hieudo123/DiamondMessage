package com.example.hieudo.diamondmessage.others.holders

import android.view.View
import android.widget.TextView
import com.appscyclone.aclibrary.view.ACRecyclerView
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.utils.AppUtils
import com.quickblox.chat.model.QBChatMessage

class ItemReceiveMessageViewHolder(view : View) : ACRecyclerView.ACBaseViewHolder<QBChatMessage>(view){
    private lateinit var tvContent : TextView
    private lateinit var tvDate : TextView
    override fun bindData(data: QBChatMessage?) {
        super.bindData(data)
        tvContent  = itemView.findViewById(R.id.itemReceiveMessaget_tvContent)
        tvDate = itemView.findViewById(R.id.itemReceiveMessaget_tvDate)
        tvContent.text = data!!.body
        tvDate.text = AppUtils.convertChatTime(itemView.context,data.dateSent,"MMM dd yyyy")
    }
}