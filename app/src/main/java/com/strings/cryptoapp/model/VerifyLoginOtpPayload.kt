package com.strings.cryptoapp.model

data class VerifyLoginOtpPayload(
    val mobile: String,
    val mobileOtp: String
)