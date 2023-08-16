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
        DebitViewState.DebugState -> {
            DebugScreen(
                onCommandClickListener = {
                    viewModel.sendCommand(it)
                },
                onClickListener = {
                    viewModel.ping()
                },
                print = {
                    viewModel.print()
                }
            )
        }
        is DebitViewState.CommandExecutionState -> {
            DebugScreen(
                viewState.commandResult,
                onCommandClickListener = {
                    viewModel.sendCommand(it)
                },
                onClickListener = {
                    viewModel.ping()
                },
                print = {
                    viewModel.print()
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
