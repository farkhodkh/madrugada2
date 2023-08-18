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
     * Метод для инициализации
     */
    fun makeInit()

    /**
     * Метод для отправки Ping
     */
    fun makePing()

    /**
     * Метод для отправки данных
     */
    fun sendData()
}