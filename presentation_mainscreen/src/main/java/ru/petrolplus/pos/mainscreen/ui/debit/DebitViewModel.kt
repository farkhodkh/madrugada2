package ru.petrolplus.pos.mainscreen.ui.debit

import android.os.Bundle
import android.util.Log
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
import ru.petrolplus.pos.persitence.ReceiptPersistence
import ru.petrolplus.pos.persitence.SettingsPersistence
import ru.petrolplus.pos.persitence.TransactionsPersistence
import ru.petrolplus.pos.persitence.dto.GUIDParamsDTO
import ru.petrolplus.pos.persitence.dto.TransactionDTO
import java.util.Calendar
import ru.petrolplus.pos.R
import ru.petrolplus.pos.core.errorhandling.launchHandling
import ru.petrolplus.pos.core.errorhandling.launchInHandling
import ru.petrolplus.pos.mainscreen.ui.debit.debug.DebitDebugGroup
import ru.petrolplus.pos.mainscreen.ui.ext.toInitDataDto
import ru.petrolplus.pos.networkapi.GatewayServerRepositoryApi
import ru.petrolplus.pos.p7LibApi.IP7LibCallbacks
import ru.petrolplus.pos.p7LibApi.IP7LibRepository
import ru.petrolplus.pos.p7LibApi.dto.TransactionUUIDDto
import ru.petrolplus.pos.printerapi.PrinterRepository
import ru.petrolplus.pos.sdkapi.CardReaderRepository
import ru.petrolplus.pos.resources.ResourceHelper
import kotlin.random.Random
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.petrolplus.pos.mainscreen.BuildConfig

class DebitViewModel @AssistedInject constructor(
    private val cardReaderRepository: CardReaderRepository,
    private val printer: PrinterRepository,
    private val gatewayServer: GatewayServerRepositoryApi,
    private val transactionsPersistence: TransactionsPersistence,
    private val settingsPersistence: SettingsPersistence,
    private val receiptPersistence: ReceiptPersistence,
    private val p7LibRepository: IP7LibRepository,
    private val p7LibCallbacks: IP7LibCallbacks,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _viewState = mutableStateOf<DebitViewState>(DebitViewState.StartingState)
    val viewState: State<DebitViewState> = _viewState

    init {
        //TODO протестировать работоспособность т.к убрана лишняя вложенность корутин
        cardReaderRepository
            .sdkRepository
            .latestCommands
            .onEach { value ->
                _viewState.value = DebitViewState
                    .CommandExecutionState(value)
            }
            .launchInHandling(viewModelScope)

        if (BuildConfig.DEBUG) {
            //Тестовое состояние экрана в случае если тип сборки DEBUG
            _viewState.value = DebitViewState.DebugState.APDU
        }
    }

    fun ping() {
        viewModelScope.launchHandling(Dispatchers.IO) {
            gatewayServer.doPing()
        }
    }

    //FIXME!! тестовый метод для проверки передачи данных на AS.
    fun sendDebit() {
        viewModelScope.launchHandling(Dispatchers.IO) {
            ResourceHelper.getAssetFile("DebitP7.bin")?.readBytes()?.let {
                val bytes = gatewayServer.sendData(byteArray = it)

                Log.v("as_repsonse", bytes?.joinToString ( ":" ) { byte ->
                    "%02x".format(byte)
                }.orEmpty())
            }
        }
    }

    fun sendCommand(command: String) {
        cardReaderRepository.sdkRepository.sendCommand(command)
    }

    /**
     * Метод который вызывается для повтора печати в случае ошибки
     * @throws IllegalStateException если функция вызвана при отсутствии ошибки печати
     */
    fun repeatPrinting() {
        when (val state = _viewState.value) {
            is DebitViewState.DebugState.PrinterState.FailedState.Receipt -> printTransactionTest(state.transactionId)
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

    //region TEST_METHODS
    //FIXME: Метод тестовый. Распечатывает сменный отчет
    //Проверяется возможна ли сейчас печать. Переход в состояние печати
    //В Debug сборке симуляция ошибки во время печати. Печать чека с отслеживанием наличия ошибки
    fun printShiftReport() {
        if (_viewState.value == DebitViewState.DebugState.PrinterState.Printing) return
        _viewState.value = DebitViewState.DebugState.PrinterState.Printing

        viewModelScope.launchHandling(Dispatchers.IO) {
            // Симуляция ошибки во время печати
            if (BuildConfig.DEBUG) {
                delay(300)
                if (!Random.nextBoolean()) {
                    onFailPrintShiftReport()
                    return@launchHandling
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
            || _viewState.value == DebitViewState.DebugState.PrinterState.Printing) return

        _viewState.value = DebitViewState.DebugState.PrinterState.Printing

        viewModelScope.launchHandling(Dispatchers.IO) {
            val data = receiptPersistence.getDebitReceipt(transactionId)
            if (data == null) {
                onFailPrintReceipt(transactionId)
                return@launchHandling
            }

            // Симуляция ошибки во время печати
            if (BuildConfig.DEBUG) {
                delay(300)
                if (!Random.nextBoolean()) {
                    onFailPrintReceipt(transactionId)
                    return@launchHandling
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
        viewModelScope.launchHandling {
            val initData = settingsPersistence.getBaseSettings().toInitDataDto()
            val uuidDto = TransactionUUIDDto()
            val cacheDir = ResourceHelper.getExternalCacheDirectory() ?: ""
            val result = p7LibRepository.init(initData, uuidDto, p7LibCallbacks, cacheDir, cacheDir)
            val b = result.code
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

    @AssistedFactory
    interface DebitViewModelFactory {
        fun create(savedStateHandle: SavedStateHandle): DebitViewModel
    }

    companion object {
        fun provideFactory(
            factory: DebitViewModelFactory,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,

            ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String, modelClass: Class<T>, handle: SavedStateHandle
                ): T {
                    return factory.create(handle) as T
                }
            }
    }
    //endregion
}