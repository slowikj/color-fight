package com.example.colorfight.ui.statistics

import com.example.colorfight.data.color.model.colorcounts.ColorCounts
import com.example.colorfight.ui.base.BaseContract

class StatisticsContract {

	interface View: BaseContract.View {

		fun setPreviousColors(colorCounts: ColorCounts)
	}

	interface Presenter<V: View>: BaseContract.Presenter<V> {

		fun requestStatistics()
	}
}