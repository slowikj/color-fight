package com.example.colorfight.ui.statistics

import android.content.Context
import com.example.colorfight.R
import com.example.colorfight.ui.base.BaseFragment

class StatisticsFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.statistics_fragment_layout

    override fun attachPresenter() {
    }

    override fun detachPresenter() {
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fragmentComponent.inject(this)
    }
}