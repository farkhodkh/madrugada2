package ru.petroplus.pos.networkworker.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.petroplus.pos.networkworker.executor.GatewayExchangeExecutorApi
import ru.petroplus.pos.networkworker.model.GatewayAction
import ru.petroplus.pos.networkworker.model.GatewayConfiguration

class GatewayConfigWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val executorTerminal: GatewayExchangeExecutorApi
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        executorTerminal
            .execute(configuration = GatewayConfiguration("001", listOf(GatewayAction.Ping)))

        return Result.success()
    }
}