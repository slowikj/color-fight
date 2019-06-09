package com.example.colorfight.ui.about

import com.example.colorfight.ui.about.library.Library
import com.example.colorfight.ui.base.BasePresenter
import javax.inject.Inject

class AboutPresenter<V: AboutContract.View> @Inject constructor(): BasePresenter<V>() ,
	AboutContract.Presenter<V> {

	private val usedLibraries = listOf(
		Library("ktx", "androidx.core:core-ktx:1.0.2"),
		Library("constraintlayout", "androidx.constraintlayout:constraintlayout:1.1.3"),
		Library("jackson", "com.fasterxml.jackson.core:jackson-core:2.7.3"),
		Library("rxjava2", "io.reactivex.rxjava2:rxjava:2.2.6"),
		Library("rxkotlin", "io.reactivex.rxjava2:rxkotlin:2.3.0"),
		Library("dagger2", "com.google.dagger:dagger:2.7"),
		Library("java-websocket", "org.java-websocket:Java-WebSocket:1.4.0"),
		Library("firebase", "com.google.firebase:firebase-core:16.0.9"),
		Library("firebase-messaging", "com.google.firebase:firebase-messaging:18.0.0"),
		Library("retrofit", "com.squareup.retrofit2:retrofit:2.1.0"),
		Library("retrofit converter jackson", "com.squareup.retrofit2:converter-jackson:2.1.0"),
		Library("retrofit rxjava2 adapter", "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0"),
		Library("retrofit rxjava2 adapter", "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0"),
		Library("google play services location", "com.google.android.gms:play-services-location:16.0.0"),
		Library("graphView", "com.jjoe64:graphview:4.2.2")
	)

	override fun requestUsedLibraries() {
		view?.setUsedLibraries(usedLibraries)
	}
}