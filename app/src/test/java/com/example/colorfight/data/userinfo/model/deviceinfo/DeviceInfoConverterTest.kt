package com.example.colorfight.data.userinfo.model.deviceinfo

import org.junit.Assert.*
import org.junit.Test

class DeviceInfoConverterTest {

	@Test
	fun `convert dto to ui`() {
		val dto = createDto()
		val expectedUi = createUi()

		assertEquals(expectedUi, DeviceInfoConverter.convertToUI(dto))
	}

	@Test
	fun `convert ui to dto`() {
		val expectedDto = createDto()
		val ui = createUi()

		assertEquals(expectedDto, DeviceInfoConverter.convertToDTO(ui))
	}

	private val version = "version"

	private val product = "product"

	private val brand = "brand"

	private val device = "device"

	private fun createUi(): DeviceInfo {
		return DeviceInfo(
			firebaseDeviceId = null,
			version = version,
			product = product,
			brand = brand,
			device = device
		)
	}

	private fun createDto(): DeviceInfoDTO {
		return DeviceInfoDTO(
			firebaseDeviceId = null,
			version = version,
			product = product,
			brand = brand,
			device = device
		)
	}
}