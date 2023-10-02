package ru.petrolplus.pos.mainscreen.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.petrolplus.pos.mainscreen.ui.settings.debug.SettingsDebugScreen

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onClickListener: (String) -> Unit
) {

    when(viewModel.viewState.collectAsState().value) {
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