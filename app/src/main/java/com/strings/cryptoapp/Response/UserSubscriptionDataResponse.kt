package com.strings.cryptoapp.Response

data class UserSubscriptionDataResponse(
    val isActive: Boolean,
    val subscriptionId: String,
    val subscriptionName: String,
    val subscriptionPrice: Int
)