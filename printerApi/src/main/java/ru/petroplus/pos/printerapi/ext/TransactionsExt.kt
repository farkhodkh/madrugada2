package ru.petroplus.pos.printerapi.ext

import ru.petroplus.pos.printerapi.ResponseCode
import ru.petroplus.pos.util.ext.roundTo
import java.lang.IllegalArgumentException

fun Int.toCardType(): String = when (this) {
    1 -> "Петрол5"
    2 -> "Петрол7"
    else -> "Неизвестно"
}

fun Int.toOperationType(): String = when (this) {
    1 -> "ДЕБЕТ"
    2 -> "КРЕДИТ КОШЕЛЬКА"
    3 -> "ОНЛАЙН ПОПОЛНЕНИЕ СЧЕТА"
    4 -> "ВОЗВРАТ НА КАРТУ"
    5 -> "ВОЗВРАТ НА СЧЕТ"
    else -> "НЕИЗВЕСТНАЯ ОПЕРАЦИЯ"
}

fun Int.toResponseCode(): ResponseCode = when(this) {
    ResponseCode.SUCCESS -> ResponseCode.Success
    ResponseCode.TIMEOUT -> ResponseCode.Timeout
    ResponseCode.CANCELED -> ResponseCode.Canceled
    ResponseCode.SERVICE_PROHIBITED -> ResponseCode.ServiceProhibited
    else -> throw IllegalArgumentException("Код ответа не обработан")
}

fun Long.toAmountString(): String {
    return (this / 100.0).roundTo(2)
}

fun Long.toCurrencyString(): String {
    return (this / 100.0).roundTo(2)
}