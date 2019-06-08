package com.example.colorfight.data.userinfo.model.userinfo

import com.example.colorfight.data.userinfo.model.deviceinfo.DeviceInfoDTO
import com.example.colorfight.data.userinfo.model.location.DeviceLocationDTO

data class UserInfoDTO(val deviceInfoDTO: DeviceInfoDTO,
					   val locationDTO: DeviceLocationDTO? = null)
