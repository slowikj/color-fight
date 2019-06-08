package com.example.colorfight.data.userinfo

import com.example.colorfight.data.userinfo.model.userinfo.UserInfo
import io.reactivex.Completable

interface UserInfoManager {

	fun sendUserInfo(userInfo: UserInfo): Completable
}