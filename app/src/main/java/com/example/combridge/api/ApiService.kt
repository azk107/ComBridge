package com.example.combridge.api

import com.example.combridge.autentikasi.LoginRequest
import com.example.combridge.autentikasi.LoginResponse
import com.example.combridge.autentikasi.RegisterRequest
import com.example.combridge.autentikasi.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

}