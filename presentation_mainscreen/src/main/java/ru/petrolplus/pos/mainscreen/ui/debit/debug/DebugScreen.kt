package ru.petrolplus.pos.mainscreen.ui.debit.debug

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.petrolplus.pos.persitence.dto.GUIDParamsDTO
import ru.petrolplus.pos.persitence.dto.TransactionDTO
import ru.petrolplus.pos.blockingScreen.FailedPrintScreen
import ru.petrolplus.pos.blockingScreen.PrintProgressScreen
import ru.petrolplus.pos.mainscreen.ui.debit.DebitViewModel
import ru.petrolplus.pos.mainscreen.ui.debit.DebitViewState
import ru.petrolplus.pos.R
import ru.petrolplus.pos.resources.ResourceHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DebugScreen(
    viewModel: DebitViewModel,
    state: DebitViewState
) {
    val tabs = listOf("APDU", "DATABASE", "PRINTER")
    val tabIndex = when (state) {
        is DebitViewState.DebugState.PrinterState -> 2
        is DebitViewState.DebugState.Debit -> 1
        else -> 0
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, tab ->
                Tab(selected = tabIndex == index,
                    onClick = { viewModel.setTab(index) },
                    text = { Text(text = tab) })
            }

        }
        when (state) {
            DebitViewState.DebugState.APDU, is DebitViewState.CommandExecutionState -> APDUScreen(
                viewModel, state
            )

            is DebitViewState.DebugState.PrinterState -> PrinterScreen(viewModel)
            is DebitViewState.DebugState.Debit -> DatabaseScreen(viewModel, state)
            else -> Surface { }
        }
    }
}

@Composable
fun PrinterScreen(viewModel: DebitViewModel) {
    var transactionId by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        when (viewModel.viewState.value) {
            DebitViewState.DebugState.PrinterState.Printing ->
                PrintProgressScreen(modifier = Modifier.fillMaxSize())

            is DebitViewState.DebugState.PrinterState.FailedState -> FailedPrintScreen(
                retry = viewModel::repeatPrinting,
                dismiss = viewModel::resetPrinter,
                modifier = Modifier.fillMaxSize()
            )

            is DebitViewState.DebugState.PrinterState -> {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Transaction ID") },
                        value = transactionId,
                        onValueChange = { transactionId = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.printTransactionTest(transactionId) }) {
                        Text(text = "Распечатать")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(modifier = Modifier.fillMaxWidth(),
                        onClick = viewModel::printShiftReport) {
                        Text(text = "Распечатать чек смены")
                    }
                }
            }

            else -> Surface {}
        }

    }
}

@Composable
fun APDUScreen(viewModel: DebitViewModel, viewState: DebitViewState) {
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

            BasicTextField(value = message, onValueChange = { newText ->
                message = newText
            }, maxLines = 100, singleLine = false, textStyle = TextStyle(
                fontSize = 18.sp,
            ), decorationBox = { innerTextField ->
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
            })

            Spacer(modifier = Modifier.height(20.dp))

            Row {

                Button(modifier = Modifier
                    .weight(1f)
                    .padding(8.dp), onClick = {
                    viewModel.testP7LibCommand()
                }) {
                    Text(
                        text = stringResource(id = R.string.P7Lib)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(modifier = Modifier
                    .weight(1f)
                    .padding(8.dp), onClick = {
                    viewModel.sendCommand(message)
                }) {
                    Text(
                        text = stringResource(id = R.string.OK)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Button(modifier = Modifier
                    .weight(1f)
                    .padding(8.dp), onClick = {
                    viewModel.sendDebit()
                }) {
                    Text(
                        text = stringResource(id = R.string.debit_label)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(modifier = Modifier
                    .weight(1f)
                    .padding(8.dp), onClick = {
                    viewModel.ping()
                }) {
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
                text = (viewState as? DebitViewState.CommandExecutionState)?.commandResult ?: "",
            )

        }
    }
}

@Composable
fun DatabaseScreen(viewModel: DebitViewModel, viewState: DebitViewState.DebugState.Debit) {
    Surface {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp, 8.dp, 16.dp, 64.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Добавление GUID параметров",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h5
            )

            val guidFields = viewState.debitDebugGroup.guidParams.takeIf {
                it.isNotEmpty()
            } ?: getTestData(
                context = LocalContext.current, fileName = "guid_fields.json"
            ) {
                if (it.first == "clockSequence") Pair(
                    it.first, (it.second as Int).toShort()
                ) else it
            }

            Form(list = guidFields) {
                viewModel.onTransactionDataChanges(viewState.debitDebugGroup.copy(guidParams = it))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.saveGUIDParams(guidFields.toGUIDParamsDTO()) }) {
                Text(text = "Сохранить")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Output(title = "GUID", listItems = viewState.debitDebugGroup.guidParamsOutput)


            Text(
                text = "Добавление транзакции",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h5
            )

            val transactionFields =
                viewState.debitDebugGroup.transaction.takeIf { it.isNotEmpty() } ?: getTestData(
                    context = LocalContext.current, fileName = "transaction_fields.json"
                )

            Form(list = transactionFields) {
                viewModel.onTransactionDataChanges(
                    viewState.debitDebugGroup.copy(
                        transaction = it
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.fetchTransactions() }) {
                Text(text = "Загрузить")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.testDebit(transactionFields.toTransactionDto()) }) {
                Text(text = "Добавить")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Output("Все транзакции", listItems = viewState.debitDebugGroup.transactionsOutput)
        }
    }
}

/**
 * Метод для получения тестовых полей из файла json
 * @param context контекст Android
 * @param fileName имя ассета в формате json
 * @return список пар ключ значение где значение может быть произвольного типа
 */
private fun getTestData(
    context: Context,
    fileName: String,
    additionalMapper: ((Pair<String, Any>) -> Pair<String, Any>)? = null
): SnapshotStateList<Pair<String, Any>> {
    val file = ResourceHelper.getAssetFile(fileName)
    require(file != null) { "отсутствует файл $fileName" }
    val text = file.readText()

    val list = text.trim()
        .removeSurrounding("{", "}")
        .split(",")
        .map { it.split(" : ") }
        .map {
            it[0].replace("\"", "").replace("\\r\\n ", "").trim() to parseTyped(
                it[1].trim().replace("\"", "")
            )
        }
        .map { if (additionalMapper != null) additionalMapper(it) else it }
        .toTypedArray()
    return mutableStateListOf(*list)
}

/**
 * Виджет форм ввода для проверки сохранения данных в бд
 * @param list список пар где ключ - это название поля (также используется для маппинга в DTO), а значение имеет тип any,
 * настоящий тип которого определяется в методе
 * @param onValueChanged вызывается каждый раз когда меняется что-то в поле, в коллбек передается модифицированный
 * список пар со всеми полями
 */
@Composable
private fun Form(
    list: SnapshotStateList<Pair<String, Any>>,
    onValueChanged: (SnapshotStateList<Pair<String, Any>>) -> Unit
) {
    list.forEachIndexed { index, (k, v) ->
        when (v) {
            is Short -> OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = k) },
                value = v.toString(),
                onValueChange = { value: String ->
                    onValueChanged(list.apply {
                        this[index] =
                            Pair(k, value.zeroIfEmpty().toShortOrNull() ?: Short.MAX_VALUE)
                    })
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            is Int -> OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = k) },
                value = v.toString(),
                onValueChange = { value: String ->
                    onValueChanged(list.apply {
                        this[index] = Pair(k, value.zeroIfEmpty().toIntOrNull() ?: Int.MAX_VALUE)
                    })
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            is Long -> OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = k) },
                value = v.toString(),
                onValueChange = { value: String ->
                    onValueChanged(list.apply {
                        this[index] = Pair(k, value.zeroIfEmpty().toLongOrNull() ?: Long.MAX_VALUE)
                    })
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            is Boolean -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = k, style = MaterialTheme.typography.caption)
                    Spacer(modifier = Modifier.width(16.dp))
                    Switch(checked = v, onCheckedChange = { value: Boolean ->
                        onValueChanged(list.apply {
                            this[index] = Pair(k, value)
                        })
                    })
                }
            }

            else -> OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = k) },
                value = v.toString(),
                onValueChange = { value: String ->
                    onValueChanged(list.apply {
                        value.takeIf { it.length <= 32 }?.let { this[index] = Pair(k, it) }
                    })
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }
    }

}

/**
 * сворачиваемый виджет для вывода текстовой информации
 * @param title заголовок
 * @param listItems список со строками
 */
@Composable
private fun Output(title: String, listItems: List<String>) {
    if (listItems.isEmpty()) return
    var isExpanded by remember {
        mutableStateOf(true)
    }

    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        val rotation = if (isExpanded) 270f else 90f
        Text(
            text = title,
            Modifier.weight(0.7f),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Button(onClick = { isExpanded = !isExpanded }, modifier = Modifier.weight(0.3f)) {
            Icon(
                painter = painterResource(id = androidx.constraintlayout.widget.R.drawable.abc_ic_arrow_drop_right_black_24dp),
                contentDescription = null,
                modifier = Modifier.rotate(rotation)
            )
        }
    }

    if (isExpanded) {
        val stringBuilder = StringBuilder()
        listItems.forEach {
            stringBuilder.append(it)
            stringBuilder.append("\n\n")
        }

        Text(
            modifier = Modifier.fillMaxWidth(), text = stringBuilder.toString()
        )
    }
}

private fun SnapshotStateList<Pair<String, Any>>.toGUIDParamsDTO(): GUIDParamsDTO {
    val map = this.associate { it }
    return GUIDParamsDTO(
        lastOnlineTransaction = map.getOrThrow("lastOnlineTransaction") as Long,
        lastGeneratedTime = map.getOrThrow("lastGeneratedTime") as Long,
        clockSequence = map.getOrThrow("clockSequence") as Short,
        hasNodeId = map.getOrThrow("hasNodeId") as Boolean,
        nodeId = map.getOrThrow("nodeId") as String,
    )
}

private fun SnapshotStateList<Pair<String, Any>>.toTransactionDto(): TransactionDTO {
    val map = this.associate { it }
    val dateString = map.getOrThrow("terminalDate") as String
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = dateFormat.parse(dateString)
    val calendar = Calendar.getInstance().apply { time = date!! }

    return TransactionDTO(
        id = map.getOrThrow("id") as String,
        acquirerId = map.getOrThrow("acquirerId") as Int,
        cardNumber = map.getOrThrow("cardNumber").toString(),
        operatorNumber = map.getOrThrow("operatorNumber").toString(),
        terminalDate = calendar,
        terminalId = map.getOrThrow("terminalId") as Int,
        serviceIdWhat = map.getOrThrow("serviceIdWhat") as Int,
        serviceIdFrom = map.getOrThrow("serviceIdFrom") as Int,
        amount = map.getOrThrow("amount") as Long,
        price = map.getOrThrow("price") as Long,
        sum = map.getOrThrow("sum") as Long,
        cardTransactionCounter = map.getOrThrow("cardTransactionCounter") as Long,
        hasReturn = map.getOrThrow("hasReturn") as Boolean,
        crs32 = map.getOrThrow("crs32") as String,
        operationType = map.getOrThrow("operationType") as Int,
        cardType = map.getOrThrow("cardType") as Int,
        loyaltySum = map.getOrThrow("loyaltySum") as Long,
        deltaBonus = map.getOrThrow("deltaBonus") as Long,
        originalDebitTransactionId = map.getOrThrow("originalDebitTransactionId") as String,
        shiftNumber = map.getOrThrow("shiftNumber") as Int,
        hasRecalculationTransaction = map.getOrThrow("hasRecalculationTransaction") as Boolean,
        rollbackCode = map.getOrThrow("rollbackCode") as String,
        receiptNumber = map.getOrThrow("receiptNumber") as Long,
        responseCode = map.getOrThrow("responseCode") as Int,
    )
}

private fun Map<String, Any>.getOrThrow(key: String): Any {
    return this[key] ?: throw IllegalStateException("невалидное значение $key")
}

private fun parseTyped(string: String): Any {
    return string.toIntOrNull() ?: string.toLongOrNull() ?: string.toBooleanStrictOrNull() ?: string
}

fun String.zeroIfEmpty(): String = this.ifBlank { "0" }