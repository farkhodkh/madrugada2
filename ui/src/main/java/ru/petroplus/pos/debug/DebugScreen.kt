package ru.petroplus.pos.debug

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.petroplus.pos.ui.R

@Composable
fun DebugScreen(
    commandResult: String = "Результат выполнения",
    onCommandClickListener: (String) -> Unit,
    onClickListener: () -> Unit,
    print: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        val placeholder: String = stringResource(id = R.string.apdu_config_line)

        var message by remember {
            mutableStateOf("")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BasicTextField(
                value = message,
                onValueChange = { newText ->
                    message = newText
                },
                maxLines = 100,
                singleLine = false,
                textStyle = TextStyle(
                    fontSize = 18.sp,
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color.DarkGray,
                                shape = RoundedCornerShape(size = 16.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                    ) {
                        if (message.isEmpty()) {
                            Text(
                                text = placeholder,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.LightGray
                            )
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Button(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(8.dp)
                    ,
                    onClick = {
                        print()
                    }
                ) {
                    Text(
                        text = "Print"
                    )
                }

                Button(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(8.dp)
                    ,
                    onClick = {
                        onCommandClickListener.invoke(message)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.OK)
                    )
                }

                Button(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(8.dp)
                    ,
                    onClick = {
                        onClickListener.invoke()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.ping)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier
                    .height(150.dp)
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                text = commandResult,
            )

        }
    }
}