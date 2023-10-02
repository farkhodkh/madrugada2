package ru.petrolplus.pos.core.errorhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

object PosCoroutineExceptionHandler :
    AbstractCoroutineContextElement(CoroutineExceptionHandler),
    CoroutineExceptionHandler {

    private val _errorsRelay: MutableSharedFlow<Throwable> = MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.SUSPEND)
    val errorsRelay: Flow<Throwable> = _errorsRelay

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        _errorsRelay.tryEmit(exception)
    }
}
