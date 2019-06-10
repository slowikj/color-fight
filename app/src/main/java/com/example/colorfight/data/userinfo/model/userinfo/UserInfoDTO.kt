package com.example.colorfight.data.userinfo.model.userinfo

import com.example.colorfight.data.userinfo.model.deviceinfo.DeviceInfoDTO
import com.example.colorfight.data.userinfo.model.location.DeviceLocationDTO
import com.fasterxml.jackson.annotation.JsonProperty

data class UserInfoDTO(val deviceInfoDTO: DeviceInfoDTO,
					   val locationDTO: DeviceLocationDTO? = null)
