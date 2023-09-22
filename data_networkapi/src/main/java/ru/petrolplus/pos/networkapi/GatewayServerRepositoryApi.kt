package ru.petrolplus.pos.networkapi

/**
 * Репозиторий для работы со шлузом F5
 */
interface GatewayServerRepositoryApi {

    /**
     * Отправляет запрос ping
     */
    suspend fun doPing()

    /**
     * Отправляет данные от p7Lib на AS
     */
    suspend fun sendData(byteArray: ByteArray): ByteArray?

}