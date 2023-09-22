package ru.petrolplus.pos.networkworker.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.petrolplus.pos.networkworker.executor.GatewayExchangeExecutorApi

/**
 *
 * Фактори для инициализации Worker
 */
class GatewayConfigFactory(private val executor: GatewayExchangeExecutorApi) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            GatewayConfigWorker::class.java.name -> {
                GatewayConfigWorker(appContext, workerParameters, executor)
            }

            else -> null
        }
    }
}