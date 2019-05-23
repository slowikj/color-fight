package com.example.colorfight.data.color

import com.example.colorfight.data.color.model.ColorCounts
import com.example.colorfight.data.color.model.ColorCountsConverter
import com.example.colorfight.data.color.model.ColorCountsDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.java_websocket.WebSocket
import org.java_websocket.client.DefaultSSLWebSocketClientFactory
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_17
import org.java_websocket.drafts.Draft_75
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.logging.Level
import java.util.logging.Logger
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class AppColorManager : ColorManager {

    private companion object {

        val URI: URI = URI("wss://n0obo1h1sj.execute-api.eu-central-1.amazonaws.com/colors")
    }

    override val onColorChangedListeners: MutableList<ColorManager.OnColorsChangedListener> =
        mutableListOf()

    private val socket: WebSocketClient by lazy {
        val x = object : WebSocketClient(URI) {

            override fun onOpen(handshakedata: ServerHandshake?) {
                queue.forEach { it() }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Logger.getAnonymousLogger().log(Level.INFO, "onClose: $reason")
            }

            override fun onMessage(message: String?) {
                val v = ObjectMapper().readValue(message, ColorCountsDTO::class.java)
                onColorChangedListeners.forEach { it.onColorChanged(ColorCountsConverter.convertToUI(v)) }
            }

            override fun onError(ex: Exception) {
                ex.printStackTrace()
            }
        }

        val sslContext = SSLContext.getInstance("SSL")

        sslContext.init(null, arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

            override fun checkClientTrusted(
                certs: Array<X509Certificate>,
                authType: String
            ) {
                println("checkClientTrusted =============")
            }

            override fun checkServerTrusted(
                certs: Array<X509Certificate>,
                authType: String
            ) {
                println("checkServerTrusted =============")
            }
        }), SecureRandom())

        x.setWebSocketFactory(DefaultSSLWebSocketClientFactory(sslContext))

        x.connect()
        x
    }


    val queue: MutableList<() -> Unit> = mutableListOf()

    override fun incrementColors(colorCounts: ColorCounts) {
        Logger.getAnonymousLogger().log(Level.INFO, socket.readyState.toString())

        val action = { socket.send(ObjectMapper().writeValueAsString(ColorCountsConverter.convertToRequest(colorCounts))) }
        if (socket.readyState in setOf(WebSocket.READYSTATE.OPEN)) {
            action()
        } else {
            queue.add(action)
        }
    }

    override fun closeConnection() {
        socket.close()
    }

}