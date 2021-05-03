package com.example.hieudo.diamondmessage.ui.setting

import android.view.View
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseFragment

class SettingFragment : BaseFragment(){
    override fun getLayoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun initView(view: View?) {
        showActionbar(view,resources.getString(R.string.setting_title))

    }

}