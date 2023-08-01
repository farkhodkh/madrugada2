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

/**
 *
 */
class EvotorISDKRepository(context: Context) : ISDKRepository {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var requestInterface: ExternalLowLevelApiInterface? = null
    override var eventBus: ReaderEventBus = ReaderEventBus()

    init {

        val intent = Intent("ru.evotor.pinpaddriver.internal.CMTransportService")
        intent.component = ComponentName(
            "ru.evotor.pinpaddriver.internal",
            "ru.evotor.pinpaddriver.internal.driver.CMTransportService"
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

    override fun sendSDKCommand(input: String) {
        val commandBytes = Hex.decodeHex(input.toCharArray())
        requestInterface?.sendCommand(commandBytes, sdkCallback)
    }

    private suspend fun onReceivedData(receivedData: ByteArray) {
        eventBus.postEvent(receivedData)
    }

    internal inner class EvotorServiceConnection : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            requestInterface = ExternalLowLevelApiInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(className: ComponentName) {
            Log.e("TAG", "Service has unexpectedly disconnected")
            requestInterface = null
        }
    }
}