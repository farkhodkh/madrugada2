package ru.petroplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.evotor.devices.commons.printer.printable.PrintableText
import ru.evotor.devices.commons.utils.Format
import ru.evotor.devices.commons.utils.PrintableDocumentItem
import ru.petrolplus.pos.persitence.dto.ReceiptDTO
import ru.petroplus.pos.printerapi.IntroductoryConstruction
import ru.petroplus.pos.printerapi.IntroductoryConstruction.DENIAL
import ru.petroplus.pos.printerapi.IntroductoryConstruction.DENIAL_CODE
import ru.petroplus.pos.printerapi.IntroductoryConstruction.FOOTER_TEXT
import ru.petroplus.pos.printerapi.IntroductoryConstruction.RECEIPT_NUMBER
import ru.petroplus.pos.printerapi.IntroductoryConstruction.RECEIPT_NUMBER_DENIAL
import ru.petroplus.pos.printerapi.IntroductoryConstruction.OPERATION_CONFIRMED
import ru.petroplus.pos.printerapi.IntroductoryConstruction.DEBIT_CONFIRMED_BY_PIN
import ru.petroplus.pos.printerapi.IntroductoryConstruction.RETURN_CONFIRMED_BY_TERMINAL
import ru.petroplus.pos.printerapi.OperationType
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

fun ReceiptDTO.toPrinterDoc(paperWidth: Int): PrinterDocument =
    when (val responseCode = this.responseCode.toResponseCode()) {
        ResponseCode.Success -> generateSuccessfulTransactionDocument(responseCode, paperWidth)
        is ResponseCode.Error -> generateFailedTransactionDocument(responseCode, paperWidth)
    }

fun ReceiptDTO.generateFailedTransactionDocument(
    responseCode: ResponseCode, paperWidth: Int
) = PrinterDocument(
    *receiptData(RECEIPT_NUMBER_DENIAL, receiptNumber, paperWidth),
    *terminalData(terminalId, terminalDate, paperWidth),
    *cardData(cardType, cardNumber),
    divider,
    *operationDescription(responseCode, operationType.toOperationType()),
    divider,
    centredText(DENIAL),
    divider,
    text("$DENIAL_CODE: ${responseCode.code}"),
    divider,
    *operatorData(operatorNumber, paperWidth),
)

fun ReceiptDTO.generateSuccessfulTransactionDocument(
    responseCode: ResponseCode, paperWidth: Int
): PrinterDocument {
    val operationType = operationType.toOperationType()
    return PrinterDocument(
        *receiptData(RECEIPT_NUMBER, receiptNumber, paperWidth),
        *orgData(organizationName, posName, organizationInn),
        divider,
        *terminalData(terminalId, terminalDate, paperWidth),
        divider,
        *cardData(cardType, cardNumber),
        divider,
        centredText(operationType.name),
        divider,
        *serviceTable(serviceName, serviceUnit, price, sum, amount, paperWidth),
        divider,
        *operationDescription(responseCode, operationType),
        divider,
        *operatorData(operatorNumber, paperWidth),
        divider,
        centredText(FOOTER_TEXT)
    )
}

fun operationDescription(
    responseCode: ResponseCode, operationType: OperationType
): Array<IPrintable> {
    val descriptionsList = mutableListOf(centredText(responseCode.description))

    if (responseCode == ResponseCode.Success) {
        when (operationType) {
            OperationType.Debit -> {
                descriptionsList.add(centredText(OPERATION_CONFIRMED))
                descriptionsList.add(centredText(DEBIT_CONFIRMED_BY_PIN))
            }

            is OperationType.Return -> {
                descriptionsList.add(centredText(OPERATION_CONFIRMED))
                descriptionsList.add(centredText(RETURN_CONFIRMED_BY_TERMINAL))
            }

            else -> {}
        }
    }

    return descriptionsList.toTypedArray()
}

fun receiptData(title: String, receiptNumber: Long, printerWidth: Int) = arrayOf(
    textJustify(
        arrayOf(title, receiptNumber.leadingZeros(RECEIPT_MASK_SIZE)),
        printerWidth
    ),
)

fun orgData(orgName: String, posName: String, inn: String): Array<IPrintable> = arrayOf(
    centredText(orgName),
    centredText("${IntroductoryConstruction.INN} $inn"),
    centredText(posName),
)


fun operatorData(operatorNumber: String, printerWidth: Int) = arrayOf(
    textJustify(arrayOf(IntroductoryConstruction.OPERATOR_NUMBER, operatorNumber), printerWidth),
)

fun cardData(cardType: Int, cardNumber: String): Array<IPrintable> = arrayOf(
    text("${IntroductoryConstruction.CARD} ${cardType.toCardType().name}: "),
    text(cardNumber),
)

fun terminalData(terminalId: Int, terminalDate: Calendar, printerWidth: Int): Array<IPrintable> =
    arrayOf(
        textJustify(terminalDate.time.toTextDate().split(" ").toTypedArray(), printerWidth),
        textJustify(
            arrayOf(
                IntroductoryConstruction.POS_NUMBER_EN,
                terminalId.leadingZeros(TERMINAL_NUMBER_MASK_SIZE)
            ), printerWidth
        ),
        textJustify(
            arrayOf(
                IntroductoryConstruction.POS_NUMBER_RU,
                terminalId.leadingZeros(TERMINAL_NUMBER_MASK_SIZE)
            ), printerWidth
        ),
    )

fun serviceTable(
    serviceName: String,
    serviceUnit: String,
    servicePrice: Long,
    sum: Long,
    amount: Long,
    paperWidth: Int
): Array<IPrintable> {
    val sumStr = sum.toCurrencyString()
    val amountStr = amount.toAmountString()
    val priceStr = servicePrice.toCurrencyString()

    val spaceOccupied: Int = arrayOf(
        IntroductoryConstruction.SERVICE_SUM, IntroductoryConstruction.PRICE_UNIT, sumStr
    ).lengthOfSymbols()

    // Кол-во "свободного" места при самой длинной строке
    val freeSpace = paperWidth - spaceOccupied
    val rightSpace = freeSpace / 2

    return arrayOf(
        textJustify(arrayOf(IntroductoryConstruction.SERVICE, serviceName), paperWidth),
        serviceLine(
            IntroductoryConstruction.SERVICE_AMOUNT,
            serviceUnit,
            amountStr,
            rightSpace + (sumStr.length - amountStr.length),
            paperWidth
        ),
        serviceLine(
            IntroductoryConstruction.SERVICE_PRICE,
            IntroductoryConstruction.PRICE_UNIT,
            priceStr,
            rightSpace + (sumStr.length - priceStr.length),
            paperWidth
        ),
        serviceLine(
            IntroductoryConstruction.SERVICE_SUM,
            IntroductoryConstruction.PRICE_UNIT,
            sumStr,
            rightSpace,
            paperWidth
        )
    )
}

private val divider = PrintableDocumentItem("-", Format.DIVIDER)
private fun text(text: String) = PrintableDocumentItem(text, Format.LEFT_WORD)
private fun centredText(text: String) = PrintableDocumentItem(text, Format.CENTER)
private fun textJustify(data: Array<String>, printerWidth: Int) =
    PrintableText(data.justify(printerWidth))


fun serviceLine(
    title: String, unit: String, value: String, rightSpaceSize: Int, paperWidth: Int
): PrintableText {
    val leftSpaceSize = paperWidth - title.length - unit.length - value.length - rightSpaceSize
    val leftSpace =
        " ".repeat(leftSpaceSize)

    val rightSpace = " ".repeat(rightSpaceSize)
    val line = "$title$leftSpace$unit$rightSpace$value"
    return PrintableText(line)
}

