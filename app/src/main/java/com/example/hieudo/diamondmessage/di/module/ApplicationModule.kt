package com.example.hieudo.diamondmessage.di.module

import android.app.Application
import com.example.hieudo.diamondmessage.R
import com.example.hieudo.diamondmessage.base.BaseApplication
import com.example.stackexchange.viewmodelexample.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import javax.inject.Singleton


@Module
class ApplicationModule(baseApplication: BaseApplication) {
    private var application: BaseApplication? = baseApplication

    @Provides
    @ApplicationScope
    fun provideApplication(): Application? {
        return this.application
    }

    @Provides
    @Singleton
    fun provideViewPumDefaultConfig(): ViewPump {
        return ViewPump.builder().addInterceptor(
            CalligraphyInterceptor(
                CalligraphyConfig.Builder()
                    .setDefaultFontPath(
                        application!!.applicationContext
                            .getString(R.string.font_roboto_regular)
                    )
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            )
        ).build()
    }
}