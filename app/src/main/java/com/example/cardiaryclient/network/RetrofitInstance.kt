package com.example.cardiaryclient.network

import com.example.cardiaryclient.network.api.CarsApi
import com.example.cardiaryclient.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val carsApi : CarsApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.carServiceBaseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarsApi::class.java)
    }
}