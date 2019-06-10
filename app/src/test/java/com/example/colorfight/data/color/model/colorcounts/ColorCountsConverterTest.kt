package com.example.colorfight.data.color.model.colorcounts

import org.junit.Assert.*
import org.junit.Test

class ColorCountsConverterTest {

	@Test
	fun `convert colorCounts ui to dto`() {
		val ui = createColorCounts()
		val expectedDto = createColorCountsDto()
		assertEquals(expectedDto, ColorCountsConverter.convertToDTO(ui))
	}

	@Test
	fun `convert colorCounts dto to ui`() {
		val expectedUi = createColorCounts()
		val dto = createColorCountsDto()
		assertEquals(expectedUi, ColorCountsConverter.convertToUI(dto))
	}

	@Test
	fun `convert colorCounts to request`() {
		val expectedRequest = ColorRequestDTO(colors = createColorCountsDto())
		val ui = createColorCounts()
		assertEquals(expectedRequest, ColorCountsConverter.convertToRequest(ui))
	}

	private fun createColorCountsDto() = ColorCountsDTO(red = red, green = green, blue = blue)

	private fun createColorCounts() = ColorCounts(red = red, green = green, blue = blue)

	val red: Long = 5

	val green: Long = 10

	val blue: Long = 100
}