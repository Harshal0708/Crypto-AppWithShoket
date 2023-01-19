package com.strings.cryptoapp.domain.model

data class AirQualityDataState(
    val airQualityDataList : List<AirQualityData> = emptyList(),
    val isLoading : Boolean = false
)