package ru.petroplus.pos.mainscreen.ui.debit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.petroplus.pos.blockingScreen.InsertClientCardScreen
import ru.petroplus.pos.debug.DebugScreen

@Composable
fun DebitScreen(
    onClickListener: (String) -> Unit,
    viewModel: DebitViewModel
) {

    when (val viewState = viewModel.viewState.value) {
        DebitViewState.StartingState -> {
            InsertClientCardScreen() {

            }
        }
        is DebitViewState.DebugState -> {
            if (viewState is DebitViewState.DebugState.Debit) {
                DebugScreen(
                    onCommandClickListener = {
                        viewModel.sendCommand(it)
                    },
                    onClickListener = {
                        viewModel.ping()
                    },
                    debitDebugGroup = viewState.debitDebugGroup,
                    debitCallback = { viewModel.onTransactionDataChanges(it)},
                    saveTransactionCallback = { viewModel.testDebit(it) },
                    getTransactionsCallback = { viewModel.fetchTransactions()},
                    saveGuidCallback = { viewModel.saveGUIDParams(it)}
                )
            } else {
                DebugScreen(
                    onCommandClickListener = {
                        viewModel.sendCommand(it)
                    },
                    onClickListener = {
                        viewModel.ping()
                    }
                )
            }
        }
        is DebitViewState.CommandExecutionState -> {
            DebugScreen(
                viewState.commandResult,
                onCommandClickListener = {
                    viewModel.sendCommand(it)
                },
                onClickListener = {
                    viewModel.ping()
                }
            )
        }
        else -> {
            Surface {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                }
            }
        }

    }

}
