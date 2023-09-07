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
import ru.petrolplus.pos.persitence.enum.OperationType
import ru.petrolplus.pos.printerapi.IntroductoryConstruction
import ru.petrolplus.pos.printerapi.StatisticByService
import ru.petrolplus.pos.printerapi.ext.formattingForPrinter
import ru.petrolplus.pos.printerapi.ext.toCurrencyString
import ru.petrolplus.pos.printerapi.ShiftStatistic
import java.util.Date

object ShiftReportComponents {
    private var paperWidth: Int = 0
    fun generateShiftReport(statistic: ShiftStatistic, currentDate: Date, paperWidth: Int): PrinterDocument {
        ShiftReportComponents.paperWidth = paperWidth
        val commonSettings = statistic.commonSettings

        return PrinterDocument(
            centredText(IntroductoryConstruction.SHIFT_REPORT_TITLE),
            *organizationData(
                commonSettings.organizationName,
                commonSettings.posName,
                commonSettings.organizationInn
            ),
            *shiftData(statistic.shiftStarted, currentDate),
            *terminalDate(statistic.terminalId, paperWidth),
            *operationsByPetrolPlus(statistic),
            operatorData(statistic.operatorNumber.toString(), paperWidth)
        )
    }
    private fun operationsByPetrolPlus(statistic: ShiftStatistic): Array<out IPrintable> = arrayOf(
        divider,
        centredText(IntroductoryConstruction.CARDS_PETROL_PLUS_TITLE),
        divider,
        *statisticByOperation(OperationType.DEBIT, statistic.debit),
        *statisticByOperation(OperationType.CARD_REFUND, statistic.returnToCard),
        *statisticByOperation(OperationType.ACCOUNT_REFUND, statistic.returnToAccount),
        *cardsProcessed(statistic),
    )

    private fun statisticByServices(statistic: List<StatisticByService>) =
        statistic.map { it.toUi(paperWidth) }.toTypedArray().flatten().toTypedArray()

    private fun statisticByOperation(operationType: OperationType, statistic: List<StatisticByService>) = arrayOf(
        centredText(operationType.description),
        divider,
        *statisticByServices(statistic),
        shiftReportFootnote(),
        divider,
    )

    private fun cardsProcessed(statistic: ShiftStatistic): Array<out IPrintable> {
        val countOfOperations = (statistic.countOfDebit + statistic.countOfReturn).toString()
        val total = IntroductoryConstruction.TOTAL
        val debitOperation = IntroductoryConstruction.DEBIT
        val returnOperation = IntroductoryConstruction.RETURN
        val priceUnit = IntroductoryConstruction.CURRENT_PRICE_UNIT

        return arrayOf(
            centredText(IntroductoryConstruction.CARD_PROCESSED),
            divider,
            textJustify(arrayOf(debitOperation, statistic.countOfDebit.toString()), paperWidth),
            textJustify(arrayOf(returnOperation, statistic.countOfReturn.toString()), paperWidth),
            divider,
            textJustify(arrayOf(total, countOfOperations), paperWidth),
            textJustify(
                arrayOf(total, priceUnit, statistic.sumByAllOperations.toCurrencyString()),
                paperWidth,
                offset = 1
            ),
            divider,
        )
    }

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