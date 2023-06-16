package ru.petroplus.pos.mainscreen.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DebitViewModel: ViewModel() {
    private val _state = mutableStateOf(UIState("Some state"))
    val state: State<UIState> = _state
}

data class UIState(var stateName: String)