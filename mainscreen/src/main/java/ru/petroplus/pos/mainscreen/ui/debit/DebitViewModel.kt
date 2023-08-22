package ru.petroplus.pos.mainscreen.ui.debit

import android.os.Bundle
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import ru.petrolplus.pos.persitence.SettingsPersistence
import ru.petrolplus.pos.persitence.TransactionsPersistence
import ru.petroplus.pos.networkapi.GatewayServerRepositoryApi
import ru.petrolplus.pos.persitence.dto.GUIDParamsDTO
import ru.petrolplus.pos.persitence.dto.TransactionDTO
import ru.petroplus.pos.mainscreen.ui.debit.debug.DebitDebugGroup
import ru.petroplus.pos.sdkapi.CardReaderRepository
import ru.petroplus.pos.ui.BuildConfig

class DebitViewModel(
    private val cardReaderRepository: CardReaderRepository,
    private val gatewayServer: GatewayServerRepositoryApi,
    private val transactionsPersistence: TransactionsPersistence,
    private val settingsPersistence: SettingsPersistence,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewState = mutableStateOf<DebitViewState>(DebitViewState.StartingState)
    val viewState: State<DebitViewState> = _viewState

    init {
        viewModelScope.launch {
            cardReaderRepository
                .sdkRepository
                .eventBus
                .events
                .collectIndexed { _, value ->
                    _viewState.value = DebitViewState
                        .CommandExecutionState(value)
                }
        }

        if (BuildConfig.DEBUG) {
            //Тестовое состояние экрана в случае если тип сборки DEBUG
            _viewState.value = DebitViewState.DebugState.APDU
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

    //FIXME: Метод тестовый, не потребуется в проде, напрямую взоидействие базы и UI не планируется
    //Загружает все транзакции из бд в IO планировщике
    fun fetchTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            loadTransactions()
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
        when(index) {
            0 -> _viewState.value = DebitViewState.DebugState.APDU
            else -> _viewState.value = DebitViewState.DebugState.Debit()
        }
    }

    fun ping() {
        viewModelScope.launch(Dispatchers.IO) {
            gatewayServer.doPing()
        }
    }

    fun sendCommand(command: String) {
        cardReaderRepository.sdkRepository.sendCommand(command)
    }

    companion object {
        fun provideFactory(
            cardReaderRepository: CardReaderRepository,
            gatewayServer: GatewayServerRepositoryApi,
            transactionsPersistence: TransactionsPersistence,
            settingsPersistence: SettingsPersistence,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return DebitViewModel(cardReaderRepository, gatewayServer, transactionsPersistence, settingsPersistence, handle) as T
                }
            }
    }
}