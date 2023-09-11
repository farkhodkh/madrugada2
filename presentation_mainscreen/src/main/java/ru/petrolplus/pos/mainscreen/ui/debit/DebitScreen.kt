package ru.petrolplus.pos.mainscreen.ui.debit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.petrolplus.pos.blockingScreen.InsertClientCardScreen
import ru.petrolplus.pos.mainscreen.ui.debit.debug.DebugScreen

@Composable
fun DebitScreen(
    viewModelFactory: AbstractSavedStateViewModelFactory,
    viewModel: DebitViewModel = viewModel(factory = viewModelFactory),
    onClickListener: (String) -> Unit,
) {

    when (viewModel.viewState.value) {
        DebitViewState.StartingState -> {
            InsertClientCardScreen {

            }
        }
        is DebitViewState.DebugState -> {
            DebugScreen(viewModel)
        }
        is DebitViewState.CommandExecutionState -> {
            DebugScreen(viewModel)
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
