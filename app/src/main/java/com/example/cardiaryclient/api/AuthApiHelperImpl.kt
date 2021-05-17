package com.example.cardiaryclient.api

import com.example.cardiaryclient.dto.AuthToken
import com.example.cardiaryclient.dto.UserCredentials
import retrofit2.Response
import javax.inject.Inject

class AuthApiHelperImpl @Inject constructor(
    private val authApiService: AuthApiService
) : AuthApiHelper {

    override suspend fun authenticate(credentials: UserCredentials): Response<AuthToken> {
        return authApiService.authenticate(credentials)
    }
}