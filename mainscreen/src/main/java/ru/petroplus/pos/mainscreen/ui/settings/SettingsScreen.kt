package ru.petroplus.pos.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    onClickListener: (String) -> Unit
) {
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