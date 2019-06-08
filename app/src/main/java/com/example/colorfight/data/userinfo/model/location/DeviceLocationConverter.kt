package com.example.colorfight.data.userinfo.model.location

import com.example.colorfight.data.common.Converter

object DeviceLocationConverter: Converter<DeviceLocationDTO, DeviceLocation> {

	override fun convertToUI(dto: DeviceLocationDTO): DeviceLocation =
		DeviceLocation(
			lat = dto.lat,
			lon = dto.lon)

	override fun convertToDTO(ui: DeviceLocation): DeviceLocationDTO =
		DeviceLocationDTO(
			lat = ui.lat,
			lon = ui.lon)
}