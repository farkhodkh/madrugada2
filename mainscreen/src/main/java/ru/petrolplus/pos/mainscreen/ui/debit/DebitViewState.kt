package ru.petrolplus.pos.mainscreen.ui.debit

import ru.petrolplus.pos.mainscreen.ui.debit.debug.DebitDebugGroup

sealed class DebitViewState {
    object StartingState: DebitViewState()

    sealed class DebugState: DebitViewState() {
        object APDU : DebugState()
        sealed class PrinterState: DebugState() {
            object WaitDocument: PrinterState()
            object Printing: PrinterState()

            sealed class FailedState: PrinterState() {
                object ShiftReport: FailedState()
                data class Receipt(val transactionId: String): FailedState()
            }
        }
        data class Debit(val debitDebugGroup: DebitDebugGroup = DebitDebugGroup()) : DebugState()

    }

    object InsertCartState: DebitViewState()

    data class CommandExecutionState(val commandResult: String): DebitViewState()

    sealed class CardDetectState(val detectDescription: String): DebitViewState() {
        object NotPetrol7Card: CardDetectState("Карта не является картой P7")
        object CardAuthError: CardDetectState("Ошибка аутентификации карты")
        object CardUnknownState: CardDetectState("Не известный статус чтения карты")

        object CardOkState: CardDetectState("Успешный результат чтения карты P7")
    }
}