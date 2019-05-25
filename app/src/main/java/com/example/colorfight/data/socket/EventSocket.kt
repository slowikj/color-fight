package com.example.colorfight.data.socket

import org.java_websocket.client.WebSocketClient
import org.java_websocket.enums.ReadyState
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class EventSocket<INPUT_MESSAGE, OUTPUT_MESSAGE>(
    private val uri: URI,
    private val inputMessageSerializer: (INPUT_MESSAGE) -> String,
    private val outputMessageDeserializer: (String) -> OUTPUT_MESSAGE
) {

    private companion object {
        val validSocketStates = setOf(ReadyState.OPEN)
    }

    private var innerWebSocket: WebSocketClient? = null

    private val webSocket: WebSocketClient
        @Synchronized get() = run {
            if (innerWebSocket?.connection?.readyState !in validSocketStates) {
                this.innerWebSocket = createWebSocket()
            }
            this.innerWebSocket!!
        }

    private val waitingMessages: MutableList<INPUT_MESSAGE> = mutableListOf()
        @Synchronized get

    private val onSocketListeners: MutableList<OnSocketListener<OUTPUT_MESSAGE>> = mutableListOf()

    @Synchronized
    fun addOnSocketListener(listener: OnSocketListener<OUTPUT_MESSAGE>) {
        onSocketListeners.add(listener)
        if (onSocketListeners.size == 1) {
            webSocket.connectBlocking()
        }
    }

    @Synchronized
    fun removeOnSocketListener(listener: OnSocketListener<OUTPUT_MESSAGE>) {
        onSocketListeners.remove(listener)
        if (onSocketListeners.size == 0 && webSocket.connection.isOpen) {
            webSocket.closeBlocking()
        }
    }

    fun send(message: INPUT_MESSAGE) {
        if (webSocket.connection.isOpen) {
            webSocket.send(inputMessageSerializer(message))
        } else {
            waitingMessages.add(message)
        }
    }

    private fun createWebSocket(): WebSocketClient {
        val x = object : WebSocketClient(uri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                waitingMessages.forEach { send(inputMessageSerializer(it)) }
                waitingMessages.clear()
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                onSocketListeners.forEach { it.onClose(code, reason, remote) }
                onSocketListeners.clear()
            }

            override fun onMessage(message: String) {
                val deserializedMessage = outputMessageDeserializer(message)
                onSocketListeners.forEach { it.onMessage(deserializedMessage) }
            }

            override fun onError(ex: Exception) {
                onSocketListeners.forEach { it.onError(ex) }
            }
        }
        x.isReuseAddr = true
        return x
    }

    interface OnSocketListener<OUTPUT_MESSAGE> {
        fun onMessage(message: OUTPUT_MESSAGE)

        fun onError(ex: Exception)

        fun onClose(code: Int, reason: String?, remote: Boolean)
    }
}

