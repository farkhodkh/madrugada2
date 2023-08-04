package ru.petroplus.pos.evotorsdk

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.evotor.pinpaddriver.external.api.ExternalLowLevelApiCallbackInterface
import ru.evotor.pinpaddriver.external.api.ExternalLowLevelApiInterface
import ru.petroplus.pos.evotorsdk.util.Hex
import ru.petroplus.pos.sdkapi.ISDKRepository
import ru.petroplus.pos.sdkapi.ReaderEventBus
import ru.petroplus.pos.util.ResourceHelper
import java.math.BigInteger

/**
 *
 */
class EvotorSDKRepository(context: Context) : ISDKRepository {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var requestInterface: ExternalLowLevelApiInterface? = null
    override var eventBus: ReaderEventBus = ReaderEventBus()
    private val requestHeader: String by lazy {
        String.format("%04x", BigInteger(1, "SBR".toByteArray(Charsets.UTF_8)))
    }
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
                scope.launch {
                    onReceivedData(it)
                }
            }
        }
    }

    override fun sendCommand(input: String) {
        if (requestInterface == null) {
            scope.launch {
                onReceivedData(
                    ResourceHelper.getStringResource(R.string.terminal_not_initialized_error)
                        .toByteArray(Charsets.UTF_8)
                )
            }
            return
        }

        commandNumber += 1

        try {
            val commandLine = "$requestHeader${commandNumber.getNextCommandNumber()}$input"
            if (BuildConfig.DEBUG) {
                scope.launch {
                    onReceivedData("Отправил команду: $commandLine")
                }
            }
            val charArray = "$commandLine".toCharArray()
            val commandBytes = Hex.decodeHex(charArray)
            requestInterface?.sendCommand(commandBytes, sdkCallback)
        } catch (ex: Exception) {
            scope.launch {
                onReceivedData(
                    "{${ex::class.java.simpleName}: ${ex.localizedMessage}}".toByteArray(
                        Charsets.UTF_8
                    )
                )
            }
        }
    }

    private fun onReceivedData(receivedData: ByteArray) {
        parseReceivedData(receivedData.map { Hex.toHexString(it) })
    }

    private suspend fun onReceivedData(receivedData: String) {
        eventBus.postEvent(receivedData)
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
        scope.launch {
            if (receiveDataList.getOrNull(4) == "0A" && receiveDataList.getOrNull(5) == "00" ) {
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
            Log.e("TAG", "Service has unexpectedly disconnected")
            requestInterface = null
        }
    }

    private fun initDevice() {
        sendCommand("0A4200000000000006")
    }
}

fun Int.getNextCommandNumber(): String {
    return Integer.toHexString(this).padStart(2, '0')
}