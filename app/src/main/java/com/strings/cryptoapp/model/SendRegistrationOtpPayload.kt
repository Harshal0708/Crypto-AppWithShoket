package com.strings.cryptoapp.model

data class SendRegistrationOtpPayload(
    val email: String,
    val firstName: String,
    val mobile: String
)