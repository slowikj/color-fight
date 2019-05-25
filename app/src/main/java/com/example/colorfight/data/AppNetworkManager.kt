package com.example.colorfight.data

import com.example.colorfight.data.color.ColorManager
import com.example.colorfight.data.color.model.ColorCounts
import io.reactivex.Observable

class AppNetworkManager (private val colorManager: ColorManager): NetworkManager {

    override fun getColorsObservable(): Observable<ColorCounts> =
        colorManager.getColorsObservable()

    override fun incrementColors(colorCounts: ColorCounts) {
        colorManager.incrementColors(colorCounts)
    }

}