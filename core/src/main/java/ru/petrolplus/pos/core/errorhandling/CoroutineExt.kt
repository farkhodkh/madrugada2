package ru.petrolplus.pos.core.errorhandling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.launchHandling(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return launch(context = context + PosCoroutineExceptionHandler, start, block)
}

fun <T> runBlockingHandling(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): T {
    return runBlocking(context = context + PosCoroutineExceptionHandler, block)
}
fun <T> Flow<T>.launchInHandling(scope: CoroutineScope): Job = scope.launchHandling {
    collect()
}
