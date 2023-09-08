package ru.petrolplus.pos.network.repository

import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.petrolplus.pos.network.BuildConfig
import ru.petrolplus.pos.networkapi.auth.GatewayAuthenticationUtil
import ru.petrolplus.pos.networkapi.GatewayServerApi
import ru.petrolplus.pos.networkapi.GatewayServerRepositoryApi

class GatewayServerRepositoryImpl(private val gatewayServer: GatewayServerApi): GatewayServerRepositoryApi {
    override suspend fun doPing() {
        /**
         * Это временное, данные будут приходить от P7Lib
         */
        val body = GatewayAuthenticationUtil
            .getPingBinFile()
            .asRequestBody()

        val result = gatewayServer.pingGatewayServer(body)

        val g = result.body()
    }

    /**
     * метод для отправки данных в AS
     * @param byteArray массив байтов (получаем из P7Lib)
     * @return массив байтов для передачи в p7Lib
     * логгирует результат в дебаг сборке
     */
    override suspend fun sendData(byteArray: ByteArray): ByteArray? {
        val result = gatewayServer.sendData(byteArray.toRequestBody("application/octet-stream".toMediaType()))
        if (BuildConfig.DEBUG) Log.d("result", result.toString())
        return result.body()?.bytes()
    }
}