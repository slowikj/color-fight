package com.example.colorfight.ui.main

import android.util.Log
import com.example.colorfight.data.NetworkManager
import com.example.colorfight.data.userinfo.model.userinfo.UserInfo
import com.example.colorfight.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject

class MainPresenter<V : MainContract.View> @Inject constructor(private val networkManager: NetworkManager) :
	BasePresenter<V>(), MainContract.Presenter<V> {

	companion object {

		val TAG = MainPresenter::class.java.toString()
	}

	override fun sendUserInfo(userInfo: UserInfo) {
		compositeDisposable.add(
			networkManager.sendUserInfo(userInfo)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
					{ onSendUserInfoCompleted() },
					{ onSendUserError(it) }
				)
		)
	}

	private fun onSendUserError(e: Throwable?) {
		e?.printStackTrace()
	}

	private fun onSendUserInfoCompleted() {
		Log.i(TAG, "sending userInfo completed")
	}

}