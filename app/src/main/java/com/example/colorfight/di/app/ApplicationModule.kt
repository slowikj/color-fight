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
import java.net.URI

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
    @ApiUri
    fun provideApiUri(): URI = API_URI

    @Provides
    @ApplicationContext
    fun provideApplicationContext(): Context = application

    @Provides
    fun provideApplication(): Application = application


}

