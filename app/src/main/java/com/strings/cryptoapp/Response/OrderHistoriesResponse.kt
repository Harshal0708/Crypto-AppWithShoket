package com.strings.cryptoapp.Response

data class OrderHistoriesResponse(
    val code: Int,
    val `data`: OrderHistoriesdataResponse,
    val isSuccess: Boolean,
    val message: String
)