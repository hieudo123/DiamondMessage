package com.example.hieudo.diamondmessage.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivityModule(private var activity: AppCompatActivity) {

    @Provides
    @Singleton
    fun provideContext(): Context? {
        return activity
    }

    @Provides
    @Singleton
    fun provideActivity(): AppCompatActivity? {
        return activity
    }
}