package com.example.cardiaryclient.api

import com.example.cardiaryclient.dto.AuthToken
import com.example.cardiaryclient.dto.UserCredentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth")
    suspend fun authenticate(
        @Body userCredentials: UserCredentials
    ) : Response<AuthToken>
}