package com.example.colorfight.di.app

import android.app.Application
import android.content.Context
import com.example.colorfight.data.AppNetworkManager
import com.example.colorfight.data.NetworkManager
import com.example.colorfight.data.color.AppColorManager
import com.example.colorfight.data.color.ColorManager
import com.example.colorfight.data.color.model.colorcounts.ColorCountsDTO
import com.example.colorfight.data.color.model.colorcounts.ColorRequestDTO
import com.example.colorfight.data.color.services.StatisticsService
import com.example.colorfight.data.common.socket.EventSocket
import com.example.colorfight.data.common.socket.EventSocketObservable
import com.example.colorfight.data.userinfo.AppUserInfoManager
import com.example.colorfight.data.userinfo.UserInfoManager
import com.example.colorfight.data.userinfo.services.UserInfoService
import com.fasterxml.jackson.databind.ObjectMapper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.net.URI
import java.util.concurrent.TimeUnit

@Module
class ApplicationModule(private val application: Application) {

	private companion object {

		val COLOR_INC_SOCKET_URI = URI("wss://n0obo1h1sj.execute-api.eu-central-1.amazonaws.com/colors")

		const val STATS_URI = "https://uwz8630959.execute-api.eu-central-1.amazonaws.com/default/"

		const val USER_INFO_API_URI = "https://dcbnklyyud.execute-api.eu-central-1.amazonaws.com/default/"

		const val READ_TIMEOUT_SEC: Long = 5

		const val CONNECT_TIMEOUT_SEC: Long = 5
	}

	@Provides
	@PerApp
	fun provideNetworkManager(
		colorManager: ColorManager,
		userInfoManager: UserInfoManager
	): NetworkManager =
		AppNetworkManager(
			colorManager = colorManager,
			userInfoManager = userInfoManager
		)

	@Provides
	@PerApp
	fun provideColorManager(
		socketObservable: EventSocketObservable<ColorCountsDTO>,
		socketEvent: EventSocket<ColorRequestDTO, ColorCountsDTO>,
		statisticsService: StatisticsService
	): ColorManager =
		AppColorManager(
			colorEventObservable = socketObservable,
			colorEventSocket = socketEvent,
			statisticsService = statisticsService
		)

	@Provides
	fun provideUserInfoManager(userInfoService: UserInfoService): UserInfoManager =
		AppUserInfoManager(userInfoService = userInfoService)

	@Provides
	@PerApp
	fun provideColorEventSocketObservable(eventSocket: EventSocket<ColorRequestDTO, ColorCountsDTO>)
			: EventSocketObservable<ColorCountsDTO> =
		EventSocketObservable(eventSocket)

	@Provides
	@PerApp
	fun provideColorEventSocket(
		@IncrementColors uri: URI,
		objectMapper: ObjectMapper
	)
			: EventSocket<ColorRequestDTO, ColorCountsDTO> =
		EventSocket(
			uri = uri,
			inputMessageSerializer = { cc -> objectMapper.writeValueAsString(cc) },
			outputMessageDeserializer = { msg -> objectMapper.readValue(msg, ColorCountsDTO::class.java) }
		)

	@Provides
	@PerApp
	fun provideObjectMapper(): ObjectMapper =
		ObjectMapper()

	@Provides
	@IncrementColors
	fun provideIncrementColorsApiUri(): URI = COLOR_INC_SOCKET_URI

	@Provides
	@Statistics
	fun provideStatisticsApiUri() = STATS_URI

	@Provides
	@UserInfo
	fun provideUserInfoApiUri() = USER_INFO_API_URI

	@Provides
	fun provideStatisticsService(@Statistics retrofit: Retrofit): StatisticsService =
		retrofit.create(StatisticsService::class.java)

	@Provides
	fun provideUserInfoService(@UserInfo retrofit: Retrofit): UserInfoService =
		retrofit.create(UserInfoService::class.java)

	@Provides
	@ApplicationContext
	fun provideApplicationContext(): Context = application

	@Provides
	fun provideApplication(): Application = application

	@Provides
	@Statistics
	fun provideStatisticsRetrofit(
		@Statistics apiUri: String,
		okHttpClient: OkHttpClient,
		converterFactory: Converter.Factory,
		callAdapterFactory: CallAdapter.Factory
	): Retrofit =
		createRetrofit(apiUri, okHttpClient, converterFactory, callAdapterFactory)

	@Provides
	@UserInfo
	fun provideUserInfoRetrofit(
		@UserInfo apiUri: String,
		okHttpClient: OkHttpClient,
		converterFactory: Converter.Factory,
		callAdapterFactory: CallAdapter.Factory
	): Retrofit =
		createRetrofit(apiUri, okHttpClient, converterFactory, callAdapterFactory)

	private fun createRetrofit(
		apiUri: String,
		okHttpClient: OkHttpClient,
		converterFactory: Converter.Factory,
		callAdapterFactory: CallAdapter.Factory
	): Retrofit {
		return Retrofit.Builder()
			.baseUrl(apiUri)
			.client(okHttpClient)
			.addConverterFactory(converterFactory)
			.addCallAdapterFactory(callAdapterFactory)
			.build()
	}

	@Provides
	fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
		OkHttpClient.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
			.readTimeout(READ_TIMEOUT_SEC, TimeUnit.SECONDS)
			.build()

	@Provides
	fun provideConverterFactory(): Converter.Factory =
		JacksonConverterFactory.create()

	@Provides
	fun provideCallAdapterFactory(): CallAdapter.Factory =
		RxJava2CallAdapterFactory.create()

	@Provides
	fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
		HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


}

