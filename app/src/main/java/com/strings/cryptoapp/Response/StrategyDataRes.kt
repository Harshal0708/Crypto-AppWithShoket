package com.strings.cryptoapp.Response

data class StrategyDataRes(
    val createdDate: String,
    val description: String,
    val id: String,
    val isActive: Boolean,
    val minCapital: Int,
    val modifiedDate: String,
    val monthlyFee: Int,
    val strategyName: String
)