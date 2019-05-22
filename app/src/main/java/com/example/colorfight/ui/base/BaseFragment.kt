package com.example.colorfight.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.colorfight.ColorApp
import com.example.colorfight.di.fragment.DaggerFragmentComponent
import com.example.colorfight.di.fragment.FragmentComponent

abstract class BaseFragment : Fragment() {

    protected abstract val layoutId: Int

    val fragmentComponent: FragmentComponent by lazy {
        DaggerFragmentComponent.builder()
            .applicationComponent((activity!!.application as ColorApp).applicationComponent)
            .build()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutId, container, false)
}