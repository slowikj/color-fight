package com.example.colorfight.data.color

import com.example.colorfight.data.color.model.ColorCounts
import com.example.colorfight.data.color.model.ColorCountsConverter
import com.example.colorfight.data.color.model.ColorCountsDTO
import com.example.colorfight.data.color.model.ColorRequestDTO
import com.example.colorfight.data.socket.EventSocket
import com.example.colorfight.data.socket.EventSocketObservable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class AppColorManager(private val colorEventObservable: EventSocketObservable<ColorCountsDTO>,
                      private val colorEventSocket: EventSocket<ColorRequestDTO, ColorCountsDTO>
) : ColorManager {

    override fun getColorsObservable(): Observable<ColorCounts> =
        colorEventObservable
            .subscribeOn(Schedulers.io())
            .map { ColorCountsConverter.convertToUI(it) }

    override fun incrementColors(colorCounts: ColorCounts) {
        colorEventSocket.send(ColorCountsConverter.convertToRequest(colorCounts))
    }

}