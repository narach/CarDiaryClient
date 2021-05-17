package com.example.cardiaryclient.repositories

import com.example.cardiaryclient.api.AuthApiService
import com.example.cardiaryclient.api.CarsApiHelper
import com.example.cardiaryclient.dto.UserCredentials
import javax.inject.Inject

class CarsRepository @Inject constructor(
    private val carsApiHelper: CarsApiHelper,
    private val authApiService: AuthApiService
) {
    suspend fun getCars() = carsApiHelper.getCars()

    suspend fun authenticate(credentials: UserCredentials) = authApiService.authenticate(credentials)
}