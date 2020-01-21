package com.example.hieudo.diamondmessage.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kaopiz.kprogresshud.KProgressHUD

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun  getLayoutId():Int
    protected abstract fun initView()
    private var mProgresDialog: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
    }

    fun showLoading(){
        if (mProgresDialog != null)
            mProgresDialog!!.show()
        else{
            mProgresDialog = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show()
        }
    }

    fun hideLoading(){
        mProgresDialog?.dismiss()
    }
}