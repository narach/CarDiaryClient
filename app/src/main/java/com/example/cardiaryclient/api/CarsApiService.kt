package com.example.cardiaryclient.api

import com.example.cardiaryclient.models.CarsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface CarsApiService {

    @GET("cars")
    suspend fun getCars(
        @Header("Authorization") authToken: String
    ) : Response<CarsData>
}