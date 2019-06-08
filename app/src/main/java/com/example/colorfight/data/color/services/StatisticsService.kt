package com.example.colorfight.data.color.services

import com.example.colorfight.data.color.model.colorcounts.ColorCountsDTO
import io.reactivex.Observable
import retrofit2.http.GET

interface StatisticsService {

	@GET("colors-statistic")
	fun getPreviousDayStatistics(): Observable<ColorCountsDTO>

}