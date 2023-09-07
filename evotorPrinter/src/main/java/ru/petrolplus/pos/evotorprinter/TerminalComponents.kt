package ru.petrolplus.pos.evotorprinter

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petrolplus.pos.evotorprinter.GeneralComponents.textJustify
import ru.petrolplus.pos.printerapi.IntroductoryConstruction
import ru.petrolplus.pos.printerapi.ext.formattingForPrinter
import ru.petrolplus.pos.printerapi.ext.formattingTerminalId
import java.util.Date

object TerminalComponents {
    internal fun terminalDataWithDate(terminalId: Int, terminalDate: Date, paperWidth: Int): Array<IPrintable> =
        arrayOf(
            textJustify(terminalDate.formattingForPrinter().split(" ").toTypedArray(), paperWidth),
            *terminalDate(terminalId, paperWidth)
        )

    internal fun terminalDate(terminalId: Int, paperWidth: Int): Array<out IPrintable> {
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
}