package ru.petroplus.pos.evatorprinter.ext

import android.annotation.SuppressLint
import ru.evotor.devices.commons.printer.PrinterDocument
import ru.evotor.devices.commons.utils.Format
import ru.evotor.devices.commons.utils.PrintableDocumentItem
import ru.petrolplus.pos.persitence.entities.TransactionDTO
import java.text.SimpleDateFormat

private fun Int.toCardType(): String = when(this) {
    1 -> "Петрол5"
    2 -> "Петрол7"
    else -> "Неизвестно"
}

private fun Int.toOperationType(): String = when(this) {
    1 -> "ДЕБЕТ"
    2 -> "КРЕДИТ КОШЕЛЬКА"
    3 -> "ОНЛАЙН ПОПОЛНЕНИЕ СЧЕТА"
    4 -> "ВОЗВРАТ НА КАРТУ"
    5 -> "ВОЗВРАТ НА СЧЕТ"
    else -> "НЕИЗВЕСТНАЯ ОПЕРАЦИЯ"
}

private fun Int.toResponseText(): String = when(this) {
    0 -> "ОДОБРЕНО"
    else -> "TODO"  // TODO: Добавить коды ответов
}

const val SUCCESS_RESPONSE_CODE = 0

@SuppressLint   ("SimpleDateFormat")
fun TransactionDTO.toPrinterDoc(): PrinterDocument { val receiptNum = 10
    val responseCode = SUCCESS_RESPONSE_CODE
    return when(responseCode) {
        SUCCESS_RESPONSE_CODE -> generateSuccessDebitDocument(this)
        else -> TODO("Обработать другие коды ответов")
    }
}

@SuppressLint("SimpleDateFormat")
fun generateSuccessDebitDocument(transactionDTO: TransactionDTO): PrinterDocument {
    val receiptNum = 1243435
    val orgName = "АЗС №1"
    val orgINN = 12345323234
    val cardNum = 1005000123

    return with(transactionDTO) {
        PrinterDocument(
            simple("Чек No.      $receiptNum"),
            simple("ИНН  $orgINN"),
            simple(orgName),
            divider,
            centred(SimpleDateFormat("dd/MM/yy      hh:mm:ss").format(terminalDate.time)),
            simple("POS No.       $terminalId"),
            simple("Терминал No.      $terminalId"),
            divider,
            simple("Карта ${cardType.toCardType()}: "),
            simple(cardNum.toString()),
            divider,
            centred(operationType.toOperationType()),
            divider,
            simple("Услуга     <ServiceName>"),
            simple("Кол-во  <service.amountUnit>   <service.amount>"),
            simple("Цена за   <service.priceUnit>   <service.price>"),
            simple("Итог   <service.sumUnit>   <service.sum>"),
            divider,
            centred(SUCCESS_RESPONSE_CODE.toResponseText()),
            divider,
            centred(TRANSACTION_CONFITMED_BY_PIN),
            divider,
            simple("$OPERATOR_N <OperatorNum>"),
            divider,
            centred(FOOTER_TEXT),
        )
    }
}

const val TRANSACTION_CONFITMED_BY_PIN = "Операция подтверждена вводов ПИН кода"
const val OPERATOR_N = "Оператор No."
const val FOOTER_TEXT = "Добро пожаловать"


private val divider = PrintableDocumentItem("-", Format.DIVIDER)
private fun centred(text: String) = PrintableDocumentItem(text, Format.CENTER)
private fun simple(text: String) = PrintableDocumentItem(text, Format.LEFT_WORD)

