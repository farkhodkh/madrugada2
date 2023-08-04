package ru.petroplus.pos.networkapi

import javax.net.ssl.SSLContext

/**
 * Интерфейс для работы с сервером (балансировщиком) F5 (AC)
 */
interface IGatewayServerApi {

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