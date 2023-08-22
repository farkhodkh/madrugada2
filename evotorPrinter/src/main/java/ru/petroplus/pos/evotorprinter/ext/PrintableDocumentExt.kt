package ru.petroplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.evotor.devices.commons.printer.printable.PrintableText
import ru.evotor.devices.commons.utils.Format
import ru.evotor.devices.commons.utils.PrintableDocumentItem
import ru.petrolplus.pos.persitence.entities.CommonSettingsDTO
import ru.petrolplus.pos.persitence.entities.ServiceDTO
import ru.petroplus.pos.printerapi.DocumentData
import ru.petroplus.pos.printerapi.IntroductoryConstruction
import ru.petroplus.pos.printerapi.ReceiptFormatting.RECEIPT_MASK_SIZE
import ru.petroplus.pos.printerapi.ReceiptFormatting.TERMINAL_NUMBER_MASK_SIZE
import ru.petroplus.pos.printerapi.ResponseCode
import ru.petroplus.pos.printerapi.ext.toAmountString
import ru.petroplus.pos.printerapi.ext.toCardType
import ru.petroplus.pos.printerapi.ext.toCurrencyString
import ru.petroplus.pos.printerapi.ext.toOperationType
import ru.petroplus.pos.printerapi.ext.toResponseCode
import ru.petroplus.pos.util.ext.justify
import ru.petroplus.pos.util.ext.leadingZeros
import ru.petroplus.pos.util.ext.lengthOfSymbols
import ru.petroplus.pos.util.ext.toTextDate
import java.util.Calendar

fun DocumentData.toPrinterDoc(printerWidth: Int): PrinterDocument =
    when (val responseCode = this.transaction.responseCode.toResponseCode()) {
        ResponseCode.Success -> generateSuccessfulTransactionDocument(
            this, responseCode, printerWidth
        )

        ResponseCode.Canceled, ResponseCode.ServiceProhibited, ResponseCode.Timeout -> generateFailedTransactionDocument(
            this, responseCode, printerWidth
        )
    }

fun generateFailedTransactionDocument(
    data: DocumentData, responseCode: ResponseCode, printerWidth: Int
): PrinterDocument {
    return with(data.transaction) {
        with(IntroductoryConstruction) {
            PrinterDocument(
                *receiptData(RECEIPT_NUMBER_DENIAL, receiptNumber, printerWidth),
                *terminalData(terminalId, terminalDate, printerWidth),
                *cardData(cardType, cardNumber),
                divider,
                centredText(responseCode.description),
                divider,
                centredText(DENIAL),
                divider,
                text("$DENIAL_CODE: ${responseCode.code}"),
                divider,
                *operatorData(operatorNumber, printerWidth),
            )
        }
    }
}

fun generateSuccessfulTransactionDocument(
    data: DocumentData, responseCode: ResponseCode, printerWidth: Int
): PrinterDocument {
    return with(data.transaction) {
        with(IntroductoryConstruction) {
            PrinterDocument(
                *receiptData(RECEIPT_NUMBER, receiptNumber, printerWidth),
                *orgData(data.commonSettings),
                divider,
                *terminalData(terminalId, terminalDate, printerWidth),
                divider,
                *cardData(cardType, cardNumber),
                divider,
                centredText(operationType.toOperationType()),
                divider,
                *serviceTable(data.service, sum, amount, printerWidth),
                divider,
                centredText(responseCode.description),
                centredText(TRANSACTION_CONFIRMED_BY_PIN_PART_I),
                centredText(TRANSACTION_CONFIRMED_BY_PIN_PART_II),
                divider,
                *operatorData(operatorNumber, printerWidth),
                divider,
                centredText(FOOTER_TEXT),
            )
        }
    }
}

fun receiptData(title: String, receiptNumber: Long, printerWidth: Int) = arrayOf(
    textJustify(
        arrayOf(title, receiptNumber.leadingZeros(RECEIPT_MASK_SIZE)),
        printerWidth
    ),
)

fun orgData(settings: CommonSettingsDTO): Array<IPrintable> = arrayOf(
    centredText(settings.organizationName),
    centredText("${IntroductoryConstruction.INN} ${settings.organizationName}"),
    centredText("<POS_NAME>"),
)


fun operatorData(operatorNumber: String, printerWidth: Int) = arrayOf(
    textJustify(arrayOf(IntroductoryConstruction.OPERATOR_NUMBER, operatorNumber), printerWidth),
)

fun cardData(cardType: Int, cardNumber: String): Array<IPrintable> = arrayOf(
    text("${IntroductoryConstruction.CART} ${cardType.toCardType()}: "),
    text(cardNumber),
)

fun terminalData(terminalId: Int, terminalDate: Calendar, printerWidth: Int): Array<IPrintable> =
    arrayOf(
        textJustify(terminalDate.time.toTextDate().split(" ").toTypedArray(), printerWidth),
        textJustify(arrayOf(IntroductoryConstruction.POS_NUMBER_EN, terminalId.leadingZeros(TERMINAL_NUMBER_MASK_SIZE)), printerWidth),
        textJustify(arrayOf(IntroductoryConstruction.POS_NUMBER_RU, terminalId.leadingZeros(TERMINAL_NUMBER_MASK_SIZE)), printerWidth),
    )

fun serviceTable(
    service: ServiceDTO, sum: Long, amount: Long, printerWidth: Int
): Array<IPrintable> {
    val sumStr = sum.toCurrencyString()
    val amountStr = amount.toAmountString()
    val priceStr = service.price.toCurrencyString()

    val spaceOccupied: Int = arrayOf(
        IntroductoryConstruction.SERVICE_SUM, IntroductoryConstruction.PRICE_UNIT, sumStr
    ).lengthOfSymbols()

    // Кол-во "свободного" места при самой длинной строке
    val freeSpace = printerWidth - spaceOccupied
    val rightSpace = freeSpace / 2

    return arrayOf(
        textJustify(arrayOf(IntroductoryConstruction.SERVICE, service.name), printerWidth),
        serviceLine(
            IntroductoryConstruction.SERVICE_AMOUNT,
            service.unit,
            amountStr,
            rightSpace + (sumStr.length - amountStr.length),
            printerWidth
        ),
        serviceLine(
            IntroductoryConstruction.SERVICE_PRICE,
            IntroductoryConstruction.PRICE_UNIT,
            priceStr,
            rightSpace + (sumStr.length - priceStr.length),
            printerWidth
        ),
        serviceLine(
            IntroductoryConstruction.SERVICE_SUM,
            IntroductoryConstruction.PRICE_UNIT,
            sumStr,
            rightSpace,
            printerWidth
        )
    )
}

private val divider = PrintableDocumentItem("-", Format.DIVIDER)
private fun text(text: String) = PrintableDocumentItem(text, Format.LEFT_WORD)
private fun centredText(text: String) = PrintableDocumentItem(text, Format.CENTER)
fun textJustify(data: Array<String>, printerWidth: Int): IPrintable {
    return PrintableText(data.justify(printerWidth))
}

fun serviceLine(
    title: String, unit: String, value: String, rightSpaceSize: Int, printerWidth: Int
): PrintableText {
    val leftSpace =
        " ".repeat(printerWidth - title.length - unit.length - value.length - rightSpaceSize)
    val rightSpace = " ".repeat(rightSpaceSize)
    val builder = StringBuilder()
    builder.append(title, leftSpace, unit, rightSpace, value)
    return PrintableText(builder.toString())
}

