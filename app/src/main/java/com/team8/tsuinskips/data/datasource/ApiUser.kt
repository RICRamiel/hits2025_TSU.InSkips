package com.team8.tsuinskips.data.datasource

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiUser {
    @POST("account/register")
    suspend fun register(@Body userRegister: UserRegisterDTO): Response<TokenResponseDTO>

    @POST("account/login")
    suspend fun login(@Body userLoginDTO: UserLoginDTO): Response<TokenResponseDTO>

    @POST("account/logout")
    suspend fun logout(): Response<ResponseBody>
}