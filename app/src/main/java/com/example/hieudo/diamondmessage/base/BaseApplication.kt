package com.example.hieudo.diamondmessage.base

import android.app.Application
import com.example.hieudo.diamondmessage.others.constant.AppConstant
import com.quickblox.auth.session.QBSettings
import com.quickblox.core.StoringMechanism
import com.quickblox.users.model.QBUser


class BaseApplication: Application() {
    private lateinit var qbUser :QBUser
    companion object {
        private lateinit var instance: BaseApplication

        @Synchronized
        fun getInstance(): BaseApplication = instance

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        init()
    }
    fun init() {
        QBSettings.getInstance().storingMehanism = StoringMechanism.UNSECURED
        QBSettings.getInstance().init(applicationContext, AppConstant.APPLICATION_ID, AppConstant.AUTH_KEY, AppConstant.AUTH_SECRET);
        QBSettings.getInstance().accountKey = AppConstant.ACCOUNT_KEY
    }

    fun setCurrentUser(qbUser: QBUser) {
        this.qbUser = qbUser
    }

    fun getCurrentUser (): QBUser = this.qbUser
}