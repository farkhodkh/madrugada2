package ru.petroplus.pos.mainscreen.ui.debit

sealed class DebitViewState {
    object StartingState: DebitViewState()
    object DebugState: DebitViewState()
    object InsertCartState: DebitViewState()

    class CommandExecutionState(val commandResult: String): DebitViewState()
}