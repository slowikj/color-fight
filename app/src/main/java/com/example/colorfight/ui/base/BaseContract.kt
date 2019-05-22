package com.example.colorfight.ui.base

interface BaseContract {

    interface View {
    }

    interface Presenter<V: View> {
        fun attach(view: V)

        fun detach()
    }
}