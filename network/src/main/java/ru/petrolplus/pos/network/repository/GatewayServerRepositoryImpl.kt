package ru.petrolplus.pos.network.repository

import okhttp3.RequestBody.Companion.asRequestBody
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
}