package ru.petrolplus.pos.evotorsdk

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import ru.evotor.pinpaddriver.external.api.ExternalLowLevelApiCallbackInterface
import ru.evotor.pinpaddriver.external.api.ExternalLowLevelApiInterface
import ru.petrolplus.pos.evotorsdk.dto.DataDto
import ru.petrolplus.pos.evotorsdk.exaption.EvotorNotInitializedException
import ru.petrolplus.pos.evotorsdk.util.HexUtil
import ru.petrolplus.pos.evotorsdk.util.TlvCommands
import ru.petrolplus.pos.sdkapi.ISDKRepository
import ru.petrolplus.pos.sdkapi.dto.CommonStateDto
import ru.petrolplus.pos.sdkapi.dto.GetCardReaderInfoDto
import ru.petrolplus.pos.sdkapi.dto.InitCardReaderDto
import ru.petrolplus.pos.sdkapi.dto.InitDeviceDto
import ru.petrolplus.pos.sdkapi.dto.TerminalDataDto
import ru.petrolplus.pos.sdkapi.tlv.BerTlvParser
import ru.petrolplus.pos.sdkapi.tlv.BerTlvs
import ru.petrolplus.pos.util.ResourceHelper
import ru.petrolplus.pos.util.ext.getNextCommandNumber
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.jvm.Throws

/**
 * Репозиторий для работы с SDK терминалов от поставщика "Эвотор"
 */
class EvotorSDKRepository(context: Context) : ISDKRepository {
    private val _latestCommands: MutableSharedFlow<TerminalDataDto> = MutableSharedFlow(10)
    override val latestCommands: Flow<TerminalDataDto> = _latestCommands

    //53 42 41 04 04 00 05 02 90 00 00 00 00 04
    private val tlvDataCodePosition = 4
    private val tlvDataRCPosition = 5
    private val tlvDataStartPosition = 6
    private var requestInterface: ExternalLowLevelApiInterface? = null
    private var commandNumber: AtomicInteger = AtomicInteger(0)
    private val actionName = "ru.evotor.pinpaddriver.internal.CMTransportService"
    private val packageName = "ru.evotor.pinpaddriver.internal"
    private val className = "ru.evotor.pinpaddriver.internal.driver.CMTransportService"
    private val successCode = 0

    init {

        val intent = Intent(actionName)
        intent.component = ComponentName(
            packageName,
            className
        )

        val connection = EvotorServiceConnection()
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    @Throws(EvotorNotInitializedException::class, Exception::class)
    override fun sendCommandTlv(bytesString: String): BerTlvs? = runBlocking {
        if (requestInterface == null) {
            throw EvotorNotInitializedException(
                ResourceHelper
                    .getStringResource(R.string.terminal_not_initialized_error)
            )
        }

        val number = commandNumber.incrementAndGet()

        return@runBlocking try {
            val commandLine =
                "${TlvCommands.RequestHeader.code}${number.getNextCommandNumber()}$bytesString"

            val charArray = "$commandLine".toCharArray()
            val commandBytes = HexUtil.decodeHex(charArray)

            val response: ByteArray = suspendCoroutine<ByteArray> { continuation ->
                requestInterface?.sendCommand(
                    commandBytes,
                    object : ExternalLowLevelApiCallbackInterface.Stub() {
                        override fun onReceive(received: ByteArray?) {
                            received?.let { result ->
                                continuation.resume(result)
                            }
                        }
                    })
            }
            parseTlvData(safeArrayCopy(response).dataArray)
        } catch (ex: Exception) {
            throw ex
        }
    }

    @Throws(EvotorNotInitializedException::class, Exception::class)
    override fun sendCommand(bytesString: String): ByteArray = runBlocking {
        if (requestInterface == null) {
            throw EvotorNotInitializedException(
                ResourceHelper
                    .getStringResource(R.string.terminal_not_initialized_error)
            )
        }

        val number = commandNumber.incrementAndGet()

        return@runBlocking try {
            val commandLine =
                "${TlvCommands.RequestHeader.code}${number.getNextCommandNumber()}$bytesString"

            val charArray = "$commandLine".toCharArray()
            val commandBytes = HexUtil.decodeHex(charArray)

            val response: ByteArray = suspendCoroutine<ByteArray> { continuation ->
                requestInterface?.sendCommand(
                    commandBytes,
                    object : ExternalLowLevelApiCallbackInterface.Stub() {
                        override fun onReceive(received: ByteArray?) {
                            received?.let { result ->
                                continuation.resume(result)
                            }
                        }
                    })
            }
            response
        } catch (ex: Exception) {
            throw ex
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
    override fun sendCommandSync(bytesString: String) {
        try {
            val result = sendCommand(bytesString)
            onReceivedData(result)
        } catch (ex: EvotorNotInitializedException) {
            onReceivedData(ex.desc)
        } catch (ex: Exception) {
            onReceivedData(
                "{${ex::class.java.simpleName}: ${ex.localizedMessage}}".toByteArray(
                    Charsets.UTF_8
                )
            )
        }
    }

    private fun onReceivedData(receivedData: ByteArray) {
        parseReceivedData(receivedData)
    }

    private fun onReceivedData(receivedData: String) {
        _latestCommands.tryEmit(CommonStateDto(data = null, dataString = receivedData))
    }

    /**
     * Метод парсер ответа Эвотор
     * @return класс DataDto с содержимом результата
     */
    private fun safeArrayCopy(receivedData: ByteArray): DataDto {
        val code = receivedData.getOrNull(tlvDataCodePosition)?.toInt()?.let {
            TlvCommands.findById(it)
        }

        val alignment = if (code?.hasAlignment == true) {
            receivedData.lastOrNull()?.toInt() ?: 0
        } else {
            0
        }

        val rcResult = receivedData.getOrNull(tlvDataRCPosition) ?: 0

        val data: ByteArray
        if (receivedData.lastIndex > tlvDataStartPosition) {
            data = ByteArray(receivedData.size - tlvDataStartPosition- alignment)
            System.arraycopy(
                receivedData,
                tlvDataStartPosition,
                data,
                0,
                receivedData.size - tlvDataStartPosition - alignment
            )
        } else {
            data = byteArrayOf()
        }

        return DataDto(code, rcResult.toInt(), data)
    }

    /**
     *  SBA<Seq(1 byte)><Code(1 byte)><Rc(1 byte)><Data(?)>
     *  SBA - Заголовок ответа
     *  Seq	- последовательный номер – копируется из запроса
     *  Code - Код команды – копируется из запроса
     *  Rc - Код завершения
     *  Data - блок TLV данных ответа (если присутствует)
     */
    private fun parseReceivedData(receivedData: ByteArray) {
        val tlvDto = safeArrayCopy(receivedData)

        val tlvData = if (tlvDto.dataArray.isNotEmpty()) {
            parseTlvData(tlvDto.dataArray)
        } else null

        when (tlvDto.code) {
            null -> {
                onReceivedData("Не известная ошибка при работе с терминалом, не определена TLV команда")
            }

            is TlvCommands.InitDevice -> {
                if (tlvDto.rc == successCode) {
                    _latestCommands.tryEmit(
                        InitDeviceDto()
                    )
                } else {
                    onReceivedData("Ошибка при инициализации терминала")
                }
            }

            is TlvCommands.GetDeviceInfo -> {
                val b = 0
            }

            is TlvCommands.GetCardReaderInfo -> {
                tlvData?.let {
                    if (parseCardReaderInfo(it)) {
                        _latestCommands.tryEmit(
                            GetCardReaderInfoDto(data = it)
                        )
                    } else (onReceivedData("Ошибка при чтении данных с карты"))
                } ?: onReceivedData("Ошибка при чтении данных с карты")
            }

            is TlvCommands.InitCardReader -> {
                if (tlvDto.rc == successCode) {
                    _latestCommands.tryEmit(
                        InitCardReaderDto()
                    )
                } else onReceivedData("Ошибка при инициализации card reader")
            }

            is TlvCommands.ExchangeWithAPDU -> {
                val b = 0
            }

            is TlvCommands.EnterOfflinePin -> {
                val b = 0
            }

            else -> {
                onReceivedData("${receivedData.map { HexUtil.toHexString(it) }}")
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
            //Init device
            sendCommandSync(TlvCommands.InitDevice.code)
        }
    }

    private fun parseTlvData(byteData: ByteArray): BerTlvs? =
        try {
            BerTlvParser().parse(byteData)
        } catch (ex: Exception) {
            null
        }

    private fun parseCardReaderInfo(tlvData: BerTlvs): Boolean {
        return try {
            return tlvData
                .list
                .lastOrNull { it.tag.bytes.contains(2) }
                ?.bytesValue?.isNotEmpty() ?: false
        } catch (ex: Exception) {
            false
        }
    }
}