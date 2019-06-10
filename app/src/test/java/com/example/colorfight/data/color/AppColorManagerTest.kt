package com.example.colorfight.data.color

import com.example.colorfight.data.color.model.colorcounts.ColorCounts
import com.example.colorfight.data.color.model.colorcounts.ColorCountsConverter
import com.example.colorfight.data.color.model.colorcounts.ColorCountsDTO
import com.example.colorfight.data.color.services.GetColorsService
import com.example.colorfight.data.color.services.SendColorsService
import com.example.colorfight.data.color.services.StatisticsService
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AppColorManagerTest {

	@Test
	fun `returns color counts when invoked getColorsObservable`() {
		val expectedColorCountsDto = ColorCountsDTO(red = 1, green = 2, blue = 3)

		val getColorsServiceMock = mock<GetColorsService> {
			on { getColorCounts() } doReturn Observable.just(expectedColorCountsDto)
		}
		val sendColorsServiceMock = mock<SendColorsService> {}
		val statisticsServiceMock = mock<StatisticsService> {}

		val colorsManager = AppColorManager(
			getColorsService = getColorsServiceMock,
			sendColorsService = sendColorsServiceMock,
			statisticsService = statisticsServiceMock
		)

		val testObserver = colorsManager
			.getColorsObservable()
			.test()

		testObserver
			.await()
			.assertResult(ColorCountsConverter.convertToUI(expectedColorCountsDto))

		verify(sendColorsServiceMock, never()).send(any())
		verify(statisticsServiceMock, never()).getPreviousDayStatistics()

		testObserver.dispose()
	}

	@Test
	fun `invoke send on sendColorService when incrementColors is invoked`() {
		val colorCounts = ColorCounts(red = 1, green = 2, blue = 3)

		val getColorsServiceMock = mock<GetColorsService> {}
		val sendColorsServiceMock = mock<SendColorsService> {}
		val statisticsServiceMock = mock<StatisticsService> {}

		val colorsManager = AppColorManager(
			getColorsService = getColorsServiceMock,
			sendColorsService = sendColorsServiceMock,
			statisticsService = statisticsServiceMock
		)

		colorsManager.incrementColors(colorCounts)
		verify(sendColorsServiceMock).send(ColorCountsConverter.convertToRequest(colorCounts))
		verify(getColorsServiceMock, never()).getColorCounts()
		verify(statisticsServiceMock, never()).getPreviousDayStatistics()
	}

	@Test
	fun `get ColorCounts with statistics when getPreviousDayStatistics is invoked`() {
		val expectedColorCounts = ColorCounts(red = 1, green = 2, blue = 3)

		val getColorsServiceMock = mock<GetColorsService> {}
		val sendColorsServiceMock = mock<SendColorsService> {}
		val statisticsServiceMock = mock<StatisticsService> {
			on {getPreviousDayStatistics()} doReturn Observable.just(ColorCountsConverter.convertToDTO(expectedColorCounts))
		}

		val colorsManager = AppColorManager(
			getColorsService = getColorsServiceMock,
			sendColorsService = sendColorsServiceMock,
			statisticsService = statisticsServiceMock
		)

		val testObserver = colorsManager
			.getPreviousDayStatistics()
			.test()

		testObserver
			.await()
			.assertValue(expectedColorCounts)

		testObserver.dispose()

		verify(getColorsServiceMock, never()).getColorCounts()
		verify(sendColorsServiceMock, never()).send(any())
	}
}