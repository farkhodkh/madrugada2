package ru.petroplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.evotor.devices.commons.utils.Format
import ru.evotor.devices.commons.utils.PrintableAdvancedItem
import ru.evotor.devices.commons.utils.PrintableDocumentItem
import ru.petroplus.pos.printerapi.DocumentData
import ru.petroplus.pos.printerapi.IntroductoryConstruction
import ru.petroplus.pos.printerapi.ResponseCode
import ru.petroplus.pos.printerapi.ext.toAmountString
import ru.petroplus.pos.printerapi.ext.toCardType
import ru.petroplus.pos.printerapi.ext.toCurrencyString
import ru.petroplus.pos.printerapi.ext.toOperationType
import ru.petroplus.pos.printerapi.ext.toResponseCode
import ru.petroplus.pos.util.ext.toTextDate

fun DocumentData.toPrinterDoc(): PrinterDocument =
    when (val responseCode = this.transaction.responseCode.toResponseCode()) {
        ResponseCode.Success -> generateSuccessfulTransactionDocument(this, responseCode)
        ResponseCode.Canceled,
        ResponseCode.ServiceProhibited,
        ResponseCode.Timeout -> generateFailedTransactionDocument(this, responseCode)
    }

fun generateFailedTransactionDocument(data: DocumentData, responseCode: ResponseCode): PrinterDocument {
    return with(data.transaction) {
        with(IntroductoryConstruction) {
            PrinterDocument(
                text("$RECEIPT_NUMBER_DENIAL $receiptNumber"),
                text(terminalDate.time.toTextDate()),
                text("$POS_NUMBER_EN $terminalId"),
                text("$POS_NUMBER_RU $terminalId"),
                text("$CART ${cardType.toCardType()}: "),
                text(cardNumber),
                divider,
                centredText(responseCode.description),
                divider,
                centredText(DENIAL),
                divider,
                centredText("$DENIAL_CODE: ${responseCode.code}"),
                divider,
                text("$OPERATOR_NUMBER $operatorNumber"),
            )
        }
    }
}

// TODO: функция, высчитвающая размер пробелов
// TODO: избавится от разрозненного запроса данных о транзакции
// TODO: пофиксить проблему с слушателем окончания события
// TODO: в чек добавили поле POS_NAME
fun generateSuccessfulTransactionDocument(data: DocumentData, responseCode: ResponseCode): PrinterDocument {
    return with(data.transaction) {
        with(IntroductoryConstruction) {
            val orgName = data.commonSettings.organizationName
            val orgINN = data.commonSettings.organizationInn

            val serviceName = data.service?.name ?: "-"
            val amountUnit = data.service?.unit ?: "-"

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
                PrintableAdvancedItem("$SERVICE $serviceName", Format.LEFT_LINE),
                text("$SERVICE_AMOUNT  $amountUnit   $amount"),
                text("$SERVICE_PRICE   $PRICE_UNIT   $price"),
                text("$SERVICE_SUM   $PRICE_UNIT   $sum"),
                divider,
                centredText(responseCode.description),
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

