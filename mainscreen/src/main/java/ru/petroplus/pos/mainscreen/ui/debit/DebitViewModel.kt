package ru.petroplus.pos.mainscreen.ui.debit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DebitViewModel: ViewModel() {
    private val _viewState = mutableStateOf<DebitViewState>(DebitViewState.StartingState)
    val viewState: State<DebitViewState> = _viewState

    init {
        //_viewState.value = DebitViewState.InsertCartState
    }
}