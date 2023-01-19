package com.strings.cryptoapp.Response

data class RegisterResponse(
    val code: Int,
    val `data`: DataX,
    val isSuccess: Boolean,
    val message: String
)