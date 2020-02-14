package com.example.hieudo.diamondmessage.others.holders

import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.appscyclone.aclibrary.view.ACRecyclerView
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.utils.AppUtils
import com.quickblox.chat.model.QBChatDialog

class ListChatViewHolder(view: View) : ACRecyclerView.ACBaseViewHolder<QBChatDialog>(view) {
    override fun bindData(data: QBChatDialog?) {
        super.bindData(data)
        val unreadCount : TextView = itemView.findViewById(R.id.itemListChat_tvUnreadCount)
        val unreadBackground = itemView.findViewById<FrameLayout>(R.id.itemListChat_frUnreadCount)
        itemView.findViewById<TextView>(R.id.itemListChat_tvName).text = data!!.name
        itemView.findViewById<TextView>(R.id.itemListChat_tvLastMessage).text = data.lastMessage
        itemView.findViewById<TextView>(R.id.itemListChat_tvChatTime).text = AppUtils.convertChatTime(itemView.context,data.lastMessageDateSent,"MMM dd yyyy")
        if (data.unreadMessageCount > 0){
            unreadBackground.visibility = View.VISIBLE
            unreadCount.text = "${data.unreadMessageCount}"
        }
        else
            unreadBackground.visibility = View.GONE
    }
}