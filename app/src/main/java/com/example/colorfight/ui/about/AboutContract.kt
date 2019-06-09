package com.example.colorfight.ui.about

import com.example.colorfight.ui.about.library.Library
import com.example.colorfight.ui.base.BaseContract

interface AboutContract {

	interface View: BaseContract.View {

		fun setUsedLibraries(libraries: List<Library>)
	}

	interface Presenter<V: AboutContract.View>: BaseContract.Presenter<V> {

		fun requestUsedLibraries()
	}
}