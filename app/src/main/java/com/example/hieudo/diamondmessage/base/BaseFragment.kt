package com.example.hieudo.diamondmessage.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    protected abstract fun getLayoutId(): Int

    protected abstract fun initView(view: View?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        initView(view)
        return view
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

}