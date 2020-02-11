package com.example.hieudo.diamondmessage.ui.home

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.appscyclone.aclibrary.view.ACRecyclerView
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment
import com.example.hieudo.diamondmessage.others.adapters.ListChatAdapter
import com.example.hieudo.diamondmessage.others.adapters.UsersAdapter
import com.example.hieudo.diamondmessage.others.constant.AppConstant
import com.example.hieudo.diamondmessage.others.interfaces.ItemUserClickListener
import com.example.hieudo.diamondmessage.others.interfaces.itemChatClickListener
import com.example.hieudo.diamondmessage.utils.SharePrefUtils
import com.example.hieudo.diamondmessage.viewmodel.HomeViewModel
import com.quickblox.chat.QBChatService
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.users.model.QBUser
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, itemChatClickListener,
    ACRecyclerView.OnLoadMoreListener {
    private var isLoadMore: Boolean = false
    private var homeViewModel : HomeViewModel ?= null
    private var adapter : ListChatAdapter ?= null
    private var qbChatDialogs : MutableList<QBChatDialog>  = ArrayList()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View?) {
        init(view)
        setUp()
    }

    private fun setUp() {
        setUpUsersViewModel()
        setUpRecyclerView(qbChatDialogs)
    }

    private fun init(view: View?) {
        showActionbar(view,getCurrentUser()!!.fullname)

    }

    private fun setUpRecyclerView(data: MutableList<QBChatDialog>) {
        adapter = ListChatAdapter(data)
        adapter!!.setItemChatClickListener(this)
        fragHome_rvChatList.setLayoutManager(LinearLayoutManager(context))
        fragHome_rvChatList.setHasFixedSize(true)
        fragHome_rvChatList.adapter = adapter
        fragHome_rvChatList.setRefresh(this)
        fragHome_rvChatList.setLoadMore(this)
    }

    private fun setUpUsersViewModel() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
//        homeViewModel!!.getListDialog(isRefresh = true, isLoadMore = false)
        homeViewModel!!.getqbChatDialogsReponse().observe(this, Observer {
            qbChatDialogs.clear()
            qbChatDialogs.addAll(it)
            fragHome_rvChatList.isRefreshing = false
            adapter!!.notifyDataSetChanged()
        })
        homeViewModel!!.eventShowLoading.observe(this, Observer {
            if (it)
                showLoading()
            else
                hideLoading()
        })
        homeViewModel!!.eventError.observe(this, Observer {
            Toast.makeText(context!!,it,Toast.LENGTH_SHORT).show()
        })
    }

    override fun onRefresh() {
        homeViewModel!!.getListDialog(true,false)
    }

    override fun onItemClickListener(qbChatDialog: QBChatDialog) {
        setQbChatDialog(qbChatDialog)
        findNavController().navigate(R.id.action_homeFragment_to_chatDetailFragment)
    }

    override fun onLoadMore() {
        isLoadMore = true
    }

}