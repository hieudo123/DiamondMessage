package com.example.hieudo.diamondmessage.base

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.data.models.UserModel
import com.kaopiz.kprogresshud.KProgressHUD
import com.quickblox.users.model.QBUser

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun  getLayoutId():Int
    protected abstract fun initView()
    var onBackClick =
        View.OnClickListener { view: View? -> onBackPressed() }
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

    fun showActionbar(view: View?, title: String?){
        val tvTitle = view!!.findViewById<TextView>(R.id.actionbar_tvTitle)
        val vBack = view.findViewById<View>(R.id.actionbar_imgBack)
        if (tvTitle != null) {
            tvTitle.text = title
        }

        vBack?.setOnClickListener(onBackClick)
    }

    fun setCurrentUser (qbUser: QBUser){
        BaseApplication.getInstance().setCurrentUser(qbUser)
    }

    fun getCurrentUser (): UserModel? = BaseApplication.getInstance().getCurrentUser()
}