package com.example.colorfight.data.color.services

import com.example.colorfight.data.color.model.colorcounts.ColorCountsDTO
import com.example.colorfight.data.common.socket.EventSocketObservable
import io.reactivex.Observable

class AppGetColorsService(private val eventColorsObservable: EventSocketObservable<ColorCountsDTO>)
	: GetColorsService {

	override fun getColorCounts(): Observable<ColorCountsDTO> = eventColorsObservable
}