package com.example.combridge.autentikasi

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val token: String? = null
)