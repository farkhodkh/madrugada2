package ru.petroplus.pos.blockingScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.petroplus.pos.ui.R

@Composable
fun InsertClientCardScreen(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        onClickListener: () -> Unit,
    ) {
    Surface(
        modifier = modifier,
        elevation = 3.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(16.dp).clickable { onClickListener.invoke() },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                text = stringResource(R.string.insert_card_label_text),
            )
        }
    }
}

@Preview
@Composable
fun previewInsertClientCardScreen() {
    InsertClientCardScreen(onClickListener = {})
}