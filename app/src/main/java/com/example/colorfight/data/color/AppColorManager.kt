package com.example.colorfight.data.color

import com.example.colorfight.data.color.model.colorcounts.ColorCounts
import com.example.colorfight.data.color.model.colorcounts.ColorCountsConverter
import com.example.colorfight.data.color.model.colorcounts.ColorCountsDTO
import com.example.colorfight.data.color.model.colorcounts.ColorRequestDTO
import com.example.colorfight.data.color.services.GetColorsService
import com.example.colorfight.data.color.services.SendColorsService
import com.example.colorfight.data.color.services.StatisticsService
import com.example.colorfight.data.common.socket.EventSocket
import com.example.colorfight.data.common.socket.EventSocketObservable
import com.example.colorfight.data.common.prepareNoneToApiObservable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class AppColorManager(
	private val getColorsService: GetColorsService,
	private val sendColorsService: SendColorsService,
	private val statisticsService: StatisticsService
) : ColorManager {

	override fun getColorsObservable(): Observable<ColorCounts> =
		getColorsService
			.getColorCounts()
			.subscribeOn(Schedulers.io())
			.map { ColorCountsConverter.convertToUI(it) }

	override fun incrementColors(colorCounts: ColorCounts) {
		sendColorsService
			.send(ColorCountsConverter.convertToRequest(colorCounts))
	}

	override fun getPreviousDayStatistics(): Observable<ColorCounts> =
		prepareNoneToApiObservable(
			apiRequest = { statisticsService.getPreviousDayStatistics() },
			converter = { ColorCountsConverter.convertToUI(it) }
		)
}