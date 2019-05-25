package com.example.colorfight.ui.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    protected var view: V? = null

    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attach(view: V) {
        this.view = view
        initCompositeDisposable()
    }

    override fun detach() {
        this.view = null
        compositeDisposable.dispose()
    }

    protected fun initCompositeDisposable() {
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }

}