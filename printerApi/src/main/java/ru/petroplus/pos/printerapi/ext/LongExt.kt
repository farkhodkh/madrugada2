package ru.petroplus.pos.printerapi.ext

import ru.petroplus.pos.printerapi.Formatting
import ru.petroplus.pos.util.ext.leadingZeros
import ru.petroplus.pos.util.ext.roundTo

fun Long.formattingReceiptNumber() = this.leadingZeros(Formatting.RECEIPT_MASK_SIZE)

fun Long.toAmountString(): String {
    return (this / 100.0).roundTo(2)
}

fun Long.toCurrencyString(): String {
    return (this / 1000.0).roundTo(2)
}