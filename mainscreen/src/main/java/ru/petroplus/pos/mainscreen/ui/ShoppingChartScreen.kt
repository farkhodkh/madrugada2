package ru.petroplus.pos.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingChartScreen(
    onClickListener: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    )
    {
        Text(
            text = "Корзина",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

    }
}