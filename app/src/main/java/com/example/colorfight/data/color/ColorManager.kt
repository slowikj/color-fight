package com.example.colorfight.data.color

import com.example.colorfight.data.color.model.colorcounts.ColorCounts
import io.reactivex.Observable

interface ColorManager {

    fun incrementColors(colorCounts: ColorCounts)

    fun getColorsObservable(): Observable<ColorCounts>

    fun getPreviousDayStatistics(): Observable<ColorCounts>

}