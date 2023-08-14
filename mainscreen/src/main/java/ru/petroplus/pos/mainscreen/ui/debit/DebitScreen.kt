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
            when(viewState) {
                is DebitViewState.DebugState.Debit -> {
                    DebugScreen(
                        onClickListener = {},
                        debitDebugGroup = viewState.debitDebugGroup,
                        debitCallback = { viewModel.onTransactionDataChanges(it)},
                        saveTransactionCallback = { viewModel.testDebit(it) },
                        getTransactionsCallback = { viewModel.fetchTransactions()},
                        saveGuidCallback = { viewModel.saveGUIDparams(it)}
                    )
                }
                else -> {
                    DebugScreen {
                        viewModel.sendAPDUCommand(it)
                    }
                }
            }
        }
        is DebitViewState.CommandExecutionState -> {
            DebugScreen(
                commandResult = viewState.commandResult
            ) {
                viewModel.sendAPDUCommand(it)
            }
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
