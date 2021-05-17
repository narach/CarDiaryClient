package com.example.cardiaryclient.api

import com.example.cardiaryclient.dto.AuthToken
import com.example.cardiaryclient.dto.UserCredentials
import retrofit2.Response

interface AuthApiHelper {
    suspend fun authenticate(credentials: UserCredentials) : Response<AuthToken>
}