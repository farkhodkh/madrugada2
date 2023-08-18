package ru.petroplus.pos.networkworker.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class GatewayConfigScheduler {

    companion object {
        const val REMOTE_CONFIG_WORKER = "GatewayConfigWorker"
    }

    fun scheduleWorker(context: Context) {
        CoroutineScope(Dispatchers.Default).launch {

            val configurationUpdateInterval = 15L

            val remoteConfigWorkRequest = PeriodicWorkRequestBuilder<GatewayConfigWorker>(
                configurationUpdateInterval, TimeUnit.SECONDS
            )
                .build()

            WorkManager
                .getInstance(context)
                .enqueueUniquePeriodicWork(
                    REMOTE_CONFIG_WORKER,
                    ExistingPeriodicWorkPolicy.UPDATE,
                    remoteConfigWorkRequest
                )
        }
    }
}