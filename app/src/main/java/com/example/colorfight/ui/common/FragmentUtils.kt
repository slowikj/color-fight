package com.example.colorfight.ui.common

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment

fun Fragment.getSharedPreferences(mode: Int = Context.MODE_PRIVATE): SharedPreferences =
		activity!!.getPreferences(mode)