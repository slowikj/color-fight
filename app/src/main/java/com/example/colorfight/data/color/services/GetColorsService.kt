package com.example.colorfight.data.color.services

import com.example.colorfight.data.color.model.colorcounts.ColorCountsDTO
import io.reactivex.Observable

interface GetColorsService {

	fun getColorCounts(): Observable<ColorCountsDTO>
}