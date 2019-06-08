package com.example.colorfight.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import com.example.colorfight.ColorApp
import com.example.colorfight.R
import com.example.colorfight.data.userinfo.model.deviceinfo.DeviceInfo
import com.example.colorfight.data.userinfo.model.location.DeviceLocation
import com.example.colorfight.data.userinfo.model.userinfo.UserInfo
import com.example.colorfight.di.activity.ActivityComponent
import com.example.colorfight.di.activity.ActivityModule
import com.example.colorfight.di.activity.DaggerActivityComponent
import com.example.colorfight.ui.about.AboutFragment
import com.example.colorfight.ui.colorpicker.ColorPickerFragment
import com.example.colorfight.ui.notifications.registerColorUpdateChannel
import com.example.colorfight.ui.statistics.StatisticsFragment
import com.example.colorfight.utils.SharedPreferencesKeys
import com.example.colorfight.utils.addFragment
import com.example.colorfight.utils.popLastFragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
	MainContract.View,
	GoogleApiClient.ConnectionCallbacks,
	GoogleApiClient.OnConnectionFailedListener {
	companion object {

		private const val PERMISSIONS_REQUEST_CODE = 1122
		private val permissions: Array<out String> = arrayOf(
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.ACCESS_COARSE_LOCATION
		)

	}
	val activityComponent: ActivityComponent by lazy {
		DaggerActivityComponent.builder()
			.applicationComponent((application as ColorApp).applicationComponent)
			.activityModule(ActivityModule(this))
			.build()
	}

	@Inject
	lateinit var presenter: MainContract.Presenter<MainContract.View>

	private lateinit var googleApiClient: GoogleApiClient

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

	override fun onStart() {
		super.onStart()
		attachPresenter()
		if (arePermissionsGranted(permissions)) {
			requestLocation()
		} else {
			requestPermissions(permissions)
		}
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode == PERMISSIONS_REQUEST_CODE) {
			if (arePermissionsGranted(grantResults.toTypedArray())) {
				requestLocation()
			} else {
				requestSendUserInfo(null)
			}
		}
	}

	private fun requestLocation() {
		googleApiClient = GoogleApiClient.Builder(this)
			.addConnectionCallbacks(this)
			.addOnConnectionFailedListener(this)
			.addApi(LocationServices.API)
			.build()
		googleApiClient.connect()
	}

	@SuppressLint("MissingPermission")
	override fun onConnected(p0: Bundle?) {
		val location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
		requestSendUserInfo(
			DeviceLocation(
			lat = location.latitude,
			lon = location.longitude))
	}

	override fun onConnectionSuspended(p0: Int) {
		requestSendUserInfo(null)
	}

	override fun onConnectionFailed(p0: ConnectionResult) {
		requestSendUserInfo(null)
	}

	private fun requestSendUserInfo(location: DeviceLocation?) {
		presenter.sendUserInfo(UserInfo(getDeviceInfo(), location))
	}

	private fun getDeviceInfo(): DeviceInfo =
		DeviceInfo(
			version = android.os.Build.VERSION.SDK_INT.toString(),
			device = android.os.Build.DEVICE,
			product = android.os.Build.PRODUCT,
			brand = android.os.Build.BRAND
		)

	private fun attachPresenter() {
		presenter.attach(this)
	}

	override fun onStop() {
		detachPresenter()
		super.onStop()
	}

	private fun detachPresenter() {
		presenter.detach()
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


	private fun requestPermissions(permissions: Array<out String>) {
		if (!arePermissionsGranted(permissions)) {
			ActivityCompat.requestPermissions(
				this, permissions,
				PERMISSIONS_REQUEST_CODE
			)
		}
	}

	private fun arePermissionsGranted(grantResults: Array<Int>): Boolean =
		grantResults.size == grantResults.size &&
				grantResults.all { it == PackageManager.PERMISSION_GRANTED }

	private fun arePermissionsGranted(permissions: Array<out String>) =
		permissions.all {
			ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
		}

}
