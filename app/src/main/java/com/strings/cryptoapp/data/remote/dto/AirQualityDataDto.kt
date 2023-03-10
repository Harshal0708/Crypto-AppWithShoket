package com.strings.cryptoapp.data.remote.dto

import com.strings.cryptoapp.domain.model.AirQualityData
import kotlinx.serialization.Serializable

@Serializable
data class AirQualityDataDto (
    val city : String,
    val aqi : Double
    ){

    fun toAirQualityData() : AirQualityData {
        return AirQualityData(
            city = city,
            aqi = aqi
        )
    }
}