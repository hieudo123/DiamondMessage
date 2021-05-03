package com.example.hieudo.diamondmessage.base

import android.app.Application
import com.example.hieudo.diamondmessage.data.models.UserModel
import com.example.hieudo.diamondmessage.di.component.ApplicationComponent
import com.example.hieudo.diamondmessage.di.component.DaggerApplicationComponent
import com.example.hieudo.diamondmessage.di.module.ApplicationModule
import com.example.hieudo.diamondmessage.others.constant.AppConstant
import com.example.hieudo.diamondmessage.utils.SharePrefUtils
import com.quickblox.auth.session.QBSettings
import com.quickblox.core.StoringMechanism
import com.quickblox.users.model.QBUser
import io.github.inflationx.viewpump.ViewPump
import javax.inject.Inject


class BaseApplication: Application() {

    @Inject
    lateinit var mViewPump: ViewPump

    private  var userModel :UserModel ?= null
    private  var _applicationComponent : ApplicationComponent ?= null

    val applicationComponent : ApplicationComponent?
    get() = this._applicationComponent

    companion object {
        private lateinit var instance: BaseApplication

        @Synchronized
        fun getInstance(): BaseApplication = instance

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        injectAppComponent()
        initQuickBlocApplication()
    }

    private fun injectAppComponent() {
        val applicationComponent : ApplicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        _applicationComponent = applicationComponent
        _applicationComponent?.inject(this)

        ViewPump.init(mViewPump)
    }

    private fun initQuickBlocApplication() {
        QBSettings.getInstance().storingMehanism = StoringMechanism.UNSECURED
        QBSettings.getInstance().init(applicationContext, AppConstant.APPLICATION_ID, AppConstant.AUTH_KEY, AppConstant.AUTH_SECRET);
        QBSettings.getInstance().accountKey = AppConstant.ACCOUNT_KEY

    }


    fun setCurrentUser(qbUser: QBUser) {
      
    }

    fun getCurrentUser (): UserModel? = userModel
}