package ru.petroplus.pos.networkworker.executor

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.petroplus.pos.networkworker.model.GatewayAction
import ru.petroplus.pos.networkworker.model.GatewayConfiguration

class GatewayExchangeExecutor(
    private val applicationContext: Context
    ): GatewayExchangeExecutorApi {
    private val executorScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun execute(configuration: GatewayConfiguration) {
        executorScope.launch {
            configuration.actions.forEach { action ->
                when (action) {
                    GatewayAction.Init -> makeInit()
                    GatewayAction.Ping -> makePing()
                    GatewayAction.SendData -> sendData()
                    else -> {
                        //TODO - make warning unknown action
                    }
                }
            }
        }
    }

    override fun makeInit() {
        TODO("Not yet implemented")
    }

    override fun makePing() {

    }

    override fun sendData() {
        TODO("Not yet implemented")
    }
}