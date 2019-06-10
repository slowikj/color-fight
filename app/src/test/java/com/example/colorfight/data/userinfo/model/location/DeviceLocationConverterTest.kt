package com.example.colorfight.data.userinfo.model.location

import org.junit.Assert.assertEquals
import org.junit.Test

class DeviceLocationConverterTest {

	@Test
	fun `convert dto to ui`() {
		val lat = 12.toDouble()
		val lon = 16.toDouble()

		val dto = DeviceLocationDTO(lat = lat, lon = lon)
		val expectedUi = DeviceLocation(lat = lat, lon = lon)

		assertEquals(expectedUi, DeviceLocationConverter.convertToUI(dto))
	}

	@Test
	fun `convert ui to dto`() {
		val lat = 122.toDouble()
		val lon = 16.toDouble()

		val ui = DeviceLocation(lat = lat, lon = lon)
		val expectedDto = DeviceLocationDTO(lat = lat, lon = lon)

		assertEquals(expectedDto, DeviceLocationConverter.convertToDTO(ui))
	}
}