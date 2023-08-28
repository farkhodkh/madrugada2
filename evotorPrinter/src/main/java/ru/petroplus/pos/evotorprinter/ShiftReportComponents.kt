package ru.petroplus.pos.evotorprinter

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petroplus.pos.evotorprinter.GeneralComponents.centredText
import ru.petroplus.pos.evotorprinter.GeneralComponents.divider
import ru.petroplus.pos.evotorprinter.GeneralComponents.operatorData
import ru.petroplus.pos.evotorprinter.GeneralComponents.organizationData
import ru.petroplus.pos.evotorprinter.GeneralComponents.shiftReportFootnote
import ru.petroplus.pos.evotorprinter.GeneralComponents.text
import ru.petroplus.pos.evotorprinter.GeneralComponents.textJustify
import ru.petroplus.pos.evotorprinter.TerminalComponents.terminalDate
import ru.petroplus.pos.printerapi.IntroductoryConstruction
import ru.petroplus.pos.printerapi.OperationType
import ru.petroplus.pos.printerapi.ext.formattingForPrinter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ShiftReportComponents {
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

}