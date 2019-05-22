package com.example.colorfight.data.color

import com.example.colorfight.data.color.model.ColorCounts
import com.example.colorfight.data.color.model.ColorCountsConverter
import com.example.colorfight.utils.prepareNoneToApiObservable
import com.example.colorfight.utils.prepareObjectToCompletable
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class AppColorManager (private val colorApiService: ColorApiService) : ColorManager {

    override fun incrementColors(colorCounts: ColorCounts): Completable =
        prepareObjectToCompletable(
            body = colorCounts,
            bodyConverter = { ColorCountsConverter.convertToDTO(it) },
            apiRequest = { colorApiService.incrementColors(it) }
        )

    override fun getColorCounts(): Observable<ColorCounts> =
        prepareNoneToApiObservable(
            apiRequest = { colorApiService.getColors() },
            converter = { ColorCountsConverter.convertToUI(it) }
        )
}