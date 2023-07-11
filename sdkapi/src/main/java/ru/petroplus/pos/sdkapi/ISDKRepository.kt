package ru.petroplus.pos.sdkapi

/**
 * Репозитор для обработки команд и коллекции ответов от Reader
 */
interface ISDKRepository {
    fun sendSDKCommand(bytes: String)
    var eventBus: ReaderEventBus
}