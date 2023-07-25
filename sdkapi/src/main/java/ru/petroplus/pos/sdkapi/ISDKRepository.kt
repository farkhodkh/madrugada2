package ru.petroplus.pos.sdkapi

/**
 * Репозитор для обработки команд и коллекции ответов от Reader
 */
interface ISDKRepository {
    fun sendCommand(bytes: String)
    var eventBus: ReaderEventBus
}