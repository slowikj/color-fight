package com.example.colorfight

import android.app.Application
import com.example.colorfight.di.app.ApplicationComponent
import com.example.colorfight.di.app.ApplicationModule
import com.example.colorfight.di.app.DaggerApplicationComponent

class ColorApp: Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent.inject(this)
    }
}