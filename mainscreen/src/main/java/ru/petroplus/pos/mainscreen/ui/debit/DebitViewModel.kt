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
import ru.petroplus.pos.mainscreen.ui.debit.http.SSLConnectionExample
import ru.petroplus.pos.sdkapi.CardReaderRepository
import ru.petroplus.pos.ui.BuildConfig
import ru.petroplus.pos.util.ext.byteArrayToString

class DebitViewModel(
    private val sdkConnection: CardReaderRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewState = mutableStateOf<DebitViewState>(DebitViewState.StartingState)
    val viewState: State<DebitViewState> = _viewState

    init {
        viewModelScope.launch {
            sdkConnection
                .sdkRepository
                .eventBus
                .events
                .collectIndexed { index, value ->
                    _viewState.value = DebitViewState
                        .CommandExecutionState(value.byteArrayToString())
                }
        }

        if (BuildConfig.DEBUG) {
            _viewState.value = DebitViewState.DebugState
        }
    }

    fun sendCommand(command: String) {
        //APDU для выбора апплета
        //
        //00A4040008A000000003000000
        //
        //cla 00
        //ins a4
        //p1 04
        //p2 00
        //Lc 08
        //Data A000000003000000

        //5342520101
        sdkConnection.sdkRepository.sendCommand(command)
    }

    fun ping() {
        viewModelScope.launch(Dispatchers.IO) {
            androidSSLConnection()
        }
    }

    private fun androidSSLConnection() {
        SSLConnectionExample().doRequest()
    }

    companion object {
        fun provideFactory(
            repository: CardReaderRepository,
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
                    return DebitViewModel(repository, handle) as T
                }
            }
    }
}