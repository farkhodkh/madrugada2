package ru.petroplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.evotor.devices.commons.printer.printable.PrintableText
import ru.evotor.devices.commons.utils.Format
import ru.evotor.devices.commons.utils.PrintableDocumentItem
import ru.petrolplus.pos.persitence.dto.ReceiptDTO
import ru.petroplus.pos.printerapi.IntroductoryConstruction
import ru.petroplus.pos.printerapi.IntroductoryConstruction.FOOTNOTE_CURRENT_PRICE
import ru.petroplus.pos.printerapi.IntroductoryConstruction.DEBIT_CONFIRMED_BY_PIN
import ru.petroplus.pos.printerapi.IntroductoryConstruction.DENIAL
import ru.petroplus.pos.printerapi.IntroductoryConstruction.DENIAL_CODE
import ru.petroplus.pos.printerapi.IntroductoryConstruction.FOOTER_TEXT
import ru.petroplus.pos.printerapi.IntroductoryConstruction.OPERATION_CONFIRMED_BY_PIN
import ru.petroplus.pos.printerapi.IntroductoryConstruction.OPERATION_CONFIRMED_BY_TERMINAL
import ru.petroplus.pos.printerapi.IntroductoryConstruction.RECEIPT_NUMBER
import ru.petroplus.pos.printerapi.IntroductoryConstruction.RECEIPT_NUMBER_DENIAL
import ru.petroplus.pos.printerapi.IntroductoryConstruction.RETURN_CONFIRMED_BY_TERMINAL
import ru.petroplus.pos.printerapi.OperationType
import ru.petroplus.pos.printerapi.ResponseCode
import ru.petroplus.pos.printerapi.ext.formattingForPrinter
import ru.petroplus.pos.printerapi.ext.formattingReceiptNumber
import ru.petroplus.pos.printerapi.ext.formattingTerminalId
import ru.petroplus.pos.printerapi.ext.toAmountString
import ru.petroplus.pos.printerapi.ext.toCardType
import ru.petroplus.pos.printerapi.ext.toCurrencyString
import ru.petroplus.pos.printerapi.ext.toOperationType
import ru.petroplus.pos.printerapi.ext.toResponseCode
import ru.petroplus.pos.util.ext.justify
import ru.petroplus.pos.util.ext.lengthOfSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun ReceiptDTO.toPrinterDoc(paperWidth: Int): PrinterDocument =
    when (val responseCode = this.responseCode.toResponseCode()) {
        ResponseCode.Success -> generateSuccessfulTransactionDocument(responseCode, paperWidth)
        is ResponseCode.Error -> generateFailedTransactionDocument(responseCode, paperWidth)
    }

fun generateShiftReport(currentDate: Date, paperWidth: Int): PrinterDocument {
    val organizationName = "АНО НИИ ТАИ"
    val posName = "Компания 1"
    val organizationInn = "12300005555134"
    val terminalId = 23
    val operatorNumber = "4000000004"

    // TODO: удалить тестовые переменные для создания даты открытия смены после создания запроса к БД
    val sdf = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
    val startShiftDate: Date = sdf.parse("26/08/18 17:30:34")!!

    val startShift = startShiftDate.formattingForPrinter()
    val endShift = currentDate.formattingForPrinter()

    return PrinterDocument(
        centredText(IntroductoryConstruction.SHIFT_REPORT_TITLE),
        *organizationData(organizationName, posName, organizationInn),
        *shiftData(startShift, endShift, paperWidth),
        *terminalDate(terminalId, paperWidth = paperWidth),
        *operationsByPetrolPlus(),
        text("<Выручка (дебеты - возвраты)>"),  // TODO: добавить данные о выручке
        operatorData(operatorNumber, paperWidth)
    )
}

private fun operationsByPetrolPlus(): Array<out IPrintable> = arrayOf(
    divider,
    centredText(IntroductoryConstruction.CARDS_PETROL_PLUS_TITLE),
    divider,
    *debits(),
    *returnsToCard(),
    *returnsToAccount(),
    *cardsProcessed(),
    )

private fun debits(): Array<out IPrintable> = arrayOf(
    centredText(OperationType.Debit.name),
    divider,
    text("<Сводка по всем сервисам>"),   // TODO: добавить сводку по всем сервисам
    divider,
    shiftReportFootnote(),
    divider,
)

private fun returnsToCard(): Array<out IPrintable> = arrayOf(
    centredText(OperationType.Return.ToCard.name),
    divider,
    text("<Сводка по всем сервисам>"),  // TODO: добавить сводку по всем сервисам
    divider,
    shiftReportFootnote(),
    divider,
)

private fun returnsToAccount(): Array<out IPrintable> = arrayOf(
    centredText(OperationType.Return.ToAccount.name),
    divider,
    text("<Сводка по всем сервисам>"),  // TODO: добавить сводку по всем сервисам
    divider,
    shiftReportFootnote(),
    divider,
)

private fun cardsProcessed(): Array<out IPrintable> = arrayOf(
    centredText(IntroductoryConstruction.CARD_PROCESSED),
    divider,
    text("<Кол-во обработаных карт>"),  // TODO: добавить данные по кол-ву обработаных карт
    divider,
)

private fun shiftData(startData: String, endData: String, paperWidth: Int): Array<IPrintable> = arrayOf(
    textJustify(arrayOf(IntroductoryConstruction.SHIFT_START, startData), paperWidth),
    textJustify(arrayOf(IntroductoryConstruction.SHIFT_TIME, endData), paperWidth)
)

fun ReceiptDTO.generateFailedTransactionDocument(
    responseCode: ResponseCode, paperWidth: Int
) = PrinterDocument(
    *receiptData(RECEIPT_NUMBER_DENIAL, receiptNumber, paperWidth),
    *terminalDataWithDate(terminalId, terminalDate.time, paperWidth),
    *cardData(cardType, cardNumber),
    divider,
    *operationType.toOperationType().toUi(responseCode),
    divider,
    centredText(DENIAL),
    divider,
    text("$DENIAL_CODE: ${responseCode.code}"),
    divider,
    operatorData(operatorNumber, paperWidth),
)

fun ReceiptDTO.generateSuccessfulTransactionDocument(
    responseCode: ResponseCode, paperWidth: Int
): PrinterDocument {
    val operationType = operationType.toOperationType()
    return PrinterDocument(
        *receiptData(RECEIPT_NUMBER, receiptNumber, paperWidth),
        *organizationData(organizationName, posName, organizationInn),
        divider,
        *terminalDataWithDate(terminalId, terminalDate.time, paperWidth),
        divider,
        *cardData(cardType, cardNumber),
        divider,
        centredText(operationType.name),
        divider,
        *serviceTable(serviceName, serviceUnit, price, sum, amount, paperWidth),
        divider,
        *operationType.toUi(responseCode),
        divider,
        operatorData(operatorNumber, paperWidth),
        divider,
        centredText(FOOTER_TEXT)
    )
}

private fun shiftReportFootnote() = text("* - $FOOTNOTE_CURRENT_PRICE")

private fun OperationType.toUi(responseCode: ResponseCode): Array<IPrintable> {
    val descriptionsList = mutableListOf(centredText(responseCode.description))
    if (responseCode != ResponseCode.Success) return descriptionsList.toTypedArray()

    if (this == OperationType.Debit) {
        descriptionsList.add(centredText(OPERATION_CONFIRMED_BY_PIN))
        descriptionsList.add(centredText(DEBIT_CONFIRMED_BY_PIN))
    } else if (this is OperationType.Return) {
        descriptionsList.add(centredText(OPERATION_CONFIRMED_BY_TERMINAL))
        descriptionsList.add(centredText(RETURN_CONFIRMED_BY_TERMINAL))
    }
    return descriptionsList.toTypedArray()
}

private fun receiptData(title: String, receiptNumber: Long, paperWidth: Int) = arrayOf(
    textJustify(
        arrayOf(title, receiptNumber.formattingReceiptNumber()),
        paperWidth
    ),
)

private fun organizationData(orgName: String, posName: String, inn: String): Array<IPrintable> = arrayOf(
    centredText(orgName),
    centredText("${IntroductoryConstruction.INN} $inn"),
    centredText(posName),
)

private fun operatorData(operatorNumber: String, paperWidth: Int) =
    textJustify(arrayOf(IntroductoryConstruction.OPERATOR_NUMBER, operatorNumber), paperWidth)

private fun cardData(cardType: Int, cardNumber: String): Array<IPrintable> = arrayOf(
    text("${IntroductoryConstruction.CARD} ${cardType.toCardType().name}: "),
    text(cardNumber),
)

private fun terminalDataWithDate(terminalId: Int, terminalDate: Date, paperWidth: Int): Array<IPrintable> =
    arrayOf(
        textJustify(terminalDate.formattingForPrinter().split(" ").toTypedArray(), paperWidth),
        *terminalDate(terminalId, paperWidth)
    )

private fun terminalDate(terminalId: Int, paperWidth: Int): Array<out IPrintable> {
    val terminalIdFormatted = terminalId.formattingTerminalId()
    return arrayOf(
        textJustify(
            arrayOf(IntroductoryConstruction.POS_NUMBER_EN, terminalIdFormatted),
            paperWidth
        ),
        textJustify(
            arrayOf(IntroductoryConstruction.POS_NUMBER_RU, terminalIdFormatted),
            paperWidth
        ),
    )
}

private fun serviceTable(
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
private fun textJustify(data: Array<String>, paperWidth: Int) =
    PrintableText(data.justify(paperWidth))


private fun serviceLine(
    title: String, unit: String, value: String, rightSpaceSize: Int, paperWidth: Int
): PrintableText {
    val leftSpaceSize = paperWidth - title.length - unit.length - value.length - rightSpaceSize
    val leftSpace =
        " ".repeat(leftSpaceSize)

    val rightSpace = " ".repeat(rightSpaceSize)
    val line = "$title$leftSpace$unit$rightSpace$value"
    return PrintableText(line)
}

