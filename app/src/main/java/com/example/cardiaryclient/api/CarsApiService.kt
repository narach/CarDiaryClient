package com.example.cardiaryclient.api

import com.example.cardiaryclient.models.CarsData
import retrofit2.Response
import retrofit2.http.GET

interface CarsApiService {

    @GET("cars")
    suspend fun getCars() : Response<CarsData>
}