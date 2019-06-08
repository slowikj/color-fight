package com.example.colorfight.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.colorfight.ColorApp
import com.example.colorfight.R
import com.example.colorfight.di.activity.ActivityComponent
import com.example.colorfight.di.activity.DaggerActivityComponent
import com.example.colorfight.ui.about.AboutFragment
import com.example.colorfight.ui.colorpicker.ColorPickerFragment
import com.example.colorfight.ui.notifications.registerColorUpdateChannel
import com.example.colorfight.ui.statistics.StatisticsFragment
import com.example.colorfight.utils.SharedPreferencesKeys
import com.example.colorfight.utils.addFragment
import com.example.colorfight.utils.popLastFragment
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Level
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

	val activityComponent: ActivityComponent by lazy {
		DaggerActivityComponent.builder()
			.applicationComponent((application as ColorApp).applicationComponent)
			.build()
	}

	private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
		when (item.itemId) {
			R.id.navigation_color_picker -> {
				addFragment(R.id.fragmentPlaceHolder, ColorPickerFragment())
				return@OnNavigationItemSelectedListener true
			}
			R.id.navigation_statistics -> {
				addFragment(R.id.fragmentPlaceHolder, StatisticsFragment())
				return@OnNavigationItemSelectedListener true
			}
			R.id.navigation_about -> {
				addFragment(R.id.fragmentPlaceHolder, AboutFragment())
				return@OnNavigationItemSelectedListener true
			}
		}
		false
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		activityComponent.inject(this)

		setContentView(R.layout.activity_main)

		navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

		if (savedInstanceState == null) {
			navView.selectedItemId = R.id.navigation_color_picker
		}

		prepareFirebaseDeviceId()
		registerColorUpdateChannel()
	}

	private fun prepareFirebaseDeviceId() {
		FirebaseInstanceId
			.getInstance()
			.instanceId
			.addOnCompleteListener { setDeviceId(it) }
	}

	private fun setDeviceId(task: Task<InstanceIdResult>) {
		if (!task.isSuccessful) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "Firebase: task is not successful")
		} else {
			val token = task.result?.token
			getPreferences(Context.MODE_PRIVATE).edit(commit = true) {
				putString(SharedPreferencesKeys.DEVICE_ID, token)
			}
		}
	}

	override fun onBackPressed() {
		if (supportFragmentManager.fragments.size > 0) {
			popLastFragment()
		} else {
			super.onBackPressed()
		}
	}
}
