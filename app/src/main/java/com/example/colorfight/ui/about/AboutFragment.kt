package com.example.colorfight.ui.about

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.colorfight.R
import com.example.colorfight.ui.about.library.Library
import com.example.colorfight.ui.about.library.LibraryAdapter
import com.example.colorfight.ui.base.BaseFragment
import kotlinx.android.synthetic.main.about_fragment_layout.*
import javax.inject.Inject

class AboutFragment : BaseFragment(), AboutContract.View {

    @Inject
    lateinit var presenter: AboutContract.Presenter<AboutContract.View>

    private val librariesAdapter = LibraryAdapter()

    override fun attachPresenter() {
        presenter.attach(this)
        presenter.requestUsedLibraries()
    }

    override fun detachPresenter() {
        presenter.detach()
    }

    override val layoutId: Int
        get() = R.layout.about_fragment_layout

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fragmentComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvLibraries.layoutManager = LinearLayoutManager(context)
        rvLibraries.adapter = librariesAdapter
    }

    override fun setUsedLibraries(libraries: List<Library>) {
        librariesAdapter.resetItems(libraries)
    }
}