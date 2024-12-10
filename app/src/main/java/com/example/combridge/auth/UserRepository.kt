package com.example.combridge.auth

import com.example.combridge.api.ApiService
import com.example.combridge.autentikasi.LoginRequest
import com.example.combridge.autentikasi.LoginResponse
import com.example.combridge.autentikasi.RegisterRequest
import com.example.combridge.autentikasi.RegisterResponse

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    //    Menyimpan sesi pengguna
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }


    //    Register pengguna
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return try {
            val registerRequest = RegisterRequest(name, email, password)
            apiService.register(registerRequest)  // Mengirimkan data sebagai JSON
        } catch (e: Exception) {
            throw Exception("Register failed: ${e.message}")
        }
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val loginRequest = LoginRequest(email, password)
        return try {
            apiService.login(loginRequest)
        } catch (e: Exception) {
            throw Exception("Login failed: ${e.message}")
        }
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}