package com.example.hieudo.diamondmessage.others.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appscyclone.aclibrary.view.ACRecyclerView
import com.appscyclone.aclibrary.view.adapter.ACBaseAdapter
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.others.holders.ListChatViewHolder
import com.example.hieudo.diamondmessage.others.interfaces.itemChatClickListener
import com.quickblox.chat.model.QBChatDialog as QBChatDialog

class ListChatAdapter : ACBaseAdapter<ACRecyclerView.ACBaseViewHolder<*>> {
    private var listChatData: List<QBChatDialog>
    private lateinit var itemChatClickListener : itemChatClickListener

    constructor(data: MutableList<QBChatDialog>) : super(data) {
        listChatData = data
    }

    fun setItemChatClickListener(itemChatClickListener: itemChatClickListener){
        this.itemChatClickListener = itemChatClickListener
    }
    override fun onCreateBaseViewHolder(parent: ViewGroup?, viewType: Int): ACRecyclerView.ACBaseViewHolder<*> {
        val view : View = LayoutInflater.from(parent!!.context).inflate(R.layout.item_list_chat,parent,false)
        return ListChatViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ACRecyclerView.ACBaseViewHolder<*>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            if (itemChatClickListener != null)
                itemChatClickListener.onItemClickListener(this.listChatData[position])
        }
    }
}