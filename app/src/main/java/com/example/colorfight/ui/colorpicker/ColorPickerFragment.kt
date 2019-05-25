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

    private val pendingActions: MutableList<() -> Unit> = mutableListOf()

    override fun updateRedCounter(value: Long) {
        invokeOrAddAsPending { activity?.runOnUiThread {  redButton.text = value.toString() } }
    }

    override fun updateGreenCounter(value: Long) {
        invokeOrAddAsPending { activity?.runOnUiThread { greenButton.text = value.toString()  } }
    }

    override fun updateBlueCounter(value: Long) {
        invokeOrAddAsPending { activity?.runOnUiThread { blueButton.text = value.toString() } }
    }

    private fun invokeOrAddAsPending(action: () -> Unit) {
        if(isVisible) {
            action()
        } else {
            pendingActions.add(action)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.requestColorCountsUpdate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        greenButton.setOnClickListener { presenter.onGreenClick(1) }
        redButton.setOnClickListener { presenter.onRedClick(1) }
        blueButton.setOnClickListener { presenter.onBlueClick(1) }

        pendingActions.forEach { it() }
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