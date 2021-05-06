package com.example.cardiaryclient.repositories

import com.example.cardiaryclient.api.CarsApiHelper
import javax.inject.Inject

class CarsRepository @Inject constructor(
    private val carsApiHelper: CarsApiHelper
) {
    suspend fun getCars() = carsApiHelper.getCars()
}