package com.example.colorfight.di.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideActivityContext(): Context = activity

    @Provides
    fun provideActivity(): AppCompatActivity = activity
}