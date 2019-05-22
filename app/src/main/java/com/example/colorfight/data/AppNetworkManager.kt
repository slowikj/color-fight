package com.example.colorfight.data

import com.example.colorfight.data.color.ColorManager
import com.example.colorfight.data.color.model.ColorCounts
import io.reactivex.Completable
import io.reactivex.Observable

class AppNetworkManager (private val colorManager: ColorManager): NetworkManager {

    override fun incrementColors(colorCounts: ColorCounts): Completable =
        colorManager.incrementColors(colorCounts)


    override fun getColorCounts(): Observable<ColorCounts> =
        colorManager.getColorCounts()
}