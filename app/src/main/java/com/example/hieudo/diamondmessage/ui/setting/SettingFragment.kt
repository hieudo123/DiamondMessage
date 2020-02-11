package com.example.hieudo.diamondmessage.ui.setting

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment
import com.example.hieudo.diamondmessage.utils.SharePrefUtils
import com.example.hieudo.diamondmessage.viewmodel.SettingViewModel
import com.quickblox.chat.QBChatService
import com.quickblox.users.QBUsers
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : BaseFragment(), View.OnClickListener {
    lateinit var settingViewModel: SettingViewModel
    override fun getLayoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun initView(view: View?) {
        showActionbar(view,resources.getString(R.string.setting_title))
        setUpSettingViewModel()
        fragSetting_tvLogout.setOnClickListener(this)
    }

    private fun setUpSettingViewModel() {
        settingViewModel = ViewModelProviders.of(this).get(SettingViewModel::class.java)
        settingViewModel.qbLogOutReponse.observe(this, Observer {
            if (it)
                removeData()
        })
        settingViewModel.eventShowLoading.observe(this, Observer {
            if (it)
                showLoading()
            else
                hideLoading()
        })
        settingViewModel.eventError.observe(this, Observer {
            Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
        })
    }

    private fun removeData() {
        SharePrefUtils.removeAllData(context)
        findNavController().navigate(R.id.action_settingFragment_to_welcomeFragment)

    }

    override fun onClick(view: View?) {
        when (view!!.id){
            R.id.fragSetting_tvLogout ->{
                logout()
            }
        }
    }

    private fun logout() {
        settingViewModel.logOut()
    }
}