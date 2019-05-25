package com.example.colorfight.ui.colorpicker

import com.example.colorfight.ui.base.BaseContract

interface ColorPickerContract {

    interface View: BaseContract.View {

        fun updateRedCounter(value: Long)

        fun updateGreenCounter(value: Long)

        fun updateBlueCounter(value: Long)

        fun onNetworkError()
    }

    interface Presenter<V: View>: BaseContract.Presenter<V> {
        fun onRedClick(count: Long)

        fun onGreenClick(count: Long)

        fun onBlueClick(count: Long)

        fun requestColorCountsUpdate()

    }
}