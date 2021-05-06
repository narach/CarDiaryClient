package com.example.cardiaryclient.api

import com.example.cardiaryclient.models.CarsData
import retrofit2.Response
import javax.inject.Inject

class CarsApiHelperImpl @Inject constructor(
    private val carsApiService: CarsApiService
) : CarsApiHelper {

    override suspend fun getCars(): Response<CarsData> {
        return carsApiService.getCars()
    }

}