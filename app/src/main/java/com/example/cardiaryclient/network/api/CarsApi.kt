package com.example.cardiaryclient.network.api

import com.example.cardiaryclient.dto.CarDto
import retrofit2.Response
import retrofit2.http.GET

interface CarsApi {
    @GET("cars")
    suspend fun getCars(): Response<List<CarDto>>
}