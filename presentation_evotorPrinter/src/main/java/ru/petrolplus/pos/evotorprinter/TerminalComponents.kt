package ru.petrolplus.pos.evotorprinter

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petrolplus.pos.evotorprinter.GeneralComponents.textJustify
import ru.petrolplus.pos.printerapi.IntroductoryConstruction
import ru.petrolplus.pos.printerapi.ext.formattingForPrinter
import ru.petrolplus.pos.printerapi.ext.formattingTerminalId
import java.util.Date

object TerminalComponents {
    internal fun getTerminalDataWithDate(terminalId: Int, terminalDate: Date, paperWidth: Int): Array<IPrintable> =
        arrayOf(
            terminalDate.formattingForPrinter().split(" ").toTypedArray().textJustify(paperWidth),
            *getTerminalDate(terminalId, paperWidth),
        )

    internal fun getTerminalDate(terminalId: Int, paperWidth: Int): Array<out IPrintable> {
        val terminalIdFormatted = terminalId.formattingTerminalId()
        return arrayOf(
            arrayOf(IntroductoryConstruction.POS_NUMBER_EN, terminalIdFormatted).textJustify(paperWidth),
            arrayOf(IntroductoryConstruction.POS_NUMBER_RU, terminalIdFormatted).textJustify(paperWidth),
        )
    }
}
