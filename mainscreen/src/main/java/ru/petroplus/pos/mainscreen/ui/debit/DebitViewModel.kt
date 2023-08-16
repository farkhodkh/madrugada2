package ru.petroplus.pos.mainscreen.ui.debit

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
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import ru.petrolplus.pos.persitence.entities.TransactionDTO
import ru.petroplus.pos.networkapi.GatewayServerRepositoryApi
import ru.petroplus.pos.printerapi.PrinterApi
import ru.petroplus.pos.printerapi.printable.documents.PrintableReceipt
import ru.petroplus.pos.sdkapi.CardReaderRepository
import ru.petroplus.pos.ui.BuildConfig
import java.util.Calendar

class DebitViewModel(
    private val cardReaderRepository: CardReaderRepository,
    private val printer: PrinterApi,
    private val gatewayServer: GatewayServerRepositoryApi,
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
            _viewState.value = DebitViewState.DebugState
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

    fun print() {
        Log.d("Printer_2", "document: viewModel")
        val transaction = TransactionDTO(
            "123",
            "123",
            "123",
            Calendar.getInstance(),
            123,
            134,
            12,
            12,
            12,
            121,
            true,
            "123",
            1,
            1,
            21,
            12,
            "12",
            123,
            true,
            "121")
        val doc = PrintableReceipt.Debit(transaction)
        printer.print(doc)
    }

    companion object {
        fun provideFactory(
            cardReaderRepository: CardReaderRepository,
            printerService: PrinterApi,
            gatewayServer: GatewayServerRepositoryApi,
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
                    return DebitViewModel(
                        cardReaderRepository,
                        printerService,
                        gatewayServer,
                        handle
                    ) as T
                }
            }
    }
}