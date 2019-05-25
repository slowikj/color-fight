package com.example.colorfight.data.color

import com.example.colorfight.data.color.model.ColorCounts
import io.reactivex.Completable
import io.reactivex.Observable

interface ColorManager {

    fun incrementColors(colorCounts: ColorCounts)

    fun getColorsObservable(): Observable<ColorCounts>

}