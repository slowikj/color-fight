package com.example.colorfight.data.userinfo.services

import com.example.colorfight.data.userinfo.model.userinfo.UserInfoDTO
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface UserInfoService {

	@POST("device-data")
	fun addUserInfo(@Body userInfoDTO: UserInfoDTO): Completable

}