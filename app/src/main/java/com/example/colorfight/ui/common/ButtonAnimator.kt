package com.example.colorfight.ui.common

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.widget.Button

class ButtonAnimator(private val targetScale: Float,
					 private val scalingDuration: Long) {

	fun animateButton(button: Button) {
		createButtonAnimator(button).start()
	}

	private fun createButtonAnimator(button: Button) =
		AnimatorSet().apply {
			play(createButtonAnimatorOneDirection(button, targetScale))
				.before(createButtonAnimatorOneDirection(button, 1.0f))
		}

	private fun createButtonAnimatorOneDirection(button: Button, targetValue: Float) =
		AnimatorSet().apply {
			play(createButtonPropertyAnim(button, "scaleX", targetValue))
				.with(createButtonPropertyAnim(button, "scaleY", targetValue))
		}

	private fun createButtonPropertyAnim(
		button: Button,
		property: String,
		targetValue: Float
	): ObjectAnimator =
		ObjectAnimator.ofFloat(button, property, targetValue)
			.apply {this.duration = scalingDuration}
}