package ru.petroplus.pos.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FilePickerDialog(onClickListener: () -> Unit) {
    Surface {
        Text(modifier = Modifier.clickable { onClickListener.invoke() },
            text = "FilePickerDialog"
        )
    }
}