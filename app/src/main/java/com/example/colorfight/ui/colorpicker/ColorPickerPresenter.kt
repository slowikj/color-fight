package com.example.colorfight.ui.colorpicker

import com.example.colorfight.data.NetworkManager
import com.example.colorfight.data.color.model.colorcounts.ColorCounts
import com.example.colorfight.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ColorPickerPresenter<V : ColorPickerContract.View> @Inject constructor(private val networkManager: NetworkManager) :
    BasePresenter<V>(), ColorPickerContract.Presenter<V> {

    override fun attach(view: V) {
        super.attach(view)
        startObservingColorCounts()
    }

    override fun onRedClick(count: Long) {
        networkManager.incrementColors(ColorCounts(red = count))
    }

    override fun onGreenClick(count: Long) {
        networkManager.incrementColors(ColorCounts(green = count))
    }

    override fun onBlueClick(count: Long) {
        networkManager.incrementColors(ColorCounts(blue = count))
    }

    override fun requestColorCountsUpdate() {
        networkManager.incrementColors(ColorCounts())
    }

    override fun requestNetworkReconnection() {
        initCompositeDisposable()
        startObservingColorCounts()
    }

    private fun onColorChanged(counts: ColorCounts) {
        view?.updateBlueCounter(counts.blue)
        view?.updateGreenCounter(counts.green)
        view?.updateRedCounter(counts.red)
    }

    private fun startObservingColorCounts() {
        compositeDisposable.add(
            networkManager.getColorsObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { colorCounts -> onColorChanged(colorCounts) },
                    { this.view?.onNetworkError() },
                    { this.view?.onNetworkError() })
        )
    }

}