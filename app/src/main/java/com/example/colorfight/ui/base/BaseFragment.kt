package com.example.colorfight.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.colorfight.ColorApp
import com.example.colorfight.di.fragment.DaggerFragmentComponent
import com.example.colorfight.di.fragment.FragmentComponent
import com.example.colorfight.di.fragment.FragmentModule

abstract class BaseFragment : Fragment(), BaseContract.View {

    protected abstract val layoutId: Int

    val fragmentComponent: FragmentComponent by lazy {
        DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule(this))
            .applicationComponent((activity!!.application as ColorApp).applicationComponent)
            .build()
    }

    override fun onStart() {
        super.onStart()
        attachPresenter()
    }

    abstract fun attachPresenter()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutId, container, false)


    override fun onStop() {
        detachPresenter()
        super.onStop()
    }

    abstract fun detachPresenter()
}