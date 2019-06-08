package com.example.colorfight.ui.main

import com.example.colorfight.data.userinfo.model.userinfo.UserInfo
import com.example.colorfight.ui.base.BaseContract

class MainContract {

	interface View: BaseContract.View {
	}

	interface Presenter<V: View>: BaseContract.Presenter<V> {

		fun sendUserInfo(userInfo: UserInfo)
	}
}