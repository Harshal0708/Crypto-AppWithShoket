package com.strings.cryptoapp.Response

data class CmsAdsListResponse(
    val code: Int,
    val `data`: List<CmsAdsListResponseData>,
    val isSuccess: Boolean,
    val message: Any
)