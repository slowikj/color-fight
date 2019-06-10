package com.example.colorfight.data.userinfo.model.userinfo

import com.example.colorfight.data.userinfo.model.deviceinfo.DeviceInfo
import com.example.colorfight.data.userinfo.model.deviceinfo.DeviceInfoDTO
import com.example.colorfight.data.userinfo.model.location.DeviceLocation
import com.example.colorfight.data.userinfo.model.location.DeviceLocationDTO
import org.junit.Assert.*
import org.junit.Test

class UserInfoConverterTest {

	@Test
	fun `convert ui to dto`() {
		val ui = createUserInfo()
		val expectedDto = createUserInfoDto()
		assertEquals(expectedDto, UserInfoConverter.convertToDTO(ui))
	}

	@Test
	fun `convert dto to ui`() {
		val expectedUi = createUserInfo()
		val dto = createUserInfoDto()
		assertEquals(expectedUi, UserInfoConverter.convertToUI(dto))
	}

	private fun createUserInfoDto(): UserInfoDTO {
		return UserInfoDTO(
			deviceInfoDTO = createDeviceInfoDto(),
			locationDTO = createLocationDto()
		)
	}

	private fun createUserInfo(): UserInfo {
		return UserInfo(
			deviceInfo = createDeviceInfo(),
			location = createLocation()
		)
	}

	private val lat = 12.toDouble()

	private val lon = 15.toDouble()

	private fun createLocation() =
		DeviceLocation(lat = lat, lon = lon)

	private fun createLocationDto() =
		DeviceLocationDTO(lat = lat, lon = lon)

	private val version = "version"

	private val product = "product"

	private val brand = "brand"

	private val device = "device"

	private fun createDeviceInfo(): DeviceInfo {
		return DeviceInfo(
			firebaseDeviceId = null,
			version = version,
			product = product,
			brand = brand,
			device = device
		)
	}

	private fun createDeviceInfoDto(): DeviceInfoDTO {
		return DeviceInfoDTO(
			firebaseDeviceId = null,
			version = version,
			product = product,
			brand = brand,
			device = device
		)
	}

}