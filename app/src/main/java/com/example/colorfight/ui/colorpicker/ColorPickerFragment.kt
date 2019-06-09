package com.example.colorfight.ui.colorpicker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import com.example.colorfight.R
import com.example.colorfight.ui.base.BaseFragment
import com.example.colorfight.ui.common.ButtonAnimator
import com.example.colorfight.ui.common.SharedPreferencesKeys
import com.example.colorfight.ui.common.getSharedPreferences
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.color_picker_fragment_layout.*
import javax.inject.Inject

class ColorPickerFragment
	: BaseFragment(),
	ColorPickerContract.View, SharedPreferences.OnSharedPreferenceChangeListener {

	@Inject
	lateinit var presenter: ColorPickerContract.Presenter<ColorPickerContract.View>

	override val layoutId: Int
		get() = R.layout.color_picker_fragment_layout

	private val pendingActions: MutableList<() -> Unit> = mutableListOf()

	private val buttonAnimator: ButtonAnimator = ButtonAnimator(
		targetScale = 1.4f,
		scalingDuration = 200
	)

	override fun updateRedCounter(value: Long) {
		invokeOrAddAsPending { activity?.runOnUiThread { redButton.text = value.toString() } }
	}

	override fun updateGreenCounter(value: Long) {
		invokeOrAddAsPending { activity?.runOnUiThread { greenButton.text = value.toString() } }
	}

	override fun updateBlueCounter(value: Long) {
		invokeOrAddAsPending { activity?.runOnUiThread { blueButton.text = value.toString() } }
	}

	private fun invokeOrAddAsPending(action: () -> Unit) {
		if (isVisible) {
			action()
		} else {
			pendingActions.add(action)
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		setHasOptionsMenu(true)
		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		greenButton.setOnClickListener {
			presenter.onGreenClick(1)
			buttonAnimator.animateButton(greenButton)
		}
		redButton.setOnClickListener {
			presenter.onRedClick(1)
			buttonAnimator.animateButton(redButton)
		}
		blueButton.setOnClickListener {
			presenter.onBlueClick(1)
			buttonAnimator.animateButton(blueButton)
		}

		deviceIdEditText.setText(
			getSharedPreferences(Context.MODE_PRIVATE).getString(SharedPreferencesKeys.DEVICE_ID, "?")
		)

		getSharedPreferences(Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this)

		pendingActions.forEach { it() }
	}

	override fun onAttach(context: Context?) {
		super.onAttach(context)
		fragmentComponent.inject(this)
	}

	override fun attachPresenter() {
		presenter.attach(this)
		presenter.requestColorCountsUpdate()
	}

	override fun detachPresenter() {
		presenter.detach()
	}

	override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
		menu?.clear()
		activity!!.menuInflater.inflate(R.menu.top_menu, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean =
		when (item?.itemId) {
			R.id.menu_refresh -> {
				presenter.requestColorCountsUpdate()
				true
			}
			else -> super.onOptionsItemSelected(item)
		}

	override fun onNetworkError() {
		Snackbar
			.make(coordinatorLayout, getString(R.string.network_error_occurred), Snackbar.LENGTH_INDEFINITE)
			.setAction(getString(R.string.retry_action)) { presenter.requestColorCountsUpdate() }
			.show()
	}

	override fun onSharedPreferenceChanged(pref: SharedPreferences, key: String) {
		if (key == SharedPreferencesKeys.DEVICE_ID) {
			deviceIdEditText.setText(getSharedPreferences(Context.MODE_PRIVATE).getString(key, "?"))
		}
	}

}