package com.strings.cryptoapp.Response

data class UserSubscriptionsResponse(
    val code: Int,
    val `data`: UserSubscriptionsDataResponse,
    val isSuccess: Boolean,
    val message: String
)