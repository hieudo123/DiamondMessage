package com.example.hieudo.diamondmessage.others.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.appscyclone.aclibrary.view.ACRecyclerView
import com.appscyclone.aclibrary.view.adapter.ACBaseAdapter
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.others.interfaces.ItemUserClickListener
import com.quickblox.users.model.QBUser

class UsersAdapter : ACBaseAdapter<ACRecyclerView.ACBaseViewHolder<*>> {
    private var  usersData : MutableList<QBUser>
     private var itemUserClickListener : ItemUserClickListener ?= null

    constructor(data: MutableList<QBUser>) : super(data) {
        this.usersData = data
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
    override fun getItemCount(): Int {
        return usersData.size
    }

    fun setItemUserClickListener(itemUserClickListener : ItemUserClickListener){
        this.itemUserClickListener = itemUserClickListener
    }
    override fun onCreateBaseViewHolder(parent: ViewGroup?, viewType: Int): ACRecyclerView.ACBaseViewHolder<*> {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_user_list,parent,false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: ACRecyclerView.ACBaseViewHolder<*>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            if (itemUserClickListener != null)
                itemUserClickListener?.onItemClickListener(this.usersData!![position])
        }
    }
    class UsersViewHolder(view : View?) : ACRecyclerView.ACBaseViewHolder<QBUser>(view){
        override fun bindData(data: QBUser?) {
            super.bindData(data)
            itemView.findViewById<TextView>(R.id.itemUserList_tvUsername).text = data!!.fullName
            val currentTime = System.currentTimeMillis()
            val userLastRequestAtTime = data.lastRequestAt.time
            if((currentTime - userLastRequestAtTime) > 5*60*1000){
                itemView.findViewById<ImageView>(R.id.itemUserList_ivUserStatus).visibility = View.GONE
            }
            else
                itemView.findViewById<ImageView>(R.id.itemUserList_ivUserStatus).visibility = View.VISIBLE
        }

    }
}