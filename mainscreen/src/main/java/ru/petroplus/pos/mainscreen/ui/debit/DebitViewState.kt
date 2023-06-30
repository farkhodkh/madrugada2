package ru.petroplus.pos.mainscreen.ui.debit

sealed class DebitViewState {
    object StartingState: DebitViewState()
    object InsertCartState: DebitViewState()
}