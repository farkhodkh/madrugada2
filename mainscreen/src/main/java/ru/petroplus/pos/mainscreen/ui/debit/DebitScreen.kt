package ru.petroplus.pos.mainscreen.ui.debit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.petroplus.pos.blockingScreen.InsertClientCardScreen

@Composable
fun DebitScreen(
    onClickListener: (String) -> Unit,
    viewModel: DebitViewModel = viewModel()
) {
    val viewState = viewModel.viewState.value

    when(viewState) {
        DebitViewState.StartingState -> {
            InsertClientCardScreen()
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
