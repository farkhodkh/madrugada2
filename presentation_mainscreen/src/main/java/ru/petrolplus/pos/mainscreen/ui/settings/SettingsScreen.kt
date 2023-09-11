package ru.petrolplus.pos.mainscreen.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.petrolplus.pos.mainscreen.ui.settings.debug.SettingsDebugScreen

@Composable
fun SettingsScreen(
    viewModelFactory: AbstractSavedStateViewModelFactory,
    viewModel: SettingsViewModel = viewModel(factory = viewModelFactory),
    onClickListener: (String) -> Unit
) {
    when(viewModel.viewState.value) {
        SettingsViewState.DebugState -> {
            SettingsDebugScreen(viewModel)
        }
        else -> {
            Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    Text(
                        text ="Настройки приложения",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                }
        }
    }

}