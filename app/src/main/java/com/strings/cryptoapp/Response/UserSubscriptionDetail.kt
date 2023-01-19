package com.strings.cryptoapp.Response

data class UserSubscriptionDetail(
    val code: Int,
    val `data`: UserSubscriptionDetailData,
    val isSuccess: Boolean,
    val message: Any
)