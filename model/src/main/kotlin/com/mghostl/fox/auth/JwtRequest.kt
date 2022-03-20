package com.mghostl.fox.auth

data class JwtRequest(
    val username: String,
    val password: String
)