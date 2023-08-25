package ru.petroplus.pos.blockingScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
fun FailedPrintScreen(
    modifier: Modifier = Modifier,
    retry: () -> Unit, dismiss: () -> Unit
) {
    Surface(
        modifier = modifier,
        elevation = 3.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                text = stringResource(id = R.string.print_failed),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = retry) {
                    Text(stringResource(id = R.string.retry))
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = dismiss,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.error)
                ) {
                    Text(stringResource(id = R.string.cancel), color = MaterialTheme.colors.onError)
                }
            }
        }
    }
}

@Preview
@Composable
fun previewFailedPrinterScreen() {
    FailedPrintScreen(Modifier.fillMaxSize(), {}, {})
}