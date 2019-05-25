package com.example.colorfight.data.socket

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class EventSocketObservable<OUTPUT_MESSAGE>(
    private val socket: EventSocket<*, OUTPUT_MESSAGE>) :
    Observable<OUTPUT_MESSAGE>() {

    override fun subscribeActual(observer: Observer<in OUTPUT_MESSAGE>) {
        observer.onSubscribe(Subscription(socket, observer))
    }

    class Subscription<OUTPUT_MESSAGE>(
        private val socket: EventSocket<*, OUTPUT_MESSAGE>,
        private val observer: Observer<in OUTPUT_MESSAGE>
    ) : EventSocket.OnSocketListener<OUTPUT_MESSAGE>, Disposable {

        private var disposed = false

        init {
            socket.addOnSocketListener(this)
        }

        override fun onMessage(message: OUTPUT_MESSAGE) {
            if (!isDisposed) {
                observer.onNext(message)
            }
        }

        override fun onError(ex: Exception) {
            if (!isDisposed) {
                observer.onError(ex)
            }
        }

        override fun onClose(code: Int, reason: String?, remote: Boolean) {
            if (!isDisposed) {
                observer.onComplete()
            }
        }

        override fun isDisposed(): Boolean = disposed

        override fun dispose() {
            socket.removeOnSocketListener(this)
            disposed = true
        }
    }
}