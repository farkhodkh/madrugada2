package ru.petroplus.pos.network.repository

import okhttp3.RequestBody.Companion.asRequestBody
import ru.petroplus.pos.network.auth.GatewayAuthenticationUtil
import ru.petroplus.pos.networkapi.GatewayServerApi
import ru.petroplus.pos.networkapi.GatewayServerRepositoryApi
import java.net.CookieHandler
import java.net.CookieManager

class GatewayServerRepositoryImpl(private val gatewayServer: GatewayServerApi): GatewayServerRepositoryApi {

    init {
        CookieHandler.setDefault(CookieManager())
    }

    override suspend fun doPing() {
        /**
         * Это временное, данные будут приходить от P7Lib
         */
        val body = GatewayAuthenticationUtil()
            .getPingBinFile()
            .asRequestBody()

        val result = gatewayServer.pingGatewayServer(body)

        val g = result.body()
    }
}