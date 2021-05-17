package com.example.cardiaryclient.api

import com.example.cardiaryclient.models.CarsData
import retrofit2.Response

interface CarsApiHelper {
    suspend fun getCars(authToken: String) : Response<CarsData>
}