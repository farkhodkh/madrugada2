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
            DebugScreen() {
                viewModel.sendAPDUCommand(it)
            }
        }

        is DebitViewState.CommandExecutionState -> {
            DebugScreen(
                viewState.commandResult
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
