package com.example.colorfight.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment

fun Fragment.getSharedPreferences(mode: Int = Context.MODE_PRIVATE): SharedPreferences =
		activity!!.getPreferences(mode)