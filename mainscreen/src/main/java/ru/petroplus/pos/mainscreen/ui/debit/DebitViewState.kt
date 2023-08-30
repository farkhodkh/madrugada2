package ru.petroplus.pos.mainscreen.ui.debit

import ru.petroplus.pos.mainscreen.ui.debit.debug.DebitDebugGroup

sealed class DebitViewState {
    object StartingState: DebitViewState()
    sealed class DebugState: DebitViewState() {
        object APDU : DebugState()
        sealed class PrinterState: DebugState() {
            object WaitDocument: PrinterState()
            object Printing: PrinterState()

            sealed class FailedState: PrinterState() {
                object ShiftReport: FailedState()
                class Receipt(val transactionId: String): FailedState()
            }
        }
        data class Debit(val debitDebugGroup: DebitDebugGroup = DebitDebugGroup()) : DebugState()

    }
    object InsertCartState: DebitViewState()
    data class CommandExecutionState(val commandResult: String): DebitViewState()
}