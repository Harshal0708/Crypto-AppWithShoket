package com.strings.cryptoapp.data.remote

import com.strings.airqualityvisualizer.data.remote.dto.TopGainersItem
import com.strings.airqualityvisualizer.domain.model.AirQualityData
import kotlinx.coroutines.flow.Flow


interface AirQualitySocketService {

    suspend fun openSession() : ConnectionState

    fun refreshData(): Flow<List<AirQualityData>>

    suspend fun  closeSession()

    companion object{
        const val BASE_URL = "wss://stream.binance.com:443/ws/!ticker@arr" //"ws://city-ws.herokuapp.com"
    }

}