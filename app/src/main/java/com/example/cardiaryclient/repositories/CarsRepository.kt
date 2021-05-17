package com.example.cardiaryclient.repositories

import com.example.cardiaryclient.api.AuthService
import com.example.cardiaryclient.api.CarsApiHelper
import com.example.cardiaryclient.dto.Credentials
import javax.inject.Inject

class CarsRepository @Inject constructor(
    private val carsApiHelper: CarsApiHelper,
    private val authService: AuthService
) {
    suspend fun getCars(authToken: String) = carsApiHelper.getCars(authToken)

    suspend fun authenticate(credentials: Credentials) =
            authService.authenticate(credentials)
}