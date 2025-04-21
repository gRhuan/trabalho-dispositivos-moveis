package com.grhuan.trabalho01.api

import com.grhuan.trabalho01.dto.LoginRequest
import com.grhuan.trabalho01.dto.LoginResponse
import com.grhuan.trabalho01.dto.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): LoginResponse

    @POST("/register")
    suspend fun loginUser(@Body registerRequest: RegisterRequest)
}