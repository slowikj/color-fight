package com.example.colorfight.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.example.colorfight.ColorApp
import com.example.colorfight.R
import com.example.colorfight.di.activity.ActivityComponent
import com.example.colorfight.di.activity.DaggerActivityComponent
import com.example.colorfight.ui.about.AboutFragment
import com.example.colorfight.ui.colorpicker.ColorPickerFragment
import com.example.colorfight.ui.statistics.StatisticsFragment
import com.example.colorfight.utils.addFragment
import com.example.colorfight.utils.popLastFragment
import kotlinx.android.synthetic.main.activity_main.*

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

        if(savedInstanceState == null) {
            navView.selectedItemId = R.id.navigation_color_picker
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.fragments.size > 0) {
            popLastFragment()
        } else {
            super.onBackPressed()
        }
    }
}
