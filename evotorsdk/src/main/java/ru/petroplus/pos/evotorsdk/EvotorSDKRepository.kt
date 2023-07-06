package ru.petroplus.pos.evotorsdk

import ru.evotor.pinpaddriver.external.api.ExternalLowLevelApiInterface
import ru.petroplus.pos.sdkapi.ApiCallbackInterface
import ru.petroplus.pos.sdkapi.IReaderService

/**
 * Reserved for future use
 */
class EvotorSDKRepository(connection: EvotorServiceConnection): IReaderService {

    private lateinit var requestInterface: ExternalLowLevelApiInterface

    init {
        connection.apiInterface?.let {
            requestInterface = it
        }
    }

    override fun sendCommand(bytes: ByteArray, callback: ApiCallbackInterface) {
        //requestInterface.sendCommand(bytes, callback)
    }

}