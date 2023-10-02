package ru.petrolplus.pos.networkworker.executor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.petrolplus.pos.core.errorhandling.launchHandling
import ru.petrolplus.pos.core.errorhandling.launchInHandling
import ru.petrolplus.pos.networkapi.GatewayServerApi
import ru.petrolplus.pos.networkapi.auth.GatewayAuthenticationUtil
import ru.petrolplus.pos.networkworker.model.GatewayAction
import ru.petrolplus.pos.networkworker.model.GatewayConfiguration
import ru.petrolplus.pos.p7LibApi.IP7LibCallbacks
import ru.petrolplus.pos.p7LibApi.IP7LibRepository
import kotlin.time.Duration

/**
 * Класс executor для выполнения действий worker
 */
class GatewayExchangeExecutor(
    private val gatewayServer: GatewayServerApi,
    private val p7LibCallbacks: IP7LibCallbacks,
    private val p7LibRepository: IP7LibRepository,
) : GatewayExchangeExecutorApi {

    /**
     * Интервал отправки Ping в шлюз
     * TODO - обсудить возможность храрения этого поля в конфигурации
     */
    private val pingInterval = Duration.parse("15s")
    private val executorScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /**
     * Количество повторов отправки пинга с интервалом 15 сек. Равено году
     */
    private val yearInSeconds = 31_536_000

    override fun execute(configuration: GatewayConfiguration) {
        executorScope.launchHandling {
            configuration.actions.forEach { action ->
                when (action) {
                    GatewayAction.Ping -> makePing()
                    else -> {
                        // TODO - make warning unknown action
                    }
                }
            }
        }
    }

    override suspend fun makePing() {
        repeat(yearInSeconds) { i ->
            // TODO - Переделать на запрос OOB от P7Lib когда такое будет реализовано
//            val requestBody = p7LibCallbacks.getPingData()
            val requestBody = GatewayAuthenticationUtil.getPingBinFile()

            gatewayServerPinger(requestBody.asRequestBody())
                .onEach {
                    // TODO - Реализовать передачу признака успешного обмена в p7lib
                    val b = this
                    val f = it
                }
                .launchInHandling(executorScope)

            delay(pingInterval)
        }
    }

    private fun gatewayServerPinger(requestBody: RequestBody): Flow<String> = flow {
        val result = gatewayServer
            .pingGatewayServer(requestBody)

        emit(result.body() as String)
    }
}
