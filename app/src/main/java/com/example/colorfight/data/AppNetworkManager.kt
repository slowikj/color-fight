package com.example.colorfight.data

import com.example.colorfight.data.color.ColorManager
import com.example.colorfight.data.color.model.ColorCounts

class AppNetworkManager (private val colorManager: ColorManager): NetworkManager {

    override fun incrementColors(colorCounts: ColorCounts) {
        colorManager.incrementColors(colorCounts)
    }

    override fun closeConnection() {
        colorManager.closeConnection()
    }

    override val onColorChangedListeners: MutableList<ColorManager.OnColorsChangedListener>
        get() = colorManager.onColorChangedListeners
}