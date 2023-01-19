package com.strings.cryptoapp.Response

data class ResetResponse(
    val code: Int,
    val resetDataResponse: ResetDataResponse,
    val isSuccess: Boolean,
    val message: String
)