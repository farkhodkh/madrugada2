package ru.petroplus.pos.sdkapi

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class ReaderEventBus {
    private val _events = Channel<ByteArray>()
    val events = _events.receiveAsFlow()

    suspend fun postEvent(event: ByteArray) {
        _events.send(event)
    }
}