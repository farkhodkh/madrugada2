package ru.petroplus.pos.networkapi

import javax.net.ssl.SSLContext

interface GatewayServerRepositoryApi {

    /**
     * SSL контекcт подключения
     */
    var sslContext: SSLContext

    /**
     * ???
     * Код последнего ответа сервере
     */
    var lastResponseCode: Int

    fun doPing()

}