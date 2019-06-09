package com.example.colorfight.di.fragment

import androidx.fragment.app.Fragment
import com.example.colorfight.ui.colorpicker.ColorPickerContract
import com.example.colorfight.ui.colorpicker.ColorPickerPresenter
import com.example.colorfight.ui.statistics.StatisticsContract
import com.example.colorfight.ui.statistics.StatisticsPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: Fragment) {

	@Provides
	fun provideFragment(): Fragment = fragment

	@PerFragment
	@Provides
	fun provideColorPickerPresenter(presenter: ColorPickerPresenter<ColorPickerContract.View>)
			: ColorPickerContract.Presenter<ColorPickerContract.View> = presenter

	@PerFragment
	@Provides
	fun provideStatisticsPresenter(presenter: StatisticsPresenter<StatisticsContract.View>):
			StatisticsContract.Presenter<StatisticsContract.View> = presenter

}