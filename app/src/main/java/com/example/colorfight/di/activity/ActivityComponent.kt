package com.example.colorfight.di.activity

import com.example.colorfight.di.app.ApplicationComponent
import com.example.colorfight.ui.MainActivity
import dagger.Component

@PerActivity
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
}