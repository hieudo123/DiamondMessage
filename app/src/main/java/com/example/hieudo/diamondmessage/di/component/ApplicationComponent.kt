package com.example.hieudo.diamondmessage.di.component

import com.example.hieudo.diamondmessage.base.BaseApplication
import com.example.hieudo.diamondmessage.di.module.ApplicationModule
import com.example.stackexchange.viewmodelexample.di.scope.ApplicationScope
import dagger.Component
import javax.inject.Singleton

@ApplicationScope
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(baseApplication: BaseApplication)
}