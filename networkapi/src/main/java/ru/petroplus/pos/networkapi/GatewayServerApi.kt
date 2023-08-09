package ru.petroplus.pos.networkapi

import retrofit2.http.Body
import retrofit2.http.POST
import ru.petroplus.pos.networkapi.request.PingRequest

/**
 * Интерфейс для работы с сервером (балансировщиком) F5 (AC)
 */
interface GatewayServerApi {
    @POST("")
    suspend fun pingGatewayServer(@Body request: PingRequest)
}