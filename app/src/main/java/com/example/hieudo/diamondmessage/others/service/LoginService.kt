package com.example.hieudo.diamondmessage.others.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hieudo.diamondmessage.base.BaseApplication
import com.example.hieudo.diamondmessage.others.constant.AppConstant
import com.example.hieudo.diamondmessage.utils.SharePrefUtils
import com.quickblox.chat.QBChatService
import com.quickblox.chat.connections.tcp.QBTcpChatConnectionFabric
import com.quickblox.chat.connections.tcp.QBTcpConfigurationBuilder
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser


private const val EXTRA_COMMAND_TO_SERVICE = "command_for_service"
private const val EXTRA_QB_USER = "qb_user"

private const val COMMAND_NOT_FOUND = 0
private const val COMMAND_LOGIN = 1
private const val COMMAND_LOGOUT = 2

private const val EXTRA_PENDING_INTENT = "pending_Intent"

class LoginService : Service(){
    private val isEventLogin : MutableLiveData<Boolean> = MutableLiveData()
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginService::class.java)
            intent.putExtra(EXTRA_COMMAND_TO_SERVICE, COMMAND_LOGIN)
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, LoginService::class.java)
            context.stopService(intent)
        }

        fun logout(context: Context) {
            val intent = Intent(context, LoginService::class.java)
            intent.putExtra(EXTRA_COMMAND_TO_SERVICE, COMMAND_LOGOUT)
            context.startService(intent)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createChatService()
        Log.d(LoginService::class.java.simpleName, "Service onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LoginService::class.java.simpleName, "Service onCreated()")
        loginToChat()

        return super.onStartCommand(intent, flags, startId)
    }

    private fun createChatService() {
        val configurationBuilder = QBTcpConfigurationBuilder()
        configurationBuilder.socketTimeout = 0
        QBChatService.setConnectionFabric(QBTcpChatConnectionFabric(configurationBuilder))
        QBChatService.setDebugEnabled(true)
    }
    private fun loginToChat() {
//        if (SharePrefUtils.getUser(applicationContext) != null){
//            val userModel = SharePrefUtils.getUser(applicationContext)
//            Log.d(LoginService::class.java.simpleName, "$userModel")
//            val qbUserLocal: QBUser = QBUser()
//            qbUserLocal.email = userModel?.email
//            qbUserLocal.password = userModel?.password
//            QBUsers.signIn(qbUserLocal).performAsync(object : QBEntityCallback<QBUser> {
//                override fun onSuccess(qbUser: QBUser?, bundle: Bundle?) {
//                    qbUserLocal.id = qbUser!!.id
//                    userModel?.id = qbUser.id
//                    Log.e(LoginService::class.java.simpleName,qbUser.toString())
//                    QBChatService.getInstance().login(qbUserLocal, object : QBEntityCallback<Any?> {
//                        override fun onSuccess(o: Any?, bundle: Bundle) {
//                            Log.e(LoginService::class.java.simpleName,"Loin onSuccess")
//                        }
//                        override fun onError(e: QBResponseException) {
//                            Log.e(LoginService::class.java.simpleName,"Loin onFail ${e.message}")
//                        }
//                    })
//                }
//                override fun onError(p0: QBResponseException?) {Log.e("LOGIN_SERVICE","Loin onFail ${p0!!.message}")}
//            })
//
//        }
    }
    override fun onTaskRemoved(rootIntent: Intent) {
        Log.d(LoginService::class.java.simpleName, "Service onTaskRemoved()")
        restartService()
        super.onTaskRemoved(rootIntent)
//        if (!isCallServiceRunning()) {
//            restartService()
//            //startService(Intent(this, LoginService.class))
//        }

    }
    private fun restartService() {
        val restartServiceIntent = Intent(applicationContext, this.javaClass)
        restartServiceIntent.setPackage(packageName)
        val restartServicePendingIntent: PendingIntent = PendingIntent.getService(applicationContext, 0,
            restartServiceIntent,
            0)
        val alarmService = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmService.set(
            AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePendingIntent
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LoginService::class.java.simpleName, "Service onDestroy()")
    }
}