package com.example.colorfight.data.color

import com.example.colorfight.data.color.model.colorcounts.ColorCounts
import com.example.colorfight.data.color.model.colorcounts.ColorCountsConverter
import com.example.colorfight.data.color.model.colorcounts.ColorCountsDTO
import com.example.colorfight.data.color.model.colorcounts.ColorRequestDTO
import com.example.colorfight.data.color.services.StatisticsService
import com.example.colorfight.data.common.socket.EventSocket
import com.example.colorfight.data.common.socket.EventSocketObservable
import com.example.colorfight.data.common.prepareNoneToApiObservable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class AppColorManager(
	private val colorEventObservable: EventSocketObservable<ColorCountsDTO>,
	private val colorEventSocket: EventSocket<ColorRequestDTO, ColorCountsDTO>,
	private val statisticsService: StatisticsService
) : ColorManager {

	override fun getColorsObservable(): Observable<ColorCounts> =
		colorEventObservable
			.subscribeOn(Schedulers.io())
			.map { ColorCountsConverter.convertToUI(it) }

	override fun incrementColors(colorCounts: ColorCounts) {
		colorEventSocket.send(ColorCountsConverter.convertToRequest(colorCounts))
	}

	override fun getPreviousDayStatistics(): Observable<ColorCounts> =
		prepareNoneToApiObservable(
			apiRequest = { statisticsService.getPreviousDayStatistics() },
			converter = { ColorCountsConverter.convertToUI(it) }
		)
}