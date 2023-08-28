package ru.petroplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.petrolplus.pos.persitence.dto.ReceiptDTO
import ru.petroplus.pos.evotorprinter.GeneralComponents.cardData
import ru.petroplus.pos.evotorprinter.GeneralComponents.centredText
import ru.petroplus.pos.evotorprinter.GeneralComponents.divider
import ru.petroplus.pos.evotorprinter.GeneralComponents.operatorData
import ru.petroplus.pos.evotorprinter.GeneralComponents.organizationData
import ru.petroplus.pos.evotorprinter.GeneralComponents.receiptData
import ru.petroplus.pos.evotorprinter.GeneralComponents.text
import ru.petroplus.pos.evotorprinter.ServiceComponents.serviceTable
import ru.petroplus.pos.evotorprinter.TerminalComponents.terminalDataWithDate
import ru.petroplus.pos.printerapi.IntroductoryConstruction.DENIAL
import ru.petroplus.pos.printerapi.IntroductoryConstruction.DENIAL_CODE
import ru.petroplus.pos.printerapi.IntroductoryConstruction.FOOTER_TEXT
import ru.petroplus.pos.printerapi.IntroductoryConstruction.RECEIPT_NUMBER
import ru.petroplus.pos.printerapi.IntroductoryConstruction.RECEIPT_NUMBER_DENIAL
import ru.petroplus.pos.printerapi.ResponseCode
import ru.petroplus.pos.printerapi.ext.toOperationType
import ru.petroplus.pos.printerapi.ext.toResponseCode

fun ReceiptDTO.toPrinterDoc(paperWidth: Int): PrinterDocument =
    when (val responseCode = responseCode.toResponseCode()) {
        ResponseCode.Success -> generateSuccessfulTransactionDocument(responseCode, paperWidth)
        is ResponseCode.Error -> generateFailedTransactionDocument(responseCode, paperWidth)
    }

private fun ReceiptDTO.generateFailedTransactionDocument(
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

private fun ReceiptDTO.generateSuccessfulTransactionDocument(
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