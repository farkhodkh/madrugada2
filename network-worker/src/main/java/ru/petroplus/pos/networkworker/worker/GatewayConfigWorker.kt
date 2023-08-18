package ru.petroplus.pos.networkworker.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.petroplus.pos.networkworker.executor.GatewayExchangeExecutorApi

class GatewayConfigWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val executorTerminal: GatewayExchangeExecutorApi
): CoroutineWorker(appContext, workerParams)  {

    override suspend fun doWork(): Result {

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.failure()
        }

        executorTerminal.makePing()

        return Result.success()
    }
}