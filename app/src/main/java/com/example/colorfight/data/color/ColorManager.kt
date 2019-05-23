package com.example.colorfight.data.color

import com.example.colorfight.data.color.model.ColorCounts
import io.reactivex.Completable
import io.reactivex.Observable

interface ColorManager {

    interface OnColorsChangedListener {

        fun onColorChanged(counts: ColorCounts)
    }

    fun incrementColors(colorCounts: ColorCounts)

    fun closeConnection()

    val onColorChangedListeners: MutableList<OnColorsChangedListener>
}