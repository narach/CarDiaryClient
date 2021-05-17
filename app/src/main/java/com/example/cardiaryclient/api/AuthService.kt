package com.example.cardiaryclient.api

import com.example.cardiaryclient.dto.AuthToken
import com.example.cardiaryclient.dto.Credentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth")
    suspend fun authenticate(
        @Body userCredential: Credentials
    ) : Response<AuthToken>
}