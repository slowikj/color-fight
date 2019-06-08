package com.example.colorfight.di.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.colorfight.ui.main.MainContract
import com.example.colorfight.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideActivityContext(): Context = activity

    @Provides
    fun provideActivity(): AppCompatActivity = activity

    @PerActivity
    @Provides
    fun provideMainActivityPresenter(presenter: MainPresenter<MainContract.View>): MainContract.Presenter<MainContract.View> =
            presenter
}