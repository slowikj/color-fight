package com.example.colorfight.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment) {
    supportFragmentManager.invoke {
        replace(frameId, fragment)
        addToBackStack(null)
    }
}

fun AppCompatActivity.popLastFragment() {
    supportFragmentManager.popBackStack()
}

inline fun FragmentManager.invoke(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}


