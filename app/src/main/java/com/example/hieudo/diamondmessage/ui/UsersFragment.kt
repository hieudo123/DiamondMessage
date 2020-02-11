package com.example.hieudo.diamondmessage.ui

import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment
import com.example.hieudo.diamondmessage.others.adapters.UsersAdapter
import com.example.hieudo.diamondmessage.others.interfaces.ItemUserClickListener
import com.example.hieudo.diamondmessage.viewmodel.UsersViewModel
import com.quickblox.users.model.QBUser
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : BaseFragment(), ItemUserClickListener, SwipeRefreshLayout.OnRefreshListener {
    private var usersViewModel : UsersViewModel?= null
    private var adapter : UsersAdapter?= null
    private var qbUsers : MutableList<QBUser>  = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.fragment_users
    }

    override fun initView(view: View?) {
        showActionbar(view, getCurrentUser()!!.fullname)
        setUpRecyclerView(qbUsers)
        setUpUsersViewModel()
    }

    private fun setUpUsersViewModel() {
        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        showLoading()
        usersViewModel!!.getAllUser()
        usersViewModel!!.getQBUsersList().observe(this, Observer {
            if (fragUsers_rvUserList.isRefreshing)
                qbUsers.clear()
            if (qbUsers.size <= 0)
                qbUsers.addAll(it)
            adapter!!.notifyDataSetChanged()
            fragUsers_rvUserList.isRefreshing = false
        })
        usersViewModel!!.eventShowLoading.observe(this, Observer {
            if (it)
                showLoading()
            else
                hideLoading()
        })
        usersViewModel!!.eventError.observe(this, Observer {
            Toast.makeText(context!!,it, Toast.LENGTH_SHORT).show()
        })
    }


    private fun setUpRecyclerView(data: MutableList<QBUser>) {
        adapter = UsersAdapter(data)
        adapter!!.setItemUserClickListener(this)
        fragUsers_rvUserList.setLayoutManager(LinearLayoutManager(context))
        fragUsers_rvUserList.setHasFixedSize(true)
        fragUsers_rvUserList.adapter = adapter
        fragUsers_rvUserList.setRefresh(this)
    }

    override fun onItemClickListener(qbUser: QBUser) {

    }

    override fun onRefresh() {

    }
}