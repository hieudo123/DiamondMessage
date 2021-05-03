package com.example.hieudo.diamondmessage.di.component

import com.example.hieudo.diamondmessage.base.BaseActivity
import com.example.hieudo.diamondmessage.di.module.ActivityModule
import com.example.hieudo.diamondmessage.di.module.NetworkModule
import com.example.hieudo.diamondmessage.di.scope.ActivityScope
import com.example.hieudo.diamondmessage.ui.auth.register.RegisterFragment
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class],modules = [ActivityModule::class, NetworkModule::class])
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(registerFragment: RegisterFragment)

}