package ru.petroplus.pos.networkworker.executor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.petroplus.pos.networkapi.GatewayServerApi
import ru.petroplus.pos.networkapi.auth.GatewayAuthenticationUtil
import ru.petroplus.pos.networkworker.model.GatewayAction
import ru.petroplus.pos.networkworker.model.GatewayConfiguration
import ru.petroplus.pos.p7LibApi.IP7LibCallbacks
import ru.petroplus.pos.p7LibApi.IP7LibRepository
import kotlin.time.Duration

class GatewayExchangeExecutor(
    private val gatewayServer: GatewayServerApi,
    private val p7LibCallbacks: IP7LibCallbacks,
    private val p7LibRepository: IP7LibRepository
) : GatewayExchangeExecutorApi {

    /**
     * Интервал отправки Ping в шлюз
     * TODO - обсудить возможность храрения этого поля в конфигурации
     */
    private val pingInterval = Duration.parse("15s")
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

    override suspend fun makePing() {
        repeat(31_536_000) { i ->
//            val requestBody = p7LibCallbacks.getPingData()
            val requestBody = GatewayAuthenticationUtil.getPingBinFile()

            gatewayServerPinger(requestBody.asRequestBody())
                .onEach {
                    val b = this
                    val f = it
                }
                .launchIn(executorScope)

            delay(pingInterval)
        }
    }

    override fun sendData() {
        TODO("Not yet implemented")
    }

    private fun gatewayServerPinger(requestBody: RequestBody): Flow<String> = flow {
        val result = gatewayServer
            .pingGatewayServer(requestBody)

        emit(result.body() as String)
    }
}