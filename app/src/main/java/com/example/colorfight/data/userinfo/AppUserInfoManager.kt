package com.example.colorfight.data.userinfo

import com.example.colorfight.data.userinfo.model.userinfo.UserInfo
import com.example.colorfight.data.userinfo.model.userinfo.UserInfoConverter
import com.example.colorfight.data.userinfo.services.UserInfoService
import com.example.colorfight.data.common.prepareObjectToCompletable
import io.reactivex.Completable

class AppUserInfoManager(private val userInfoService: UserInfoService) : UserInfoManager {

	override fun sendUserInfo(userInfo: UserInfo): Completable =
		prepareObjectToCompletable(
			body = userInfo,
			bodyConverter = { UserInfoConverter.convertToDTO(it) },
			apiRequest = { userInfoService.addUserInfo(it) }
		)
}