package com.example.cardiaryclient.repositories

import com.example.cardiaryclient.api.AuthApiService
import com.example.cardiaryclient.api.CarsApiHelper
import com.example.cardiaryclient.dto.UserCredentials
import javax.inject.Inject

class CarsRepository @Inject constructor(
    private val carsApiHelper: CarsApiHelper,
    private val authApiService: AuthApiService
) {
    suspend fun getCars(authToken: String) = carsApiHelper.getCars(authToken)

    suspend fun authenticate(credentials: UserCredentials) = authApiService.authenticate(credentials)
}