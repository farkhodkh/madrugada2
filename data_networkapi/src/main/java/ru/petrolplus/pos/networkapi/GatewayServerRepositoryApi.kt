package ru.petrolplus.pos.networkapi

/**
 * Репозиторий для работы со шлузом F5
 */
interface GatewayServerRepositoryApi {

    /**
     * Отправляет запрос ping
     */
    suspend fun doPing()

}