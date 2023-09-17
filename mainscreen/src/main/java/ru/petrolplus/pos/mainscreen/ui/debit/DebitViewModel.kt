package ru.petrolplus.pos.mainscreen.ui.debit

import android.os.Bundle
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.petrolplus.pos.evotorsdk.util.HexUtil
import ru.petrolplus.pos.evotorsdk.util.TlvCommands
import ru.petrolplus.pos.evotorsdk.util.TlvTags
import ru.petrolplus.pos.persitence.ReceiptPersistence
import ru.petrolplus.pos.persitence.SettingsPersistence
import ru.petrolplus.pos.persitence.TransactionsPersistence
import ru.petrolplus.pos.persitence.dto.GUIDParamsDTO
import ru.petrolplus.pos.persitence.dto.TransactionDTO
import ru.petrolplus.pos.mainscreen.BuildConfig
import ru.petrolplus.pos.mainscreen.R
import ru.petrolplus.pos.mainscreen.ui.debit.debug.DebitDebugGroup
import ru.petrolplus.pos.mainscreen.ui.ext.toInitDataDto
import ru.petrolplus.pos.networkapi.GatewayServerRepositoryApi
import ru.petrolplus.pos.p7LibApi.IP7LibCallbacks
import ru.petrolplus.pos.p7LibApi.IP7LibRepository
import ru.petrolplus.pos.p7LibApi.OnP7LibResultListener
import ru.petrolplus.pos.p7LibApi.dto.CardKeyDto
import ru.petrolplus.pos.p7LibApi.dto.TransactionUUIDDto
import ru.petrolplus.pos.p7LibApi.dto.card.CardInfo
import ru.petrolplus.pos.p7LibApi.requests.ApduData
import ru.petrolplus.pos.p7LibApi.requests.toEvotorApduByteArray
import ru.petrolplus.pos.p7LibApi.responces.ApduAnswer
import ru.petrolplus.pos.p7LibApi.responces.CardAuthError
import ru.petrolplus.pos.p7LibApi.responces.NotPetrol7Card
import ru.petrolplus.pos.p7LibApi.responces.OK
import ru.petrolplus.pos.printerapi.PrinterRepository
import ru.petrolplus.pos.sdkapi.CardReaderRepository
import ru.petrolplus.pos.sdkapi.tlv.BerTag
import ru.petrolplus.pos.sdkapi.tlv.BerTlvBuilder
import ru.petrolplus.pos.sdkapi.tlv.BerTlvParser
import ru.petrolplus.pos.util.ResourceHelper
import ru.petrolplus.pos.util.ext.getEvotorAlignmentString
import ru.petrolplus.pos.util.ext.toEvotorCommandLine
import java.util.Calendar
import kotlin.random.Random

class DebitViewModel(
    private val cardReaderRepository: CardReaderRepository,
    private val printer: PrinterRepository,
    private val gatewayServer: GatewayServerRepositoryApi,
    private val transactionsPersistence: TransactionsPersistence,
    private val settingsPersistence: SettingsPersistence,
    private val receiptPersistence: ReceiptPersistence,
    private val p7LibRepository: IP7LibRepository,
    private val p7LibCallbacks: IP7LibCallbacks,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val tlvParser: BerTlvParser = BerTlvParser()
    private val _viewState = mutableStateOf<DebitViewState>(DebitViewState.StartingState)
    val viewState: State<DebitViewState> = _viewState

    init {
        viewModelScope.launch {
            cardReaderRepository
                .sdkRepository
                .latestCommands
                .onEach { value ->
                    _viewState.value = DebitViewState
                        .CommandExecutionState(value.dataString)
                }
                .launchIn(viewModelScope)
        }

        p7LibCallbacks.listener = object : OnP7LibResultListener {
            override fun onCardReset(answer: ApduAnswer): String? {
                val berTlv = cardReaderRepository
                    .sdkRepository
                    .sendCommandTlv(
                        TlvCommands
                            .GetCardReaderInfo
                            .code
                    )

                return berTlv?.list?.lastOrNull()?.bytesValue?.let { tagData ->
                    HexUtil.toHexString(tagData)
                }
            }

            override fun onSendDataToCard(data: ApduData, answer: ApduAnswer): ByteArray? {
                val dataArray = data.toEvotorApduByteArray()

                val byteArray: List<String> = BerTlvBuilder()
                    .addHex(BerTag(TlvTags.CardFind.code), TlvTags.CardFind.defValue)
                    .addHex(BerTag(TlvTags.Apdu.code), HexUtil.toHexString(dataArray))
                    .buildArray().map { HexUtil.toHexString(it) }

                val command = byteArray.toEvotorCommandLine()

                val berTlv = cardReaderRepository
                    .sdkRepository
                    .sendCommandTlv(
                "${TlvCommands.ExchangeWithAPDU.code}$command${command.getEvotorAlignmentString()}"
                    )

                return berTlv?.list?.lastOrNull()?.bytesValue
            }
        }

        if (BuildConfig.DEBUG) {
            //Тестовое состояние экрана в случае если тип сборки DEBUG
            _viewState.value = DebitViewState.DebugState.APDU
        }
    }

    fun ping() {
        viewModelScope.launch(Dispatchers.IO) {
            gatewayServer.doPing()
        }
    }

    fun sendCommand(command: String) {
        cardReaderRepository.sdkRepository.sendCommandSync(command)
    }

    /**
     * Метод который вызывается для повтора печати в случае ошибки
     * @throws IllegalStateException если функция вызвана при отсутствии ошибки печати
     */
    fun repeatPrinting() {
        when (val state = _viewState.value) {
            is DebitViewState.DebugState.PrinterState.FailedState.Receipt -> printTransactionTest(
                state.transactionId
            )

            DebitViewState.DebugState.PrinterState.FailedState.ShiftReport -> printShiftReport()
            else ->
                throw IllegalStateException(ResourceHelper.getStringResource(R.string.current_state_forbidden_calling_function))
        }
    }

    fun resetPrinter() {
        _viewState.value = DebitViewState.DebugState.PrinterState.WaitDocument
    }

    private fun onFailPrintShiftReport() {
        _viewState.value = DebitViewState.DebugState.PrinterState.FailedState.ShiftReport
    }

    private fun onFailPrintReceipt(transactionId: String) {
        _viewState.value = DebitViewState.DebugState.PrinterState.FailedState.Receipt(transactionId)
    }

    companion object {
        fun provideFactory(
            cardReaderRepository: CardReaderRepository,
            printerRepository: PrinterRepository,
            gatewayServer: GatewayServerRepositoryApi,
            transactionsPersistence: TransactionsPersistence,
            settingsPersistence: SettingsPersistence,
            receiptPersistence: ReceiptPersistence,
            owner: SavedStateRegistryOwner,
            p7LibRepository: IP7LibRepository,
            p7LibCallbacks: IP7LibCallbacks,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String, modelClass: Class<T>, handle: SavedStateHandle
                ): T {
                    return DebitViewModel(
                        cardReaderRepository,
                        printerRepository,
                        gatewayServer,
                        transactionsPersistence,
                        settingsPersistence,
                        receiptPersistence,
                        p7LibRepository,
                        p7LibCallbacks,
                        handle
                    ) as T
                }
            }
    }

    //region TEST_METHODS

    //FIXME: Метод тестовый. Распечатывает сменный отчет
    //Проверяется возможна ли сейчас печать. Переход в состояние печати
    //В Debug сборке симуляция ошибки во время печати. Печать чека с отслеживанием наличия ошибки
    fun printShiftReport() {
        if (_viewState.value == DebitViewState.DebugState.PrinterState.Printing) return
        _viewState.value = DebitViewState.DebugState.PrinterState.Printing

        viewModelScope.launch(Dispatchers.IO) {
            // Симуляция ошибки во время печати
            if (BuildConfig.DEBUG) {
                delay(300)
                if (!Random.nextBoolean()) {
                    onFailPrintShiftReport()
                    return@launch
                }
            }

            val shiftReceipt = receiptPersistence.getShiftReceipt()
            val currentDate = Calendar.getInstance().time
            when (printer.printShiftReport(shiftReceipt, currentDate)) {
                null -> resetPrinter()
                else -> onFailPrintShiftReport()
            }
        }
    }

    //FIXME: Метод тестовый. Распечатывает чек по введенному ID
    //Проверяется возможна ли сейчас печать. Переход в состояние печати
    //Запрос данных для печати чека из БД. Проверка полученных данных
    //В Debug сборке симуляция ошибки во время печати. Печать чека с отслеживанием наличия ошибки
    fun printTransactionTest(transactionId: String) {
        if (transactionId.isEmpty()
            || _viewState.value == DebitViewState.DebugState.PrinterState.Printing
        ) return

        _viewState.value = DebitViewState.DebugState.PrinterState.Printing

        viewModelScope.launch(Dispatchers.IO) {
            val data = receiptPersistence.getDebitReceipt(transactionId)
            if (data == null) {
                onFailPrintReceipt(transactionId)
                return@launch
            }

            // Симуляция ошибки во время печати
            if (BuildConfig.DEBUG) {
                delay(300)
                if (!Random.nextBoolean()) {
                    onFailPrintReceipt(transactionId)
                    return@launch
                }
            }

            when (printer.printReceipt(data)) {
                null -> resetPrinter()
                else -> onFailPrintReceipt(transactionId)
            }
        }
    }

    //FIXME: Метод тестовый. Проверка работы библиотеки P7Lib
    fun testP7LibCommand() {
        viewModelScope.launch {
            val initData = settingsPersistence.getBaseSettings().toInitDataDto()
            val uuidDto = TransactionUUIDDto()
            val cacheDir = ResourceHelper.getExternalCacheDirectory() ?: ""
            val result = p7LibRepository.init(initData, uuidDto, p7LibCallbacks, cacheDir, cacheDir)

            _viewState.value = when (result) {
                is OK -> {
                    DebitViewState
                        .CommandExecutionState("Библиотека P7Lib успешно инициализирован")
                }

                else -> {
                    DebitViewState
                        .CommandExecutionState("Ошибка при инициализации P7Lib")
                }
            }
        }
    }

    //FIXME: Метод тестовый, не потребуется в проде, напрямую взоидействие базы и UI не планируется
    //Загружает все транзакции из бд
    private suspend fun loadTransactions() {
        val oldState = _viewState.value as? DebitViewState.DebugState.Debit
        val transactions = transactionsPersistence.getAll().map { it.toString() }
        _viewState.value = oldState?.copy(
            debitDebugGroup = oldState.debitDebugGroup.copy(
                transactionsOutput = transactions
            )
        ) ?: DebitViewState.DebugState.Debit(DebitDebugGroup(transactionsOutput = transactions))
    }

    //FIXME: Метод тестовый, не потребуется в проде, напрямую взоидействие базы и UI не планируется
    //добавляет GUID параметры в бд и подгружает ее из бд
    fun saveGUIDParams(guidParams: GUIDParamsDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsPersistence.setGUIDparams(guidParams)
            val oldState = _viewState.value as DebitViewState.DebugState.Debit
            _viewState.value = oldState.copy(
                debitDebugGroup = oldState.debitDebugGroup.copy(
                    guidParamsOutput = listOf(settingsPersistence.getGUIDparams().toString())
                )
            )
        }
    }

    //FIXME: Метод тестовый, не потребуется в проде, напрямую взоидействие базы и UI не планируется
    //Изза бага с рассинхроном состояния экрана и отображения, при переключении вкладок так-же меняем стейты
    fun setTab(index: Int) {
        // Блокировака переключения пока идёт печать
        if (_viewState.value == DebitViewState.DebugState.PrinterState.Printing) return

        when (index) {
            0 -> _viewState.value = DebitViewState.DebugState.APDU
            2 -> _viewState.value = DebitViewState.DebugState.PrinterState.WaitDocument
            else -> _viewState.value = DebitViewState.DebugState.Debit()
        }
    }


    //FIXME: Метод тестовый, не потребуется в проде, напрямую взоидействие базы и UI не планируется
    //Загружает все транзакции из бд в IO планировщике
    fun fetchTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            loadTransactions()
        }
    }


    //FIXME: Метод тестовый, по хорошему заменится тестами или переедет в тестовую вью модель
    //меняет текущий вьюстейт на тестовый или подменяет текущее тестовое состояние новым
    fun onTransactionDataChanges(debitDebugGroup: DebitDebugGroup) {
        _viewState.value = DebitViewState.DebugState.Debit(debitDebugGroup)
    }

    //FIXME: Метод тестовый, не потребуется в проде, напрямую взоидействие базы и UI не планируется
    //добавляет транзакцию в бд и подгружает все остальные транзакции
    fun testDebit(transactionDTO: TransactionDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionsPersistence.add(transactionDTO)
            loadTransactions()
        }
    }

    //FIXME: Метод тестовый, не потребуется в проде, для инициализации всех ридеров
    fun testInitCardReader() {
        //1 байт маски 0x01:MS , 0x02:ICC, 0x04:CLESS
        //Init card reader
        //TAG 01 -
        //030107

        //Вместо 53 42 52 02 03 01 02
        //нужно отправлять 53 42 52 02 03 01 01 02
        cardReaderRepository
            .sdkRepository
            .sendCommandSync(
                "${TlvCommands.InitCardReader.code}${TlvTags.CardFind.codeStr}0102"
            )
    }

    //FIXME: Метод тестовый, не потребуется в проде, для чтения карты из всех ридеров
    fun testReadCardData() {
        cardReaderRepository
            .sdkRepository
            .sendCommandSync(
                TlvCommands.GetCardReaderInfo.code
            )
    }

    //FIXME: Метод тестовый, не потребуется в проде, для вызова detect карты в p7lib
    fun testDetectCardData() {
        val cardKey = CardKeyDto()
        val cardData = CardInfo()
        p7LibRepository
            .detect(cardKey, cardData).let { result ->
                when (result) {
                    is NotPetrol7Card -> {
                        val b = 0
                    }
                    is CardAuthError -> {

                    }
                    else -> {
                        val b = 0
                    }
                }
            }

        //p7LibCallbacks.sendDataToSam
        cardReaderRepository
            .sdkRepository
    }
    //endregion
}