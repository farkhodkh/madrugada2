package ru.petrolplus.pos.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.petrolplus.pos.ui.R
import ru.petrolplus.pos.util.constants.Constants.DEMO_CONFIGURATION

@Composable
fun FilePickerDialog(onClickListener: (String) -> Unit) {
    Surface {
        val message = remember { mutableStateOf("") }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LaunchedEffect(Unit) {
                message.value = DEMO_CONFIGURATION
            }

            Text(
                text = stringResource(R.string.add_configuration_content_label_text)
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                message.value,
                { message.value = it },
                modifier = Modifier
                    .height(250.dp)
                    .width(IntrinsicSize.Max),
                label = { Text(text = stringResource(id = R.string.configuration_content_label_text)) },
                placeholder = { Text(text = stringResource(id = R.string.put_configuration_content_here)) },
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { onClickListener.invoke(message.value) }) {
                Text(text = stringResource(id = R.string.OK))
            }
        }
    }
}