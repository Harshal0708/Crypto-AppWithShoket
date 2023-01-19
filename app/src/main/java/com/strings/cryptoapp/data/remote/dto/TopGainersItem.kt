package com.strings.cryptoapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.strings.cryptoapp.domain.model.AirQualityData
import kotlinx.serialization.Serializable

@Serializable
data class TopGainersItem(
    val A: String,
    val B: String,
    val C: Long,
    val E: Long,
    val F: Long,
    val L: Long,
    val O: Long,
    val P: String,
    val Q: String,
    @SerializedName("a")val aa: String,
    @SerializedName("b")val bb: String,
    val c: String,
    val e: String,
    val h: String,
    val l: String,
    val n: Int,
    val o: String,
    @SerializedName("p")val pp: String,
    @SerializedName("q")val qq: String,
    val s: String,
    val v: String,
    val w: String,
    val x: String){

    fun toAirQualityData() : AirQualityData {
        return AirQualityData(
            city = s,
            aqi = C.toDouble()
        )
    }
}