package ru.petrolplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petrolplus.pos.persitence.dto.ReceiptDTO
import ru.petrolplus.pos.evotorprinter.GeneralComponents.getCardData
import ru.petrolplus.pos.evotorprinter.GeneralComponents.centredText
import ru.petrolplus.pos.evotorprinter.GeneralComponents.dashDivider
import ru.petrolplus.pos.evotorprinter.GeneralComponents.getOperatorData
import ru.petrolplus.pos.evotorprinter.GeneralComponents.getOrganizationData
import ru.petrolplus.pos.evotorprinter.GeneralComponents.getReceiptData
import ru.petrolplus.pos.evotorprinter.GeneralComponents.text
import ru.petrolplus.pos.evotorprinter.ServiceComponents.getServiceTable
import ru.petrolplus.pos.evotorprinter.TerminalComponents.getTerminalDataWithDate
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.DENIAL
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.DENIAL_CODE
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.FOOTER_TEXT
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.RECEIPT_NUMBER
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.RECEIPT_NUMBER_DENIAL
import ru.petrolplus.pos.printerapi.ResponseCode
import ru.petrolplus.pos.printerapi.ext.toOperationType
import ru.petrolplus.pos.printerapi.ext.toResponseCode

fun ReceiptDTO.toPrinterDoc(paperWidth: Int): PrinterDocument =
    when (val responseCode = responseCode.toResponseCode()) {
        ResponseCode.Success -> generateSuccessfulTransactionDocument(responseCode, paperWidth, dashDivider)
        is ResponseCode.Error -> generateFailedTransactionDocument(responseCode, paperWidth, dashDivider)
    }

private fun ReceiptDTO.generateFailedTransactionDocument(
    responseCode: ResponseCode, paperWidth: Int, divider: IPrintable
) = PrinterDocument(
    *getReceiptData(RECEIPT_NUMBER_DENIAL, receiptNumber, paperWidth),
    *getTerminalDataWithDate(terminalId, terminalDate.time, paperWidth),
    *getCardData(cardType, cardNumber),
    divider,
    responseCode.toUi(),
    divider,
    centredText(DENIAL),
    divider,
    text("$DENIAL_CODE: ${responseCode.code}"),
    divider,
    getOperatorData(operatorNumber, paperWidth),
)

private fun ReceiptDTO.generateSuccessfulTransactionDocument(
    responseCode: ResponseCode, paperWidth: Int, divider: IPrintable
): PrinterDocument {
    val operationType = operationType.toOperationType()
    return PrinterDocument(
        *getReceiptData(RECEIPT_NUMBER, receiptNumber, paperWidth),
        *getOrganizationData(organizationName, posName, organizationInn),
        divider,
        *getTerminalDataWithDate(terminalId, terminalDate.time, paperWidth),
        divider,
        *getCardData(cardType, cardNumber),
        divider,
        centredText(operationType.description),
        divider,
        *getServiceTable(serviceName, serviceUnit, price, sum, amount, paperWidth),
        divider,
        responseCode.toUi(),
        *operationType.toUi(),
        divider,
        getOperatorData(operatorNumber, paperWidth),
        divider,
        centredText(FOOTER_TEXT)
    )
}