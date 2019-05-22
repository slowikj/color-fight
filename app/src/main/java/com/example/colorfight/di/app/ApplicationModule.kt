package com.example.colorfight.di.app

import android.app.Application
import android.content.Context
import com.example.colorfight.data.AppNetworkManager
import com.example.colorfight.data.NetworkManager
import com.example.colorfight.data.color.AppColorManager
import com.example.colorfight.data.color.ColorApiService
import com.example.colorfight.data.color.ColorManager
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApplicationModule(private val application: Application) {

    private companion object {
        const val DEFAULT_READ_TIMEOUT: Long = 5000

        const val DEFAULT_CONNECTION_TIMEOUT: Long = 5000

        const val API_URI: String = "https://ibicf94w7l.execute-api.us-east-2.amazonaws.com/default/serverlessrepo-dynamodb-p-dynamodbprocessstreampyt-1FQDIKPPQUHRA/"
    }

    @Provides
    @ApiUri
    fun provideApiUri(): String = API_URI

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
    fun provideColorManager(colorApiService: ColorApiService): ColorManager =
        AppColorManager(colorApiService)

    @PerApp
    @Provides
    fun provideColorApiService(retrofit: Retrofit): ColorApiService =
        retrofit.create(ColorApiService::class.java)

    @PerApp
    @Provides
    fun provideRetrofitClient(@ApiUri apiUri: String,
                              okHttpClient: OkHttpClient,
                              converterFactory: Converter.Factory,
                              callAdapterFactory: CallAdapter.Factory): Retrofit =
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(apiUri)
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(converterFactory)
                .build()

    @PerApp
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()


    @PerApp
    @Provides
    fun provideConverterFactory(): Converter.Factory =
        JacksonConverterFactory.create()

    @PerApp
    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory =
        RxJava2CallAdapterFactory.create()
}

