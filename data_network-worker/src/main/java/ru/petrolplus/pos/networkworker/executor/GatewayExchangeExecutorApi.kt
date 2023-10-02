package ru.petrolplus.pos.networkworker.executor

import ru.petrolplus.pos.networkworker.model.GatewayConfiguration

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
}
