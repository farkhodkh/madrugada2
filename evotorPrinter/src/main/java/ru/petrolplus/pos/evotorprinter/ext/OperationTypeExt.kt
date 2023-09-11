package ru.petrolplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petrolplus.pos.evotorprinter.GeneralComponents.centredText
import ru.petrolplus.pos.persitence.enum.OperationType
import ru.petrolplus.pos.printerapi.IntroductoryConstruction

internal fun OperationType.toUi(): Array<IPrintable> = when (this) {
    OperationType.DEBIT -> confirmedByPINUi()
    else -> confirmedByTerminalUi()
}

private fun confirmedByPINUi(): Array<IPrintable> = arrayOf(
    centredText(IntroductoryConstruction.OPERATION_CONFIRMED_BY_PIN),
    centredText(IntroductoryConstruction.DEBIT_CONFIRMED_BY_PIN)
)

private fun confirmedByTerminalUi(): Array<IPrintable> = arrayOf(
    centredText(IntroductoryConstruction.OPERATION_CONFIRMED_BY_TERMINAL),
    centredText(IntroductoryConstruction.RETURN_CONFIRMED_BY_TERMINAL)
)