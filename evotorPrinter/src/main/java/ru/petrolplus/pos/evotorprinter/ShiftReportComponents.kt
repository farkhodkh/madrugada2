package ru.petrolplus.pos.evotorprinter

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petrolplus.pos.evotorprinter.GeneralComponents.centredText
import ru.petrolplus.pos.evotorprinter.GeneralComponents.getShiftReportFootnote
import ru.petrolplus.pos.evotorprinter.GeneralComponents.textJustify
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
    internal fun getOperationsByPetrolPlus(
        statistic: ShiftReceiptDTO, divider: IPrintable, paperWidth: Int
    ): Array<out IPrintable> = arrayOf(
        divider,
        centredText(IntroductoryConstruction.CARDS_PETROL_PLUS_TITLE),
        divider,
        *getStatisticByOperation(OperationType.DEBIT, statistic.debits, divider, paperWidth),
        *getStatisticByOperation(OperationType.CARD_REFUND, statistic.cardRefunds, divider, paperWidth),
        *getStatisticByOperation(OperationType.ACCOUNT_REFUND, statistic.accountRefunds, divider, paperWidth),
        *getCardsProcessedData(statistic, divider, paperWidth),
    )

    private fun getStatisticByServices(statistic: List<ServiceTotalDTO>, paperWidth: Int) =
        statistic.map { it.toUi(paperWidth) }.toTypedArray().flatten().toTypedArray()

    private fun getStatisticByOperation(
        operationType: OperationType,
        statistic: List<ServiceTotalDTO>,
        divider: IPrintable,
        paperWidth: Int
    ) = arrayOf(
        centredText(operationType.description),
        divider,
        *getStatisticByServices(statistic, paperWidth),
        getShiftReportFootnote(),
        divider,
    )

    private fun getCardsProcessedData(statistic: ShiftReceiptDTO, divider: IPrintable, paperWidth: Int) =
        arrayOf(
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

    internal fun getShiftData(startData: Date, endData: Date, paperWidth: Int) = arrayOf(
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