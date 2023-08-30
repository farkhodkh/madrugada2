package ru.petrolplus.pos.blockingScreen

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.petrolplus.pos.ui.R

@Composable
fun StartingApplicationBlockingScreen(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
) {
    Text(
        modifier = Modifier.padding(16.dp),
        textAlign = TextAlign.Center,
        style = typography.h4,
        text = stringResource(R.string.starting_application_label_text)
    )
}

@Preview()
@Composable
fun StartingApplicationBlockingScreenPreview() {
    StartingApplicationBlockingScreen()
}

