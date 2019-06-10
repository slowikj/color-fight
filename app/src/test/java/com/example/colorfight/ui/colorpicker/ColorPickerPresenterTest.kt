package com.example.colorfight.ui.colorpicker

import com.example.colorfight.data.NetworkManager
import com.example.colorfight.data.color.model.colorcounts.ColorCounts
import com.nhaarman.mockitokotlin2.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ColorPickerPresenterTest {

	@Test
	fun `perform increment red color on network manager when onRedColorClick`() {
		testAppropriateColorIncrementation(
			colorClicks = 1,
			onColorClick = { onRedClick(it) },
			colorCounts = ColorCounts(red = 1)
		)
	}

	@Test
	fun `perform increment blue color on network manager when onBlueColorClick`() {
		testAppropriateColorIncrementation(
			colorClicks = 1,
			onColorClick = { onBlueClick(it) },
			colorCounts = ColorCounts(blue = 1)
		)
	}

	@Test
	fun `perform increment green color on network manager when onGreenColorClick`() {
		testAppropriateColorIncrementation(
			colorClicks = 1,
			onColorClick = { onGreenClick(it) },
			colorCounts = ColorCounts(green = 1)
		)
	}

	private fun testAppropriateColorIncrementation(
		colorClicks: Long,
		onColorClick: ColorPickerPresenter<ColorPickerFragment>.(Long) -> Unit,
		colorCounts: ColorCounts
	) {
		val networkManagerMock = mock<NetworkManager> {
			on { incrementColors(colorCounts) } doAnswer {}
		}

		val colorPickerPresenter = ColorPickerPresenter<ColorPickerFragment>(networkManagerMock)
		colorPickerPresenter.onColorClick(colorClicks)
		verify(networkManagerMock, times(1)).incrementColors(colorCounts)
		verifyNoMoreInteractions(networkManagerMock)
	}
}