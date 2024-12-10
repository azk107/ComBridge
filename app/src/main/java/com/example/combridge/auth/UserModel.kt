package com.example.combridge.auth

data class UserModel(
    val email: String,
    val isLogin: Boolean = false
)