package com.example.colorfight.data

import com.example.colorfight.data.color.ColorManager
import com.example.colorfight.data.color.model.colorcounts.ColorCounts
import com.example.colorfight.data.userinfo.AppUserInfoManager
import com.example.colorfight.data.userinfo.UserInfoManager
import com.example.colorfight.data.userinfo.model.userinfo.UserInfo
import io.reactivex.Completable
import io.reactivex.Observable

class AppNetworkManager (private val colorManager: ColorManager,
                         private val userInfoManager: UserInfoManager
): NetworkManager {

    override fun getPreviousDayStatistics(): Observable<ColorCounts> =
        colorManager.getPreviousDayStatistics()

    override fun sendUserInfo(userInfo: UserInfo): Completable =
        userInfoManager.sendUserInfo(userInfo)

    override fun getColorsObservable(): Observable<ColorCounts> =
        colorManager.getColorsObservable()

    override fun incrementColors(colorCounts: ColorCounts) {
        colorManager.incrementColors(colorCounts)
    }

}