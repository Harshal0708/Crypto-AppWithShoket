package com.strings.cryptoapp.Response

data class OrderHistoriesdataResponse(
    val orderHistories: List<OrderHistory>,
    val pagingParameterResponseModel: PagingParameterResponseModel
)