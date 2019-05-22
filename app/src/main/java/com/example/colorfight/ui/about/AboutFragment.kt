package com.example.colorfight.ui.about

import android.content.Context
import com.example.colorfight.R
import com.example.colorfight.ui.base.BaseFragment

class AboutFragment : BaseFragment() {

    override fun attachPresenter() {
    }

    override fun detachPresenter() {
    }

    override val layoutId: Int
        get() = R.layout.about_fragment_layout

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fragmentComponent.inject(this)
    }
}