package com.example.colorfight.di.fragment

import com.example.colorfight.di.app.ApplicationComponent
import com.example.colorfight.ui.about.AboutFragment
import com.example.colorfight.ui.colorpicker.ColorPickerFragment
import com.example.colorfight.ui.statistics.StatisticsFragment
import dagger.Component

@PerFragment
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {

    fun inject(colorPickerFragment: ColorPickerFragment)

    fun inject(statisticsFragment: StatisticsFragment)

    fun inject(aboutFragment: AboutFragment)

}