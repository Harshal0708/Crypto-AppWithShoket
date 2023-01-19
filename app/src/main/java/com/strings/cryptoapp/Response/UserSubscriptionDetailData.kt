package com.strings.cryptoapp.Response

data class UserSubscriptionDetailData(
    val isActive: Boolean,
    val noOfStrategies: Int,
    val subscriptionId: String,
    val subscriptionName: String,
    val subscriptionPrice: Int
)