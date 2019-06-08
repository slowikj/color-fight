package com.example.colorfight.data.userinfo.model.userinfo

import com.example.colorfight.data.userinfo.model.deviceinfo.DeviceInfo
import com.example.colorfight.data.userinfo.model.location.DeviceLocation

data class UserInfo(val deviceInfo: DeviceInfo,
					val location: DeviceLocation? = null) {
}