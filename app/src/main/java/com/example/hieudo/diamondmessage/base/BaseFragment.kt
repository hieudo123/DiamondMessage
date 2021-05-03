package com.example.hieudo.diamondmessage.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hieudo.diamondmessage.ui.MainActivity
import com.example.hieudo.diamondmessage.data.models.UserModel
import com.example.hieudo.diamondmessage.di.component.ActivityComponent
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.users.model.QBUser

abstract class BaseFragment : Fragment() {
    protected abstract fun getLayoutId(): Int

    protected abstract fun initView(view: View?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    open fun showLoading() {
        if (activity is BaseActivity) {
            (activity as BaseActivity?)!!.showLoading()
        }
    }

    open fun hideLoading() {
        if (activity is BaseActivity) {
            (activity as BaseActivity?)!!.hideLoading()
        }
    }

    open fun showActionbar(view: View?, title: String?){
        if (activity is BaseActivity){
            (activity as BaseActivity).showActionbar(view,title)
        }
    }

    fun setCurrentUser (qbUser: QBUser){
        if (activity is BaseActivity)
            (activity as BaseActivity).setCurrentUser(qbUser)
    }

    fun getCurrentUser (): UserModel? {
        if (activity is BaseActivity) return (activity as BaseActivity).getCurrentUser()
        return  null
    }

    fun getActivityComponent () : ActivityComponent? {
        if (activity is BaseActivity)
            return (activity as BaseActivity).activityComponent
        return null
    }

}