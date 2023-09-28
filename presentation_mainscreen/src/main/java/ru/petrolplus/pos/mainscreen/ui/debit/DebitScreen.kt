package ru.petrolplus.pos.mainscreen.ui.debit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import ru.petrolplus.pos.blockingScreen.InsertClientCardScreen
import ru.petrolplus.pos.mainscreen.ui.debit.debug.DebugScreen

@Composable
fun DebitScreen(
    viewModel: DebitViewModel,
    onClickListener: (String) -> Unit,
) {

    when (val state = viewModel.viewState.collectAsState(viewModel.viewModelScope).value) {
        DebitViewState.StartingState -> {
            InsertClientCardScreen {

            }
        }
        is DebitViewState.DebugState -> {
            DebugScreen(viewModel, state)
        }
        is DebitViewState.CommandExecutionState -> {
            DebugScreen(viewModel, state)
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
