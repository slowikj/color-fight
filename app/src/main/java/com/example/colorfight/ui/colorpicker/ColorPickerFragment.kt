package com.example.colorfight.ui.colorpicker

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.colorfight.R
import com.example.colorfight.ui.base.BaseFragment
import kotlinx.android.synthetic.main.color_picker_fragment_layout.*
import javax.inject.Inject

class ColorPickerFragment
    : BaseFragment(),
    ColorPickerContract.View {

    @Inject
    lateinit var presenter: ColorPickerContract.Presenter<ColorPickerContract.View>

    override val layoutId: Int
        get() = R.layout.color_picker_fragment_layout

    override fun updateRedCounter(value: Long) {
        activity?.runOnUiThread {  redButton.text = value.toString() }
    }

    override fun updateGreenCounter(value: Long) {
        activity?.runOnUiThread { greenButton.text = value.toString()  }
    }

    override fun updateBlueCounter(value: Long) {
        activity?.runOnUiThread { blueButton.text = value.toString() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        greenButton.setOnClickListener { presenter.onGreenClick(1) }
        redButton.setOnClickListener { presenter.onRedClick(1) }
        blueButton.setOnClickListener { presenter.onBlueClick(1) }

        presenter.requestColorCountsUpdate()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fragmentComponent.inject(this)
    }

    override fun attachPresenter() {
        presenter.attach(this)
    }

    override fun detachPresenter() {
        presenter.detach()
    }
}