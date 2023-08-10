package ru.petroplus.pos.networkapi

/**
 * Репозиторий для работы со шлузом F5
 */
interface GatewayServerRepositoryApi {

    /**
     * Отправляет запрос ping
     */
    suspend fun doPing()

}