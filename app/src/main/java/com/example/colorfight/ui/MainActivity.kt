package com.example.colorfight.ui

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.colorfight.R
import com.example.colorfight.utils.addFragment
import com.example.colorfight.utils.popLastFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
