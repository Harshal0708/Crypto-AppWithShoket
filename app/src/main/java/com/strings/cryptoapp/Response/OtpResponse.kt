package com.strings.cryptoapp.Response

data class OtpResponse(
    val code : String,
    val message : String,
    val data : OtpUserDataResponse,
)

