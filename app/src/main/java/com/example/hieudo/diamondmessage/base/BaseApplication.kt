package com.example.hieudo.diamondmessage.base

import android.app.Application
import com.example.hieudo.diamondmessage.data.models.UserModel
import com.example.hieudo.diamondmessage.others.constant.AppConstant
import com.example.hieudo.diamondmessage.utils.SharePrefUtils
import com.quickblox.auth.session.QBSettings
import com.quickblox.core.StoringMechanism
import com.quickblox.users.model.QBUser


class BaseApplication: Application() {
    private  var userModel :UserModel ?= null
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

        if (SharePrefUtils.getUser(applicationContext).id!=0){
            userModel = SharePrefUtils.getUser(applicationContext)
        }
    }

    fun setCurrentUser(qbUser: QBUser) {
        userModel = UserModel(qbUser.id,qbUser.email,qbUser.password,qbUser.fullName)
        SharePrefUtils.saveUser(userModel!!,applicationContext)
    }

    fun getCurrentUser (): UserModel? = userModel
}