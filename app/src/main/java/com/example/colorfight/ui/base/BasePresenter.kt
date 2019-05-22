package com.example.colorfight.ui.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    protected var view: V? = null

    protected var compositeDisposable: CompositeDisposable? = null

    override fun attach(view: V) {
        this.view = view
        compositeDisposable = CompositeDisposable()
    }

    override fun detach() {
        compositeDisposable?.dispose()
        this.view = null
    }

}