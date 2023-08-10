package ru.petroplus.pos.networkapi

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Интерфейс для работы с сервером (балансировщиком) F5 (AC)
 */
interface GatewayServerApi {
    /**
     * Метод сервера по Ping
     */
    @Headers(
        "Accept: application/octet-stream",
        "SECURITY_FLAGS: SECURITY_FLAG_IGNORE_UNKNOWN_CA, SECURITY_FLAG_IGNORE_CERT_WRONG_USAGE, SECURITY_FLAG_IGNORE_CERT_CN_INVALID, SECURITY_FLAG_IGNORE_CERT_DATE_INVALID",
        "Connection: Keep-Alive",
        "User-Agent: P7_client",
        "X-SSL-Client-CN: POS-4000-00001-123456789",
        "X-Terminal-IP: 127.0.0.1",
        "Content-Type: application/octet-stream"
    )
    @POST(".")
    suspend fun pingGatewayServer(@Body request: RequestBody): Response<Any>
}