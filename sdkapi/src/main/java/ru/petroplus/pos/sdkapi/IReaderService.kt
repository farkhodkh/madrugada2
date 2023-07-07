package ru.petroplus.pos.sdkapi

/**
 * Reserved for future use
 */
interface IReaderService {
    fun sendCommand(bytes: ByteArray, callback: ApiCallbackInterface)
}