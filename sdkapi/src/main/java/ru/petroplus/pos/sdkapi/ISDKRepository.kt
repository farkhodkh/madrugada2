package ru.petroplus.pos.sdkapi

interface ISDKRepository {
    fun sendSDKCommand(bytes: String)
    var eventBus: ReaderEventBus
}