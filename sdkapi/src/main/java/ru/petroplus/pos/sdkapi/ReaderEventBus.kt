package ru.petroplus.pos.sdkapi

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Event Bus для коллекции ответов от Reader терминала
 */
class ReaderEventBus {
    private val _events = Channel<String>()
    val events = _events.receiveAsFlow()

    suspend fun postEvent(event: String) {
        _events.send(event)
    }
}