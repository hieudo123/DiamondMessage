package com.example.hieudo.diamondmessage.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseActivity
import com.example.hieudo.diamondmessage.others.enums.AuthenticateState
import com.example.hieudo.diamondmessage.others.service.LoginService
import com.example.hieudo.diamondmessage.ui.auth.AuthenticateViewModel
import com.quickblox.chat.QBChatService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private lateinit var navController: NavController
    private lateinit var authenticateViewModel: AuthenticateViewModel

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun initView() {
//        if (SDK_INT >= M) {
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        }

        setUpNavController()
        setUpAuthenticateViewModel()
    }

    private fun setUpAuthenticateViewModel() {
        authenticateViewModel = ViewModelProviders.of(this).get(AuthenticateViewModel::class.java)

        authenticateViewModel.authenticateState.observe(this, Observer {
            when(it){
                AuthenticateState.UNAUTHENTICATED -> {

                }
                AuthenticateState.AUTHENTICATED -> {
                    val graph = navController.navInflater.inflate(R.navigation.nav_graph)
                    graph.startDestination = R.id.homeFragment
                    navController.graph = graph
                }
            }
        })

        authenticateViewModel.firebaseUser.observe(this, Observer {

        })
    }

    private fun startLoginService() {
        if (!QBChatService.getInstance().isLoggedIn)
            LoginService.Companion.start(this)
    }


    private fun setUpNavController() {
        navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupWithNavController(activityMain_bottomMenu,navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.e("DESTINATION", destination.label.toString())
            when(destination.id){
                R.id.homeFragment -> {
                    showNavMenu(true)
                }
                R.id.chatDetailFragment ->{
                    showNavMenu(false)
                }
                R.id.welcomeFragment ->{
                    showNavMenu(false)
                }
                R.id.usersFragment ->{
                    showNavMenu(true)
                }
                R.id.settingFragment ->{
                    showNavMenu(true)
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

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

    override fun onResume() {
        super.onResume()
//        startLoginService()
    }

}
