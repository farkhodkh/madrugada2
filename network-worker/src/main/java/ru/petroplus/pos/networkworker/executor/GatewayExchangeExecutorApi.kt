package ru.petroplus.pos.networkworker.executor

import ru.petroplus.pos.networkworker.model.GatewayConfiguration

/**
 * Интерфейс для обмена со шлюзом
 */
interface GatewayExchangeExecutorApi {
    /**
     * Executor для Worker
     */
    fun execute(configuration: GatewayConfiguration)

    /**
     * Метод для отправки Ping
     */
    suspend fun makePing()

    /**
     * Метод для отправки данных
     */
    fun sendData()
}