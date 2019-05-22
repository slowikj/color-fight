package com.example.colorfight.di.app

import android.app.Application
import android.content.Context
import com.example.colorfight.ColorApp
import com.example.colorfight.data.NetworkManager
import dagger.Component

@PerApp
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: ColorApp)

    @ApplicationContext
    fun applicationContext(): Context

    fun application(): Application

    fun networkManager(): NetworkManager
}