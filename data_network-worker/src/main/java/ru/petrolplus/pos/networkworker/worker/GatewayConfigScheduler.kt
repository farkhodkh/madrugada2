package ru.petrolplus.pos.networkworker.worker

import android.content.Context
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.petrolplus.pos.core.errorhandling.launchHandling

/**
 * Планировщик для инициализации Worker по пингу шлюза
 */
class GatewayConfigScheduler {

    companion object {
        const val REMOTE_CONFIG_WORKER = "GatewayConfigWorker"
    }

    fun scheduleWorker(context: Context) {
        CoroutineScope(Dispatchers.Default).launchHandling {

            val remoteConfigWorkRequest =
                OneTimeWorkRequest.Builder(GatewayConfigWorker::class.java).build()

            WorkManager
                .getInstance(context)
                .enqueue(remoteConfigWorkRequest)
        }
    }
}