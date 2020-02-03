package com.example.hieudo.diamondmessage

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.*
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.hieudo.diamondmessage.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun initView() {
        if (SDK_INT >= M) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }
    }


}
