package com.example.colorfight.di.fragment

import androidx.fragment.app.Fragment
import com.example.colorfight.di.app.ApplicationComponent
import dagger.Component

@PerFragment
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {

    fun inject(fragment: Fragment)

}