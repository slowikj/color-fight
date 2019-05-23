package com.example.colorfight.di.app

import android.app.Application
import android.content.Context
import com.example.colorfight.data.AppNetworkManager
import com.example.colorfight.data.NetworkManager
import com.example.colorfight.data.color.AppColorManager
import com.example.colorfight.data.color.ColorManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
class ApplicationModule(private val application: Application) {

    private companion object {
        const val DEFAULT_READ_TIMEOUT: Long = 5000

        const val DEFAULT_CONNECTION_TIMEOUT: Long = 5000

        const val DEFAULT_WRITE_TIMEOUT: Long = 5000

        const val SOCKET_BASE_URL: String = "wss://n0obo1h1sj.execute-api.eu-central-1.amazonaws.com/colors"
    }

    @Provides
    @ApiUri
    fun provideApiUri(): String = SOCKET_BASE_URL

    @Provides
    @ApplicationContext
    fun provideApplicationContext(): Context = application

    @Provides
    fun provideApplication(): Application = application

    @PerApp
    @Provides
    fun provideNetworkManager(colorManager: ColorManager): NetworkManager =
        AppNetworkManager(colorManager)

    @PerApp
    @Provides
    fun provideColorManager(): ColorManager =
        AppColorManager()

    @PerApp
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

}

