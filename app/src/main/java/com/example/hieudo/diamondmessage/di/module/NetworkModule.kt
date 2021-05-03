package com.example.hieudo.diamondmessage.di.module

import com.example.hieudo.diamondmessage.repositories.UserRepository
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideUserRepository() : UserRepository = UserRepository()
}