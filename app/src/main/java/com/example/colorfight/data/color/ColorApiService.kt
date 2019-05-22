package com.example.colorfight.data.color

import com.example.colorfight.data.color.model.ColorCountsDTO
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ColorApiService {

    @GET("/")
    fun getColors(): Observable<ColorCountsDTO>

    @POST("/")
    fun incrementColors(@Body colorCounts: ColorCountsDTO): Completable
}