package com.example.colorfight.utils

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun <DTO, UI> prepareNoneToApiObservable(
    apiRequest: () -> Observable<DTO>,
    converter: (DTO) -> UI
): Observable<UI> =
    apiRequest()
        .subscribeOn(Schedulers.io())
        .map { converter(it) }

fun prepareNoneToCompletable(apiRequest: () -> Completable): Completable =
    apiRequest()
        .subscribeOn(Schedulers.io())

fun <BODY_DTO, BODY_UI, RESPONSE_DTO, RESPONSE_UI> prepareObjectToApiObservable(
    body: BODY_UI,
    bodyConverter: (BODY_UI) -> BODY_DTO,
    apiRequest: (BODY_DTO) -> Observable<RESPONSE_DTO>,
    responseConverter: (RESPONSE_DTO) -> RESPONSE_UI
): Observable<RESPONSE_UI> =
    Observable.just(body)
        .subscribeOn(Schedulers.io())
        .map { bodyConverter(it) }
        .flatMap { apiRequest(it) }
        .map { responseConverter(it) }

fun <BODY_DTO, BODY_UI> prepareObjectToCompletable(
    body: BODY_UI,
    bodyConverter: (BODY_UI) -> BODY_DTO,
    apiRequest: (BODY_DTO) -> Completable
): Completable =
    Observable.just(body)
        .subscribeOn(Schedulers.io())
        .map { bodyConverter(it) }
        .flatMapCompletable { apiRequest(it) }
