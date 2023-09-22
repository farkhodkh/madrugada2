package ru.petrolplus.pos.evotorsdk

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.evotor.pinpaddriver.external.api.ExternalLowLevelApiCallbackInterface
import ru.evotor.pinpaddriver.external.api.ExternalLowLevelApiInterface
import ru.petrolplus.pos.core.errorhandling.launchHandling
import ru.petrolplus.pos.evotorsdk.util.HexUtil
import ru.petrolplus.pos.evotorsdk.util.TlvCommands
import ru.petrolplus.pos.sdkapi.ISDKRepository
import ru.petrolplus.pos.resources.ResourceHelper
import ru.petrolplus.pos.util.ext.getNextCommandNumber
import ru.petrolplus.pos.R

/**
 * Репозиторий для работы с SDK терминалов от поставщика "Эвотор"
 */
class EvotorSDKRepository(context: Context) : ISDKRepository {
    private val _latestCommands: MutableSharedFlow<String> = MutableSharedFlow(10)
    override val latestCommands: Flow<String> = _latestCommands

    private val scope = CoroutineScope(Dispatchers.IO)
    private var requestInterface: ExternalLowLevelApiInterface? = null
    private var commandNumber: Int = 0
    private val actionName = "ru.evotor.pinpaddriver.internal.CMTransportService"
    private val packageName = "ru.evotor.pinpaddriver.internal"
    private val className = "ru.evotor.pinpaddriver.internal.driver.CMTransportService"

    init {

        val intent = Intent(actionName)
        intent.component = ComponentName(
            packageName,
            className
        )

        val connection = EvotorServiceConnection()
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    private val sdkCallback = object : ExternalLowLevelApiCallbackInterface.Stub() {
        override fun onReceive(received: ByteArray?) {
            received?.let {
                scope.launchHandling {
                    onReceivedData(it)
                }
            }
        }
    }

    /**
     * 1) При запуске приложения вызывать команду 00 (пакет будет выглядеть так: 5342520100)
     * 2) В процессе работы проверять результат работы команды 25, и если код ошибки = 08,
     * то делать вывод, что произошла рассинхронизация драйвера и модуля эквайринга и делать повтор 00.
     * Если вы в дальнейшем захотите работать с онлайн пин, то к команде 00 добавите команду 0A
     * с установкой необходимых ключей.
     * То есть, в этой схеме инициализация/синхронизация вызывается по необходимости.
     */
    override fun sendCommand(bytesString: String) {
        if (requestInterface == null) {
            scope.launchHandling {
                onReceivedData(
                    ResourceHelper
                        .getStringResource(R.string.terminal_not_initialized_error).toByteArray(Charsets.UTF_8)
                )
            }
            return
        }

        commandNumber += 1

        try {
            val commandLine = "${TlvCommands.RequestHeader.code}${commandNumber.getNextCommandNumber()}$bytesString"
            if (BuildConfig.DEBUG) {
                scope.launchHandling {
                    onReceivedData("Отправил команду: $commandLine")
                }
            }
            val charArray = "$commandLine".toCharArray()
            val commandBytes = HexUtil.decodeHex(charArray)
            requestInterface?.sendCommand(commandBytes, sdkCallback)
        } catch (ex: Exception) {
            scope.launchHandling {
                onReceivedData(
                    "{${ex::class.java.simpleName}: ${ex.localizedMessage}}".toByteArray(
                        Charsets.UTF_8
                    )
                )
            }
        }
    }

    private fun onReceivedData(receivedData: ByteArray) {
        parseReceivedData(receivedData.map { HexUtil.toHexString(it) })
    }

    private fun onReceivedData(receivedData: String) {
        _latestCommands.tryEmit(receivedData)
    }

    /**
     *  SBA<Seq(1 byte)><Code(1 byte)><Rc(1 byte)><Data(?)>
     *  SBA - Заголовок ответа
     *  Seq	- последовательный номер – копируется из запроса
     *  Code - Код команды – копируется из запроса
     *  Rc - Код завершения
     *  Data - блок TLV данных ответа (если присутствует)
     */
    private fun parseReceivedData(receiveDataList: List<String>) {
        scope.launchHandling {
            if (receiveDataList.getOrNull(4) == "0A" && receiveDataList.getOrNull(5) == "00") {
                onReceivedData("Терминал инициализирован, можно работать")
            } else {
                onReceivedData("$receiveDataList")
            }
        }
    }

    internal inner class EvotorServiceConnection : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            requestInterface = ExternalLowLevelApiInterface.Stub.asInterface(service)
            initDevice()
        }

        override fun onServiceDisconnected(className: ComponentName) {
            requestInterface = null
        }

        /**
         * 1) При запуске приложения вызывать команду 00 (пакет будет выглядеть так: 00)
         * 2) В процессе работы проверять результат работы команды 25, и если код ошибки = 08,
         * то делать вывод, что произошла рассинхронизация драйвера и модуля эквайринга и делать повтор 00.
         * Если вы в дальнейшем захотите работать с онлайн пин, то к команде 00 добавите команду 0A с
         * установкой необходимых ключей.
         * То есть, в этой схеме инициализация/синхронизация вызывается по необходимости.
         */
        private fun initDevice() {
            sendCommand(TlvCommands.InitDevice.code)
        }
    }
}