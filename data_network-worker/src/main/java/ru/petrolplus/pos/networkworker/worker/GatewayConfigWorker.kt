package ru.petrolplus.pos.networkworker.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.petrolplus.pos.networkworker.executor.GatewayExchangeExecutorApi
import ru.petrolplus.pos.networkworker.model.GatewayAction
import ru.petrolplus.pos.networkworker.model.GatewayConfiguration

/**
 * Worker для выполнения работ планировщика
 */
class GatewayConfigWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val executorTerminal: GatewayExchangeExecutorApi
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        executorTerminal
            .execute(configuration = GatewayConfiguration(GatewayConfiguration.VERSION_1, listOf(GatewayAction.Ping)))

        return Result.success()
    }
}