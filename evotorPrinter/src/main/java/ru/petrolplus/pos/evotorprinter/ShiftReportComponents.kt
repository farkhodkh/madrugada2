package ru.petrolplus.pos.evotorprinter

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petrolplus.pos.evotorprinter.GeneralComponents.centredText
import ru.petrolplus.pos.evotorprinter.GeneralComponents.divider
import ru.petrolplus.pos.evotorprinter.GeneralComponents.operatorData
import ru.petrolplus.pos.evotorprinter.GeneralComponents.organizationData
import ru.petrolplus.pos.evotorprinter.GeneralComponents.shiftReportFootnote
import ru.petrolplus.pos.evotorprinter.GeneralComponents.textJustify
import ru.petrolplus.pos.evotorprinter.TerminalComponents.terminalDate
import ru.petrolplus.pos.evotorprinter.ext.toUi
import ru.petrolplus.pos.persitence.dto.ServiceTotalDTO
import ru.petrolplus.pos.persitence.dto.ShiftReceiptDTO
import ru.petrolplus.pos.persitence.enum.OperationType
import ru.petrolplus.pos.printerapi.IntroductoryConstruction
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.CURRENT_PRICE_UNIT
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.DEBIT
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.REFUND
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.TOTAL
import ru.petrolplus.pos.printerapi.ext.formattingForPrinter
import ru.petrolplus.pos.printerapi.ext.toCurrencyString
import java.util.Date

object ShiftReportComponents {
    private var paperWidth: Int = 0
    fun generateShiftReport(receipt: ShiftReceiptDTO, currentDate: Date, paperWidth: Int): PrinterDocument {
        ShiftReportComponents.paperWidth = paperWidth

        return PrinterDocument(
            centredText(IntroductoryConstruction.SHIFT_REPORT_TITLE),
            *organizationData(
                receipt.organizationName,
                receipt.posName,
                receipt.organizationInn
            ),
            *shiftData(receipt.currentShiftStart.time, currentDate),
            *terminalDate(receipt.terminalId, paperWidth),
            *operationsByPetrolPlus(receipt),
            operatorData(receipt.operatorNumb.toString(), paperWidth)
        )
    }
    private fun operationsByPetrolPlus(statistic: ShiftReceiptDTO): Array<out IPrintable> = arrayOf(
        divider,
        centredText(IntroductoryConstruction.CARDS_PETROL_PLUS_TITLE),
        divider,
        *statisticByOperation(OperationType.DEBIT, statistic.debits),
        *statisticByOperation(OperationType.CARD_REFUND, statistic.cardRefunds),
        *statisticByOperation(OperationType.ACCOUNT_REFUND, statistic.accountRefunds),
        *cardsProcessed(statistic),
    )

    private fun statisticByServices(statistic: List<ServiceTotalDTO>) =
        statistic.map { it.toUi(paperWidth) }.toTypedArray().flatten().toTypedArray()

    private fun statisticByOperation(operationType: OperationType, statistic: List<ServiceTotalDTO>) = arrayOf(
        centredText(operationType.description),
        divider,
        *statisticByServices(statistic),
        shiftReportFootnote(),
        divider,
    )

    private fun cardsProcessed(statistic: ShiftReceiptDTO) = arrayOf(
        centredText(IntroductoryConstruction.CARD_PROCESSED),
        divider,
        textJustify(arrayOf(DEBIT, statistic.totalDebitsOperations.toString()), paperWidth),
        textJustify(arrayOf(REFUND, statistic.totalRefundsOperations.toString()), paperWidth),
        divider,
        textJustify(arrayOf(TOTAL, statistic.totalOperations.toString()), paperWidth),
        textJustify(
            arrayOf(TOTAL, CURRENT_PRICE_UNIT, statistic.totalSum.toCurrencyString()),
            paperWidth,
            offset = 1
        ),
        divider,
    )

    private fun shiftData(startData: Date, endData: Date) = arrayOf(
        textJustify(
            arrayOf(IntroductoryConstruction.SHIFT_START, startData.formattingForPrinter()),
            paperWidth
        ),
        textJustify(
            arrayOf(IntroductoryConstruction.SHIFT_TIME, endData.formattingForPrinter()),
            paperWidth
        )
    )
}