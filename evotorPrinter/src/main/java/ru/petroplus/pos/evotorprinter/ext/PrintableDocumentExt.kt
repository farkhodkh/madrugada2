package ru.petroplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.evotor.devices.commons.utils.Format
import ru.evotor.devices.commons.utils.PrintableDocumentItem
import ru.petroplus.pos.printerapi.DocumentData
import ru.petroplus.pos.printerapi.ResponseCodes
import ru.petroplus.pos.printerapi.Text
import ru.petroplus.pos.printerapi.ext.toAmountString
import ru.petroplus.pos.printerapi.ext.toCardType
import ru.petroplus.pos.printerapi.ext.toCurrencyString
import ru.petroplus.pos.printerapi.ext.toOperationType
import ru.petroplus.pos.printerapi.ext.toResponseText
import ru.petroplus.pos.util.ext.toTextDate

fun DocumentData.toPrinterDoc(): PrinterDocument {
    return when (this.transaction.responseCode) {
        ResponseCodes.SUCCESS -> generateDebitDocument(this)
        else -> TODO("Обработать другие коды ответов")
    }
}

fun generateDebitDocument(data: DocumentData): PrinterDocument {
    return with(data.transaction) {
        with(Text) {
            val orgName = data.commonSettings.organizationName
            val orgINN = data.commonSettings.organizationInn

            val serviceName = data.service.name
            val amountUnit = data.service.unit

            val amount = amount.toAmountString()
            val price = price.toCurrencyString()
            val sum = sum.toCurrencyString()

            PrinterDocument(
                text("$RECEIPT_NUMBER $receiptNumber"),
                centredText(orgName),
                centredText("$INN $orgINN"),
                centredText("<POS_NAME>"),
                divider,
                text(terminalDate.time.toTextDate()),
                text("$POS_NUMBER_EN $terminalId"),
                text("$POS_NUMBER_RU $terminalId"),
                divider,
                text("$CART ${cardType.toCardType()}: "),
                centredText(cardNumber),
                divider,
                centredText(operationType.toOperationType()),
                divider,
                text("$SERVICE     $serviceName"),
                text("$SERVICE_AMOUNT  $amountUnit   $amount"),
                text("$SERVICE_PRICE   $PRICE_UNIT   $price"),
                text("$SERVICE_SUM   $PRICE_UNIT   $sum"),
                divider,
                centredText(responseCode.toResponseText()),
                centredText(TRANSACTION_CONFIRMED_BY_PIN_PART_I),
                centredText(TRANSACTION_CONFIRMED_BY_PIN_PART_II),
                divider,
                text("$OPERATOR_NUMBER $operatorNumber"),
                divider,
                centredText(FOOTER_TEXT),
            )
        }
    }
}

private val divider = PrintableDocumentItem("-", Format.DIVIDER)
private fun centredText(text: String) = PrintableDocumentItem(text, Format.CENTER)
private fun text(text: String) = PrintableDocumentItem(text, Format.LEFT_WORD)

