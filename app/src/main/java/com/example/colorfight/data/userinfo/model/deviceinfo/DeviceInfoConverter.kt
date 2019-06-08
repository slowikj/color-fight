package com.example.colorfight.data.userinfo.model.deviceinfo

import com.example.colorfight.data.common.Converter

object DeviceInfoConverter: Converter<DeviceInfoDTO, DeviceInfo> {

	override fun convertToUI(dto: DeviceInfoDTO): DeviceInfo =
		DeviceInfo(
			version = dto.version,
			product = dto.product,
			brand = dto.brand,
			device = dto.device
		)

	override fun convertToDTO(ui: DeviceInfo): DeviceInfoDTO =
		DeviceInfoDTO(
			version = ui.version,
			product = ui.product,
			brand = ui.brand,
			device = ui.device
		)

}