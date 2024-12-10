package com.example.combridge.autentikasi

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)