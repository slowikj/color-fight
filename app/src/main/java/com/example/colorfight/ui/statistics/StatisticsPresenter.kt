package com.example.colorfight.ui.statistics

import android.util.Log
import com.example.colorfight.data.NetworkManager
import com.example.colorfight.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class StatisticsPresenter<V : StatisticsContract.View> @Inject constructor(private val networkManager: NetworkManager) :
	BasePresenter<V>(), StatisticsContract.Presenter<V> {

	companion object {
		val TAG = StatisticsPresenter::class.java.toString()
	}

	override fun requestStatistics() {
		compositeDisposable.add(
			networkManager.getPreviousDayStatistics()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
					{ view?.setPreviousColors(it) },
					{ onGetPreviousDayColorsError() },
					{ }
				)
		)
	}

	private fun onGetPreviousDayColorsError() {
		Log.e(TAG, "onGetPreviousDayColorsError")
		view?.onNetworkError()
	}


}