package com.example.hieudo.diamondmessage

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.hieudo.diamondmessage.base.BaseActivity
import com.example.hieudo.diamondmessage.others.service.LoginService
import com.example.hieudo.diamondmessage.viewmodel.MainViewModel
import com.quickblox.chat.model.QBChatDialog
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    private lateinit var mainViewModel: MainViewModel
    lateinit var navController: NavController
    lateinit var appBarConfiguration : AppBarConfiguration
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun initView() {
        if (SDK_INT >= M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setUpNavController()
        setMainVIewModel()
    }

    private fun startLoginService() {
        LoginService.Companion.start(this)
    }
    private fun setMainVIewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.inCommingMessage()
        mainViewModel.addSessionListener()
    }

    private fun setUpNavController() {
        navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupWithNavController(activityMain_bottomMenu,navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.e("DESTINATION", destination.label.toString())
            when(destination.id){
                R.id.startScreenFragment ->{
                    showNavMenu(false)
                }
                R.id.homeFragment -> {
                    showNavMenu(true)
                }
                R.id.chatDetailFragment ->{
                    showNavMenu(false)
                }
            }
        }
    }

    private fun showNavMenu(isShow: Boolean) {
        if (isShow){
            activityMain_line.visibility = View.VISIBLE
            activityMain_bottomMenu.visibility = View.VISIBLE
        }
        else{
            activityMain_bottomMenu.visibility = View.GONE
            activityMain_line.visibility = View.GONE

        }
    }

    fun setQbChatDialog(qbChatDialog: QBChatDialog){
        mainViewModel.setQBChatDialog(qbChatDialog)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }
    override fun onResume() {
        super.onResume()
        startLoginService()
    }
}
