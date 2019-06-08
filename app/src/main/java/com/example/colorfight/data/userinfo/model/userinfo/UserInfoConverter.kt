package com.example.colorfight.data.userinfo.model.userinfo

import com.example.colorfight.data.common.Converter
import com.example.colorfight.data.userinfo.model.deviceinfo.DeviceInfoConverter
import com.example.colorfight.data.userinfo.model.location.DeviceLocationConverter

object UserInfoConverter: Converter<UserInfoDTO, UserInfo> {

	override fun convertToUI(dto: UserInfoDTO): UserInfo =
		UserInfo(
			deviceInfo = DeviceInfoConverter.convertToUI(dto.deviceInfoDTO),
			location = DeviceLocationConverter.convertToUI(dto.locationDTO)
		)

	override fun convertToDTO(ui: UserInfo): UserInfoDTO =
		UserInfoDTO(
			deviceInfoDTO = DeviceInfoConverter.convertToDTO(ui.deviceInfo),
			locationDTO = DeviceLocationConverter.convertToDTO(ui.location)
		)

}