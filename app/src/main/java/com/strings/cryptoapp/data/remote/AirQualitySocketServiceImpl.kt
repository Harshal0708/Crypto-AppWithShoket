package com.strings.cryptoapp.data.remote

import android.util.Log
import com.google.gson.Gson
import com.strings.cryptoapp.data.remote.dto.TopGainersItem
import com.strings.cryptoapp.domain.model.AirQualityData
import io.ktor.client.*
import io.ktor.client.plugins.json.serializer.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AirQualitySocketServiceImpl(
    private val client: HttpClient
): AirQualitySocketService {

    private var socketSession : WebSocketSession? = null

    override suspend fun openSession(): ConnectionState {

        return try {
            socketSession = client.webSocketSession {
                url(AirQualitySocketService.BASE_URL)
            }
            if (socketSession?.isActive == true){
                ConnectionState.Connected("Connection")
            }else{
                ConnectionState.CannotConnect("Connection not established")
            }
        }catch (e: Exception){
            ConnectionState.Error("Unknown Error")
        }
    }

    override fun refreshData(): Flow<List<AirQualityData>> {
        return try{
            socketSession?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json  = (it as? Frame.Text)?.readText() ?: ""
                    Log.d("test"," : "+json);
                    val gson = Gson()
                    val objectList = gson.fromJson(json, Array<TopGainersItem>::class.java).asList()
//                    val listAirQualityDataDto = Json.decodeFromString<List<TopGainersItem>>(json)

//                    objectList.

                    objectList.map { dto ->
                        dto.toAirQualityData()
                    }
                } ?: flow{}
        }catch(e : Exception){
            flow{}
        }

    }

    override suspend fun closeSession() {
        socketSession?.close()
    }
}

