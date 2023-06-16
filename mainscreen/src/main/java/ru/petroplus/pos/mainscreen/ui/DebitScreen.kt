package ru.petroplus.pos.mainscreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.petroplus.pos.mainscreen.viewModel.DebitViewModel

@Composable
fun DebitScreen(
    onClickListener: (String) -> Unit,
    viewModel: DebitViewModel = viewModel()
) {
    val state = viewModel.state.value

    Surface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = state.stateName)
        }
    }
}
