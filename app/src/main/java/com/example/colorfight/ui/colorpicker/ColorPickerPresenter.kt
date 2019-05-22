package com.example.colorfight.ui.colorpicker

import com.example.colorfight.data.NetworkManager
import com.example.colorfight.data.color.model.ColorCounts
import com.example.colorfight.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ColorPickerPresenter<V : ColorPickerContract.View> @Inject constructor(private val networkManager: NetworkManager) :
    BasePresenter<V>(), ColorPickerContract.Presenter<V> {

    override fun onRedClick(count: Long) {
        compositeDisposable?.add(
            createColorUpdateObservable(ColorCounts(red = count))
        )
    }

    override fun onGreenClick(count: Long) {
        compositeDisposable?.add(
            createColorUpdateObservable(ColorCounts(green = count))
        )
    }

    override fun onBlueClick(count: Long) {
        compositeDisposable?.add(
            createColorUpdateObservable(ColorCounts(blue = count))
        )
    }

    private fun createColorUpdateObservable(colorCounts: ColorCounts): Disposable {
        return networkManager
            .incrementColors(colorCounts)
            .andThen(networkManager.getColorCounts())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { updateColorCounts(it) },
                {})
    }

    private fun updateColorCounts(counts: ColorCounts): Unit? {
        view?.updateBlueCounter(counts.blue)
        view?.updateGreenCounter(counts.green)
        return view?.updateRedCounter(counts.red)
    }

    override fun requestColorCountsUpdate() {
        compositeDisposable?.add(
            networkManager.getColorCounts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {updateColorCounts(it)},
                    {}
                )
        )
    }

}