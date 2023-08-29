package ru.petroplus.pos.evotorprinter

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petroplus.pos.evotorprinter.GeneralComponents.centredText
import ru.petroplus.pos.evotorprinter.GeneralComponents.divider
import ru.petroplus.pos.evotorprinter.GeneralComponents.operatorData
import ru.petroplus.pos.evotorprinter.GeneralComponents.organizationData
import ru.petroplus.pos.evotorprinter.GeneralComponents.shiftReportFootnote
import ru.petroplus.pos.evotorprinter.GeneralComponents.textJustify
import ru.petroplus.pos.evotorprinter.TerminalComponents.terminalDate
import ru.petroplus.pos.evotorprinter.ext.toUi
import ru.petroplus.pos.printerapi.IntroductoryConstruction
import ru.petroplus.pos.printerapi.OperationType
import ru.petroplus.pos.printerapi.ShiftStatistic
import ru.petroplus.pos.printerapi.StatisticByService
import ru.petroplus.pos.printerapi.ext.formattingForPrinter
import ru.petroplus.pos.printerapi.ext.toCurrencyString
import java.util.Date

object ShiftReportComponents {
    private var paperWidth: Int = 0
    fun generateShiftReport(statistic: ShiftStatistic, currentDate: Date, paperWidth: Int): PrinterDocument {
        this.paperWidth = paperWidth
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
        *statisticByOperation(OperationType.Debit, statistic.debit),
        *statisticByOperation(OperationType.Return.ToCard, statistic.returnToCard),
        *statisticByOperation(OperationType.Return.ToAccount, statistic.returnToAccount),
        *cardsProcessed(statistic),
    )

    private fun statisticByServices(statistic: List<StatisticByService>) =
        statistic.map { it.toUi(paperWidth) }.toTypedArray().flatten().toTypedArray()

    private fun statisticByOperation(operationType: OperationType, statistic: List<StatisticByService>) = arrayOf(
        centredText(operationType.name),
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