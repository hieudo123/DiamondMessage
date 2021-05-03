package com.example.hieudo.diamondmessage.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.data.models.UserModel
import com.example.hieudo.diamondmessage.di.component.ActivityComponent
import com.example.hieudo.diamondmessage.di.component.DaggerActivityComponent
import com.example.hieudo.diamondmessage.di.module.ActivityModule
import com.example.hieudo.diamondmessage.di.module.NetworkModule
import com.kaopiz.kprogresshud.KProgressHUD
import com.quickblox.users.model.QBUser
import io.github.inflationx.viewpump.ViewPumpContextWrapper

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun  getLayoutId():Int
    protected abstract fun initView()

    var onBackClick =
        View.OnClickListener { _: View? -> onBackPressed() }

    private var mProgressDialog: KProgressHUD? = null

    private var _activityComponent : ActivityComponent ?=  null

    val activityComponent: ActivityComponent?
    get() = _activityComponent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectActivityComponent()
        setContentView(getLayoutId())
        initView()
    }

    private fun injectActivityComponent() {
        val activityComponent: ActivityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .applicationComponent(BaseApplication.getInstance().applicationComponent)
            .networkModule(NetworkModule())
            .build()
        this._activityComponent = activityComponent
        _activityComponent?.inject(this)
    }

    fun showLoading(){
        if (mProgressDialog != null)
            mProgressDialog!!.show()
        else{
            mProgressDialog = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show()
        }
    }

    fun hideLoading(){
        mProgressDialog?.dismiss()
    }

    fun showActionbar(view: View?, title: String?){
        val tvTitle = view!!.findViewById<TextView>(R.id.actionbar_tvTitle)
        val vBack = view.findViewById<View>(R.id.actionbar_imgBack)
        if (tvTitle != null) {
            tvTitle.text = title
        }

        vBack?.setOnClickListener(onBackClick)
    }

    fun setCurrentUser (qbUser: QBUser) {
        BaseApplication.getInstance().setCurrentUser(qbUser)
    }

    fun getCurrentUser (): UserModel? = BaseApplication.getInstance().getCurrentUser()

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { ViewPumpContextWrapper.wrap(it) })
    }
}