package com.example.colorfight.di.app

import android.app.Application
import android.content.Context
import com.example.colorfight.data.AppNetworkManager
import com.example.colorfight.data.NetworkManager
import com.example.colorfight.data.color.AppColorManager
import com.example.colorfight.data.color.ColorManager
import com.example.colorfight.data.color.model.ColorCountsDTO
import com.example.colorfight.data.color.model.ColorRequestDTO
import com.example.colorfight.data.socket.EventSocket
import com.example.colorfight.data.socket.EventSocketObservable
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.java_websocket.client.DefaultSSLWebSocketClientFactory
import org.java_websocket.client.WebSocketClient
import java.net.URI
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
class ApplicationModule(private val application: Application) {

    private companion object {

        val API_URI = URI("wss://n0obo1h1sj.execute-api.eu-central-1.amazonaws.com/colors")
    }

    @Provides
    @PerApp
    fun provideNetworkManager(colorManager: ColorManager): NetworkManager =
        AppNetworkManager(colorManager)

    @Provides
    @PerApp
    fun provideColorManager(
        socketObservable: EventSocketObservable<ColorCountsDTO>,
        socketEvent: EventSocket<ColorRequestDTO, ColorCountsDTO>
    ): ColorManager =
        AppColorManager(
            colorEventObservable = socketObservable,
            colorEventSocket = socketEvent
        )

    @Provides
    @PerApp
    fun provideColorEventSocketObservable(eventSocket: EventSocket<ColorRequestDTO, ColorCountsDTO>)
            : EventSocketObservable<ColorCountsDTO> =
        EventSocketObservable(eventSocket)

    @Provides
    @PerApp
    fun provideColorEventSocket(
        @ApiUri uri: URI,
        wsf: WebSocketClient.WebSocketClientFactory,
        objectMapper: ObjectMapper
    )
            : EventSocket<ColorRequestDTO, ColorCountsDTO> =
        EventSocket(
            uri = uri,
            wsf = wsf,
            inputMessageSerializer = { cc -> objectMapper.writeValueAsString(cc) },
            outputMessageDeserializer = { msg -> objectMapper.readValue(msg, ColorCountsDTO::class.java) }
        )

    @Provides
    @PerApp
    fun provideObjectMapper(): ObjectMapper =
        ObjectMapper()

    @Provides
    @PerApp
    fun provideWebSocketFactory(sslContext: SSLContext): WebSocketClient.WebSocketClientFactory =
        DefaultSSLWebSocketClientFactory(sslContext)

    @Provides
    @PerApp
    fun provideSSLContext(): SSLContext =
        SSLContext.getInstance("SSL").apply {
            init(null, arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                override fun checkClientTrusted(
                    certs: Array<X509Certificate>,
                    authType: String
                ) {
                }

                override fun checkServerTrusted(
                    certs: Array<X509Certificate>,
                    authType: String
                ) {
                }
            }), SecureRandom())
        }

    @Provides
    @ApiUri
    fun provideApiUri(): URI = API_URI

    @Provides
    @ApplicationContext
    fun provideApplicationContext(): Context = application

    @Provides
    fun provideApplication(): Application = application


}

