package com.example.hieudo.diamondmessage.others.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appscyclone.aclibrary.view.ACRecyclerView
import com.appscyclone.aclibrary.view.adapter.ACBaseAdapter
import com.example.hieudo.diamondmessage.R
import com.quickblox.users.model.QBUser

class UsersAdapter(data: MutableList<QBUser>?) : ACBaseAdapter<ACRecyclerView.ACBaseViewHolder<*>>(data) {
    override fun onCreateBaseViewHolder(parent: ViewGroup?, viewType: Int): ACRecyclerView.ACBaseViewHolder<*> {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_user_list,parent,false)
        return UsersViewHolder(view)
    }
    class UsersViewHolder(view : View?) : ACRecyclerView.ACBaseViewHolder<QBUser>(view){
        override fun bindData(data: QBUser?) {
            super.bindData(data)
            itemView.findViewById<TextView>(R.id.itemUserList_tvUsername).text = data!!.fullName
        }

    }
}